package org.geekhub.vkPlayer.fragments;

import org.geekhub.vkPlayer.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class LoginFragment extends Fragment{
	
	final String LOG_TAG = "myLogs";
	
	private View view;

	
	 @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		 Log.d(LOG_TAG, "--- LoginFragment - onCreate --- ");
       view = inflater.inflate(R.layout.login_fragment, container, false);
       return view;
   }
	 
	 @Override
	    public void onActivityCreated(Bundle savedInstanceState) {
		 Log.d(LOG_TAG, "--- LoginFragment - onActivityCreated --- ");
	        super.onActivityCreated(savedInstanceState);
	 }
}
