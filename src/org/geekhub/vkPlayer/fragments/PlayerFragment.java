package org.geekhub.vkPlayer.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.perm.kate.api.Audio;

import org.geekhub.vkPlayer.BaseFragment;
import org.geekhub.vkPlayer.PlayerService;
import org.geekhub.vkPlayer.R;
import org.geekhub.vkPlayer.utils.Account;
import org.geekhub.vkPlayer.utils.Utilities;
import org.holoeverywhere.widget.Toast;


public class PlayerFragment extends BaseFragment {

    View view;

    public static PlayerFragment INSTANCE;

    private Handler mHandler = new Handler();
    final String LOG_TAG = "myLogs";
    private Menu mOptionsMenu;
       

    public static Button btnPlay;
    Button btnFwd;
    Button btnRwd;
    Button btnDwn;

    TextView songCurrentDurationLabel;
    TextView songTotalDurationLabel;
    TextView artist;
    TextView trackName;

    SeekBar songProgressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.player, container, false);

        btnPlay = (Button) view.findViewById(R.id.btnPlay);
        btnFwd = (Button) view.findViewById(R.id.btnFwd);
        btnRwd = (Button) view.findViewById(R.id.btnRwd);
        btnDwn = (Button) view.findViewById(R.id.download);
        
        songCurrentDurationLabel = (TextView) view.findViewById(R.id.songCurrentDurationLabel);
        songTotalDurationLabel = (TextView) view.findViewById(R.id.songTotalDurationLabel);
        artist = (TextView) view.findViewById(R.id.artist);
        trackName = (TextView) view.findViewById(R.id.trackName);

        songProgressBar = (SeekBar) view.findViewById(R.id.songProgressBar);

        songProgressBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromTouch) {

            }

            /**
             * When user starts moving the progress handler
             * */
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // remove message Handler from updating progress bar
                mHandler.removeCallbacks(mUpdateUITask);
            }

            /**
             * When user stops moving the progress hanlder
             * */
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mHandler.removeCallbacks(mUpdateUITask);
                if(PlayerService.INSTANCE != null){
                    int totalDuration = PlayerService.INSTANCE.getDuration();
                    int currentPosition = Utilities.progressToTimer(seekBar.getProgress(), totalDuration);

                    // forward or backward to certain seconds
                    PlayerService.INSTANCE.seekTo(currentPosition);

                    // update timer progress again
                    updateProgressBar();
                }

            }
        });

        INSTANCE = this;

        btnPlay.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(PlayerService.INSTANCE != null){
//                	if(PlayerService.INSTANCE.player.isPlaying()){
//                		btnPlay.setBackgroundResource(R.drawable.btn_play);
//                	}else{
//                		btnPlay.setBackgroundResource(R.drawable.btn_pause);
//                	};
                	Log.d(LOG_TAG, "--- PlayerFragment - play - R.drawable -- " + btnPlay.getBackground().hashCode());
//                	if(btnPlay.getBackground().getCurrent() == R.drawable.btn_play)
					// Changing button image to pause button					
                    PlayerService.INSTANCE.play();
//                    btnPlay.setBackgroundResource(R.drawable.btn_pause);
                }
            }
        });
        btnRwd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(PlayerService.INSTANCE != null){
                    PlayerService.INSTANCE.prev();
                }
            }
        });
        btnFwd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(PlayerService.INSTANCE != null){
                    PlayerService.INSTANCE.next();
                }
            }
        });

        btnDwn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(PlayerService.INSTANCE != null){
                    org.geekhub.vkPlayer.utils.Audio a = new org.geekhub.vkPlayer.utils.Audio(PlayerService.INSTANCE.getCurrentSong());
                    if(!a.isSaved(getActivity())){
                        a.save(getActivity());
                    }
                }
            }
        });

        updateProgressBar();

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public void onSaveInstanceState(Bundle out){
    }

    public void onDestroy(){
        INSTANCE = null;
        super.onDestroy();
    }

    /**
     * Update timer on seekbar
     * */
    public void updateProgressBar() {
        mHandler.postDelayed(mUpdateUITask, 100);
    }
    
    
    
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        mOptionsMenu = menu;
        menu.findItem(R.id.logOut).setVisible(true);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
	            case R.id.logOut:
	            	 //to do - Clear token method
	                 break;	            
	        }
	        return super.onOptionsItemSelected(item);
	    }    

    /**
     * Background Runnable thread
     * */
    private Runnable mUpdateUITask = new Runnable() {
        public void run() {
            if(PlayerService.INSTANCE != null){
                long totalDuration = PlayerService.INSTANCE.getDuration();
                long currentDuration = PlayerService.INSTANCE.getCurrentPosition();

                Audio audio = PlayerService.INSTANCE.getCurrentSong();

                // Displaying Total Duration time
                songTotalDurationLabel.setText(""+Utilities.milliSecondsToTimer(totalDuration));
                // Displaying time completed playing
                songCurrentDurationLabel.setText(""+Utilities.milliSecondsToTimer(currentDuration));

                artist.setText(audio.artist);

                trackName.setText(audio.title);

                btnDwn.setVisibility((new org.geekhub.vkPlayer.utils.Audio(audio)).isSaved(getActivity())?1:0);

                // Updating progress bar
                int progress = (int)(Utilities.getProgressPercentage(currentDuration, totalDuration));
                //Log.d("Progress", ""+progress);
                songProgressBar.setProgress(progress);

                // Running this thread after 100 milliseconds
                mHandler.postDelayed(this, 100);
            }
        }
    };

}
