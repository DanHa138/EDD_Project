package com.example.a11_9project.ui.calendar;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.a11_9project.R;
import com.example.a11_9project.databinding.FragmentCalendarBinding;

public class CalendarActivity extends AppCompatActivity {

    private CalendarViewModel calendarViewModel;
    private FragmentCalendarBinding binding;
    private  static final String TAG = "CalendarActivity";
    private CalendarView mCalendarView;
    private String pattern = "dd/MM/yyyy";


    /*
    private MutableLiveData<String> mText;

    Context context;

    private CalendarService calendarProvider = new CalendarService();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_calendar);

        CalendarService.readCalendar(class.this);

        mText = new MutableLiveData<>();

        final TextView textView = binding.textCalendar;
        mText.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        mText.setValue("Agenda for Today");




        mCalendarView = (CalendarView) findViewById(R.id.calendarView);

        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String date = year + "/" + month + "/"+ dayOfMonth ;
                mText.setValue("Agenda for "+ date);


            }
        });



    }

     */
}
