package org.geekhub.vkPlayer.activities;

import org.geekhub.vkPlayer.R;
import org.geekhub.vkPlayer.fragments.SettingsFragment;
import android.os.Bundle;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;


public class SettingsActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings_activity);
		  if (savedInstanceState == null) {
	            handleIntentExtras(getIntent());
	        }
	    }

	 private void handleIntentExtras(Intent intent) {
	        SettingsFragment fragment = new SettingsFragment();
	        fragment.setArguments(intent.getExtras());
	        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
	        transaction.replace(R.id.settings_frag, fragment).commit();
	    }
	}
