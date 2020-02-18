package com.bahaa.github.data.network.interfaces;

import com.bahaa.github.models.RepoModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {
    @GET("JakeWharton/repos")
    Observable<List<RepoModel>> getData(@Query("page") int page, @Query("per_page") int items);

}
