package com.shtain.irina.weatherapp.view.screens.home.cities.di;

import com.shtain.irina.weatherapp.domain.CitiesRepository;
import com.shtain.irina.weatherapp.root.network.RetrofitHelper;
import com.shtain.irina.weatherapp.root.network.WeatherService;
import com.shtain.irina.weatherapp.root.rx.SchedulerHelper;
import com.shtain.irina.weatherapp.view.screens.home.cities.CityListContract;
import com.shtain.irina.weatherapp.view.screens.home.cities.CityListPresenter;
import com.shtain.irina.weatherapp.view.screens.home.di.MainScope;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
import io.realm.Realm;

/**
 * Created by Irina Shtain on 03.04.2018.
 */
@Module
public class DiCitiesModule {
    @Provides
    @MainScope
    CityListContract.Presenter provideCityPresenter(CityListContract.Model model, CompositeDisposable compositeDisposable) {
        return new CityListPresenter(model, compositeDisposable);
    }

    @Provides
    @MainScope
    CityListContract.Model provideCityData(RetrofitHelper helper, SchedulerHelper schedulerHelper) {
        return new CitiesRepository(provideWeatherService(helper), schedulerHelper);
    }

    @Provides
    @MainScope
    WeatherService provideWeatherService(RetrofitHelper helper) {
        return helper.createService(WeatherService.class);
    }

}
