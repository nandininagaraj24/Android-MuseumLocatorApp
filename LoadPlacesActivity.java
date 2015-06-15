package com.example.alok.test1;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class LoadPlacesActivity extends Activity {
    private GridView gridView;
    private GridViewAdapter gridAdapter;
    private double currentLatitude;
    private double currentLongitude;
    private static final double DEFAULT_DISTANCE_IN_MILES = 0.3;
    private PlaceDAO placeDAO;
    public static final String PLACE_ID = "PLACE_ID";
    public static final String LONGITUDE = "LONGITUDE";
    public static final String LATITUDE = "LATITUDE";
    public static final String MY_PREFERENCES = "MyPrefs";
    public static final String KEY_SEARCH_RADIUS = "searchRadiusKey";
    SharedPreferences sharedPreferences;
    private float searchRadius = 0.3f;      //default

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_places);
        placeDAO = new PlaceDAO(this, getResources());

        sharedPreferences = getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
        if(sharedPreferences.contains(KEY_SEARCH_RADIUS)) {
            searchRadius = sharedPreferences.getFloat(KEY_SEARCH_RADIUS, 0.0f);
        }

        Intent intent = getIntent();
        currentLatitude = intent.getDoubleExtra(LATITUDE,0.0);
        currentLongitude = intent.getDoubleExtra(LONGITUDE, 0.0);
        gridView = (GridView) findViewById(R.id.gridView);
        gridAdapter = new GridViewAdapter(this, R.layout.grid_item_layout, getPlacesData());
        gridView.setAdapter(gridAdapter);

        final ViewGroup actionBarLayout = (ViewGroup)getLayoutInflater().inflate(R.layout.custom_actionbar, null);
        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(actionBarLayout);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                ImageItem item = (ImageItem) parent.getItemAtPosition(position);
                //Create intent
                Intent intent = new Intent(LoadPlacesActivity.this, PlaceDetailsActivity.class);
                intent.putExtra(PLACE_ID, item.getId());
                //Start details activity
                startActivity(intent);
        }
        });

    }

    private ArrayList<ImageItem> getPlacesData() {
        final ArrayList<ImageItem> imageItems = new ArrayList<>();
        PlaceUtils placeUtils = new PlaceUtils(getResources(), placeDAO);
        List<Place> placeList;
        if(currentLatitude == 0.0 || currentLongitude == 0.0) {
            return getAllPlacesData();
        } else {
            placeList = placeUtils.getPlacesWithinDistance(currentLatitude, currentLongitude, searchRadius);
            for (Place p : placeList) {
                imageItems.add(new ImageItem(p.getId(), p.getBitmap(), p.getPlaceName()));
            }
            return imageItems;
        }
    }

    private ArrayList<ImageItem> getAllPlacesData() {
        final ArrayList<ImageItem> imageItems = new ArrayList<>();
        PlaceUtils placeUtils = new PlaceUtils(getResources(), placeDAO);
        List<Place> placeList = placeUtils.getAllPlaces();
        for(Place p : placeList) {
            imageItems.add(new ImageItem(p.getId(), p.getBitmap(), p.getPlaceName()));
        }
        return imageItems;
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
            Toast.makeText(LoadPlacesActivity.this, message, Toast.LENGTH_LONG).show();
        }
    }

    public void loadMemoir(View view) {
        //Toast.makeText(LoadPlacesActivity.this, "Load Memoir Button clicked", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, LoadMemoirActivity.class);
        startActivity(intent);
    }

    public void range(View view) {
        showPopupDialog();
    }

    private void showPopupDialog() {
        final AlertDialog.Builder popDialog = new AlertDialog.Builder(this);
        popDialog.setTitle("Set search radius");
        popDialog.setIcon(android.R.drawable.btn_star_big_on);

        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setGravity(Gravity.CENTER);

        final TextView text = new TextView(this);
        sharedPreferences = getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
        if(sharedPreferences.contains(KEY_SEARCH_RADIUS)) {
            searchRadius = sharedPreferences.getFloat(KEY_SEARCH_RADIUS, 0.0f);
        }

        text.setText("Search Radius : " + searchRadius + "miles");
        text.setPadding(10, 10, 10, 10);
        text.setGravity(Gravity.CENTER);

        final SeekBar seek = new SeekBar(this);
        seek.setMax(50);
        seek.setProgress((int)(searchRadius*10));

        linearLayout.addView(seek);
        linearLayout.addView(text);

        popDialog.setView(linearLayout);

        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
                //Do something here with new value
                //txtView.setText("Value of : " + progress);
                searchRadius = (float)progress/10;
                text.setText("Search Radius : " + (double)progress/10 + "miles");
            }

            public void onStartTrackingTouch(SeekBar arg0) {
                // TODO Auto-generated method stub

            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub

            }
        });


        // Button OK
        popDialog.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putFloat(KEY_SEARCH_RADIUS, searchRadius);
                        editor.commit();
                        dialog.dismiss();
                        LocationUtil locationUtil = new LocationUtil();
                        Location location = locationUtil.getLocation(LoadPlacesActivity.this, LoadPlacesActivity.this);
                        if(location != null) {
                            Intent intent = getIntent();
                            intent.putExtra(LONGITUDE, location.getLongitude());
                            intent.putExtra(LATITUDE, location.getLatitude());
                            startActivity(intent);
                        } else {
                            String message = String.format("Current Location: \n Error - No location found");
                            Toast.makeText(LoadPlacesActivity.this, message, Toast.LENGTH_LONG).show();
                        }
                    }

                });


        popDialog.create();
        popDialog.show();


    }

    public void overview(View view) {
        Intent intent = getIntent();
        intent.putExtra(LONGITUDE, 0.0);
        intent.putExtra(LATITUDE, 0.0);
        finish();
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        placeDAO.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        placeDAO.close();
        super.onPause();
    }
}
