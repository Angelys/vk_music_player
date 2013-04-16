package org.geekhub.vkPlayer.activities;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.Toast;

import org.geekhub.vkPlayer.PlayerService;
import org.geekhub.vkPlayer.R;
import org.geekhub.vkPlayer.fragments.MainFragment;
import org.geekhub.vkPlayer.utils.Account;
import org.geekhub.vkPlayer.utils.ConnectionDetector;
import org.geekhub.vkPlayer.utils.Constants;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.perm.kate.api.Api;

public class MainActivity extends SherlockFragmentActivity {
	
	final String LOG_TAG = "myLogs";

	protected Api api;
    Account account = new Account();
    public static final int REQUEST_LOGIN=1;
    ConnectionDetector cd = new ConnectionDetector(this);
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	Log.d(LOG_TAG, "--- Main Activity - onCreate() ---");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        if (savedInstanceState == null) {
        	Log.d(LOG_TAG, "--- Main Activity - onCreate() --- (savedInstanceState == null)");
        	connectionCheck();
        }
    }
    
    
    protected void connectionCheck(){
    	Log.d(LOG_TAG, "--- Main Activity - connectionCheck() --- ");
        account.restore(getApplicationContext());

        if (account.isAuthenticated()){
        	Log.d(LOG_TAG, "--- Main Activity - connectionCheck().isisAuthenticated --- TRUE ");
            if(PlayerService.INSTANCE == null){
                startService(new Intent(this, PlayerService.class).putExtra(PlayerService.ACTION_TAG, PlayerService.ACTION_IDLE));
            }
            handleIntentExtras(getIntent());
        }
        else if(cd.isConnectingToInternet()){
        	Log.d(LOG_TAG, "--- Main Activity - connectionCheck().isConnectingToInternet --- TRUE ");
            Intent intent = new Intent(this, LoginActivity.class);
            Log.d(LOG_TAG, "--- Main Activity - start LoginActivity --- FOR RESULT ");
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
    	Log.d(LOG_TAG, "--- Main Activity - pushToFinish() --- ");
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
					Log.d(LOG_TAG, "--- Main Activity - finish()");
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
    	Log.d(LOG_TAG, "--- Main Activity - onActivityResult --- requestCode = " + requestCode);
    	Log.d(LOG_TAG, "--- Main Activity - onActivityResult --- resultCode = " + resultCode);
    	Log.d(LOG_TAG, "--- Main Activity - onActivityResult --- Intent data = " + data);
        if (requestCode == REQUEST_LOGIN) {
        	Log.d(LOG_TAG, "--- Main Activity - onActivityResult --- requestCode == REQUEST_LOGIN");
            if (resultCode == RESULT_OK) {
            	Log.d(LOG_TAG, "--- Main Activity - onActivityResult --- resultCode == RESULT_OK");
                //Authorization success
                account.access_token=data.getStringExtra("token");
                account.user_id=data.getLongExtra("user_id", 0);
                account.save(this);
                api=new Api(account.access_token, Constants.API_ID);
                Log.d(LOG_TAG, "--- Main Activity - onActivityResult --- account.access_token == " + account.access_token);
                Log.d(LOG_TAG, "--- Main Activity - onActivityResult --- new Api " + Constants.API_ID);

                handleIntentExtras(getIntent());
            } else {
                finish();
            }
        }
    }


    
    private void handleIntentExtras(Intent intent) {
	 Log.d(LOG_TAG, "--- Main Activity - handleIntentExtras(intent) - " + intent );
        MainFragment fragment = new MainFragment();
        Log.d(LOG_TAG, "--- Main Activity - handleIntentExtras(intent) - new- MainFragment");
        fragment.setArguments(intent.getExtras());
        Log.d(LOG_TAG, "--- Main Activity - handleIntentExtras(intent) - getIntent");
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Log.d(LOG_TAG, "--- Main Activity - handleIntentExtras(intent) - transaction");
        transaction.add(R.id.main_frag, fragment).commit();
        Log.d(LOG_TAG, "--- Main Activity - handleIntentExtras(intent) - fragment).commit()");
    }
 
 public void onBackPressed(){
     super.onBackPressed();
     //TODO: mb leave app if not authenticated
 }
}
