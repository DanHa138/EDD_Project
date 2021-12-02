package com.example.a11_9project.ui.lockout.Utils;

import static android.app.AppOpsManager.MODE_ALLOWED;

import android.app.AppOpsManager;
import android.content.Context;
import android.os.Process;

public class Utils {

    public static boolean checkPermissions(Context context){
        AppOpsManager appOpsManager = (AppOpsManager)context.getSystemService(Context.APP_OPS_SERVICE);
        int mode = appOpsManager.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, Process.myUid(), context.getPackageName());

        return mode == MODE_ALLOWED;
    }

}
