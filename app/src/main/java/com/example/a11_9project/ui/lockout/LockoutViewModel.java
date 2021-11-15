package com.example.a11_9project.ui.lockout;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LockoutViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public LockoutViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is lockout fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}