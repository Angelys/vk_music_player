package org.geekhub.vkPlayer.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import org.apache.http.util.ByteArrayBuffer;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;


public class Downloader {

    public static void DownloadFromUrlExternal(String DownloadUrl, String fileName) {

        try {
            File root = android.os.Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC);

            File dir = new File(root.getAbsolutePath() + "/vk_music_player");
            if (!dir.exists()) {
                dir.mkdirs();
            }

            URL url = new URL(DownloadUrl); //you can write here any link
            File file = new File(dir, fileName);

            long startTime = System.currentTimeMillis();
            Log.d("DownloadManager", "download begining");
            Log.d("DownloadManager", "download url:" + url);
            Log.d("DownloadManager", "downloaded file name:" + fileName);

            /* Open a connection to that URL. */
            URLConnection ucon = url.openConnection();

            /*
            * Define InputStreams to read from the URLConnection.
            */
            InputStream is = ucon.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);

            /*
            * Read bytes to the Buffer until there is nothing more to read(-1).
            */
            ByteArrayBuffer baf = new ByteArrayBuffer(5000);
            int current = 0;
            while ((current = bis.read()) != -1) {
                baf.append((byte) current);
            }


            /* Convert the Bytes read to a String. */
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(baf.toByteArray());
            fos.flush();
            fos.close();
            Log.d("DownloadManager", "download ready in" + ((System.currentTimeMillis() - startTime) / 1000) + " sec");

        } catch (IOException e) {
            Log.d("DownloadManager", "Error: " + e);
        }

    }

    public static void DownloadFromUrlInternal(Context context, String DownloadUrl, String fileName){
        try {
            URL url = new URL(DownloadUrl); //you can write here any link

            long startTime = System.currentTimeMillis();
            Log.d("DownloadManager", "download begining");
            Log.d("DownloadManager", "download url:" + url);
            Log.d("DownloadManager", "downloaded file name:" + fileName);

            /* Open a connection to that URL. */
            URLConnection ucon = url.openConnection();

            /*
            * Define InputStreams to read from the URLConnection.
            */
            InputStream is = ucon.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);

            /*
            * Read bytes to the Buffer until there is nothing more to read(-1).
            */
            ByteArrayBuffer baf = new ByteArrayBuffer(5000);
            int current = 0;
            while ((current = bis.read()) != -1) {
                baf.append((byte) current);
            }

            FileOutputStream outputStream;

            try {
                outputStream = context.openFileOutput(fileName, Context.MODE_WORLD_READABLE);
                outputStream.write(baf.buffer());
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.d("DownloadManager", "download ready in" + ((System.currentTimeMillis() - startTime) / 1000) + " sec");
        }catch (IOException e){
            Log.d("DownloadManager", "Error: " + e);
        }
    }

}
