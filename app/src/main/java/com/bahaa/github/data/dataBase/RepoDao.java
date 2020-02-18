package com.bahaa.github.data.dataBase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.bahaa.github.models.RepoModel;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;

@Dao
public interface RepoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<RepoModel> repoModel);

    @Query("DELETE FROM repos")
    void deleteAll();

    @Query("SELECT * from repos")
    List<RepoModel> getRepos();
}
