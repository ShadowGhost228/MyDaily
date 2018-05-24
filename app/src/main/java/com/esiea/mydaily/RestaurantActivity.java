package com.esiea.mydaily;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.logging.Logger;
import com.google.android.gms.location.LocationServices;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class RestaurantActivity extends AppCompatActivity {

    private LocationListener locationListener;
    private LocationManager locationManager;
    private static Location location = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        final TextView latitudeTextView = (TextView) findViewById(R.id.textlatitude);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            return;
        }
        location = locationManager.getLastKnownLocation("gps");

        latitudeTextView.append("\n " + location.getLongitude() + " " + location.getLatitude());

        GetLocationServices.startActionGetAllLocations(this, location);

        IntentFilter intentFilter = new IntentFilter(LOCATION_UPDATE);

        LocalBroadcastManager.getInstance(this).registerReceiver(new LocationUpdate(), intentFilter);

    }

    //Action a la fin du téléchargement
    public static final String LOCATION_UPDATE = "com.esiea.mydaily.LOCATION_UPDATE";
    public class LocationUpdate extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Notifications notifications=new Notifications();
            //String action = getIntent().getAction();
            createNotification();
            Log.i("TAG", "Téléchargement terminé"); // prévoir une action de notification ici
           // notifications.notificationFunction(this , "toast" , "Téléchargement terminé");
        }
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
                locationManager.getLastKnownLocation("gps");
                break;
            default:
                break;
        }
    }

    public static Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    private final void createNotification(){
        final NotificationManager nNotification=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        final Intent launchNotificationIntent=new Intent(this, RestaurantActivity.class);
        final PendingIntent pendingIntent=PendingIntent.getActivity(this , 100 ,
                launchNotificationIntent , PendingIntent.FLAG_ONE_SHOT);

        /*
        Notification.Builder builder=new Notification.Builder(this)
                .setWhen(System.currentTimeMillis())
                .setTicker("ICI LA NOTIFICATION")
                .setContentTitle("Notice")
                .setSmallIcon(android.R.drawable.btn_star_big_on)
                .setContentText(getResources().getString(R.string.Notification))
                .setContentIntent(pendingIntent);
        */

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(android.R.drawable.btn_star_big_on)
                .setContentTitle("Notice")
                .setContentText(getResources().getString(R.string.Notification))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        nNotification.notify(0 , mBuilder.build());

    }
}
