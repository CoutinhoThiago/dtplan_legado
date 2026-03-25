package br.com.sportingforlife.ui.dieta;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class DietaViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public DietaViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dieta fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}