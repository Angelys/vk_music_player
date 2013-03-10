package org.geekhub.vkPlayer.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created with IntelliJ IDEA.
 * User: angelys
 * Date: 3/10/13
 * Time: 12:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class Account {

    public String access_token;
    public Long user_id;

    public void save(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor=prefs.edit();
        editor.putString("access_token", access_token);
        editor.putLong("user_id", user_id);
        editor.commit();
    }

    public void restore(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        access_token=prefs.getString("access_token", null);
        user_id=prefs.getLong("user_id", 0);
    }

    public boolean isAuthenticated(){
        return (access_token != null) && (user_id != 0);
    }

}
