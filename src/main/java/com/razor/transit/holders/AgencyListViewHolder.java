package com.razor.transit.holders;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;


import com.razor.transit.viewmodels.AgencyViewModel;
import razor.android.transit.R;

import java.util.List;

/**
 * 
 * @author phemmings
 */

public class AgencyListViewHolder implements OnClickListener {

    /**
     * The list view this holder supports
     */
	
	public static final int LayoutId = R.layout.agency_list_layout;

    /**
     * OnAgencyListViewEventListener
     * setting the listener will result in notification of agency selection
     */
	
	public interface OnAgencyListViewEventListener{
		void onAgencySelected(AgencyViewModel agency);
	}
	private OnAgencyListViewEventListener listener;

    /**
     * Constructor
     * @param listener : OnAgencyListViewEventListener
     * @param parentView : View
     */
	
	public AgencyListViewHolder(final OnAgencyListViewEventListener listener,
                                final View parentView) {
		this.listener = listener;
	}

    /**
     * Update the agencies in the view
     * @param agencies
     */
	
	public void updateAgencies(final List<AgencyViewModel> agencies) {
		// todo: update the agencies within the view
	}

    /**
     * Capture user event, notify that that an agency list item has been selected
     * @param v : View
     */

	public final void onClick(final View v) {
		if (this.listener != null) {
			// retrieve selected agency (there is a way to do this, can't remember)
			AgencyViewModel viewmodel = null;
			// notify listener
			this.listener.onAgencySelected(viewmodel);
		}
	}
	
	private static void hideKeyboard(View view) {
	    Context context = view.getContext();
	    InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
	    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
	}
}
