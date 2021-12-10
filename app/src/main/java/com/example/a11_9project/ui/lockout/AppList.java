package com.example.a11_9project.ui.lockout;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.a11_9project.R;
import com.example.a11_9project.ui.lockout.Adapter.AppAdapters;
import com.example.a11_9project.ui.lockout.Model.AppItem;
import com.example.a11_9project.ui.lockout.Services.BackgroundManager;
import com.example.a11_9project.ui.lockout.Utils.Utils;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/*
public class AppList extends AppCompatActivity{

    LinearLayout layout_permission;
    BackgroundManager backgroundManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_layout);

        initToolbar();

        initView();

        BackgroundManager.getInstance().init(this).startService();
        BackgroundManager.getInstance().init(this).startAlarmManager();
    }

    private void initView() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view_app);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        AppAdapters appAdapters = new AppAdapters(this, getAllApps());
        recyclerView.setAdapter(appAdapters);

        layout_permission = findViewById(R.id.layout_permission);
    }

    private List<AppItem> getAllApps() {
        List<AppItem> results = new ArrayList<>();

        PackageManager pk = getPackageManager();

        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> resolveInfoList = pk.queryIntentActivities(intent, 0);

        for(ResolveInfo resolveInfo : resolveInfoList){
            ActivityInfo activityInfo = resolveInfo.activityInfo;
            if(!activityInfo.loadLabel(pk).toString().equals("Companion App"))
                results.add(new AppItem(activityInfo.loadIcon(pk), activityInfo.loadLabel(pk).toString(), activityInfo.packageName));
        }

        return results;
    }

    private void initToolbar() {

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);

        getSupportActionBar().setTitle("App List");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == android.R.id.home)
            finish();

        return true;
    }

    public void setPermission(View view) {
        startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));
    }

    @Override
    protected void onResume() {
        if(Utils.checkPermissions(this))
            layout_permission.setVisibility(View.GONE);
        else
            layout_permission.setVisibility(View.VISIBLE);
        super.onResume();
    }
}

 */
/*
public class AppList extends FragmentActivity {
    LinearLayout layout_permission;
    BackgroundManager backgroundManager;
    View root;
    Button button;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //View root = binding.getRoot();
        root = inflater.inflate(R.layout.app_layout, container, false);

        initToolbar();

        initView();

        button = root.findViewById(R.id.permBtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));
            }
        });

        BackgroundManager.getInstance().init(this).startService();
        BackgroundManager.getInstance().init(this).startAlarmManager();

        return root;



    }

    private void initView() {
        RecyclerView recyclerView = root.findViewById(R.id.recycler_view_app);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        AppAdapters appAdapters = new AppAdapters(this, getAllApps());
        recyclerView.setAdapter(appAdapters);

        layout_permission = root.findViewById(R.id.layout_permission);
    }

    private List<AppItem> getAllApps() {
        List<AppItem> results = new ArrayList<>();

        PackageManager pk = this.getPackageManager();

        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> resolveInfoList = pk.queryIntentActivities(intent, 0);

        for(ResolveInfo resolveInfo : resolveInfoList){
            ActivityInfo activityInfo = resolveInfo.activityInfo;
            if(!activityInfo.loadLabel(pk).toString().equals("Companion App"))
                results.add(new AppItem(activityInfo.loadIcon(pk), activityInfo.loadLabel(pk).toString(), activityInfo.packageName));
        }

        return results;
    }

    private void initToolbar() {

        Toolbar toolbar = root.findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        toolbar.setTitleTextColor(Color.WHITE);

        //getSupportActionBar().setTitle("App List");
        toolbar.setTitle("App List");
        //toolbar
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == android.R.id.home)
            finish();

        return true;
    }

    public void setPermission(View view) {
        startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));
    }

    @Override
    protected void onResume() {
        if(Utils.checkPermissions(this))
            layout_permission.setVisibility(GONE);
        else
            layout_permission.setVisibility(VISIBLE);
        super.onResume();
    }

}

 */

public class AppList extends AppCompatActivity{

    LinearLayout layout_permission;
    BackgroundManager backgroundManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_layout);

        initToolbar();

        initView();

        BackgroundManager.getInstance().init(this).startService();
        BackgroundManager.getInstance().init(this).startAlarmManager();
    }

    private void initView() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view_app);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        AppAdapters appAdapters = new AppAdapters(this, getAllApps());
        recyclerView.setAdapter(appAdapters);

        layout_permission = findViewById(R.id.layout_permission);
    }

    private List<AppItem> getAllApps() {
        List<AppItem> results = new ArrayList<>();

        PackageManager pk = getPackageManager();

        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> resolveInfoList = pk.queryIntentActivities(intent, 0);

        for(ResolveInfo resolveInfo : resolveInfoList){
            ActivityInfo activityInfo = resolveInfo.activityInfo;
            if(!activityInfo.loadLabel(pk).toString().equals(R.string.app_name))
                results.add(new AppItem(activityInfo.loadIcon(pk), activityInfo.loadLabel(pk).toString(), activityInfo.packageName));
        }

        return results;
    }

    private void initToolbar() {

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);

        getSupportActionBar().setTitle("App List");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == android.R.id.home)
            finish();

        return true;
    }

    public void setPermission(View view) {
        startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));
    }

    @Override
    protected void onResume() {
        if(Utils.checkPermissions(this))
            layout_permission.setVisibility(View.GONE);
        else
            layout_permission.setVisibility(View.VISIBLE);
        super.onResume();
    }
}