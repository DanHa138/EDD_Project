package com.example.a11_9project.ui.calendar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;

import com.example.a11_9project.R;
import com.example.a11_9project.databinding.FragmentCalendarDetailsBinding;

public class CalendarDetails extends AppCompatActivity {

    private MutableLiveData<String> mText1, mText2;
    private FragmentCalendarDetailsBinding binding;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_calendar_details);

        String newString;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                newString= null;
            } else {
                newString= extras.getString("title");
            }
        } else {
            newString= (String) savedInstanceState.getSerializable("title");
        }

        String newString2;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                newString2 = null;
            } else {
                newString2 = extras.getString("data");
            }
        } else {
            newString2 = (String) savedInstanceState.getSerializable("data");
        }

        TextView element1 = findViewById(R.id.event_text);
        element1.setText(newString);
        TextView element2 = findViewById(R.id.info_text);
        element2.setText(newString2);
        TextView element3 = findViewById(R.id.back_text);
        element3.setText("Press the back button to go back");








    }


}
