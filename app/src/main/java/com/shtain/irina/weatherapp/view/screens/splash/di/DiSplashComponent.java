package com.shtain.irina.weatherapp.view.screens.splash.di;

/**
 * Created by Irina Shtain on 02.04.2018.
 */

import com.shtain.irina.weatherapp.root.DiRootComponent;
import com.shtain.irina.weatherapp.view.screens.splash.SplashActivity;

import dagger.Component;

@SplashScope
@Component(modules = DiSplashModule.class, dependencies = DiRootComponent.class)
public interface DiSplashComponent {
    void inject(SplashActivity activity);
}
