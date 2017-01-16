package com.haoshi.rxjava.example3.utils;

import com.haoshi.hao.Constant;
import com.haoshi.rxjava.example3.bean.Login;
import com.haoshi.rxjava.example3.bean.News;
import com.haoshi.rxjava.example3.bean.Register;

import java.util.Map;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
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

public class NetWorks {

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

        @FormUrlEncoded
        @POST("")
        Observable<Register> register(@FieldMap Map<String, String> map);

        @FormUrlEncoded
        @POST("")
        Observable<Login> login(@Field("username") String username, @Field("password") String password);

        @GET("")
        Observable<Login> hao1(@Query("username") String username, @Query("password") String password);

        //GET请求，设置缓存
        @Headers("Cache-Control: public," + CACHE_CONTROL_CACHE)
        @GET("")
        Observable<Login> hao2(@Query("username") String username, @Query("password") String password);


        @Headers("Cache-Control: public," + CACHE_CONTROL_NETWORK)
        @GET("")
        Observable<Login> hao3();
    }

    public static void getNews(Observer<News> observer) {
        setSubscribe(service.news(), observer);
    }

    public static void doRegister(Map<String, String> map, Observer<Register> observer) {
        setSubscribe(service.register(map), observer);
    }

    public static void doLogin(String username, String password, Observer<Login> observer) {
        setSubscribe(service.login(username, password), observer);
    }

    //Get请求设置缓存
    public static void verfacationCodeGetCache(String username, String password, Observer<Login> observer) {
        setSubscribe(service.hao2(username, password), observer);
    }

    public static void Getcache(Observer<Login> observer) {
        setSubscribe(service.hao3(), observer);
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

    private static Retrofit retrofit;
    private static OkHttpClient okHttpClient;

    private static Retrofit getRetrofit() {

        if (retrofit == null) {

            if (okHttpClient == null) {
                okHttpClient = OkHttpUtils.getOkHttpClient();
            }

            retrofit = new Retrofit.Builder()
                    .baseUrl(Constant.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(okHttpClient)
                    .build();

        }
        return retrofit;
    }
}
