package com.example.a11_9project.ui.schedule;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ScheduleViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ScheduleViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Scheduler fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}