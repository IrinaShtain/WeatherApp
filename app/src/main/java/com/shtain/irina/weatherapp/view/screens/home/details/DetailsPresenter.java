package com.shtain.irina.weatherapp.view.screens.home.details;

import android.util.Log;

import com.shtain.irina.weatherapp.model.City;
import com.shtain.irina.weatherapp.model.SunInfo;
import com.shtain.irina.weatherapp.model.Temperature;
import com.shtain.irina.weatherapp.model.Weather;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Irina Shtain on 03.04.2018.
 */
public class DetailsPresenter implements DetailsContract.Presenter {

    private DetailsContract.View mView;
    private DetailsContract.Model mModel;
    private City mCity;
    private CompositeDisposable mCompositeDisposable;

    @Inject
    public DetailsPresenter(DetailsContract.Model model) {
        mModel = model;
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void subscribe() {
        mCity = mView.getChosenCity();
        Log.e("myLog", mCity.getAddress());
        mCompositeDisposable.add(mModel.getWeather(mCity.getLatitude(), mCity.getLongitude())
                .subscribe(response -> {
                    Weather weather = response.weather[0];

                }, throwable -> {
                    Log.e("myLog", "throwable makeSearch >>>" + throwable.getMessage());
                }));
    }

    @Override
    public void unsubscribe() {
        mCompositeDisposable.clear();
    }

    @Override
    public void setView(DetailsContract.View view) {
        mView = view;
    }
}
