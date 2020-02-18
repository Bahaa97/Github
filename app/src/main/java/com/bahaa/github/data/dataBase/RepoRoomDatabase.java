package com.bahaa.github.data.dataBase;

import android.content.Context;
import android.os.AsyncTask;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.bahaa.github.models.RepoModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = RepoModel.class, version = 1,exportSchema = false)
public abstract class RepoRoomDatabase extends RoomDatabase {

    public abstract RepoDao RepoDeo();

    private static volatile RepoRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static RepoRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (RepoRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            RepoRoomDatabase.class, "repos")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public List<RepoModel> getAllUsers(){
        try {
            return new GetUsersAsyncTask().execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }


    private class GetUsersAsyncTask extends AsyncTask<Void, Void,List<RepoModel>>
    {
        @Override
        protected List<RepoModel> doInBackground(Void... voids) {
            return INSTANCE.RepoDeo().getRepos();
        }
    }
}
