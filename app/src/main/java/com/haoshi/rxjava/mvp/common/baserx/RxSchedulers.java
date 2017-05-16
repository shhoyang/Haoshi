package com.haoshi.rxjava.mvp.common.baserx;


import com.haoshi.rxjava.mvp.common.basebean.BaseResponse;
import com.haoshi.rxjava.mvp.exception.NewsException;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by qihuang on 16-11-6.
 */

public class RxSchedulers {
    public static <T> Observable.Transformer<BaseResponse<T>, T> io_mainWithRespo() {
        return new Observable.Transformer<BaseResponse<T>, T>() {
            @Override
            public Observable<T> call(Observable<BaseResponse<T>> tObservable) {
                return tObservable.flatMap(new Func1<BaseResponse<T>, Observable<? extends T>>() {
                    @Override
                    public Observable<? extends T> call(BaseResponse<T> tBaseResponse) {
                        if (tBaseResponse.isStatus()) {
                            return createData(tBaseResponse.result);
                        } else {
                            return Observable.error(new NewsException(tBaseResponse.reason));
                        }
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static <T> Observable.Transformer<T, T> io_main() {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> observable) {
                return observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    private static <T> Observable<T> createData(final T data) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscribe) {
                subscribe.onNext(data);
                subscribe.onCompleted();
            }
        });
    }
}
