package com.example.a11_9project.ui.lockout;

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

import com.example.a11_9project.databinding.FragmentLockoutBinding;

public class LockoutFragment extends Fragment {

    private LockoutViewModel lockoutViewModel;
    private FragmentLockoutBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        lockoutViewModel =
                new ViewModelProvider(this).get(LockoutViewModel.class);

        binding = FragmentLockoutBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textLockout;
        lockoutViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
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