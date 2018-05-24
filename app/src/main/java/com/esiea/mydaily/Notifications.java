package com.esiea.mydaily;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class Notifications {

    public Notifications(){

    }

    public void notificationFunction(Context context, String type , String message){

        switch (type)
        {
            case "toast":
               Toast.makeText(context, message , Toast.LENGTH_SHORT).show();
                break;
        }

    }
}
