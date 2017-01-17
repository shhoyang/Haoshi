package com.haoshi.rxjava.example3.utils;

import com.haoshi.hao.Constant;
import com.haoshi.rxjava.example3.bean.News;

import java.util.Map;

import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author: HaoShi
 */

public class NetWorks extends RetrofitUtils {

    private static final NetService service = getRetrofit().create(NetService.class);

    //设缓存有效期为1天
    protected static final long CACHE_STALE_SEC = 60 * 60 * 24 * 1;
    //查询缓存的Cache-Control设置，使用缓存
    protected static final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_SEC;
    //查询网络的Cache-Control设置。不使用缓存
    protected static final String CACHE_CONTROL_NETWORK = "max-age=0";

    private interface NetService {

        @GET(Constant.NEWS_HOT)
        Observable<News> news();

        //POST请求传入map
        @FormUrlEncoded
        @POST(Constant.NEWS_HOT)
        Observable<News> hao(@FieldMap Map<String, String> map);

        //POST请求带参数
        @FormUrlEncoded
        @POST(Constant.NEWS_HOT)
        Observable<News> hao(@Field("username") String username, @Field("password") String password);

        //GET请求带参数
        @GET(Constant.NEWS_HOT)
        Observable<News> hao1(@Query("username") String username, @Query("password") String password);

        //GET请求，设置缓存
        @Headers("Cache-Control: public," + CACHE_CONTROL_CACHE)
        @GET(Constant.NEWS_HOT)
        Observable<News> hao2(@Query("username") String username, @Query("password") String password);

        @Headers("Cache-Control: public," + CACHE_CONTROL_NETWORK)
        @GET(Constant.NEWS_HOT)
        Observable<News> hao();
    }

    public static void getNews(Observer<News> observer) {
        setSubscribe(service.news(), observer);
    }

    /**
     * 插入观察者
     */
    public static <T> void setSubscribe(Observable<T> observable, Observer<T> observer) {
        observable.subscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .subscribe(observer);
    }
}
