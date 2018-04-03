package com.shtain.irina.weatherapp.view.screens.splash;

import com.shtain.irina.weatherapp.view.base.BasePresenter;
import com.shtain.irina.weatherapp.view.base.BaseView;

/**
 * Created by Irina Shtain on 02.04.2018.
 */
public interface SplashContract {
    interface SplashView extends BaseView {
        void runSplashAnimation();
        void startHomeScreen();
    }

    interface SplashPresenter extends BasePresenter<SplashView> {
        void startNextScreen();
    }
}
