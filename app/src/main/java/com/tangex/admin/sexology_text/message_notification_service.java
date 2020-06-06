package com.tangex.admin.sexology_text;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class message_notification_service extends Service {

    private int importance = NotificationManager.IMPORTANCE_HIGH;
    private DatabaseReference ref_user_wants_tochat;
    private DatabaseReference ref_check;
    private String iduser;
    private String user_wants_to_chat;

    public message_notification_service() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        ref_user_wants_tochat = FirebaseDatabase.getInstance().getReference();
        ref_check = FirebaseDatabase.getInstance().getReference();
        iduser = intent.getStringExtra("iduser");
        user_wants_to_chat = intent.getStringExtra("user_wants_to_chat");

        ref_user_wants_tochat.child("Mess").child(user_wants_to_chat).child(iduser).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                final String get_UserName = dataSnapshot.child("to_user").getValue().toString();
                Toast.makeText(message_notification_service.this, "hhhhhhhhhhhhh", Toast.LENGTH_SHORT).show();
                ref_check.child("User").child(user_wants_to_chat).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot3) {
                        if (get_UserName != dataSnapshot3.child("userName").getValue().toString()){
                            localPushNotifications26GreaterAPI("Bạn có tin nhắn mới !!!");
                            Toast.makeText(message_notification_service.this, "new mess", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return Service.START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void localPushNotifications26GreaterAPI( String noidung) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationManager mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

// The id of the channel.
            String id = "my_channel_2";
            CharSequence name = "iam.peace";
            String description = "Description";

            Intent intent = new Intent(this, Home.class);
            PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationChannel mChannel = new NotificationChannel(id, name, importance);
            mChannel.setDescription(description);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            mNotificationManager.createNotificationChannel(mChannel);
            mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
            int notifyID = 2;
            String CHANNEL_ID = "my_channel_2";
            Notification notification = new Notification.Builder(this, CHANNEL_ID)
                    .setContentTitle("SEFA")
                    .setContentText(noidung)
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.drawable.defaut_avt)
                    .setChannelId(CHANNEL_ID)
                    .build();
            mNotificationManager.notify(notifyID, notification);
        } else {
            localPushNotifications26LowerAPI(this, noidung);
        }
    }


    public void localPushNotifications26LowerAPI(Context context, String noidung) {

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
