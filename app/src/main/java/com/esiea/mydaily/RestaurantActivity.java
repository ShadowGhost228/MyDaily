package com.esiea.mydaily;

import android.Manifest;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.esiea.mydaily.JsonTraitment.Restaurant;
import com.google.android.gms.location.FusedLocationProviderClient;


public class RestaurantActivity extends AppCompatActivity {

    private LocationListener locationListener;
    private LocationManager locationManager;
    public static Location locate = null;
    Notifications notifications = new Notifications();
    long[] pattern = {0, 100, 1000, 300, 200, 100, 500, 200, 100};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        final TextView latitudeTextView = (TextView) findViewById(R.id.textlatitude);

        final TextView latitudeDeuxTextView = (TextView) findViewById(R.id.textlatitudedeux);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            //Boite de Dialog

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) &&
                    ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.ACCESS_COARSE_LOCATION)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        1);
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        2);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
            //location = locationManager.getLastKnownLocation("gps");

            locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    if (ActivityCompat.checkSelfPermission(RestaurantActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                            && ActivityCompat.checkSelfPermission(RestaurantActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    if (location != null) {
                        latitudeTextView.setText("\n Location :" + location.getLatitude() + " \n Location :" + location.getLongitude());

                        //setLocate(locationManager.getLastKnownLocation("gps"));


                    }

                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {
                    Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                }
            };

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, locationListener);

            setLocate(locationManager.getLastKnownLocation("gps"));

            if (locate != null) {
                latitudeDeuxTextView.setText("\n Locate: " + locate.getLatitude() + " \n Locate: " + locate.getLongitude());
                GetLocationServices.startActionGetAllLocations(RestaurantActivity.this, locate);
            }

        }


        IntentFilter intentFilter = new IntentFilter(LOCATION_UPDATE);
        LocalBroadcastManager.getInstance(this).registerReceiver(new LocationUpdate(), intentFilter);

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case 10:
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    return;
                }
                break;
            default:
                break;
        }
    }

    //Action a la fin du téléchargement
    public static final String LOCATION_UPDATE = "com.esiea.mydaily.LOCATION_UPDATE";

    public class LocationUpdate extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i("TAG", "Téléchargement terminé");
            notifications.notificationFunction(RestaurantActivity.this, "toast", " " + getString(R.string.message_telechargement));
            createNotification();
            GetLocationServices.startActionParseJson(RestaurantActivity.this);
        }
    }

    private final void createNotification() {
        final Notifications notifications = new Notifications();
        final NotificationManager mNotification = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        final Intent launchNotifiactionIntent = new Intent(this, MainActivity.class);
        final PendingIntent pendingIntent = PendingIntent.getActivity(this,
                100, launchNotifiactionIntent,
                PendingIntent.FLAG_ONE_SHOT);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setWhen(System.currentTimeMillis())
                .setTicker(" " + getString(R.string.message_telechargement))
                .setContentTitle("Json")
                .setSmallIcon(android.R.drawable.btn_radio)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.logoapp))
                .setContentText(getResources().getString(R.string.message_telechargement))
                .setContentIntent(pendingIntent)
                .setVibrate(pattern);

        mNotification.notify(0, builder.build());
    }

    public static Location getLocate() {
        return locate;
    }

    public static void setLocate(Location locate) {
        RestaurantActivity.locate = locate;
    }

}

