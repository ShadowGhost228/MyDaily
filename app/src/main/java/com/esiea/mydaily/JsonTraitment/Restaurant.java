package com.esiea.mydaily.JsonTraitment;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.IOException;


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



public class Restaurant {

    private String formatted_address;
    private String name;
    private String icon;
    private Opening_hours opening_hours;

    public Restaurant(String formatted_address, String name, String icon, Opening_hours opening_hours) {
        this.formatted_address = formatted_address;
        this.name = name;
        this.icon = icon;
        this.opening_hours = opening_hours;
    }

    public String getFormatted_address() {
        return formatted_address;
    }

    public void setFormatted_address(String formatted_address) {
        this.formatted_address = formatted_address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Opening_hours getOpening_hours() {
        return opening_hours;
    }

    public void setOpening_hours(Opening_hours opening_hours) {
        this.opening_hours = opening_hours;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "formatted_address='" + formatted_address + '\'' +
                ", name='" + name + '\'' +
                ", icon='" + icon + '\'' +
                ", opening_hours=" + opening_hours +
                '}';
    }

    //Class
    public static class Opening_hours {
        private String open_now;
        private String weekday_text;

        public Opening_hours(String open_now, String weekday_text) {
            this.open_now = open_now;
            this.weekday_text = weekday_text;
        }

        public String getOpen_now() {
            return open_now;
        }

        public void setOpen_now(String open_now) {
            this.open_now = open_now;
        }

        public String getWeekday_text() {
            return weekday_text;
        }

        public void setWeekday_text(String weekday_text) {
            this.weekday_text = weekday_text;
        }
    }
}
