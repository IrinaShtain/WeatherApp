package com.shtain.irina.weatherapp.domain;

import com.shtain.irina.weatherapp.model.WeatherResponse;
import com.shtain.irina.weatherapp.root.network.WeatherService;
import com.shtain.irina.weatherapp.root.rx.SchedulerHelper;
import com.shtain.irina.weatherapp.view.screens.home.details.DetailsContract;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by Irina Shtain on 03.04.2018.
 */
public class WeatherRepository implements DetailsContract.Model {
    private WeatherService mService;
    private SchedulerHelper mSchedulerHelper;

    @Inject
    public WeatherRepository(WeatherService service, SchedulerHelper schedulerHelper) {
        mService = service;
        mSchedulerHelper = schedulerHelper;
    }

    @Override
    public Observable<WeatherResponse> getWeather(String lat, String lon) {
        return mSchedulerHelper.getNetworkObservable(mService.getDayWeather(lat, lon));
    }
}
