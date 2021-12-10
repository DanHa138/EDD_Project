package com.example.a11_9project.ui.lunch;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.a11_9project.R;
import com.example.a11_9project.databinding.FragmentLunchBinding;
/*
public class LunchFragment extends Fragment {

    //private LunchViewModel LunchViewModel;
    //private FragmentLunchBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        LunchViewModel =
                new ViewModelProvider(this).get(LunchViewModel.class);

        binding = FragmentLunchBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textLunch;
        LunchViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
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

 */
public class LunchFragment extends Fragment{

    private MutableLiveData<String> mText;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.fragment_lunch, container, false);

        TextView textView = root.findViewById(R.id.text_lunch);

        mText = new MutableLiveData<>();

        mText.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                textView.setText(s);
            }
        });

        mText.setValue("This is lunch schedule fragment");


        return root;
    }
}