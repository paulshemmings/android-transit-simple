package com.razor.transit.activities;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import com.razor.transit.applications.TransitApplication;
import com.razor.transit.handlers.StopsHandler;
import com.razor.transit.helpers.LogHelper;
import com.razor.transit.viewmodels.IViewModel;
import com.razor.transit.viewmodels.StopViewModel;

import razor.android.transit.R;

public class TransitMapActivity extends 	BaseActivity 
								implements 	StopsHandler.OnStopsLoadListener,
											OnMarkerClickListener, 
											LocationSource, 
											LocationListener, 
											OnMapLongClickListener, 
											OnInfoWindowClickListener  {

	private static final String TAG = "TransitMapActivity";

	private GoogleMap googleMap = null;
	private OnLocationChangedListener locationListener;
	private LocationManager locationManager;
	private List<IViewModel> stopList = new ArrayList<IViewModel>();
	protected ProgressDialog progressDialog = null;

	/**
	 * Create
	 */

	@Override
	public void onCreate(Bundle savedInstanceState) {         
		super.onCreate(savedInstanceState);
		setContentView(R.layout.transit_map_layout);

		Criteria locationCriteria = new Criteria();
		locationCriteria.setAccuracy(Criteria.ACCURACY_FINE);
		this.locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		this.locationManager.requestLocationUpdates(locationManager.getBestProvider(locationCriteria, true), 1L, 2F, this);				
	}  
	
	/**
	 * load saved state
	 */
	
	private List<IViewModel> loadSavedModels(Bundle savedInstanceState) {
		List<IViewModel> savedModels = new ArrayList<IViewModel>();
        if (savedInstanceState != null) {

            Object[] stopListArrays
                    = (Object[]) savedInstanceState.getSerializable("stopListArray");

        	if (stopListArrays != null && stopListArrays.length > 0) {
        		LogHelper.w(TAG, "restoring %d events", stopListArrays.length);
        		for(Object stop: stopListArrays){
                    this.stopList.add((IViewModel) stop);
        		}	
        	}
        }		
        return savedModels;
	}
	
    /**
     * Save instance state when it goes to the preview screen
     * @see android.app.Activity#onSaveInstanceState(android.os.Bundle)
     */
    
    public void onSaveInstanceState(Bundle state) {
    	if (this.stopList != null) {
    		LogHelper.w(TAG, "saving %d stops", this.stopList.size());
    		state.putSerializable("stopListArray", this.stopList.toArray());
    	}
    	if (this.googleMap != null && this.googleMap.getMyLocation() != null) {
    		double currentLatitude = this.googleMap.getMyLocation().getLatitude();
    		double currentLongitude = this.googleMap.getMyLocation().getLongitude();
    		state.putSerializable("currentLatitude", currentLatitude);
    		state.putSerializable("currentLongitude", currentLongitude);
    	}
    }	

	/**
	 * pause location manager
	 * @see android.app.Activity#onPause()
	 */

	@Override
	public void onPause()
	{
		if(locationManager != null)
		{
			locationManager.removeUpdates(this);
		}
		super.onPause();
	}

	/**
	 * resume location manager
	 */

	@Override
	public void onResume()
	{
		super.onResume();

		if( initialiseMap()){
			if(locationManager != null)
			{
				this.googleMap.setMyLocationEnabled(true);
			}
		}
	}  
	
	/**
	 * set up the map if not already done so
	 */
	
	private boolean initialiseMap() {
	    if (this.googleMap == null) 
	    {
            this.googleMap = ((MapFragment) getFragmentManager()
                    .findFragmentById(R.id.transit_main_map)).getMap();
			
			if (this.googleMap != null) {
				this.googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);		
				this.googleMap.setMyLocationEnabled(true);	
				this.googleMap.setOnMarkerClickListener(this);		
				this.googleMap.setOnInfoWindowClickListener(this);
				this.googleMap.setOnMapLongClickListener(this);
				this.googleMap.setLocationSource(this);				
	        }
	    }
	    return this.googleMap!=null;
	}
	
	/**
	 * center to the markers
	 */
	
	private void centerToStops(){		
		
		if (this.stopList != null && this.stopList.size() > 0) {
			
			LogHelper.w(TAG, "zooming map to %d events", this.stopList.size());

			StopViewModel stopViewModel = null;
			double  minLatitude = 0; 
			double  maxLatitude = 0; 
			double  minLongitude = 0; 
			double  maxLongitude = 0;

			for (IViewModel vm:this.stopList) {
				if(stopViewModel==null){
					stopViewModel = (StopViewModel) vm;
					minLatitude = stopViewModel.getModel().getLocation().getLatitude();
					maxLatitude = stopViewModel.getModel().getLocation().getLatitude();
					minLongitude = stopViewModel.getModel().getLocation().getLongitude();
					maxLongitude = stopViewModel.getModel().getLocation().getLongitude();
				} else {
					StopViewModel event = (StopViewModel) vm;
					double latitude = event.getModel().getLocation().getLatitude();
					double longitude = event.getModel().getLocation().getLongitude();
					
					if(latitude < minLatitude) minLatitude = latitude;
					if(latitude > maxLatitude) maxLatitude = latitude;
					if(longitude < minLongitude) minLongitude = longitude;
					if(longitude > maxLongitude) maxLongitude = longitude;
				}
			}
			
			LatLng minLocation = new LatLng(minLatitude,minLongitude);
			LatLng maxLocation = new LatLng(maxLatitude,maxLongitude);
			LatLngBounds bounds = new LatLngBounds(minLocation,maxLocation);
			this.googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds,200));
		}else{
			LogHelper.e(TAG, "stop list was empty or null");
		}
	}

	/**
	 * center to current location (but only if current location is outside the currently viewed area)
	 */

	private void centerToLocation(){    	
		Location location = this.googleMap.getMyLocation();
		if (location != null) {
			LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
			LatLngBounds bounds = this.googleMap.getProjection().getVisibleRegion().latLngBounds;	
			if (!bounds.contains(currentLocation)) {
				// zoom to current location
				float currentZoom = this.googleMap.getCameraPosition().zoom;				
				float maximumZoom = TransitApplication.instance().getMaximumMapZoom();
				float targetZoom = currentZoom > maximumZoom ? maximumZoom : currentZoom;
				CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(currentLocation, targetZoom);
				this.googleMap.animateCamera(cameraUpdate, new GoogleMap.CancelableCallback(){
					public void onCancel() {
					}
					public void onFinish() {
						TransitMapActivity.this.requestEvents(false);
					}
					
				});
			}    	
		}        		
	}

	/**
	 * Load the events
	 */

	private void requestEvents(boolean includeImages){
		LogHelper.w(TAG, "requesting events from system");
		this.showProgressDialog(true);
		StopsHandler handler = new StopsHandler();
		LatLngBounds bounds = this.googleMap.getProjection().getVisibleRegion().latLngBounds;
		// handler.requestStops(this, includeImages, bounds);
	}

	/**
	 * On events loaded
	 */

	@Override
	public void onStopsLoaded(final List<IViewModel> eventItems) {
		if(eventItems!=null){
			LogHelper.w(TAG, "loaded %d new events", eventItems.size());
			this.runOnUiThread(new Runnable(){
				public void run() {
					TransitMapActivity.this.showProgressDialog(false);
					TransitMapActivity.this.loadEventsOntoMap(eventItems);
				}				
			});
		}
	}  	

	/**
	 * event load failed
	 */

	@Override
	public void onStopsLoadFailed(final String errorMessage) {
		this.runOnUiThread(new Runnable(){
			@Override
			public void run() {
				TransitMapActivity.this.showProgressDialog(false);
				Toast.makeText(TransitMapActivity.this, "failed to load list", 1000).show();
			}			
		});		
	}

	
	/**
	 * load events onto map
	 */
	
	private void loadEventsOntoMap(List<IViewModel> eventItems){
		// clear the events
		this.stopList.clear();
		// loop through creating a marker for each
		for(int index=0;index<eventItems.size();index++){
			// get the event
			StopViewModel viewModel = (StopViewModel) eventItems.get(index);
			// add a marker to the map
			Marker marker = TransitMapActivity.this.addMaker(viewModel);
			// join the new marker to the event
			// viewModel.setMapMarkerId(marker.getId());
			// add the event to the list
			this.stopList.add(viewModel);					
		}
		// center map around events in the list
		this.centerToStops();
	}

	/**
	 * add a marker for that event (along with its image)
	 */
	
	private Marker addMaker(StopViewModel viewModel){
		Bitmap icon = null;
        /*
		if(viewModel!=null && viewModel.getModel()!=null && viewModel.getModel().getImageData()!=null){
			byte[] bitmapdata = viewModel.getModel().getImageData();
			Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapdata , 0, bitmapdata .length);
			icon = this.imageWrapper.resizeImage(bitmap, 150);
		}
           */
		Marker marker = TransitMapActivity.this.addMarker(viewModel, icon);
		return marker;
	}

	private Marker addMarker(StopViewModel viewModel, Bitmap icon){

		LatLng locationPoint =
                new LatLng(viewModel.getModel().getLocation().getLatitude(),
                        viewModel.getModel().getLocation().getLongitude());

		MarkerOptions markerOptions = new MarkerOptions();
		markerOptions.position(locationPoint);	
		markerOptions.title(viewModel.getTitle());
		markerOptions.snippet(viewModel.getDetails());
		
		if(icon==null){
			float markerColor = BitmapDescriptorFactory.HUE_AZURE;
			markerOptions.icon(BitmapDescriptorFactory.defaultMarker(markerColor));			
		}else{
			markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon));
		}
		
		Marker marker = this.googleMap.addMarker(markerOptions);
		return marker;				
	}	

	private BitmapDescriptor buildDescriptor(Bitmap image){
		BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(image);
		return bitmapDescriptor;
	}


	/*
	private void addPhotoToMarker(Marker marker, Bitmap bm){
		BitmapDescriptor bitmapDescriptor = this.buildDescriptor(bm);
		LatLng firstPosition = marker.getPosition();
		LatLng secondPosition = new LatLng( firstPosition.latitude + 1, firstPosition.longitude + 1);
		LatLngBounds bounds = new LatLngBounds(firstPosition, secondPosition);
		this.addGroundOverlay(bitmapDescriptor, bounds, (float) 0.5);		
	}
	 */

	/*
	private GroundOverlay addGroundOverlay(BitmapDescriptor image, LatLngBounds bounds, float transparency){

		 GroundOverlay groundOverlay = this.googleMap.addGroundOverlay(new GroundOverlayOptions()
		      .image(image)
		      .positionFromBounds(bounds)
		      .transparency(transparency));

		 return groundOverlay;
	}
	 */

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item){
/*
		switch(item.getItemId()){
		case R.id.menu_add_event:
			Intent addEventIntent = new Intent(this, UploadEventActivity.class);
			this.startActivity(addEventIntent);
			break;
		case R.id.menu_list_events:
			Intent listEventsIntent = new Intent(this, EventListActivity.class);
			this.startActivity(listEventsIntent);
			break;
		case R.id.menu_center_on_events:
			this.centerToEvents();
			break;
		case R.id.menu_refresh_events:
			this.googleMap.clear();
			this.requestEvents(true);
			break;
		case R.id.menu_hybrid:
			this.googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
			break;
		case R.id.menu_normal:
			this.googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
			break;
		case R.id.menu_satelite:
			this.googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
			break;				
		}
*/
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onMapLongClick(LatLng point) {
	}
	
	@Override
	public void onInfoWindowClick(Marker marker) {
        /*
		for(IViewModel model:this.stopList){
			StopViewModel eventViewModel = (StopViewModel) model;
			if(eventViewModel.getMapMarkerId()!=null){
				if(eventViewModel.getMapMarkerId().equals(marker.getId())){
					Intent showEvent = new Intent(this, EventViewActivity.class);
					showEvent.putExtra(EventViewActivity.EVENT_VIEW_MODEL_INTENT_VALUE, eventViewModel);
					this.startActivity(showEvent);
				}
			}			
		}
		*/
	}	

	@Override
	public boolean onMarkerClick(final Marker marker) {	
		marker.showInfoWindow();		
		return false;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.google.android.gms.maps.LocationSource#activate(com.google.android.gms.maps.LocationSource.OnLocationChangedListener)
	 */


	@Override
	public void activate(final OnLocationChangedListener listener)
	{
        locationListener = listener;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.google.android.gms.maps.LocationSource#deactivate()
	 */

	@Override
	public void deactivate() 
	{
        locationListener = null;
	}
	
	/*
	 * (non-Javadoc)
	 * @see android.location.LocationListener#onLocationChanged(android.location.Location)
	 */

	@Override
	public void onLocationChanged(Location location) 
	{		
		if (locationListener != null ) {
            locationListener.onLocationChanged( location );
            // this.googleMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude())));
            this.centerToLocation();
		}
	}

	@Override
	public void onProviderDisabled(String provider) 
	{
		Toast.makeText(this, "provider disabled", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onProviderEnabled(String provider) 
	{
		Toast.makeText(this, "provider enabled", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) 
	{
		// Toast.makeText(this, "status changed", Toast.LENGTH_SHORT).show();
	}


}
