package com.example.a11_9project.ui.lockout.Services;

import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.Nullable;

import com.example.a11_9project.ui.lockout.Broadcast.ReceiverAppBlock;

public class ServiceAppBlock extends IntentService {

    public ServiceAppBlock() {
        super("ServiceAppBlock");
    }

    private void runAppBlock(){

        long endTime = System.currentTimeMillis()+210;
        while(System.currentTimeMillis() < endTime){
            synchronized(this){
                try {
                    Intent intent = new Intent(this, ReceiverAppBlock.class);
                    sendBroadcast(intent);
                    wait(endTime - System.currentTimeMillis());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        runAppBlock();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        BackgroundManager.getInstance().init(this).startService();
        BackgroundManager.getInstance().init(this).startAlarmManager();

        super.onTaskRemoved(rootIntent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }

    @Override
    public void onDestroy() {

        BackgroundManager.getInstance().init(this).startService();
        BackgroundManager.getInstance().init(this).startAlarmManager();
        super.onDestroy();
    }
}
