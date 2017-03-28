package com.haoshi.rxjava.mvp.api;

import com.haoshi.rxjava.mvp.bean.News;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @author HaoShi
 */
public interface ApiService {
    @GET("index")
    Observable<News> getNews(@Query("type") String type, @Query("key") String apiKey);
}
