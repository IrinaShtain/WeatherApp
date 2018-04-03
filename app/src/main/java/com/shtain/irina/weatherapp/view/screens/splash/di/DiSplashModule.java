package com.shtain.irina.weatherapp.view.screens.splash.di;

import com.shtain.irina.weatherapp.view.screens.splash.SplashContract;
import com.shtain.irina.weatherapp.view.screens.splash.SplashPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Irina Shtain on 02.04.2018.
 */

@Module
public class DiSplashModule {
    @Provides
    @SplashScope
    SplashContract.SplashPresenter provideSplashPresenter() {
        return new SplashPresenter();
    }
}
