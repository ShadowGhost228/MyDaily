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
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.esiea.mydaily.JsonTraitment.Restaurant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
    private static final String ACTION_PARSE_JSON = "com.esiea.mydaily.action.PARSE_JSON";

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

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionParseJson(Context context) {
        Intent intent = new Intent(context, GetLocationServices.class);
        intent.setAction(ACTION_PARSE_JSON);
        context.startService(intent);
    }


    //LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(RestaurantActivity.LOCATION_UPDATE));


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_GET_ALL_LOCATIONS.equals(action)) {
                handleActionGetAllLocations(RestaurantActivity.getLocate());
            }
            if (ACTION_PARSE_JSON.equals(action)) {
                HandleActionParseJsonToRestaurant();
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
        try {
            Log.i("TAG", "Je suis dans le try");
            url = new URL("https://maps.googleapis.com/maps/api/place/textsearch/json?query=restaurants&location=" + location.getLatitude() + "," + location.getLongitude() + "&radius=10000&key=AIzaSyAnYVkPDWerM5AWh6Gw79_mh9xIg54c3Ws");
            Log.i("TAG", "J'ai travaillé l'url");
            HttpURLConnection conn = (HttpsURLConnection) url.openConnection();
            Log.i("TAG", "J'ai travaillez la connection");
            conn.setRequestMethod("GET");
            conn.connect();
            Log.i("TAG", "Connection etablie");
            if (HttpsURLConnection.HTTP_OK == conn.getResponseCode()) {
                Log.i("TAG", "Je rentre dans le if");
                //Log.i("TAG", convertStreamToString(conn.getInputStream()));

                File file = new File(getCacheDir(), "locationData.json");

                copyInputStreamToFile(conn.getInputStream(), file);
                Log.i("TAG", "dataLocation.json DOWLOADING");

                LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(RestaurantActivity.LOCATION_UPDATE));

            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.i("TAG", "Je suis une execption");
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void copyInputStreamToFile(InputStream in, File file) {
        try {
            OutputStream out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            out.close();
            in.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private JSONArray getAllLocationsFromFileArray() {
        try {
            InputStream is = new FileInputStream(getCacheDir() + "/" + "locationData.json");

            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();
            Log.i("TAG", "JE recupere le fichier");
            return new JSONArray(new String(buffer, "UTF-8"));

            //return new JSONArray(convertStreamToString(is));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return new JSONArray();
        } catch (IOException e) {
            e.printStackTrace();
            return new JSONArray();
        } catch (JSONException e) {
            e.printStackTrace();
            return new JSONArray();
        }
    }

    private JSONObject getAllLocationsFromFileObject() {
        try {
            InputStream is = new FileInputStream(getCacheDir() + "/" + "locationData.json");
            /*
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();
            Log.i("TAG", "JE recupere le fichier");
            return new JSONArray(new String(buffer, "UTF-8"));
            */
            return new JSONObject(convertStreamToString(is));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return new JSONObject();
        } catch (IOException e) {
            e.printStackTrace();
            return new JSONObject();
        } catch (JSONException e) {
            e.printStackTrace();
            return new JSONObject( );
        }
    }

    private void HandleActionParseJsonToRestaurant() {

        try {
            //Get location
            //JSONArray jsonArray = getAllLocationsFromFileArray();
            JSONObject jsonObject = getAllLocationsFromFileObject();

            JSONArray results = jsonObject.getJSONArray("results");

            Log.i("TAG", results.toString());

            //Parsing
            for (int i = 0; i < results.length(); i++) {
                JSONObject data = results.getJSONObject(i);

                String name = data.getString("name");
                String formatted_address = data.getString("formatted_address");
                String icon = data.getString("icon");

                // opening_hours node
                //JSONObject dataNode = data.getJSONObject("opening_hours");
                //String open_now = dataNode.getString("open_now");
                //String weekday_text = dataNode.getString("weekday_text");


                Restaurant.Opening_hours opening_hours;

                JSONObject openingNode = data.getJSONObject("opening_hours");

                String open_now = " ";

                open_now = openingNode.getString("open_now");

                opening_hours = new Restaurant.Opening_hours(open_now);

                /*
                if(openingNode.isNull("opening_hours")){
                    opening_hours = new Restaurant.Opening_hours(open_now);

                } else {
                    open_now = openingNode.getString("open_now");
                    opening_hours = new Restaurant.Opening_hours(open_now);
                }
                */


                Restaurant restaurant = new Restaurant(formatted_address, name, icon, opening_hours);
                Log.i("TAG", "\n"+restaurant.toString()+"\n");


            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private String convertStreamToString(InputStream inputStream) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

}
