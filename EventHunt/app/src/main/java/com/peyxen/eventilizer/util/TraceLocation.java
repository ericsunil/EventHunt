package com.peyxen.eventilizer.util;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by Pci on 1/11/2016.
 */
public class TraceLocation {
    private static Context context;
    private static GPSTracker gps;
    public static String mLocation;

    public TraceLocation(Context context) {
        this.context = context;
    }

    public static void traceLocation() {
        gps = new GPSTracker(context);

        // check if GPS enabled
        if (gps.canGetLocation()) {

            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();

            // \n is for new line
            Toast.makeText(context, getLocationName(latitude, longitude), Toast.LENGTH_LONG).show();
            mLocation = getLocationName(latitude, longitude);
        } else {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }
    }

    public static String getLocationName(double lat, double lng) {
        String mLocation = "";
        Geocoder gcd = new Geocoder(context, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = gcd.getFromLocation(lat, lng, 1);
            if (addresses.size() > 0) {
                mLocation = addresses.get(0).getLocality();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mLocation;
    }
}
