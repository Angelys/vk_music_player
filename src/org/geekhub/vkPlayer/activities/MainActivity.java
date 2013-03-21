package org.geekhub.vkPlayer.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import org.geekhub.vkPlayer.BaseActivity;
import org.geekhub.vkPlayer.R;
import org.geekhub.vkPlayer.fragments.MainFragment;

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
