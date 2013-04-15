package org.geekhub.vkPlayer.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.actionbarsherlock.app.SherlockFragment;
import com.perm.kate.api.Api;
import com.perm.kate.api.Audio;
import org.geekhub.vkPlayer.PlayerService;
import org.geekhub.vkPlayer.R;
import org.geekhub.vkPlayer.adapters.AudioAdapter;
import org.geekhub.vkPlayer.utils.Constants;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: angelys
 * Date: 4/15/13
 * Time: 9:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class PlayerFragment extends SherlockFragment {

    View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.player, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    public void onSaveInstanceState(Bundle out){
    }

}
