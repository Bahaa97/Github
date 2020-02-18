package com.bahaa.github.ui.fragment.offline;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bahaa.github.util.AppTools;

public class OfflineViewModel extends ViewModel {

    public MutableLiveData<Integer> mutableLiveData;
    public OfflineViewModel() {
        mutableLiveData = new MutableLiveData<>();
    }


    public void onTryAgain(){
        mutableLiveData.setValue(AppTools.TRY_AGAIN);
    }
}