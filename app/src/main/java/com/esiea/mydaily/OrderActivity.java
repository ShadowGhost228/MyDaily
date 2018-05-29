package com.esiea.mydaily;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.wifi.WifiConfiguration;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.common.api.CommonStatusCodes;

import java.lang.Throwable;
import java.lang.Object;

public class OrderActivity extends AppCompatActivity {
    Notifications notifications = new Notifications();
    private static final int DIALOG_ALERT = 11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        final Button btn_choix_maps = (Button) findViewById(R.id.btn_choix_maps);
        btn_choix_maps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PackageManager pm = getPackageManager();

                try {
                    Intent intent = pm.getLaunchIntentForPackage("com.google.android.apps.maps");
                    startActivity(intent);
                } catch (Exception e) {
                    showDialog(DIALOG_ALERT);

                }
            }
        });

        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                // Log.i(TAG, "Place: " + place.getName());
                String placeName = place.getName().toString();
                notifications.notificationFunction(getApplicationContext(), "toast", "" + placeName);
            }

            @Override
            public void onError(Status status) {

            }


        });
    }


}
