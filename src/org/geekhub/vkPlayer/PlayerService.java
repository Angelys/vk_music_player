package org.geekhub.vkPlayer;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;
import com.perm.kate.api.Audio;
import org.geekhub.vkPlayer.fragments.PlayerFragment;

import java.io.IOException;
import java.util.ArrayList;


public class PlayerService extends Service {

    final String LOG_T = "PlayerService";
    public final static String ACTION_TAG = "Action";
    public final static int ACTION_IDLE = 0;
    public final static int ACTION_PLAY = 1;
    public final static int ACTION_PAUSE = 2;
    public final static int ACTION_STOP = 3;
    
    public static PlayerService INSTANCE;

    public static MediaPlayer player = new MediaPlayer();
    private int currentSong = 0;
    private ArrayList<Audio> playlist;

    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                if(playlist != null){
                    mediaPlayer.reset();
                    next();
                }
            }
        });
        Log.d(LOG_T, "PLayer created");        
    }

    public int onStartCommand(Intent intent, int flags, int startId) {

        if(intent != null){
            int action = intent.getIntExtra(ACTION_TAG, 0);

            switch (action){
                case ACTION_IDLE : {
                    break;
                }
                case ACTION_PLAY : {
                    play(0);
                    break;
                }
                case ACTION_PAUSE : {
                    pause();
                    break;
                }
                case ACTION_STOP : {
                    stop();
                    break;
                }
                default: {}
            }
        }

        return super.onStartCommand(intent, flags, startId);
    }

    public void onDestroy() {
        INSTANCE = null;
        player.release();
        super.onDestroy();
        Log.d(LOG_T, "Player destroyed");
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void loadPlaylist(ArrayList<com.perm.kate.api.Audio> collection){
        playlist = new ArrayList<Audio>(collection);
    }

    public void play(){
        if(player.isPlaying()){
            player.pause();
            setBackGroundButton(true);
        } else {
            player.start();
            setBackGroundButton(false);
        }
    }

    public void play(int i){    	
        setBackGroundButton(false);

        player.reset();

        Audio audio = null;
        if(playlist != null){
            audio = playlist.get(i);
            currentSong = i;
        }

        try{
            if(audio != null){
                player.setDataSource(new org.geekhub.vkPlayer.utils.Audio(audio).getDataSource(getApplicationContext()));
            } else if( playlist != null ){
                player.setDataSource(new org.geekhub.vkPlayer.utils.Audio(playlist.get(currentSong)).getDataSource(getApplicationContext()));
            } else {
                return;
            }

            player.prepare();
            player.start();
        } catch (IOException e){
            Log.e(LOG_T, "PLayer IOException");
        }
    }
    

    public void next(){
        setBackGroundButton(false);
        currentSong++;
        if(currentSong > (playlist.size()-1)){
            currentSong = 0;
        }
        player.reset();

        try{
            if(playlist != null){
                player.setDataSource(new org.geekhub.vkPlayer.utils.Audio(playlist.get(currentSong)).getDataSource(getApplicationContext()));
            } else {
                return;
            }

            player.prepare();
            player.start();
        } catch (IOException e){
            Log.e(LOG_T, "PLayer IOException");
        }
    }

    public void prev(){    	
        setBackGroundButton(false);

        currentSong--;
        if(currentSong < 0){
            currentSong = playlist.size()-1;
        }
        player.reset();

        try{
            if(playlist != null){
                player.setDataSource(new org.geekhub.vkPlayer.utils.Audio(playlist.get(currentSong)).getDataSource(getApplicationContext()));
            } else {
                return;
            }

            player.prepare();
            player.start();
        } catch (IOException e){
            Log.e(LOG_T, "PLayer IOException");
        }
    }

    public void pause(){
        setBackGroundButton(true);
        if(player.isPlaying()){
            player.pause();
        }
    }

    public void stop(){
        setBackGroundButton(true);
        player.stop();
    }

    public int getDuration(){
        try{
            return player.getDuration();
        } catch (IllegalStateException e){
            return 0;
        }
    }

    public int getCurrentPosition(){
        try{
            return player.getCurrentPosition();
        } catch (IllegalStateException e){
            return 0;
        }
    }

    public void seekTo(int position){
        if(player!= null ){
            player.seekTo(position);
        }
    }

    public Audio getCurrentSong(){
        return (playlist != null && playlist.size() != 0)? playlist.get(currentSong):new Audio();
    }

    public void setBackGroundButton(boolean play){

        if(PlayerService.INSTANCE != null){
            PlayerFragment.INSTANCE.btnPlay.setBackgroundResource(play?R.drawable.btn_play:R.drawable.btn_pause);
        }
    }
}
