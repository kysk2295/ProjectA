package com.example.projecta.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.NonNull;

import com.example.projecta.MainActivity;
import com.firebase.jobdispatcher.JobService;

public class NotificationJobFireBaseService extends JobService {

    @Override
    public boolean onStartJob(@NonNull com.firebase.jobdispatcher.JobParameters job) {
        AlarmManager manager=(AlarmManager) getSystemService(ALARM_SERVICE);

        Intent intent= new Intent(this,AlarmBroadcastReceiver.class);
        PendingIntent pendingIntent= PendingIntent.getBroadcast(getApplicationContext(),111,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            manager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()+10000,pendingIntent);
        }else if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
            manager.setExact(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()+10000,pendingIntent);
        }else{
            manager.set(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()+10000,pendingIntent);
        }
        return false;
    }

    @Override
    public boolean onStopJob(@NonNull com.firebase.jobdispatcher.JobParameters job) {
        return false;
    }
}
