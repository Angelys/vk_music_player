package org.geekhub.vkPlayer.activities;

import org.geekhub.vkPlayer.R;
import org.geekhub.vkPlayer.fragments.LoginFragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;


/**
 * Created with IntelliJ IDEA.
 * User: angelys
 * Date: 3/10/13
 * Time: 1:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class LoginActivity extends FragmentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        if (savedInstanceState == null) {
            handleIntentExtras(getIntent());
        }
    }

 private void handleIntentExtras(Intent intent) {
        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(intent.getExtras());
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.login_frag, fragment).commit();
    }
}