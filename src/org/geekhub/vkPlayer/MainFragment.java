package org.geekhub.vkPlayer;


import org.geekhub.vkPlayer.activities.LoginActivity;
import org.geekhub.vkPlayer.utils.Account;
import org.geekhub.vkPlayer.utils.ConnectionDetector;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


public class MainFragment extends Fragment{
	
	private View view;
	Account account = new Account();
	Context context;
	ConnectionDetector cd = new ConnectionDetector(context);
	
	 @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.main_fragment, container, false);
        context = view.getContext();

        return view;
    }
	 
	@Override
	    public void onActivityCreated(Bundle savedInstanceState) {
	        super.onActivityCreated(savedInstanceState);
		        connectionCheck();
	        	 }
	
	private void connectionCheck(){
		if (isLoggedIn() == true){
			Toast.makeText(context, "Вы авторизированы!", Toast.LENGTH_LONG).show();
		}
		else{
			Toast.makeText(context, "Вы не авторизированы!", Toast.LENGTH_LONG).show();
			Intent intent = new Intent(getActivity(), LoginActivity.class);
			startActivity(intent);
		}
	}
	 
	private boolean isLoggedIn(){
				if (account.isAuthenticated() == true){					
					return true;		
				}				
				 return false;		 
		}
		
		private boolean isOnline(){
			if (cd.isConnectingToInternet() == true){
				Toast.makeText(context, "connected to internet", Toast.LENGTH_LONG).show();
				return true;
			}
			Toast.makeText(context, "NOT CONNECTED TO INTEDNET", Toast.LENGTH_LONG).show();
			return false;
		}
		
}
