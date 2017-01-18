package com.haoshi.rxjava.example4.api;

import com.haoshi.rxjava.example4.bean.News;

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
