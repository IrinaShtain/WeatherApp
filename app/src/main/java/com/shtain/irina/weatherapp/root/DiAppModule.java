package com.shtain.irina.weatherapp.root;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Irina Shtain on 02.04.2018.
 */
@Module
public class DiAppModule {
    private Context mContext;

    public DiAppModule(final Context context) {
        mContext = context;
    }

    @Provides
    @Singleton
    Context provideContext() {
        return mContext;
    }
}

