package com.example.a11_9project.ui.home;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.a11_9project.R;
import com.example.a11_9project.databinding.FragmentHomeBinding;
import com.example.a11_9project.databinding.NavHeaderMainBinding;
import com.google.android.material.navigation.NavigationView;

import java.security.Permission;
import java.security.Permissions;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private ArrayList<String> eventsList = new ArrayList<>(), eventsListWeekly = new ArrayList<>();
    private ArrayList<String> dayEvents = new ArrayList<>(), dayEventsWeekly = new ArrayList<>();
    private ArrayAdapter<String> eventsListAdapter, eventsListAdapterWeekly;
    private ListView mListView, mListView2;
    private MutableLiveData<String> mText, eText;
    private SwipeRefreshLayout swipeRefreshLayout;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //View root = binding.getRoot();
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        /*
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);


        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        final TextView textView1 = binding.textView2;
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView1.setText(s);
            }
        });

         */

        if (ContextCompat.checkSelfPermission(
                getContext(), Manifest.permission.READ_CALENDAR) ==
                PackageManager.PERMISSION_GRANTED) {
            // You can use the API that requires the permission.
            //performAction(...);
        //} else if (shouldShowRequestPermissionRationale()) {
            // In an educational UI, explain to the user why your app requires this
            // permission for a specific feature to behave as expected. In this UI,
            // include a "cancel" or "no thanks" button that allows the user to
            // continue using your app without granting the permission.
            //showInContextUI(...);
        } else {
            // You can directly ask for the permission.
            // The registered ActivityResultCallback gets the result of this request.
            requestPermissionLauncher.launch(
                    Manifest.permission.READ_CALENDAR);
        }


        TextView dailyView = root.findViewById(R.id.text_home);
        TextView weeklyView = root.findViewById(R.id.textView2);
        swipeRefreshLayout = root.findViewById(R.id.homeSwipe);
        mText = new MutableLiveData<>();
        eText = new MutableLiveData<>();
        mText.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                dailyView.setText(s);
            }
        });


        eText.observe( getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                weeklyView.setText(s);
            }
        });

        mText.setValue("Today's Tasks and Events");
        eText.setValue("This Week's Tasks and Events");




        //List for Today's events

        mListView = root.findViewById(R.id.list_home);

        eventsListAdapter = new ArrayAdapter<>
                (HomeFragment.this.getContext(), android.R.layout.simple_list_item_1, eventsList);
        mListView.setAdapter(eventsListAdapter);

        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);

        String[] projection = new String[] { CalendarContract.Events.CALENDAR_ID, CalendarContract.Events.TITLE, CalendarContract.Events.DESCRIPTION, CalendarContract.Events.DTSTART, CalendarContract.Events.DTEND, CalendarContract.Events.ALL_DAY, CalendarContract.Events.EVENT_LOCATION };

        // 0 = January, 1 = February, ...

        java.util.Calendar startTime = java.util.Calendar.getInstance();
        startTime.set(year,month,day-1,00,00);

        java.util.Calendar endTime= java.util.Calendar.getInstance();
        endTime.set(year,month,day,00,00);

        // the range is based on the click date

        String selection = "(( " + CalendarContract.Events.DTSTART + " >= " + startTime.getTimeInMillis() + " ) AND ( " + CalendarContract.Events.DTSTART + " <= " + endTime.getTimeInMillis() + " ))";

        Cursor cursor = root.getContext().getContentResolver().query( CalendarContract.Events.CONTENT_URI, projection, selection, null, null );

        // output the events

        if (cursor.moveToFirst()) {
            do {
                String event = "Title: " + cursor.getString(1)
                        + "\nDue Date: " + (new Date(cursor.getLong(3)))
                        .toString();
                if((new Date(cursor.getLong(3))).toString().contains("19:00:00 EST")) {
                    if ((new Date(cursor.getLong(4))).toString().contains("19:00:00 EST")) {
                        event = "Title: " + cursor.getString(1)
                                + "\nWhole Day Event: " +(month +1)+ "/" + day + "/"
                                + year;
                    }
                }
                String data = cursor.getString(2);
                boolean inAlready = false;
                if(!eventsList.isEmpty())
                {

                    for(int loop = 0; loop < eventsList.size() && !inAlready; loop++)
                    {
                        if(eventsList.get(loop).toString().equals(event))
                        {
                            inAlready = true;
                        }
                    }

                }

                if(!inAlready)
                {
                    eventsList.add(event);
                    dayEvents.add(data);


                }


                //Toast.makeText( view.getContext().getApplicationContext(), "Title: " + cursor.getString(1) + " Start-Time: " + (new Date(cursor.getLong(3))).toString(), Toast.LENGTH_LONG ).show();

                eventsListAdapter.notifyDataSetChanged();
            } while ( cursor.moveToNext());
        }

        if(eventsList.isEmpty())
        {
            eventsList.add("There are no events scheduled for today.");
            eventsListAdapter.notifyDataSetChanged();
        }

        //Weekly view

        mListView2 = root.findViewById(R.id.list_home_2);

        eventsListAdapterWeekly = new ArrayAdapter<>
                (HomeFragment.this.getContext(), android.R.layout.simple_list_item_1, eventsListWeekly);
        mListView2.setAdapter(eventsListAdapterWeekly);


        String[] projectionWeekly = new String[] { CalendarContract.Events.CALENDAR_ID, CalendarContract.Events.TITLE, CalendarContract.Events.DESCRIPTION, CalendarContract.Events.DTSTART, CalendarContract.Events.DTEND, CalendarContract.Events.ALL_DAY, CalendarContract.Events.EVENT_LOCATION };

        // 0 = January, 1 = February, ...

        for(int dayLoop = day-1; dayLoop <= day+5;dayLoop++)
        {
            java.util.Calendar startTimeWeekly = java.util.Calendar.getInstance();
            startTimeWeekly.set(year,month,dayLoop,00,00);

            java.util.Calendar endTimeWeekly= java.util.Calendar.getInstance();
            endTimeWeekly.set(year,month,dayLoop+1,00,00);

            // the range is based on the click date

            String selectionWeekly = "(( " + CalendarContract.Events.DTSTART + " >= " + startTimeWeekly.getTimeInMillis() + " ) AND ( " + CalendarContract.Events.DTSTART + " <= " + endTimeWeekly.getTimeInMillis() + " ))";

            Cursor cursorWeekly = root.getContext().getContentResolver().query( CalendarContract.Events.CONTENT_URI, projectionWeekly, selectionWeekly, null, null );

            // output the events

            if (cursorWeekly.moveToFirst()) {
                do {
                    String event = "Title: " + cursorWeekly.getString(1)
                            + "\nDue Date: " + (new Date(cursorWeekly.getLong(3)))
                            .toString();
                    if((new Date(cursorWeekly.getLong(3))).toString().contains(":00:00 EST")) {
                        if ((new Date(cursorWeekly.getLong(4))).toString().contains(":00:00 EST")) {
                            event = "Title: " + cursorWeekly.getString(1)
                                    + "\nWhole Day Event: " +(month +1)+ "/" + (dayLoop+1) + "/"
                                    + year;

                        }
                        if(!cursorWeekly.getString(2).isEmpty()){
                            event = "Title: " + cursorWeekly.getString(1)
                                    + "\nWhole Day Assignment: " +(month +1)+ "/" + (dayLoop+1) + "/"
                                    + year;
                        }
                    }
                    String data = cursorWeekly.getString(2);
                    boolean inAlready = false;
                    if(!eventsListWeekly.isEmpty())
                    {

                        for(int loop = 0; loop < eventsListWeekly.size() && !inAlready; loop++)
                        {
                            if(eventsListWeekly.get(loop).toString().equals(event))
                            {
                                inAlready = true;
                            }
                        }

                    }

                    if(!inAlready)
                    {
                        eventsListWeekly.add(event);
                        dayEventsWeekly.add(data);


                    }


                    //Toast.makeText( view.getContext().getApplicationContext(), "Title: " + cursor.getString(1) + " Start-Time: " + (new Date(cursor.getLong(3))).toString(), Toast.LENGTH_LONG ).show();

                    eventsListAdapterWeekly.notifyDataSetChanged();
                } while ( cursorWeekly.moveToNext());
            }
        }

        if(eventsListWeekly.isEmpty())
        {
            eventsListWeekly.add("There are no upcoming events.");
            eventsListAdapterWeekly.notifyDataSetChanged();
        }

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                eventsList.clear();
                Intent i = getActivity().getIntent();
                getActivity().finish();
                startActivity(i);
            }
        });

        /*

        NavigationView navigationView = getView().findViewById(R.id.nav_view);
        View headView = navigationView.getHeaderView(0);
        TextView emailView = headView.findViewById(R.id.textView);
        TextView nameView = headView.findViewById(R.id.PersonName);
        mText = new MutableLiveData<>();
        eText = new MutableLiveData<>();
        mText.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                nameView.setText(s);
            }
        });


        eText.observe( getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                emailView.setText(s);
            }
        });
        mText.setValue("Sign in with Google");
        eText.setValue("In the Settings Tab");





         */




        return root;
    }

    private ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    // Permission is granted. Continue the action or workflow in your
                    // app.
                } else {
                    // Explain to the user that the feature is unavailable because the
                    // features requires a permission that the user has denied. At the
                    // same time, respect the user's decision. Don't link to system
                    // settings in an effort to convince the user to change their
                    // decision.
                }
            });

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}