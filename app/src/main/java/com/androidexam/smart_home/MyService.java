package com.androidexam.smart_home;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Random;

public class MyService extends Service {

    private static final int NOTIFICATION_ID = 1;
    private static final String CHANNEL_ID = "MyServiceChannel";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        createNotificationChannel();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("CẢNH BÁO")
                .setContentText("Có Lửa Lớn Trong Nhà")
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.iconfire));

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(new Random().nextInt(), builder.build());

        startForeground(NOTIFICATION_ID, builder.build());
        //stopForeground(true);

        return START_STICKY;

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "My Service Channel", NotificationManager.IMPORTANCE_LOW);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


}