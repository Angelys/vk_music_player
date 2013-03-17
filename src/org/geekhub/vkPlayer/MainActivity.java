package org.geekhub.vkPlayer;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;
import com.perm.kate.api.Api;
import org.geekhub.vkPlayer.activities.BaseActivity;
import org.geekhub.vkPlayer.utils.Account;
import org.geekhub.vkPlayer.utils.Constants;

public class MainActivity extends BaseActivity {

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        if (savedInstanceState == null) {
            handleIntentExtras(getIntent());
        }
    }

 private void handleIntentExtras(Intent intent) {
        MainFragment fragment = new MainFragment();
        fragment.setArguments(intent.getExtras());
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.main_frag, fragment).commit();
    }
}
