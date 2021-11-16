package com.example.a11_9project.ui.loading;

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

import com.example.a11_9project.databinding.FragmentLoadingBinding;
import com.example.a11_9project.ui.loading.LoadingViewModel;

public class LoadingFragment extends Fragment {

    private LoadingViewModel loadingViewModel;
    private FragmentLoadingBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        loadingViewModel =
                new ViewModelProvider(this).get(LoadingViewModel.class);

        binding = FragmentLoadingBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textLoading;
        loadingViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
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