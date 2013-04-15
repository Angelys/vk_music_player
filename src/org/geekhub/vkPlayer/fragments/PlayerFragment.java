package org.geekhub.vkPlayer.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.perm.kate.api.Api;
import com.perm.kate.api.Audio;
import org.geekhub.vkPlayer.PlayerService;
import org.geekhub.vkPlayer.R;
import org.geekhub.vkPlayer.adapters.AudioAdapter;
import org.geekhub.vkPlayer.utils.Constants;

import android.widget.ImageButton;

import java.util.ArrayList;


public class PlayerFragment extends SherlockFragment {

    View view;
    
    final String LOG_TAG = "myLogs";
    
    private static int count;
    
    PlayerService playerService = new PlayerService();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.player, container, false);
        
        ImageButton btnPlay = (ImageButton) view.findViewById(R.id.btnPlay);
        ImageButton btnFwd = (ImageButton) view.findViewById(R.id.btnFwd);
        ImageButton btnRwd = (ImageButton) view.findViewById(R.id.btnRwd);
        
        TextView songCurrentDurationLabel = (TextView) view.findViewById(R.id.songCurrentDurationLabel);
        TextView songTotalDurationLabel = (TextView) view.findViewById(R.id.songTotalDurationLabel);
        
        SeekBar songProgressBar = (SeekBar) view.findViewById(R.id.songProgressBar);

        btnPlay.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(PlayerService.INSTANCE != null){
                    PlayerService.INSTANCE.play();
                }
            }
        });
        btnRwd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(PlayerService.INSTANCE != null){
                    PlayerService.INSTANCE.prev();
                    PlayerService.INSTANCE.play();
                }
            }
        });
        btnFwd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(PlayerService.INSTANCE != null){
                    PlayerService.INSTANCE.next();
                    PlayerService.INSTANCE.play();
                }
            }
        });


        btnPlay.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(PlayerService.INSTANCE != null){
                    PlayerService.INSTANCE.play();
                }
            }
        });
        btnRwd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(PlayerService.INSTANCE != null){
                    PlayerService.INSTANCE.prev();
                    PlayerService.INSTANCE.play();
                }
            }
        });
        btnFwd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(PlayerService.INSTANCE != null){
                    PlayerService.INSTANCE.next();
                    PlayerService.INSTANCE.play();
                }
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    public void onSaveInstanceState(Bundle out){
    }

}
