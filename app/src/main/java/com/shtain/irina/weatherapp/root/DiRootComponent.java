package com.shtain.irina.weatherapp.root;

import android.content.Context;

import com.shtain.irina.weatherapp.root.network.DiNetworkModule;
import com.shtain.irina.weatherapp.root.network.INetworkManager;
import com.shtain.irina.weatherapp.root.network.RetrofitHelper;
import com.shtain.irina.weatherapp.root.rx.DiRxModule;
import com.shtain.irina.weatherapp.root.rx.DiRxSchedulerModule;
import com.shtain.irina.weatherapp.root.rx.SchedulerHelper;

import javax.inject.Singleton;

import dagger.Component;
import io.reactivex.disposables.CompositeDisposable;
import io.realm.Realm;

/**
 * Created by Irina Shtain on 02.04.2018.
 */
@Singleton
@Component(modules = {
        DiAppModule.class,
        DiRxSchedulerModule.class,
        DiNetworkModule.class,
        DiRxModule.class
})
public interface DiRootComponent {
    Context context();
    SchedulerHelper schedulerHelper();
    CompositeDisposable compositeDisposable();
    RetrofitHelper retrofitHelper();
    INetworkManager networkManager();
}
