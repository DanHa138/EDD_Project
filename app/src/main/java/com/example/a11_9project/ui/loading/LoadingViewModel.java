package com.example.a11_9project.ui.loading;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LoadingViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public LoadingViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is loading fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}