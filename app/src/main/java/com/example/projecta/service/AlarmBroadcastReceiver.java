package com.example.projecta.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.projecta.R;

public class AlarmBroadcastReceiver extends BroadcastReceiver {

    private final static int NOTIFICATION_ID=222;
    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationCompat.Builder builder= new NotificationCompat.Builder(context,"channel_id")
                .setContentText("text")
                .setContentTitle("Title")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setSmallIcon(R.mipmap.ic_launcher_round);

        NotificationManagerCompat notifiactionmanger= NotificationManagerCompat.from(context);
        notifiactionmanger.notify(NOTIFICATION_ID,builder.build());

    }
}
