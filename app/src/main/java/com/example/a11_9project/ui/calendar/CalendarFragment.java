package com.example.a11_9project.ui.calendar;

import static android.content.Context.ACCOUNT_SERVICE;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


import com.example.a11_9project.MainActivity;
import com.example.a11_9project.R;
import com.example.a11_9project.databinding.FragmentCalendarBinding;
import com.example.a11_9project.ui.schedule.Model.ToDoModel;
import com.example.a11_9project.ui.schedule.ScheduleFragment;
import com.example.a11_9project.ui.schedule.ScheduleViewModel;
import com.example.a11_9project.ui.settings.SettingsFragment;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.credentials.CredentialRequest;
import com.google.android.gms.auth.api.credentials.CredentialRequestResponse;
import com.google.android.gms.auth.api.credentials.Credentials;
import com.google.android.gms.auth.api.credentials.CredentialsClient;
import com.google.android.gms.auth.api.credentials.IdentityProviders;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonString;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.calendar.Calendar.Events.CalendarImport;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
//import java.util.Calendar;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import me.everything.providers.android.calendar.CalendarProvider;


public class CalendarFragment extends Fragment{

    private CalendarViewModel calendarViewModel;
    private FragmentCalendarBinding binding;
    private  static final String TAG = "CalendarActivity";
    private CalendarView mCalendarView;
    private String pattern = "dd/MM/yyyy";
    private ListView mListView;
    private int sDay, sMonth, sYear;
    private String info1, info2;
    private ArrayList<String> dayEvents = new ArrayList<>();


    private MutableLiveData<String> mText;

    private ArrayList<String> eventsList = new ArrayList<>();
    private ArrayAdapter<String> eventsListAdapter;




    private CalendarService calendarProvider = new CalendarService();
    private CredentialsClient mCredentialsApiClient, mCredentialsClient;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        calendarViewModel =
                new ViewModelProvider(this).get(CalendarViewModel.class);

        binding = FragmentCalendarBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mText = new MutableLiveData<>();

        final TextView textView = binding.textCalendar;
        mText.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        mText.setValue("Tap on Day to get Agenda");

        mCalendarView = (CalendarView) root.findViewById(R.id.calendarView);
        mListView = root.findViewById(R.id.listView);

        eventsListAdapter = new ArrayAdapter<>
                (CalendarFragment.this.getContext(), android.R.layout.simple_list_item_1, eventsList);
        mListView.setAdapter(eventsListAdapter);

        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                eventsList.clear();
                dayEvents.clear();
                eventsListAdapter.notifyDataSetChanged();

                mListView.refreshDrawableState();

                sDay = dayOfMonth;
                sMonth = month;
                sYear = year;



                String date = year + "/" + (month + 1) + "/"+ dayOfMonth ;
                mText.setValue("Agenda for "+ date);

                String[] projection = new String[] { CalendarContract.Events.CALENDAR_ID, CalendarContract.Events.TITLE, CalendarContract.Events.DESCRIPTION, CalendarContract.Events.DTSTART, CalendarContract.Events.DTEND, CalendarContract.Events.ALL_DAY, CalendarContract.Events.EVENT_LOCATION };

                // 0 = January, 1 = February, ...

                java.util.Calendar startTime = java.util.Calendar.getInstance();
                startTime.set(year,month,dayOfMonth,00,00);

                java.util.Calendar endTime= java.util.Calendar.getInstance();
                endTime.set(year,month,dayOfMonth+1,00,00);

                // the range is based on the click date

                String selection = "(( " + CalendarContract.Events.DTSTART + " >= " + startTime.getTimeInMillis() + " ) AND ( " + CalendarContract.Events.DTSTART + " <= " + endTime.getTimeInMillis() + " ))";

                Cursor cursor = view.getContext().getContentResolver().query( CalendarContract.Events.CONTENT_URI, projection, selection, null, null );

                // output the events





                if (cursor.moveToFirst()) {
                    do {
                        String event = "Title: " + cursor.getString(1)
                                + " Due Date: " + (new Date(cursor.getLong(4)))
                                .toString();
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


            }

        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getActivity(), CalendarDetails.class);
                i.putExtra("title", eventsList.get(position));
                i.putExtra("data", dayEvents.get(position));
                startActivity(i);
            }
        });




        return root;
    }







    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}


