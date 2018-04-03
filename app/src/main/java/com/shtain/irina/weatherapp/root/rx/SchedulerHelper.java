package com.shtain.irina.weatherapp.root.rx;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Scheduler;

/**
 * Created by Irina Shtain on 02.04.2018.
 */
public class SchedulerHelper {
    private Scheduler mObserveOn;
    private Scheduler mSubscribeOn;

    @Inject
    public SchedulerHelper(Scheduler observeOn, Scheduler subscribeOn) {
        mObserveOn = observeOn;
        mSubscribeOn = subscribeOn;
    }

    public <T> Observable<T> getNetworkObservable(Observable<T> observable) {
        return observable.observeOn(mObserveOn)
                .subscribeOn(mSubscribeOn);
    }

    public <T> Flowable<T> getNetworkFlowable(Flowable<T> observable) {
        return observable.observeOn(mObserveOn)
                .subscribeOn(mSubscribeOn);
    }


}
