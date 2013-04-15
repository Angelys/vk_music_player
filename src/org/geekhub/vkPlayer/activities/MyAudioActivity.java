package org.geekhub.vkPlayer.activities;

import android.os.Bundle;
import android.util.Log;

import org.geekhub.vkPlayer.BaseActivity;
import org.geekhub.vkPlayer.R;


public class MyAudioActivity extends BaseActivity {
	
	final String LOG_TAG = "myLogs";

    public void onCreate(Bundle savedInstanceState){
    	Log.d(LOG_TAG, "--- MyAudioActivity - onCreate() --- ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_audio);
    }

}