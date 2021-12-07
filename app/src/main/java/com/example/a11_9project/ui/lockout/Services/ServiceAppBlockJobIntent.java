package com.example.a11_9project.ui.lockout.Services;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;

import com.example.a11_9project.ui.lockout.Broadcast.ReceiverAppBlock;

public class ServiceAppBlockJobIntent extends JobIntentService {

    private static final int JOB_ID = 15462;

    public static void enqueueWork(Context context, Intent work){
        enqueueWork(context, ServiceAppBlockJobIntent.class, JOB_ID, work);
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent){
            runAppBlock();
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        BackgroundManager.getInstance().init(this).startService();
        BackgroundManager.getInstance().init(this).startAlarmManager();
        super.onTaskRemoved(rootIntent);
    }

    @Override
    public void onDestroy() {
        BackgroundManager.getInstance().init(this).startService();
        BackgroundManager.getInstance().init(this).startAlarmManager();
        super.onDestroy();
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
}
