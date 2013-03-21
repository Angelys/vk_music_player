package org.geekhub.vkPlayer;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;
import com.perm.kate.api.Api;


import org.geekhub.vkPlayer.R;
import org.geekhub.vkPlayer.activities.LoginActivity;
import org.geekhub.vkPlayer.utils.Account;
import org.geekhub.vkPlayer.utils.ConnectionDetector;
import org.geekhub.vkPlayer.utils.Constants;


public class BaseActivity extends FragmentActivity {

    public static final int REQUEST_LOGIN=1;
    protected Api api;
    Account account = new Account();
    ConnectionDetector cd = new ConnectionDetector(this);

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        connectionCheck();
    }

    protected void connectionCheck(){

        account.restore(getApplicationContext());

        if (account.isAuthenticated()){
            Toast.makeText(this, "Вы авторизированы!", Toast.LENGTH_LONG).show();
        }
        else if(cd.isConnectingToInternet()){
            Toast.makeText(this, "Вы не авторизированы!", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivityForResult(intent, REQUEST_LOGIN);
        } else
        {
        	pushToFinish();
        	Toast.makeText(this, "Вы не подключены к интернету!", Toast.LENGTH_LONG).show();
        }
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
					BaseActivity.this.finish();
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
            }
        }
    }

    public void onBackPressed(){
        super.onBackPressed();
        //TODO: mb leave app if not authenticated
    }
}