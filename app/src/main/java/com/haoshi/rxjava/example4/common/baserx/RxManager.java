package com.haoshi.rxjava.example4.common.baserx;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * @author HaoShi
 */
public class RxManager {
    private CompositeSubscription cs = new CompositeSubscription();

    public void add(Subscription s) {
        cs.add(s);
    }

    public void clear() {
        if (!cs.isUnsubscribed()) {
            cs.unsubscribe();
        }
    }
}
