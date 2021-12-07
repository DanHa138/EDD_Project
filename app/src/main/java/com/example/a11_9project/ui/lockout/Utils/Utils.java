package com.example.a11_9project.ui.lockout.Utils;

import static android.app.AppOpsManager.MODE_ALLOWED;

import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.app.usage.UsageEvents;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Process;
import android.text.TextUtils;

import androidx.annotation.RequiresApi;

import java.util.List;

import io.paperdb.Paper;

public class Utils {
    private volatile static Utils instance;

    private static final String TAG ="Util";
    private String EXTRA_LAST_APP = "EXTRA_LAST_APP";
    private Context context;
    private SharedPreferences preferences;

    public Utils(Context c){
        this.context = c;
        Paper.init(c);
    }

    private Utils(){
    }

    public boolean isBlock(String packageName){
        return Paper.book().read(packageName) != null;
    }

    public void lock(String pk){
        Paper.book().write(pk, pk);
    }

    public void unlock(String pk){
        Paper.book().delete(pk);
    }

    public void setLastApp(String pk){
        Paper.book().write(EXTRA_LAST_APP, pk);
    }

    public String getLastApp(){
        return Paper.book().read(EXTRA_LAST_APP);
    }

    public void clearLastApp(){
        Paper.book().delete(EXTRA_LAST_APP);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static boolean checkPermissions(Context c){
        AppOpsManager appOpsManager = (AppOpsManager)c.getSystemService(Context.APP_OPS_SERVICE);
        int mode = appOpsManager.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, Process.myUid(), c.getPackageName());

        return mode == MODE_ALLOWED;
    }

    public static Utils getInstance(){
        if(null == instance){
            synchronized (Utils.class){
                if(null == instance){
                    instance = new Utils();
                }
            }
        }
        return instance;
    }

    UsageStatsManager usageStatsManager;
    public String getLauncherTopApp(){
        ActivityManager manager = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
            usageStatsManager = (UsageStatsManager)context.getSystemService(Context.USAGE_STATS_SERVICE);

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            List<ActivityManager.RunningTaskInfo> taskInfoList = manager.getRunningTasks(1);
            if (null != taskInfoList && !taskInfoList.isEmpty()) {
                return taskInfoList.get(0).topActivity.getPackageName();
            }
        }
        else {
            long endTime = System.currentTimeMillis();
            long beginTIme = endTime - 10000;

            String result = "";
            UsageEvents.Event event = new UsageEvents.Event();
            UsageEvents usageEvents = usageStatsManager.queryEvents(beginTIme, endTime);

            while (usageEvents.hasNextEvent()) {
                usageEvents.getNextEvent(event);
                if (event.getEventType() == UsageEvents.Event.MOVE_TO_FOREGROUND) {
                    result = event.getPackageName();
                }
            }

            if (!TextUtils.isEmpty(result))
                return result;
        }
        return "";
    }

    public boolean getBoolean(String key, boolean def) {
        return preferences.getBoolean(key, def);
    }

    public boolean getBoolean(String key) {
        return preferences.getBoolean(key, false);
    }
}
