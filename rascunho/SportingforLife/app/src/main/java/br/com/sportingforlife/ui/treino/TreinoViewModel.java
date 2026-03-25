package br.com.sportingforlife.ui.treino;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class TreinoViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public TreinoViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is treino fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}