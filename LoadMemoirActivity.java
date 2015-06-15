package com.example.alok.test1;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;


public class LoadMemoirActivity extends Activity {

    private PlaceDAO placeDAO;
    private GridView memoirGridView;
    private MemoirGridViewAdapter memoirGridViewAdapter;
    private PlaceDetailsDAO placeDetailsDAO;
    private static final String PACKAGE_NAME = "com.example.alok.test1";
    private static final String RESOURCE_TYPE = "drawable";
    public static final String PLACE_ID = "PLACE_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_memoir);

        placeDAO = new PlaceDAO(this, getResources());
        placeDetailsDAO = new PlaceDetailsDAO(this);
        placeDetailsDAO.open();

        memoirGridView = (GridView)findViewById(R.id.memoirGridView);
        memoirGridView.setEmptyView(findViewById(R.id.empty_grid_view));
        memoirGridViewAdapter = new MemoirGridViewAdapter(this, R.layout.memoir_grid_item_layout, getAllPlacesData());
        memoirGridView.setAdapter(memoirGridViewAdapter);

        final ViewGroup actionBarLayout = (ViewGroup)getLayoutInflater().inflate(R.layout.custom_actionbar, null);
        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(actionBarLayout);

        ImageButton memoirButton = (ImageButton)actionBar.getCustomView().findViewById(R.id.memoirButton);
        ImageButton gpsButton = (ImageButton)actionBar.getCustomView().findViewById(R.id.gpsButton);
        TextView actionBarTitle = (TextView)actionBar.getCustomView().findViewById(R.id.actionbar_title_text);

        actionBarTitle.setText("Memoir");
        memoirButton.setVisibility(View.INVISIBLE);
        gpsButton.setVisibility(View.INVISIBLE);



        memoirGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ImageItem item = (ImageItem) parent.getItemAtPosition(position);
                Intent intent = new Intent(LoadMemoirActivity.this, PlaceDetailsActivity.class);
                intent.putExtra(PLACE_ID, item.getId());
                //Start details activity
                startActivity(intent);
                //Toast.makeText(LoadMemoirActivity.this, "Item with id"+ item.getId() + " clicked", Toast.LENGTH_LONG).show();
            }
        });
    }


    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_load_memoir, menu);
        return true;
    }*/

    private ArrayList<ImageItem> getAllPlacesData() {
        final ArrayList<ImageItem> imageItems = new ArrayList<>();
        List<PlaceDetails> placeDetailsList = placeDetailsDAO.getAllPlaceDetails();
        for(PlaceDetails placeDetails : placeDetailsList) {
            if(placeDetails.getInMemoir() != 0) {
                imageItems.add(new ImageItem(placeDetails.getPlaceId(),
                        BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(placeDetails.getImage(),RESOURCE_TYPE, PACKAGE_NAME)), placeDetails.getPrimaryText()));
            }
        }
        return imageItems;
    }

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
}
