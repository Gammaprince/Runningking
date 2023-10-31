package com.raees.android;

import android.app.Application;

import java.util.HashMap;

public class Betplay extends Application {

    public static HashMap<String, String> temp_follows = new HashMap<>();
    static public FeedModel feedModel;

    static Boolean isLocked = true;
    static public FeedModel getFeedModel() {
        return feedModel;
    }

    static public void setFeedModel(FeedModel feedModel2) {
        feedModel = feedModel2;
    }


    @Override
    public void onCreate() {
        super.onCreate();

        isLocked = true;

    }

    public static void setIsLocked(Boolean isLocked) {
        Betplay.isLocked = isLocked;
    }

    public static Boolean getIsLocked() {
        return isLocked;
    }

    static public void addTempFollow(String mobile, String status){
        temp_follows.put(mobile, status);
    }

    static public String checkFollow(String mobile){
        if(temp_follows.containsKey(mobile) && temp_follows.get(mobile).equals("1"))
        {
            return "1";
        } else if (temp_follows.containsKey(mobile) && temp_follows.get(mobile).equals("0")){
            return "0";
        } else {
            return "-1";
        }
    }
}
