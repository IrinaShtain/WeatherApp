package com.shtain.irina.weatherapp.domain;

import android.util.Log;

import com.shtain.irina.weatherapp.model.City;
import com.shtain.irina.weatherapp.model.dbmodels.CityDB;
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
    public CitiesRepository() {
    }

    @Override
    public RealmResults<CityDB> cities(Realm realm) {
        mRealm = realm;
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
