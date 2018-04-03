package com.shtain.irina.weatherapp.view.screens.home.cities;

import com.google.android.gms.location.places.Place;
import com.shtain.irina.weatherapp.model.City;
import com.shtain.irina.weatherapp.model.dbmodels.CityDB;
import com.shtain.irina.weatherapp.utils.Constants;
import com.shtain.irina.weatherapp.view.screens.home.cities.adapter.CityDH;

import java.util.ArrayList;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Irina Shtain on 02.04.2018.
 */
public class CityListPresenter implements CityListContract.Presenter {

    private CityListContract.View mView;
    private CityListContract.Model mModel;
    private Realm mRealm;

    @Inject
    public CityListPresenter(CityListContract.Model model, Realm realm) {
        mModel = model;
        mRealm = realm;
    }

    @Override
    public void subscribe() {
        RealmResults<CityDB> cities = mModel.cities(mRealm);
        ArrayList<CityDH> cityDHS = new ArrayList<>();
        for (CityDB city : cities) {
            cityDHS.add(new CityDH(new City(city.getAddress(),
                    city.getLatitude(),
                    city.getLongitude())));
        }
        if (!cityDHS.isEmpty())
            mView.showCities(cityDHS);
        else
            mView.showPlaceHolder();
    }

    @Override
    public void unsubscribe() {
        mModel.cancelTransactions();
    }

    @Override
    public void setView(CityListContract.View view) {
        mView = view;
    }

    @Override
    public void removeCity(CityDH item, int pos) {
        mModel.deleteCity(item.getCity(), new DBListener() {
            @Override
            public void onSuccess() {
                mView.deleteCity(pos);
                mView.showMessage(Constants.MessageType.CITY_REMOVED);
            }

            @Override
            public void onError(Throwable error) {
                updateAppearance(item, pos);
                mView.showMessage(Constants.MessageType.UNKNOWN);

            }
        });
    }

    private void updateAppearance(CityDH item, int pos) {
        mView.addCity(item, pos);
        mView.deleteCity(pos);

    }

    @Override
    public void handlePlace(Place place) {
        City city = new City(place.getAddress().toString(),
                String.valueOf(place.getLatLng().latitude),
                String.valueOf(place.getLatLng().longitude));
        mModel.addCityToDB(city, new DBListener() {
            @Override
            public void onSuccess() {
                mView.addCity(new CityDH(city), -1);
                mView.showMessage(Constants.MessageType.CITY_ADDED);
            }

            @Override
            public void onError(Throwable error) {
                if (error.getMessage().contains("Primary key value already exists"))
                    mView.showMessage(Constants.MessageType.CITY_ALREADY_EXIST);
                else
                    mView.showMessage(Constants.MessageType.UNKNOWN);
            }
        });
    }

    @Override
    public void checkCapacity(int itemCount) {
        if (itemCount == 0)
            mView.showPlaceHolder();
    }
}