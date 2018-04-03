package com.shtain.irina.weatherapp.view.screens.home.details.di;

import com.shtain.irina.weatherapp.domain.WeatherRepository;
import com.shtain.irina.weatherapp.root.network.RetrofitHelper;
import com.shtain.irina.weatherapp.root.network.WeatherService;
import com.shtain.irina.weatherapp.root.rx.SchedulerHelper;
import com.shtain.irina.weatherapp.view.screens.home.details.DetailsContract;
import com.shtain.irina.weatherapp.view.screens.home.details.DetailsPresenter;
import com.shtain.irina.weatherapp.view.screens.home.di.MainScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Irina Shtain on 03.04.2018.
 */
@Module
public class DiDetailsModule {
    @Provides
    @MainScope
    DetailsContract.Presenter provideDetailsPresenter(DetailsContract.Model model) {
        return new DetailsPresenter(model);
    }

    @Provides
    @MainScope
    DetailsContract.Model provideDataRepository(RetrofitHelper helper, SchedulerHelper schedulerHelper) {
        return new WeatherRepository(provideWeatherService(helper), schedulerHelper);
    }

    @Provides
    @MainScope
    WeatherService provideWeatherService(RetrofitHelper helper) {
        return helper.createService(WeatherService.class);
    }


}
