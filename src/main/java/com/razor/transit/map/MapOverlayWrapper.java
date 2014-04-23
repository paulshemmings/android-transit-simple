package com.razor.transit.map;

import java.util.ArrayList;
import java.util.List;

import com.razor.transit.helpers.MapHelper;
import com.razor.transit.viewmodels.IViewModel;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

public class MapOverlayWrapper
        extends ItemizedOverlay<ViewModelOverlayItem>
        implements IMapOverlayWrapper {
	
	public interface onMapOverlayEventListener{
		void onOverlayItemTapped(ViewModelOverlayItem item);
	}
	private List<ViewModelOverlayItem> items = new ArrayList<ViewModelOverlayItem>();
	private Drawable marker = null;
	private onMapOverlayEventListener eventListener = null;
	private int textSize;

	public MapOverlayWrapper(onMapOverlayEventListener eventListener, Drawable marker, int textSize) {			
		super(marker);
		this.eventListener = eventListener;
		this.marker = marker;
		this.textSize = textSize;
		boundCenterBottom(marker);
	}

	/* (non-Javadoc)
	 * @see com.bbconnect.lib.wrappers.IMapOverlayWrapper#addItems(com.google.android.maps.OverlayItem[])
	 */
	public int addItems(ViewModelOverlayItem[] items){
		for(ViewModelOverlayItem item: items){
			this.items.add(item);	
		}			
		return this.size();
	}
	
	/* (non-Javadoc)
	 * @see com.bbconnect.lib.wrappers.IMapOverlayWrapper#addItem(com.google.android.maps.OverlayItem)
	 */
	public int addItem(ViewModelOverlayItem item){
		this.items.add(item);
		return this.size();
	}
	
	/* (non-Javadoc)
	 */

	public int addItem(double lat, double lon, IViewModel model){
		ViewModelOverlayItem item = new ViewModelOverlayItem(MapHelper.getPoint(lat, lon), model);
		this.items.add(item);
		return this.size();			
	}
	
	/* (non-Javadoc)
	 * @see com.bbconnect.lib.wrappers.IMapOverlayWrapper#loadItems()
	 */
	public void loadItems(){
		this.populate();
	}

	@Override
	protected ViewModelOverlayItem createItem(int i) {
		return (items.get(i));
	}

	@Override
	protected boolean onTap(int index) {
		ViewModelOverlayItem item = items.get(index);
        if(this.eventListener!=null){
        	this.eventListener.onOverlayItemTapped(item);
        }
        return this.eventListener!=null;
	}

	/* (non-Javadoc)
	 * @see com.bbconnect.lib.wrappers.IMapOverlayWrapper#size()
	 */
	@Override
	public int size() {
		return (items.size());
	}
	
	public GeoPoint getOverlayCenter(){
		GeoPoint center = null;
		if(this.items!=null&&this.items.size()>0){	
			
			int minLat = items.get(0).getPoint().getLatitudeE6();
			int maxLat = items.get(0).getPoint().getLatitudeE6();
			int minLong = items.get(0).getPoint().getLongitudeE6();
			int maxLong = items.get(0).getPoint().getLongitudeE6();
			
			for(ViewModelOverlayItem item:this.items){
				int lat = item.getPoint().getLatitudeE6();
				int longi = item.getPoint().getLongitudeE6();
				
				if(lat<minLat) minLat = lat;
				if(lat>maxLat) maxLat = lat;
				if(longi<minLong) minLong = longi;
				if(longi>maxLong) maxLong = longi;			
			}
			int latWidth = maxLat-minLat;
			int longWidth = maxLong-minLong;
			
			center = new GeoPoint(minLat+(latWidth/2),minLong+(longWidth/2));
		}
		return center;
	}
	
	public int getOverlayLatitudeSpan(){
		return this.getLatSpanE6();
	}
	public int getOverlayLongitudeSpan(){
		return this.getLonSpanE6();		
	}
	
    @Override
    public void draw(android.graphics.Canvas canvas, MapView mapView, boolean shadow)
    {
        super.draw(canvas, mapView, shadow);

        if (shadow == false)
        {
            //cycle through all overlays
            for (int index = 0; index < items.size(); index++)
            {
                OverlayItem item = items.get(index);

                // Converts lat/lng-Point to coordinates on the screen
                GeoPoint point = item.getPoint();
                Point ptScreenCoord = new Point() ;
                mapView.getProjection().toPixels(point, ptScreenCoord);

                //Paint
                Paint paint = new Paint();
                //paint.setColor(Color.WHITE); 
                //paint.setStyle(Style.FILL); 
                //canvas.drawPaint(paint); 
                
                paint.setTextAlign(Paint.Align.CENTER);
                paint.setTextSize(this.textSize);
                paint.setARGB(150, 0, 0, 0); // alpha, r, g, b (Black, semi see-through)

                //show text to the right of the icon
                canvas.drawText(item.getTitle(), ptScreenCoord.x, ptScreenCoord.y+this.textSize, paint);
            }
        }
    }	
}	
