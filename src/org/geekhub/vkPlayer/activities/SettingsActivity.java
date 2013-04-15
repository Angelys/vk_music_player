package org.geekhub.vkPlayer.activities;

import org.geekhub.vkPlayer.BaseActivity;
import org.geekhub.vkPlayer.R;
import org.geekhub.vkPlayer.fragments.SettingsFragment;
import android.os.Bundle;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;


public class SettingsActivity extends BaseActivity {
	
	final String LOG_TAG = "myLogs";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.d(LOG_TAG, "--- SettingsActivity - onCreate() ---");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings_activity);
        if (savedInstanceState == null) {
        	Log.d(LOG_TAG, "--- SettingsActivity - onCreate() ---savedInstanceState == null");
            handleIntentExtras(getIntent());
        }
    }

	private void handleIntentExtras(Intent intent) {
		Log.d(LOG_TAG, "--- SettingsActivity - handleIntentExtras(Intent intent)");
        SettingsFragment fragment = new SettingsFragment();
        fragment.setArguments(intent.getExtras());
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Log.d(LOG_TAG, "--- SettingsActivity - handleIntentExtras() ----- go to SettingsFragment()");
        transaction.replace(R.id.settings_frag, fragment).commit();
	}
}
