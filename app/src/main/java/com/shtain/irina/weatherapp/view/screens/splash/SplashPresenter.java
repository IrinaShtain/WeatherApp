package com.shtain.irina.weatherapp.view.screens.splash;

import javax.inject.Inject;

/**
 * Created by Irina Shtain on 02.04.2018.
 */
public class SplashPresenter implements SplashContract.SplashPresenter {
    private SplashContract.SplashView mView;

    @Inject
    public SplashPresenter() {
    }

    @Override
    public void startNextScreen() {
        mView.startHomeScreen();
    }

    @Override
    public void subscribe() {
        mView.runSplashAnimation();
    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void setView(SplashContract.SplashView view) {
        mView = view;
    }
}
