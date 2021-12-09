package com.example.a11_9project.ui.events;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.a11_9project.R;
import com.example.a11_9project.databinding.FragmentEventsBinding;
import com.example.a11_9project.databinding.FragmentLockoutBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class EventsFragment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_events);

        ListView resultsListView = (ListView) findViewById(R.id.results_listview);

        HashMap<String, String> nameAddresses = new HashMap<>();
        nameAddresses.put("GMAS Testing -- Algebra I", "Wednesday, Decemeber 8");
        nameAddresses.put("JCB Paid Apprenticeship Visit", "Monday, December 13 @ 10:00 AM");
        nameAddresses.put("Cap and Gown Payment", "Due on Monday, January 31");
        nameAddresses.put("Cap and Gown Late Payment", "Starts on Tuesday, February 1");
        nameAddresses.put("Senior Yearbook Page Payment", "Due on Tuesday, February 1");
        nameAddresses.put("Senior Dues", "Due on Thursday, March 31");
        nameAddresses.put("Cap and Gown Pictures", "April 2022");

        List<HashMap<String, String>> listItems = new ArrayList<>();
        SimpleAdapter adapter = new SimpleAdapter(this, listItems, R.layout.list_item,
                new String[]{"First Line", "Second Line"},
                new int[]{R.id.text1, R.id.text2});

        Iterator it = nameAddresses.entrySet().iterator();
        while(it.hasNext())
        {
            HashMap<String, String> resultsMap = new HashMap<>();
            Map.Entry pair = (Map.Entry)it.next();
            resultsMap.put("First Line", pair.getKey().toString());
            resultsMap.put("Second Line", pair.getValue().toString());
            listItems.add(resultsMap);
        }

        resultsListView.setAdapter(adapter);
    }
}