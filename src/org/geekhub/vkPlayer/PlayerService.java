package org.geekhub.vkPlayer;

import android.app.Application;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;
import com.perm.kate.api.Audio;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: angelys
 * Date: 4/6/13
 * Time: 11:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class PlayerService extends Service {

    final String LOG_TAG = "PlayerService";
    final String ACTION_TAG = "Action";
    final int ACTION_PLAY = 1;
    final int ACTION_PAUSE = 2;
    final int ACTION_STOP = 3;

    public static PlayerService INSTANCE;

    private MediaPlayer player = new MediaPlayer();
    private Audio currentSong;

    public Runnable onComplete;

    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                if(onComplete != null){
                    onComplete.run();
                }
            }
        });
        Log.d(LOG_TAG, "PLayer created");
    }

    public int onStartCommand(Intent intent, int flags, int startId) {

        int action = intent.getIntExtra(ACTION_TAG, 0);

        switch (action){
            case ACTION_PLAY : {
                play(null);
            }
            case ACTION_PAUSE : {
                pause();
            }
            case ACTION_STOP : {
                stop();
            }
        }

        return super.onStartCommand(intent, flags, startId);
    }

    public void onDestroy() {
        INSTANCE = null;
        player.release();
        super.onDestroy();
        Log.d(LOG_TAG, "Player destroyed");
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void play(Audio audio){
        if(player.isPlaying()){
            player.reset();
        }

        try{
            if(audio != null){
                player.setDataSource(audio.url);
            } else if(currentSong != null){
                player.setDataSource(currentSong.url);
            } else {
                return;
            }

            player.prepare();
            player.start();
        } catch (IOException e){
            Log.e(LOG_TAG, "PLayer IOException");
        }


    }

    public void pause(){
        if(player.isPlaying()){
            player.pause();
        }
    }

    public void stop(){
        player.stop();
    }
}
