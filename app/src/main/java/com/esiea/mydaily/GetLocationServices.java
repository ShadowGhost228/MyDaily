package com.esiea.mydaily;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.Context;
import android.location.Location;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import static java.lang.System.in;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class GetLocationServices extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_GET_ALL_LOCATIONS = "com.esiea.mydaily.action.GET_ALL_LOCATIONS";

    public GetLocationServices() {
        super("GetLocationServices");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionGetAllLocations(Context context, Location location) {
        Intent intent = new Intent(context, GetLocationServices.class);
        intent.setAction(ACTION_GET_ALL_LOCATIONS);
        context.startService(intent);
    }

    //LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(RestaurantActivity.LOCATION_UPDATE));


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_GET_ALL_LOCATIONS.equals(action)) {
                handleActionGetAllLocations(RestaurantActivity.getLocation());
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionGetAllLocations(Location location) {
        Log.i("TAG", "Handle action Location");
        URL url = null;
        try{
            Log.i("TAG" , "Je suis dans le try");
            url= new URL("https://maps.googleapis.com/maps/api/place/textsearch/json?query=restaurants&location="+ location.getLongitude() +","+location.getLatitude() +"&radius=10000&key=AIzaSyAnYVkPDWerM5AWh6Gw79_mh9xIg54c3Ws");
            Log.i("TAG" , "J'ai travaillé l'url");
            HttpURLConnection conn=(HttpsURLConnection) url.openConnection();
            Log.i("TAG" , "J'ai travaillez la connection");
            conn.setRequestMethod("GET");
            conn.connect();
            Log.i("TAG" , "Connection etablie");
            if(HttpsURLConnection.HTTP_OK == conn.getResponseCode()){
                Log.i("TAG" , "Je rentre dans le if");
                copyInputStreamToFile(conn.getInputStream() , new File(getCacheDir(), "locationData.json"));
                Log.i("TAG" , "dataLocation.json DOWLOADED");

                LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(RestaurantActivity.LOCATION_UPDATE));

            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.i("TAG" , "Je suis une execption");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void copyInputStreamToFile(InputStream inputStream, File file) {
        try {
            OutputStream out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while((len=in.read(buf))>0){
                out.write(buf,0,len);
            }
            out.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
