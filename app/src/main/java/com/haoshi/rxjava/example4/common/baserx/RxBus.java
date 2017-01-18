package com.haoshi.rxjava.example4.common.baserx;


import java.util.Vector;

import rx.Observable;
import rx.Subscription;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * @author HaoShi
 */
public class RxBus {
    private static RxBus rxBus;

    private RxBus() {

    }

    public static RxBus getInstance() {
        if (rxBus == null) {
            synchronized (RxBus.class) {
                if (rxBus == null) {
                    rxBus = new RxBus();
                }
            }
        }
        return rxBus;
    }

    public final Subject<Object, Object> _bus = new SerializedSubject<>(PublishSubject.create());

    public final Vector<Subscription> subscriptions = new Vector<>();

    public void send(Object o) {
        _bus.onNext(o);
    }

    public Observable<Object> toObserverable() {
        return _bus;
    }

    public void addSubscription(Subscription s) {
        subscriptions.add(s);
    }

    public void unSubscribeAll() {
        for (Subscription s : subscriptions) {
            if (!s.isUnsubscribed()) {
                s.unsubscribe();
            }
        }
    }
}
