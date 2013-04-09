package org.geekhub.vkPlayer.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: angelys
 * Date: 4/9/13
 * Time: 9:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class Audio extends com.perm.kate.api.Audio {

    public Audio(com.perm.kate.api.Audio a){
        aid = a.aid;
        owner_id = a.owner_id;
        artist = a.artist;
        title = a.title;
        duration = a.duration;
        url = a.url;
        lyrics_id = a.lyrics_id;
    }

    public boolean isSaved(Context context){
        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC+"/vk_music_player"), getFileName() );

            return file.exists();

        } else {
            return false;
        }
    }

    public void save(){
        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Downloader.DownloadFromUrl(url, getFileName());
                }
            }).start();
        }
    }

    public String getFilePath(Context context){
        if(isSaved(context)){
            return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC+"/vk_music_player").getAbsolutePath()+"/"+getFileName();
        }
        return "";
    }

    public String getFileName(){
        return artist+'-'+title+'.'+aid+".mp3";
    }

    public String getDataSource(Context context){

        if(!isSaved(context)){
            save();
        }

        return isSaved(context)?getFilePath(context):url;
    }

}
