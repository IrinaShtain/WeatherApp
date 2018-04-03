package com.shtain.irina.weatherapp.root.network;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Irina Shtain on 02.04.2018.
 */
@Module
public class DiNetworkModule {
    @Provides
    @Singleton
    RetrofitHelper provideRetrofitHelper(INetworkManager networkManager) {
        return new RetrofitHelper(networkManager);
    }

    @Provides
    @Singleton
    INetworkManager provideNetworkManager(Context context) {
        return new NetworkManagerImpl(context);
    }
}
