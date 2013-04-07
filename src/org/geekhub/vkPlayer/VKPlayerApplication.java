package org.geekhub.vkPlayer;

import android.app.Application;
import android.content.Intent;

/**
 * Created with IntelliJ IDEA.
 * User: angelys
 * Date: 4/7/13
 * Time: 9:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class VKPlayerApplication extends Application {

    public void onCreate(){
        startService(new Intent(this, PlayerService.class));
        super.onCreate();
    }

}
