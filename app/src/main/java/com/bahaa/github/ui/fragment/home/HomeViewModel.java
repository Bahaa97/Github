package com.bahaa.github.ui.fragment.home;

import android.annotation.SuppressLint;
import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bahaa.github.data.dataBase.RepoRoomDatabase;
import com.bahaa.github.data.network.ConnectivityStatus;
import com.bahaa.github.data.network.NetworkState;
import com.bahaa.github.data.network.RetrofitClient;
import com.bahaa.github.models.RepoModel;
import com.bahaa.github.ui.activities.base.BaseActivity;

import java.util.List;
import java.util.concurrent.Executor;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class HomeViewModel extends ViewModel {

    MutableLiveData<List<RepoModel>> moviesResponseMutableLiveData;
    MutableLiveData<Integer> page;
    MutableLiveData<Integer> items;
    boolean isLastPage = false, isLoading = false;
    private RepoRoomDatabase database;
    private Application application;

    public HomeViewModel(Application application) {
        this.application = application;
        moviesResponseMutableLiveData = new MutableLiveData<>();
        page = new MutableLiveData<>();
        items = new MutableLiveData<>();
        database = RepoRoomDatabase.getDatabase(application);
        page.setValue(1);
        items.setValue(15);
        getData();
    }

    void getData() {
        if (!ConnectivityStatus.isConnected(application)) {
            getDataFromDataBase();
        } else {
            getDataFromApi();
        }
    }

    @SuppressLint("CheckResult")
    void getDataFromApi() {
        RetrofitClient.webService().getData(page.getValue(), items.getValue())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    isLoading = false;
                    if (result.size() > 0) {
                        if (moviesResponseMutableLiveData.getValue()!= null && moviesResponseMutableLiveData.getValue().size() > 0) {
                            moviesResponseMutableLiveData.postValue(result);
                            addData(result);
                        } else {
                            moviesResponseMutableLiveData.postValue(result);
                            addData(result);
                        }

                    } else {
                        isLastPage = true;
                    }
                }, throwable -> {
                    Log.e("NETWORK ERROR", throwable.getMessage());
                });
    }

    void addData(List<RepoModel> repoModel) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                database.RepoDeo().insert(repoModel);
            }
        }).start();
    }

    void getDataFromDataBase() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                moviesResponseMutableLiveData = new MutableLiveData<>();
                moviesResponseMutableLiveData.postValue(database.RepoDeo().getRepos());
            }
        }).start();

    }


    protected void onCleared() {
        super.onCleared();
    }


}