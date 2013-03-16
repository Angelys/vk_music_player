package org.geekhub.vkPlayer.fragments;

import org.geekhub.vkPlayer.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SettingsFragment extends Fragment{

	private View view;

	
	 @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      view = inflater.inflate(R.layout.settings_fragment, container, false);
      return view;
  }
	 
	 @Override
	    public void onActivityCreated(Bundle savedInstanceState) {
	        super.onActivityCreated(savedInstanceState);
	 }


}
