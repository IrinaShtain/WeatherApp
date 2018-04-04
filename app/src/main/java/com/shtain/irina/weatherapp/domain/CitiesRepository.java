package com.shtain.irina.weatherapp.domain;

import android.util.Log;

import com.shtain.irina.weatherapp.model.City;
import com.shtain.irina.weatherapp.model.dbmodels.CityDB;
import com.shtain.irina.weatherapp.model.dbmodels.MainWeatherDB;
import com.shtain.irina.weatherapp.model.dbmodels.TemperatureDB;
import com.shtain.irina.weatherapp.model.dbmodels.WeatherDB;
import com.shtain.irina.weatherapp.view.screens.home.cities.CityListContract;
import com.shtain.irina.weatherapp.view.screens.home.cities.DBListener;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmAsyncTask;
import io.realm.RealmResults;

/**
 * Created by Irina Shtain on 03.04.2018.
 */
public class CitiesRepository implements CityListContract.Model {
    private Realm mRealm;
    private RealmAsyncTask transaction;

    @Inject
    public CitiesRepository(Realm realm) {
        mRealm = realm;
    }

    @Override
    public RealmResults<CityDB> cities() {
        return this.mRealm.where(CityDB.class).findAll();
    }

    @Override
    public void addCityToDB(City city, DBListener listener) {
        transaction = mRealm.executeTransactionAsync(bgRealm -> {
                    CityDB realmObject = bgRealm.createObject(CityDB.class, city.getAddress()); // Create a new object
                    realmObject.setLongitude(city.getLongitude());
                    realmObject.setLatitude(city.getLatitude());
                },
                listener::onSuccess,
                error -> {
                    Log.e("myLog", " " + error.getMessage());
                    listener.onError(error);
                });
    }

    @Override
    public void deleteCity(City city, DBListener listener) {
        try {
            mRealm.executeTransaction(realm -> {
                RealmResults<CityDB> results = realm.where(CityDB.class).equalTo("address", city.getAddress()).findAll();
                results.deleteFirstFromRealm();

                MainWeatherDB realmObject;
                realmObject = mRealm.where(MainWeatherDB.class).equalTo("city.address", city.getAddress()).findFirst();
                if (realmObject != null)
                    realmObject.deleteFromRealm();

                WeatherDB weatherDB;
                weatherDB = mRealm.where(WeatherDB.class).equalTo("city.address", city.getAddress()).findFirst();
                if (weatherDB != null)
                    weatherDB.deleteFromRealm();

                TemperatureDB temperatureDB;
                temperatureDB = mRealm.where(TemperatureDB.class).equalTo("city.address", city.getAddress()).findFirst();
                if (temperatureDB != null)
                    temperatureDB.deleteFromRealm();

            });
            listener.onSuccess();
        } catch (Throwable error) {
            listener.onError(error);
        }
    }

    @Override
    public void cancelTransactions() {
        if (transaction != null && !transaction.isCancelled()) {
            transaction.cancel();
        }
    }
}
