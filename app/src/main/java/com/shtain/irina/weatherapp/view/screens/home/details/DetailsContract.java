package com.shtain.irina.weatherapp.view.screens.home.details;

import com.google.android.gms.location.places.Place;
import com.shtain.irina.weatherapp.model.City;
import com.shtain.irina.weatherapp.model.WeatherResponse;
import com.shtain.irina.weatherapp.utils.Constants;
import com.shtain.irina.weatherapp.view.base.BasePresenter;
import com.shtain.irina.weatherapp.view.base.BaseView;
import com.shtain.irina.weatherapp.view.screens.home.cities.CityListContract;
import com.shtain.irina.weatherapp.view.screens.home.cities.DBListener;
import com.shtain.irina.weatherapp.view.screens.home.cities.adapter.CityDH;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Irina Shtain on 03.04.2018.
 */
public interface DetailsContract {
    interface View extends BaseView {
        City getChosenCity();
    }

    interface Presenter extends BasePresenter<DetailsContract.View> {

    }

    interface Model {
        Observable<WeatherResponse> getWeather(String lat, String lon);

    }
}
