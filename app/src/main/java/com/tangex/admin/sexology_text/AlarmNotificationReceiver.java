package com.tangex.admin.sexology_text;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import static android.content.Context.NOTIFICATION_SERVICE;

public class AlarmNotificationReceiver extends BroadcastReceiver {
    private final String CHANNEL_ID = "personal_notifications";
    private final int NOTIFICATION_ID = 001;
    private String noidung;

    int importance = NotificationManager.IMPORTANCE_HIGH;

    @Override
    public void onReceive(Context context, Intent intent) {


         noidung = intent.getStringExtra("noidung");
         Intent i = new Intent(context,AlarmNotification_Service.class);
         i.putExtra("noidung",noidung);
         i.putExtra("vitringay",intent.getIntExtra("vitringay",0));
         i.putExtra("iduser",intent.getStringExtra("iduser"));
         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
             context.stopService(i);
             context.startForegroundService(i);
         }else {context.startService(i);}

//            localPushNotifications26GreaterAPI(context,intent);
//        Intent notificationIntent = new Intent(context, Login.class);
//        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
//        stackBuilder.addNextIntent(notificationIntent);
//        localPushNotifications26GreaterAPI(context,notificationIntent);



    }


    public void localPushNotifications26GreaterAPI(Context context, Intent intent){


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O){
            NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

// The id of the channel.
            String id = "my_channel_01";
            CharSequence name = "iam.peace";
            String description = "Description";
            PendingIntent contentIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationChannel mChannel = new NotificationChannel(id, name,importance);
            mChannel.setDescription(description);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            mNotificationManager.createNotificationChannel(mChannel);
            mNotificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
            int notifyID = 1;
            String CHANNEL_ID = "my_channel_01";
            Notification notification = new Notification.Builder(context,CHANNEL_ID)
                    .setContentTitle("SEFA")
                    .setContentText(noidung)
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.drawable.defaut_avt)
                    .setChannelId(CHANNEL_ID)
                    .setContentIntent(contentIntent)
                    .build();
            mNotificationManager.notify(notifyID, notification);
        } else{
            localPushNotifications26LowerAPI(context);
        }


    }


    public void localPushNotifications26LowerAPI(Context context){

        Intent intent = new Intent(context, Login.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder b = new NotificationCompat.Builder(context);

        b.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.defaut_avt)
                .setTicker("I am Peace")
                .setContentTitle("SEFA")
                .setContentText(noidung)
                .setDefaults(Notification.DEFAULT_LIGHTS| Notification.DEFAULT_SOUND)
                .setContentIntent(contentIntent)
                .setContentInfo("Info");


        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, b.build());
    }

}
