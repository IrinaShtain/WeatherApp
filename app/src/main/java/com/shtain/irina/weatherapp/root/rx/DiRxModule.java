package com.shtain.irina.weatherapp.root.rx;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Irina Shtain on 02.04.2018.
 */
@Module
public class DiRxModule {
    @Provides
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }
}
