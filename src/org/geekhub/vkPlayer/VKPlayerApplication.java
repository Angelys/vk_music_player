package org.geekhub.vkPlayer;

import android.app.Application;
import android.content.Intent;
import android.util.Log;


public class VKPlayerApplication extends Application {
	
	final String LOG_TAG = "myLogs";

    public void onCreate(){    	
        super.onCreate();
        Log.d(LOG_TAG, "--- VKPlayerApplication - onCreate() ---");
       
    }

}
