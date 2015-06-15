package com.example.alok.test1;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class PlaceDetailsActivity extends Activity {
    private PlaceDetailsDAO placeDetailsDAO;
    private static final String PACKAGE_NAME = "com.example.alok.test1";
    private static final String RESOURCE_TYPE = "drawable";
    public static final String LONGITUDE = "LONGITUDE";
    public static final String LATITUDE = "LATITUDE";
    private int placeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_details);

        Intent intent = getIntent();
        placeId = intent.getIntExtra(LoadPlacesActivity.PLACE_ID,0);
        placeDetailsDAO = new PlaceDetailsDAO(this);
        placeDetailsDAO.open();

        PlaceDetails placeDetails = placeDetailsDAO.getPlaceDetail(placeId);

        final ViewGroup actionBarLayout = (ViewGroup)getLayoutInflater().inflate(R.layout.custom_actionbar, null);
        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(actionBarLayout);
        TextView mainTextView = (TextView)findViewById(R.id.detailText);
        String mainText = placeDetails.getMainText();
        mainText = mainText.replaceAll("\\\\n","\\\n");
        mainText = mainText.replaceAll("\\\\t","\\\t");
        mainTextView.setText(mainText);

        ImageView centerImage = (ImageView)findViewById(R.id.centerImage);
        centerImage.setImageResource(getResources().getIdentifier(placeDetails.getImage(),RESOURCE_TYPE, PACKAGE_NAME));

        TextView primaryTextView = (TextView)findViewById(R.id.placePrimaryText);
        primaryTextView.setText(placeDetails.getPrimaryText());
        TextView secondaryTextView = (TextView)findViewById(R.id.placeSecondaryText);
        secondaryTextView.setText(placeDetails.getSecondaryText());

    }

    public void add(View view) {
        placeDetailsDAO.enableMemoir(placeId);
        Toast.makeText(PlaceDetailsActivity.this,"Added to memoir successfully", Toast.LENGTH_LONG).show();
    }

    public void map(View view) {
        Toast.makeText(PlaceDetailsActivity.this,
                "Map button clicked",
                Toast.LENGTH_LONG).show();
    }

    public void share(View view) {
        Toast.makeText(PlaceDetailsActivity.this,
                "Share button clicked",
                Toast.LENGTH_LONG).show();
    }

    public void loadPlaces(View view) {
        LocationUtil locationUtil = new LocationUtil();
        Location location = locationUtil.getLocation(this, this);

        if(location != null) {
            Intent intent = new Intent(this, LoadPlacesActivity.class);
            intent.putExtra(LONGITUDE, location.getLongitude());
            intent.putExtra(LATITUDE, location.getLatitude());
            startActivity(intent);
        } else {
            String message = String.format("Current Location: \n Error - No location found");
            Toast.makeText(PlaceDetailsActivity.this, message, Toast.LENGTH_LONG).show();
        }
    }

    public void loadMemoir(View view) {
        //Toast.makeText(PlaceDetailsActivity.this, "Load Memoir Button clicked", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, LoadMemoirActivity.class);
        startActivity(intent);
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_place_details, menu);
        return true;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        placeDetailsDAO.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        placeDetailsDAO.close();
        super.onPause();
    }
}
