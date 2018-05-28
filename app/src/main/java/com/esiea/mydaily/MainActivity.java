package com.esiea.mydaily;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.esiea.mydaily.AccountActivity.*;

public class MainActivity extends AppCompatActivity  {

    private CardView settingCardView, restaurantCardView, taxiCardView, orderCardView, kioskCardView;
    private static final int DIALOG_ALERT = 10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Notifications notifications=new Notifications();
        notifications.notificationFunction(this , "toast" , "Connexion réuissie");
        settingCardView = (CardView) findViewById(R.id.settingcardview);

        restaurantCardView = (CardView) findViewById(R.id.restaurantcardview);

        taxiCardView = (CardView) findViewById(R.id.taxicardview);

        orderCardView = (CardView) findViewById(R.id.ordercardview);

        kioskCardView = (CardView) findViewById(R.id.kioskcardview);

        settingCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SettingActivity.class));
            }
        });

        restaurantCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RestaurantActivity.class));
            }
        });

        kioskCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNotification();
               // startActivity(new Intent(MainActivity.this, KioskActivity.class));
            }
        });

        orderCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, OrderActivity.class));
            }
        });

        taxiCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PackageManager pm=getPackageManager();

                try {
                    Intent intent = pm.getLaunchIntentForPackage("com.ubercab");
                    startActivity(intent);
                }
                catch(Exception e) {
                    showDialog(DIALOG_ALERT);

                }

                //startActivity(new Intent(MainActivity.this, TaxiActivity.class));
            }
        });
    }

    private final void createNotification(){
        final Notifications notifications=new Notifications();
        notifications.notificationFunction(this,"toast" , "Je suis dans la notiiii");
        final NotificationManager mNotification = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        final Intent launchNotifiactionIntent = new Intent(this, MainActivity.class);
        final PendingIntent pendingIntent = PendingIntent.getActivity(this,
                100, launchNotifiactionIntent,
                PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setWhen(System.currentTimeMillis())
                .setTicker("ICI LA NOTIFICATION")
                .setContentTitle("Notice")
                .setSmallIcon(android.R.drawable.btn_radio)
                .setContentText(getResources().getString(R.string.Notification))
                .setContentIntent(pendingIntent);

        mNotification.notify(0, builder.build());
    }

    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DIALOG_ALERT:
                // Create out AlterDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(" "+getString(R.string.messageTaxi));
                builder.setCancelable(true);
                builder.setPositiveButton("I agree", new MainActivity.OkOnClickListener());
                builder.setNegativeButton("No, no", new MainActivity.CancelOnClickListener());
                AlertDialog dialog = builder.create();
                dialog.show();
        }
        return super.onCreateDialog(id);
    }
    private final class CancelOnClickListener implements
            DialogInterface.OnClickListener {
        public void onClick(DialogInterface dialog, int which) {
           // notifications.notificationFunction(getApplicationContext(), "toast" , "GoodBye");
            try {
                MainActivity.this.clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }

        }
    }

    private final class OkOnClickListener implements
            DialogInterface.OnClickListener {
        public void onClick(DialogInterface dialog, int which) {
            PackageManager pm=getPackageManager();
            Intent intent = pm.getLaunchIntentForPackage("com.android.vending");
            startActivity(intent);

        }
    }


}