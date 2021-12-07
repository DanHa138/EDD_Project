package com.example.a11_9project.ui.lockout.Broadcast;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.example.a11_9project.ui.lockout.AppList;
import com.example.a11_9project.ui.lockout.LockoutFragment;
import com.example.a11_9project.ui.lockout.Services.BackgroundManager;
import com.example.a11_9project.ui.lockout.Services.ServiceAppBlock;
import com.example.a11_9project.ui.lockout.Utils.Utils;

public class ReceiverAppBlock extends BroadcastReceiver {
    @Override
    public void onReceive(@NonNull Context context, Intent intent) {

        /*BackgroundManager.getInstance().init(context).startService(AppList.class);
        if(Utils.getInstance().getBoolean(AppConstants.LOCK_STATE, false)){
            BackgroundManager.getInstance().init(context).startService(ServiceAppBlock.class);
            BackgroundManager.getInstance().init(context).startAlarmManager();
        }*/

        Utils utils = new Utils(context);
        String appRunning = utils.getLauncherTopApp();

        if(utils.isBlock(appRunning)) {
            if (!appRunning.equals(utils.getLastApp())) {
                utils.clearLastApp();
                utils.setLastApp(appRunning);

                Intent i = new Intent(context, LockoutFragment.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i.putExtra("broadcast_receiver", "broadcast_receiver");
                context.startActivity(i);
            }
        }



    }
}
