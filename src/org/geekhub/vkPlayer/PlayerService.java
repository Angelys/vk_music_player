package org.geekhub.vkPlayer;

import android.app.Application;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;
import org.geekhub.vkPlayer.utils.Audio;

import java.io.IOException;


public class PlayerService extends Service {

    final String LOG_T = "PlayerService";
    final String ACTION_TAG = "Action";
    final int ACTION_PLAY = 1;
    final int ACTION_PAUSE = 2;
    final int ACTION_STOP = 3;
    
    final String LOG_TAG = "myLogs";

    public static PlayerService INSTANCE;

    private MediaPlayer player = new MediaPlayer();
    private Audio currentSong;

    public Runnable onComplete;

    public void onCreate() {
    	Log.d(LOG_TAG, "--- PlayerService - onCreate() --- ");
        super.onCreate();
        INSTANCE = this;
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
            	Log.d(LOG_TAG, "--- PlayerService - onCompletion() --- ");
                if(onComplete != null){
                	Log.d(LOG_TAG, "--- PlayerService - onCompletion() ---(onComplete != null) ");
                    onComplete.run();
                }
            }
        });
        Log.d(LOG_T, "PLayer created");
        
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
    	Log.d(LOG_TAG, "--- PlayerService - onStartCommand() --- flags = " + flags + "startId" + startId);

    	Log.d(LOG_TAG, "--- PlayerService - onStartCommand() --- intent = " + intent);
    	
    	
    	int action = intent.getIntExtra(ACTION_TAG, 0);
        Log.d(LOG_TAG, "--- PlayerService - onStartCommand() --- action = " + action);

        switch (action){
            case ACTION_PLAY : {
            	Log.d(LOG_TAG, "--- PlayerService - onStartCommand() --- action = ACTION_PLAY");
                play(null);
            }
            case ACTION_PAUSE : {
            	Log.d(LOG_TAG, "--- PlayerService - onStartCommand() --- action = ACTION_PAUSE");
                pause();
            }
            case ACTION_STOP : {
            	Log.d(LOG_TAG, "--- PlayerService - onStartCommand() --- action = ACTION_STOP");
                stop();
            }
        }
        Log.d(LOG_TAG, "--- PlayerService - onStartCommand() --- return -- flags = " + flags + "-- startId = " + startId);
        return super.onStartCommand(intent, flags, startId);
    }

    public void onDestroy() {
    	Log.d(LOG_TAG, "--- PlayerService - onDestroy() --- ");
        INSTANCE = null;
        player.release();
        super.onDestroy();
        Log.d(LOG_TAG, "--- PlayerService - onDestroy() --- Player destroyed ");
        Log.d(LOG_T, "Player destroyed");
    }

    public IBinder onBind(Intent intent) {
    	Log.d(LOG_TAG, "--- PlayerService - onBind(intent) --- return NULL");
        return null;
    }

    public void play(com.perm.kate.api.Audio a){
    	Log.d(LOG_TAG, "--- PlayerService - play(a) --- ");

        Audio audio = new Audio(a);

        if(player.isPlaying()){
        	Log.d(LOG_TAG, "--- PlayerService - play(a) --- (player.isPlaying())");
            player.reset();
        }

        try{
            if(audio != null){
            	Log.d(LOG_TAG, "--- PlayerService - play(a) --- (audio != null)");
                player.setDataSource(audio.getDataSource(getApplicationContext()));
            } else if(currentSong != null){
            	Log.d(LOG_TAG, "--- PlayerService - play(a) --- (currentSong != null)");
                player.setDataSource(currentSong.getDataSource(getApplicationContext()));
            } else {
            	Log.d(LOG_TAG, "--- PlayerService - play(a) --- (audio != null) - else - RETURN");
                return;
            }

            player.prepare();
            player.start();
        } catch (IOException e){
        	Log.d(LOG_TAG, "--- PlayerService - play(a) --- (audio != null) - (IOException e)");
            Log.e(LOG_T, "PLayer IOException");
        }


    }

    public void pause(){
    	Log.d(LOG_TAG, "--- PlayerService - pause() --- ");
        if(player.isPlaying()){
        	Log.d(LOG_TAG, "--- PlayerService - pause() ---(player.isPlaying()) ");
            player.pause();
        }
    }

    public void stop(){
    	Log.d(LOG_TAG, "--- PlayerService - stop() ---");
        player.stop();
    }
}
