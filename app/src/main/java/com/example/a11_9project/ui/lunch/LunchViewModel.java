package com.example.a11_9project.ui.lunch;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LunchViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public LunchViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is lunch schedule fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}