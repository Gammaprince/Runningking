package com.raees.android;

import android.content.Context;

public class constant {
    // DO NOT EDIT


    ///// CONFIGURATION /////

    // API FOLDER URL
    static String prefix = "https://sattamatkafire.net/runningking/api/";

    // APK DOWNLOAD LINK
    static String link = "https://play.google.com/store/apps/details?id=com.runningking.android";

    // PROJECT ROOT URL
    static String project_root = "https://sattamatkafire.net/runningking/";

    // MIN AMOUNT ALLOWED IN TOTAL FOR BETTING
    static int min_total = 10;

    // MAX AMOUNT ALLOWED IN TOTAL FOR BETTING
    static int max_total = 10000;

    // MIN AMOUNT ALLOWED FOR SINGLE BET
    static int min_single = 10;

    // MAX AMOUNT ALLOWED FOR SINGLE BET
    static int max_single = 10000;

    // MIN DEPOSIT AMOUNT THROUGH PAYMENT GATEWAY
    static int min_deposit = 500;

    static public String getWhatsapp(Context context){

        String number = context.getSharedPreferences(constant.prefs,Context.MODE_PRIVATE).getString("whatsapp",null);
        if (number.contains("+91")){
            return  "http://wa.me/"+context.getSharedPreferences(constant.prefs,Context.MODE_PRIVATE).getString("whatsapp",null);
        } else {
            return  "http://wa.me/+91"+context.getSharedPreferences(constant.prefs,Context.MODE_PRIVATE).getString("whatsapp",null);
        }

    }

}
