package org.geekhub.vkPlayer.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;


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
            File file = new File(context.getFilesDir(), getFileName() );

            return file.exists();
        }
    }

    public void save(final Context context){
        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Downloader.DownloadFromUrlExternal(url, getFileName());
                }
            }).start();
        } else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Downloader.DownloadFromUrlInternal(context, url, getFileName());
                }
            }).start();
        }
    }

    public String getFilePath(Context context){
        if(isSaved(context)){
            if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
                return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC+"/vk_music_player").getAbsolutePath()+"/"+getFileName();
            } else {
                return context.getFilesDir().getAbsolutePath()+'/'+getFileName();
            }
        }
        return "";
    }

    public String getFileName(){
        return artist+'-'+title+'.'+aid+".mp3";
    }

    public String getDataSource(Context context){
        return isSaved(context)?getFilePath(context):url;
    }

}
