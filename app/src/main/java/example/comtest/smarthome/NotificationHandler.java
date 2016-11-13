package example.comtest.smarthome;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Albin on 2016-11-13.
 */



public class NotificationHandler {

    private NotificationCompat.Builder mBuilder;
    private Context mContext;
    private NotificationManager notificationManager;
    private int id;

    public NotificationHandler(Context context, int setIcon, String setTitle, String setContent) {
                mContext = context;
                mBuilder =
                new NotificationCompat.Builder(mContext)
                        .setSmallIcon(setIcon)
                        .setContentTitle(setTitle)
                        .setContentText(setContent);
    }

    public void startNotification(){
        Notification n = mBuilder.build();
        notificationManager =
                (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);

        Random rn = new Random();
        int range = 9999 - 1 + 1;
        int randomNum =  rn.nextInt(range) + 1;
        id = randomNum;

        notificationManager.notify(id, n);
    }

    public int getID(){

        return id;
    }

    public void updateText(String text){
        mBuilder.setContentText(text);
        notificationManager.notify(id, mBuilder.build());
    }


}
