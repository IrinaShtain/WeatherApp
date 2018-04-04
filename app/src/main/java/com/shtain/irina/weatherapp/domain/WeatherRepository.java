package com.shtain.irina.weatherapp.domain;

import android.util.Log;

import com.shtain.irina.weatherapp.model.City;
import com.shtain.irina.weatherapp.model.WeatherResponse;
import com.shtain.irina.weatherapp.model.dbmodels.CityDB;
import com.shtain.irina.weatherapp.model.dbmodels.MainWeatherDB;
import com.shtain.irina.weatherapp.model.dbmodels.TemperatureDB;
import com.shtain.irina.weatherapp.model.dbmodels.WeatherDB;
import com.shtain.irina.weatherapp.root.network.WeatherService;
import com.shtain.irina.weatherapp.root.rx.SchedulerHelper;
import com.shtain.irina.weatherapp.view.screens.home.cities.DBListener;
import com.shtain.irina.weatherapp.view.screens.home.details.DetailsContract;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.realm.Realm;
import io.realm.RealmAsyncTask;
import io.realm.RealmResults;

/**
 * Created by Irina Shtain on 03.04.2018.
 */
public class WeatherRepository implements DetailsContract.Model {
    private WeatherService mService;
    private SchedulerHelper mSchedulerHelper;
    private Realm mRealm;
    private RealmAsyncTask transaction;

    @Inject
    public WeatherRepository(WeatherService service, SchedulerHelper schedulerHelper) {
        mService = service;
        mSchedulerHelper = schedulerHelper;
    }

    @Override
    public Observable<WeatherResponse> getWeather(String lat, String lon) {
        return mSchedulerHelper.getNetworkObservable(mService.getDayWeather(lat, lon));
    }

    @Override
    public void saveData(WeatherResponse data, City city, DBListener listener) {
        mRealm = Realm.getDefaultInstance();
        transaction = mRealm.executeTransactionAsync(bgRealm -> {
                    CityDB cityDB = bgRealm.where(CityDB.class)
                            .equalTo("address", city.getAddress())
                            .findFirst();

                    MainWeatherDB realmObject;
                    realmObject = bgRealm.where(MainWeatherDB.class).equalTo("city.address", city.getAddress()).findFirst();
                    if (realmObject == null)
                        realmObject = bgRealm.createObject(MainWeatherDB.class);

                    WeatherDB weatherDB;
                    weatherDB = bgRealm.where(WeatherDB.class).equalTo("city.address", city.getAddress()).findFirst();
                    if (weatherDB == null)
                        weatherDB = bgRealm.createObject(WeatherDB.class);
                    weatherDB.setDescription(data.weather[0].getDescription());
                    weatherDB.setIcon(data.weather[0].getIcon());

                    TemperatureDB temperatureDB;
                    temperatureDB = bgRealm.where(TemperatureDB.class).equalTo("city.address", city.getAddress()).findFirst();
                    if (temperatureDB == null)
                        temperatureDB = bgRealm.createObject(TemperatureDB.class);
                    temperatureDB.setHumidity(data.temperature.getHumidity());
                    temperatureDB.setTemp(data.temperature.getTemp());

                    realmObject.setCity(cityDB);
                    realmObject.setTemperature(temperatureDB);
                    realmObject.setWeather(weatherDB);
                    realmObject.setTimeStamp(data.timeStamp);
                },
                listener::onSuccess,
                error -> {
                    Log.e("myLog", " " + error.getMessage());
                    listener.onError(error);
                });
    }

    @Override
    public MainWeatherDB getSaveData(String address) {
        return mRealm.where(MainWeatherDB.class).equalTo("city.address", address).findFirst();
    }

    @Override
    public void cancelTransactions() {
        if (transaction != null && !transaction.isCancelled()) {
            transaction.cancel();
        }
    }

    @Override
    public void createRealmInstance() {
        mRealm = Realm.getDefaultInstance();
    }

    @Override
    public void closeRealmInstance() {
        mRealm.close();
    }
}
