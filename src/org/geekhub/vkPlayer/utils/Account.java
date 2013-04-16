package org.geekhub.vkPlayer.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.util.Log;


public class Account {
	
	final String LOG_TAG = "myLogs";

	public String access_token;
    public Long user_id;

    public void save(Context context){    	
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Editor editor=prefs.edit();
        editor.putString("access_token", access_token);
        editor.putLong("user_id", user_id);
        editor.commit();
        Log.d(LOG_TAG, "--- Account.class - save(context)  --- user_id = " + user_id + " -- access_token = " + access_token);
    }

    public void restore(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        access_token=prefs.getString("access_token", null);
        user_id=prefs.getLong("user_id", 0);
        Log.d(LOG_TAG, "--- Account.class - restore(context)  --- user_id = " + user_id + access_token);
    }

    public boolean isAuthenticated(){    	
        boolean isAuthorized = (access_token != null) && (user_id != 0);
        Log.d(LOG_TAG, "--- Account.class - isAuthenticated() ? --- " + isAuthorized);
    	return isAuthorized;
    }

    public void clear(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Editor editor=prefs.edit();
        editor.remove("access_token");
        editor.remove("user_id");
        editor.commit();
        Log.d(LOG_TAG, "--- Account.class - clear(context)  ");
    }
}