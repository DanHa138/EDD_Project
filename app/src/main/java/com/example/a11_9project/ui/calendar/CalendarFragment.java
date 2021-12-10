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
import android.widget.Button;
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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import com.example.a11_9project.MainActivity;
import com.example.a11_9project.R;
import com.example.a11_9project.databinding.FragmentCalendarBinding;
import com.example.a11_9project.ui.schedule.Model.ToDoModel;
import com.example.a11_9project.ui.schedule.ScheduleFragment;
import com.example.a11_9project.ui.schedule.ScheduleViewModel;
import com.example.a11_9project.ui.settings.SettingsFragment;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonString;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.Calendar.Events.CalendarImport;
//import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
//import com.google.api.services.calendar.model.Events;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
//import java.util.Calendar;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import me.everything.providers.android.calendar.CalendarProvider;


public class CalendarFragment extends Fragment {

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
    private FloatingActionButton mAddEvent;
    private SwipeRefreshLayout swipeRefreshLayout;

    private static final String APPLICATION_NAME = "Companion App";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES = Collections.singletonList(CalendarScopes.CALENDAR_READONLY);
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";




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
        mAddEvent = root.findViewById(R.id.addEventBtn);

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





                String date = (month + 1) + "/"+ dayOfMonth + "/" + year ;
                mText.setValue("Agenda for "+ date);

                String[] projection = new String[] { CalendarContract.Events.CALENDAR_ID, CalendarContract.Events.TITLE, CalendarContract.Events.DESCRIPTION, CalendarContract.Events.DTSTART, CalendarContract.Events.DTEND, CalendarContract.Events.ALL_DAY, CalendarContract.Events.EVENT_LOCATION };

                // 0 = January, 1 = February, ...

                java.util.Calendar startTime = java.util.Calendar.getInstance();
                startTime.set(year,month,dayOfMonth-1,00,00);

                java.util.Calendar endTime= java.util.Calendar.getInstance();
                endTime.set(year,month,dayOfMonth,00,00);

                // the range is based on the click date

                String selection = "(( " + CalendarContract.Events.DTSTART + " >= " + startTime.getTimeInMillis() + " ) AND ( " + CalendarContract.Events.DTSTART + " <= " + endTime.getTimeInMillis() + " ))";

                Cursor cursor = view.getContext().getContentResolver().query( CalendarContract.Events.CONTENT_URI, projection, selection, null, null );

                // output the events





                if (cursor.moveToFirst()) {
                    do {
                        String event = "Title: " + cursor.getString(1)
                                + "\nDue Date: " + (new Date(cursor.getLong(3)))
                                .toString();
                        if((new Date(cursor.getLong(3))).toString().contains("19:00:00 EST")) {
                            if ((new Date(cursor.getLong(4))).toString().contains("19:00:00 EST")) {
                                event = "Title: " + cursor.getString(1)
                                        + "\nWhole Day Event: " +(month +1)+ "/" + dayOfMonth + "/"
                                        + year;
                            }
                            if(!cursor.getString(2).isEmpty()){
                                event = "Title: " + cursor.getString(1)
                                        + "\nWhole Day Assignment: " +(month +1)+ "/" + dayOfMonth + "/"
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



                /*
                try {
                    eventsList = getEvents();
                } catch (GeneralSecurityException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if(eventsList.isEmpty())
                {
                    eventsList.add("There are no events scheduled for today.");
                    eventsListAdapter.notifyDataSetChanged();
                }
                else {
                    eventsList.add("Invalid");
                    eventsListAdapter.notifyDataSetChanged();
                }


                 */



            }

        });



        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(dayEvents.size() > 0)
                {
                    Intent i = new Intent(getActivity(), CalendarDetails.class);
                    i.putExtra("title", eventsList.get(position));
                    i.putExtra("data", dayEvents.get(position));
                    startActivity(i);
                }
                else
                {
                    Toast.makeText( view.getContext().getApplicationContext(), "There is no available event", Toast.LENGTH_LONG ).show();
                }

            }
        });

        mAddEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendarEvent = Calendar.getInstance();
                Intent i = new Intent(Intent.ACTION_EDIT);
                i.setType("vnd.android.cursor.item/event");

                //i.putExtra("beginTime", calendarEvent.getTimeInMillis());
                //i.putExtra("allDay", true);
                //i.putExtra("rule", "FREQ=YEARLY");
                //i.putExtra("endTime", calendarEvent.getTimeInMillis() + 60 * 60 * 1000);
                //i.putExtra("title", "Calendar Event");
                startActivity(i);
            }
        });








        return root;
    }

    public void AddCalendarEvent(View view) {
        Calendar calendarEvent = Calendar.getInstance();
        Intent i = new Intent(Intent.ACTION_EDIT);
        i.setType("vnd.android.cursor.item/event");
        i.putExtra("beginTime", calendarEvent.getTimeInMillis());
        i.putExtra("allDay", true);
        i.putExtra("rule", "FREQ=YEARLY");
        i.putExtra("endTime", calendarEvent.getTimeInMillis() + 60 * 60 * 1000);
        i.putExtra("title", "Calendar Event");
        startActivity(i);
    }

    public void onAddEventClicked(View view){
        Intent intent = new Intent(Intent.ACTION_INSERT);
        intent.setType("vnd.android.cursor.item/event");

        Calendar cal = Calendar.getInstance();
        long startTime = cal.getTimeInMillis();
        long endTime = cal.getTimeInMillis()  + 60 * 60 * 1000;

        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startTime);
        intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,endTime);
        intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true);

        intent.putExtra(CalendarContract.Events.TITLE, "Neel Birthday");
        intent.putExtra(CalendarContract.Events.DESCRIPTION,  "This is a sample description");
        intent.putExtra(CalendarContract.Events.EVENT_LOCATION, "My Guest House");
        intent.putExtra(CalendarContract.Events.RRULE, "FREQ=YEARLY");

        startActivity(intent);
    }

    /*
    public ArrayList<String> getEvents() throws GeneralSecurityException, IOException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Calendar service = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
        DateTime now = new DateTime(System.currentTimeMillis());
        Events events = service.events().list("primary")
                .setMaxResults(10)
                .setTimeMin(now)
                .setOrderBy("startTime")
                .setSingleEvents(true)
                .execute();
        List<Event> items = events.getItems();
        ArrayList<String> eventsList = new ArrayList<>();
        //eventsList = items;
        if (items.isEmpty()) {
            eventsList.add("No upcoming events found.");
        } else {
            //System.out.println("Upcoming events");
            for (Event event : items) {
                DateTime start = event.getStart().getDateTime();
                if (start == null) {
                    start = event.getStart().getDate();
                }
                System.out.printf("%s (%s)\n", event.getSummary(), start);
                eventsList.add(event.getStatus());
            }
        }
        return eventsList;
    }

    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        InputStream in = CalendarQuickstart.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

     */







    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}


