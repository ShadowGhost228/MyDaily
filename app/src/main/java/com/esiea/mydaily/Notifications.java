package com.esiea.mydaily;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class Notifications extends Activity {

    public Notifications(){

    }

    public void notificationFunction(Context context, String type , String message){

        switch (type)
        {
            case "toast":
               Toast.makeText(context, message , Toast.LENGTH_SHORT).show();
                break;
            case "notification":
                //createNotification(message , context);
                break;
        }

    }


}
