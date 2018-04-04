package com.shtain.irina.weatherapp.view.screens.home.details;

import com.shtain.irina.weatherapp.model.City;
import com.shtain.irina.weatherapp.model.WeatherResponse;
import com.shtain.irina.weatherapp.model.dbmodels.MainWeatherDB;
import com.shtain.irina.weatherapp.utils.Constants;
import com.shtain.irina.weatherapp.view.base.BasePresenter;
import com.shtain.irina.weatherapp.view.base.ContentView;
import com.shtain.irina.weatherapp.view.screens.home.cities.DBListener;

import io.reactivex.Observable;
import io.realm.RealmResults;

/**
 * Created by Irina Shtain on 03.04.2018.
 */
public interface DetailsContract {
    interface View extends ContentView {
        City getChosenCity();

        void showProgress();

        void hideProgress();

        void showOfflineMode();

        void setIcon(String icon);

        void setDescription(String description);

        void setTemperature(float temp);

        void setHumidity(String humidity);

        void setDate(String date);

        void showPlaceholder(Constants.PlaceholderType placeholderType);
    }

    interface Presenter extends BasePresenter<DetailsContract.View> {

    }

    interface Model {
        Observable<WeatherResponse> getWeather(String lat, String lon);

        void saveData(WeatherResponse data, City city, DBListener listener);

        MainWeatherDB getSaveData(String address);

        void cancelTransactions();

    }
}
