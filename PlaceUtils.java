package com.example.alok.test1;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.location.Location;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akp77 on 4/12/2015.
 */
public class PlaceUtils {

    private Resources resources;
    private Context context;
    private PlaceDAO placeDAO;

    PlaceUtils(Resources resources, PlaceDAO placeDAO) {
        this.resources = resources;
        this.placeDAO = placeDAO;
    }

    public List<Place> getPlacesWithinDistance(double srcLatitude, double srcLongitude, double distanceInMiles) {
        placeDAO.open();
        List<Place> allPlaces = placeDAO.getAllPlaces();
        List<Place> placeInRangeList = new ArrayList<Place>();
        Location srcLocation = new Location("default");
        srcLocation.setLatitude(srcLatitude);
        srcLocation.setLongitude(srcLongitude);
        for(Place p : allPlaces) {
            Location itrLocation = new Location("default");
            itrLocation.setLatitude(p.getLatitude());
            itrLocation.setLongitude(p.getLongitude());
            if((srcLocation.distanceTo(itrLocation)*0.00062) < distanceInMiles) {
                placeInRangeList.add(p);
            }
        }
        return placeInRangeList;
    }

    public List<Place> getAllPlaces() {
        placeDAO.open();
        List<Place> allPlaces = placeDAO.getAllPlaces();
        return allPlaces;
    }
}
