package com.tangex.admin.sexology_text;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

public class AlarmNotification_Service extends Service {
    int importance = NotificationManager.IMPORTANCE_HIGH;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final String noidung = intent.getStringExtra("noidung");
        final int vitringay = intent.getIntExtra("vitringay",0);
        final String iduser = intent.getStringExtra("iduser");
        localPushNotifications26GreaterAPI(noidung,vitringay,iduser);
        return Service.START_STICKY;
    }

    public void localPushNotifications26GreaterAPI(String noidung,int vitringay,String iduser) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationManager mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

// The id of the channel.
            String id = "my_channel_01";
            CharSequence name = "iam.peace";
            String description = "Description";

            Intent intent = new Intent(this, Notification_act.class);
            intent.putExtra("vitringay",vitringay);
            intent.putExtra("iduser",iduser);
            PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationChannel mChannel = new NotificationChannel(id, name, importance);
            mChannel.setDescription(description);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            mNotificationManager.createNotificationChannel(mChannel);
            mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
            int notifyID = 1;
            String CHANNEL_ID = "my_channel_01";

            if (vitringay>0){
                Notification notification = new Notification.Builder(this, CHANNEL_ID)
                        .setContentTitle("SEFA")
                        .setContentText(noidung)
                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(R.drawable.defaut_avt)
                        .setChannelId(CHANNEL_ID)
                        .setContentIntent(contentIntent)
                        .build();
                mNotificationManager.notify(notifyID, notification);
                startForeground(1,notification);
            } else {
                Notification notification = new Notification.Builder(this, CHANNEL_ID)
                        .setContentTitle("SEFA")
                        .setContentText(noidung)
                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(R.drawable.defaut_avt)
                        .setChannelId(CHANNEL_ID)
                        .setAutoCancel(false)
                        .build();
                mNotificationManager.notify(notifyID, notification);
                startForeground(2,notification);
            }
        } else {
            localPushNotifications26LowerAPI(this, noidung,vitringay);
        }
    }


    public void localPushNotifications26LowerAPI(Context context, String noidung, int vitringay) {

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
                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND)
                .setContentInfo("Info");


        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, b.build());
    }

}
