package org.geekhub.vkPlayer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import org.geekhub.vkPlayer.activities.MainActivity;


public class BaseActivity extends SherlockFragmentActivity {
	
	final String LOG_TAG = "myLogs";


    public void onCreate(Bundle savedInstanceState) {
    	Log.d(LOG_TAG, "--- BaseActivity - onCreate() --- ");
    	
        super.onCreate(savedInstanceState);
    }

    public void onBackPressed(){
        super.onBackPressed();
    }
    
    private void showAudioList(){
    	Intent intent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(intent);
    }

    private void showMessage(final String message,final int duration ){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(BaseActivity.this, message, duration);
            }
        });
    }
}