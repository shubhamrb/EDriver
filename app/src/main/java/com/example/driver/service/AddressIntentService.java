package com.example.driver.service;


import android.app.IntentService;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.List;
import java.util.Locale;

/**
 * Created by Akshay Raj on 30/12/19 at 9:20 PM.
 * akshay@snowcorp.org
 * www.snowcorp.org
 */

public class AddressIntentService extends IntentService {
    private static final String IDENTIFIER = "AddressIntentService";
    private ResultReceiver addressResultReceiver;

    public AddressIntentService() {
        super(IDENTIFIER);
    }

    //handle the address request
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String msg = "";
        //get result receiver from intent
        assert intent != null;
        addressResultReceiver = intent.getParcelableExtra("add_receiver");

        if (addressResultReceiver == null) {
            Log.e("GetAddressIntentService",
                    "No receiver, not processing the request further");
            return;
        }

        Location location = intent.getParcelableExtra("add_location");

        //send no location error to results receiver
        if (location == null) {
            msg = "No location, can't go further without location";
            sendResultsToReceiver(0, msg,location.getLatitude(),location.getLongitude());
            return;
        }
        //call GeoCoder getFromLocation to get address
        //returns list of addresses, take first one and send info to result receiver
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = null;

        try {
            addresses = geocoder.getFromLocation(
                    location.getLatitude(),
                    location.getLongitude(),
                    1);
        } catch (Exception ioException) {
            Log.e("", "Error in getting address for the location");
        }

        if (addresses == null || addresses.size()  == 0) {
            msg = "No address found for the location";
            sendResultsToReceiver(1, msg,location.getLatitude(),location.getLongitude());
        } else {
            Address address = addresses.get(0);
            StringBuilder addressDetails = new StringBuilder();

//            Log.i("Address", address.getAddressLine(0));

            if (address.getFeatureName() != null) {
                addressDetails.append(address.getFeatureName());
                addressDetails.append(",");
            }
//            if (address.getAddressLine(0) != null) {
//                addressDetails.append(address.getAddressLine(0));
//                addressDetails.append(",");
//            }
            if (address.getThoroughfare() != null) {
                addressDetails.append(address.getThoroughfare());
                addressDetails.append(",");
            }
            if (address.getLocality() != null) {
                addressDetails.append(address.getLocality());
                addressDetails.append(",");
            }
            if (address.getAdminArea() != null) {
                addressDetails.append(address.getAdminArea());
            }

//            addressDetails.append("Country: ");
//            addressDetails.append(address.getCountryName());
//            addressDetails.append("\n");
//
//            addressDetails.append("Postal Code: ");
//            addressDetails.append(address.getPostalCode());
//            addressDetails.append("\n");

            sendResultsToReceiver(2, address.getAddressLine(0),location.getLatitude(),location.getLongitude());
        }
    }
    //to send results to receiver in the source activity
    private void sendResultsToReceiver(int resultCode, String message,double latitude,double longitude) {
        Bundle bundle = new Bundle();
        bundle.putString("address_result", message);
        bundle.putString("latitude", String.valueOf(latitude));
        bundle.putString("longitude", String.valueOf(longitude));
        addressResultReceiver.send(resultCode, bundle);
    }
}
