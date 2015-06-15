package com.example.nn262.vgmap;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        mMap.addMarker(new MarkerOptions().position(new LatLng(42.444974, -76.480832)).title("Gates Hall!!"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(42.444526, -76.482633)).title("Duffield Hall!!"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(42.447901, -76.484325)).title("Olin Library!!"));

        mMap.addMarker(new MarkerOptions().position(new LatLng(42.446754, -76.484649)).title("Cornell store!!"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(42.449309, -76.480153)).title("Lincoln Hall!!"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(42.450690, -76.486185)).title("Johnson museum of Art!!"));

        mMap.addMarker(new MarkerOptions().position(new LatLng(42.445907, -76.483258)).title("Sage Hall!!"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(42.447176, -76.484402)).title("Sage Chappel!!"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(42.452410, -76.486598)).title("MC Graw Hall!!"));
        LatLng myCoordinates = new LatLng(42.445093 , -76.480768);
        CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(myCoordinates, 13);
        mMap.animateCamera(yourLocation);
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(myCoordinates)      // Sets the center of the map to LatLng (refer to previous snippet)
                .zoom(15)                   // Sets the zoom
                .bearing(90)                // Sets the orientation of the camera to east
                .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }
}
