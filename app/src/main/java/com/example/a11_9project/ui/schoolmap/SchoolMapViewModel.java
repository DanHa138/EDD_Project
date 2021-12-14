package com.example.a11_9project.ui.schoolmap;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SchoolMapViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SchoolMapViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is lunch schedule fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}