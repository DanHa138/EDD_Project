package com.example.a11_9project.ui.schedule;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.a11_9project.databinding.FragmentScheduleBinding;

public class ScheduleFragment extends Fragment {

    private ScheduleViewModel ScheduleViewModel;
    private FragmentScheduleBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ScheduleViewModel =
                new ViewModelProvider(this).get(com.example.a11_9project.ui.schedule.ScheduleViewModel.class);

        binding = FragmentScheduleBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textSchedule;
        ScheduleViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
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