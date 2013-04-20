package org.geekhub.vkPlayer.activities;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import org.geekhub.vkPlayer.PlayerService;
import org.geekhub.vkPlayer.R;
import org.geekhub.vkPlayer.fragments.MainFragment;
import org.geekhub.vkPlayer.utils.Account;
import org.geekhub.vkPlayer.utils.ConnectionDetector;
import org.geekhub.vkPlayer.utils.Constants;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.perm.kate.api.Api;

public class MainActivity extends SherlockFragmentActivity {
	
	protected Api api;
    Account account = new Account();
    public static final int REQUEST_LOGIN=1;
    ConnectionDetector cd = new ConnectionDetector(this);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        if (savedInstanceState == null) {

        	connectionCheck();
        }
    }    
    
    protected void connectionCheck(){
        account.restore(getApplicationContext());

        if (account.isAuthenticated()){
           if(PlayerService.INSTANCE == null){
                startService(new Intent(this, PlayerService.class).putExtra(PlayerService.ACTION_TAG, PlayerService.ACTION_IDLE));
            }
            handleIntentExtras(getIntent());
        }
        else if(cd.isConnectingToInternet()){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivityForResult(intent, REQUEST_LOGIN);
        } else{
        	pushToFinish();
        }
    }

    public void restart(){
        Intent i = getBaseContext().getPackageManager()
                .getLaunchIntentForPackage( getBaseContext().getPackageName() );
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    private void pushToFinish(){
    	AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
   	 
		// Setting Dialog Title
		alertDialogBuilder.setTitle("Internet Connection Error");

		// Setting Dialog Message
		alertDialogBuilder
			.setMessage("Please connect to working Internet connection")
			.setCancelable(false)
			.setIcon(R.drawable.ic_launcher)
			.setPositiveButton("OK",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {
					// if this button is clicked, close
					// current activity
					MainActivity.this.finish();
				}
			 });	
		// Create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();

		// Showing Alert Message
		alertDialog.show();		 
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_LOGIN) {
            if (resultCode == RESULT_OK) {
                //Authorization success
                account.access_token=data.getStringExtra("token");
                account.user_id=data.getLongExtra("user_id", 0);
                account.save(this);
                api=new Api(account.access_token, Constants.API_ID);
                restart();
            } else {
                finish();
            }
        }
    }
    
    private void handleIntentExtras(Intent intent) {
        MainFragment fragment = new MainFragment();
        fragment.setArguments(intent.getExtras());
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.main_frag, fragment).commit();
    }
 
 public void onBackPressed(){
     super.onBackPressed();
 }
}
