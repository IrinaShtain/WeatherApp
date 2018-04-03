package com.shtain.irina.weatherapp.root.rx;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Irina Shtain on 02.04.2018.
 */
@Module
public class DiRxSchedulerModule {

    @Provides
    @Singleton
    Scheduler provideSchedulerUI() {
        return AndroidSchedulers.mainThread();
    }

    @Provides
    @Singleton
    Scheduler provideSchedulerIO() {
        return Schedulers.io();
    }

    @Provides
    @Singleton
    SchedulerHelper provideSchedulerHelper() {
        return new SchedulerHelper(provideSchedulerUI(), provideSchedulerIO());
    }


}
