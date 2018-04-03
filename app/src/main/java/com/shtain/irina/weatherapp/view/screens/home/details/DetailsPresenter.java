package com.shtain.irina.weatherapp.view.screens.home.details;

import android.util.Log;

import com.shtain.irina.weatherapp.model.City;
import com.shtain.irina.weatherapp.model.SunInfo;
import com.shtain.irina.weatherapp.model.Temperature;
import com.shtain.irina.weatherapp.model.Weather;
import com.shtain.irina.weatherapp.model.exceptions.ConnectionException;
import com.shtain.irina.weatherapp.utils.Constants;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

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
        Log.e("myLog", " getLatitude" + mCity.getLatitude());
        Log.e("myLog", "getLongitude" + mCity.getLongitude());
        mView.showProgress();
        mCompositeDisposable.add(mModel.getWeather(mCity.getLatitude(), mCity.getLongitude())
                .subscribe(response -> {
                    mView.hideProgress();
                    Weather weather = response.weather[0];
                    if (weather.getDescription() != null)
                        mView.setDescription(response.weather[0].getDescription());
                    if (weather.getIcon() != null)
                        mView.setIcon(response.weather[0].getIcon());
                    if (response.timeStamp != 0) {
                        mView.setDate(convertToDate(response.timeStamp));
                    }
                    Temperature temperature = response.temperature;
                    mView.setTemperature(temperature.getTemp());
                    mView.setHumidity(String.valueOf(temperature.getHumidity()));

                }, throwable -> {
                    mView.hideProgress();
                    throwable.printStackTrace();
                    Log.e("myLog", throwable.getMessage());
                    if (throwable instanceof ConnectionException) {
                        mView.showMessage(Constants.MessageType.CONNECTION_PROBLEMS);
                    } else {
                        mView.showMessage(Constants.MessageType.UNKNOWN);
                    }
                }));
    }

    private String convertToDate(long input) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy 'at' hh:mm a", Locale.getDefault());
        return   simpleDateFormat.format(new Date(input*1000));

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
