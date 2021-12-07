package com.example.a11_9project.ui.lockout.Services;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;

import androidx.annotation.RequiresApi;

import com.example.a11_9project.ui.lockout.Broadcast.RestartServiceWhenStopped;

public class BackgroundManager {

    //15 minutes
    private static final int period = 1000;
    private static final int ALARM_ID = 159874;

    private static BackgroundManager instance;
    private Context context;
    private Settings settings;

    public static BackgroundManager getInstance(){
        if(instance == null)
            instance = new BackgroundManager();

        return instance;
    }

    public BackgroundManager init(Context c){
        context = c;
        return this;
    }

    public boolean isServiceRunning(Class<?> serviceClass){

        ActivityManager manager = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        for(ActivityManager.RunningServiceInfo serviceInfo : manager.getRunningServices(Integer.MAX_VALUE)){
            if(serviceClass.getName().equals(serviceInfo.service.getClassName())){
                return true;
            }
        }

        return false;
    }

    public void startService(Class<?> serviceClass){

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            /*if(!isServiceRunning(ServiceAppBlockJobIntent.class)){
                Intent intent = new Intent(context, ServiceAppBlockJobIntent.class);
                ServiceAppBlockJobIntent.enqueueWork(context, intent);
            }*/
            startForegroundServices(serviceClass);
        }
        else{
            context.startService(new Intent(context, serviceClass));
        }

    }

    public void startService(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (!isServiceRunning(ServiceAppBlockJobIntent.class)) {
                Intent intent = new Intent(context, ServiceAppBlockJobIntent.class);
                ServiceAppBlockJobIntent.enqueueWork(context, intent);
            }
        } else {
            if (!isServiceRunning(ServiceAppBlock.class)) {
                context.startService(new Intent(context, ServiceAppBlock.class));
            }
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void startForegroundServices(Class<?> serviceClass){
        context.startForegroundService(new Intent(context, serviceClass));
    }

    public void stopService(Class<?> serviceClass){
        if(isServiceRunning(serviceClass)){
            context.stopService(new Intent(context, serviceClass));
        }
    }

    public void startAlarmManager(){

        Intent intent = new Intent(context, RestartServiceWhenStopped.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ALARM_ID, intent, 0);
        AlarmManager manager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        manager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+period, pendingIntent);

    }

    public void stopAlarm(){

        Intent intent = new Intent(context, RestartServiceWhenStopped.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ALARM_ID, intent, 0);
        AlarmManager manager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        manager.cancel(pendingIntent);

    }

}
