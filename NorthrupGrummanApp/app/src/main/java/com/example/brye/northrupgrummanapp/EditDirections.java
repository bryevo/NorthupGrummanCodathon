package com.example.brye.northrupgrummanapp;

import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.model.LatLng;


public class EditDirections extends SampleActivityBase implements PlaceSelectionListener {

    private TextView trackText;
    private TextView mPlaceDetailsText;
    private TextView mPlaceAttribution;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_directions);

        // Retrieve the PlaceAutocompleteFragment.
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        // Register a listener to receive callbacks when a place has been selected or an error has
        // occurred.
        autocompleteFragment.setOnPlaceSelectedListener(this);

        //Retrieve the TextViews that will display details about the selected place.
       trackText = (TextView) findViewById(R.id.loc_Info);
        mPlaceDetailsText = (TextView) findViewById(R.id.txt_stuff1);
        mPlaceAttribution = (TextView) findViewById(R.id.txt_stuff2);
    }
    /**
     * Callback invoked when a place has been selected from the PlaceAutocompleteFragment.
     */
    @Override
    public void onPlaceSelected(Place place) {
        Log.i(TAG, "Place Selected: " + place.getName());

        // Format the returned place's details and display them in the TextView.
        trackText.setText(place.getName()+ " is now being tracked.");
        HomePage.lat = place.getLatLng().latitude;
        HomePage.lon = place.getLatLng().longitude;
        HomePage.name = place.getName().toString();

        mPlaceDetailsText.setText(formatPlaceDetails(getResources(), place.getName(),
                place.getAddress(), place.getLatLng()));

        CharSequence attributions = place.getAttributions();
        if (!TextUtils.isEmpty(attributions)) {
            mPlaceAttribution.setText(Html.fromHtml(attributions.toString()));
        } else {
            mPlaceAttribution.setText("");
        }
    }

    /**
     * Callback invoked when PlaceAutocompleteFragment encounters an error.
     */
    @Override
    public void onError(Status status) {
        Log.e(TAG, "onError: Status = " + status.toString());

        Toast.makeText(this, "Place selection failed: " + status.getStatusMessage(),
                Toast.LENGTH_SHORT).show();
    }

    private static Spanned formatPlaceDetails(Resources res, CharSequence name,
                                              CharSequence address, LatLng latlng) {
        Log.e(TAG, res.getString(R.string.place_details, name, address, latlng));
        return Html.fromHtml(res.getString(R.string.place_details, name, address, latlng));

    }

}
