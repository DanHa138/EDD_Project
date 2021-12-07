package com.example.a11_9project.ui.lockout.Broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

import com.example.a11_9project.ui.lockout.LockoutFragment;
import com.example.a11_9project.ui.lockout.Services.BackgroundManager;
import com.example.a11_9project.ui.lockout.Services.ServiceAppBlock;
import com.example.a11_9project.ui.lockout.Utils.Utils;

public class RestartServiceWhenStopped extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, Intent intent) {

        /*boolean lockstate = Utils.getInstance().getBoolean(AppConstants.LOCK_STATE);
        if (intent != null && lockstate){
            String type = intent.getStringExtra("type");
            if(type.contentEquals("lockservice"))
                BackgroundManager.getInstance().init(context).startService(ServiceAppBlock.class);
            else if(type.contentEquals("startlockserviceFromAM")){
                if(!BackgroundManager.getInstance().init(context).isServiceRunning(ServiceAppBlock.class));
                    BackgroundManager.getInstance().init(context).startService(ServiceAppBlock.class);
            }
            BackgroundManager.getInstance().init(context).startAlarmManager();
        }*/

        final Utils utils = new Utils(context);
        final String appRunning = utils.getLauncherTopApp();

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
