package com.shtain.irina.weatherapp.view.screens.home.cities;

import com.google.android.gms.location.places.Place;
import com.shtain.irina.weatherapp.model.City;
import com.shtain.irina.weatherapp.model.dbmodels.CityDB;
import com.shtain.irina.weatherapp.utils.Constants;
import com.shtain.irina.weatherapp.view.base.BasePresenter;
import com.shtain.irina.weatherapp.view.base.BaseView;
import com.shtain.irina.weatherapp.view.base.ContentView;
import com.shtain.irina.weatherapp.view.screens.home.cities.adapter.CityDH;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Irina Shtain on 02.04.2018.
 */
public interface CityListContract {
    interface View extends ContentView {
        void showCities(ArrayList<CityDH> cityDHs);

        void deleteCity(int position);

        void addCity(CityDH cityDH, int position);

        void showPlaceHolder();
    }

    interface Presenter extends BasePresenter<CityListContract.View> {
        void removeCity(CityDH item, int pos);

        void handlePlace(Place place);

        void checkCapacity(int itemCount);
    }

    interface Model {
        RealmResults<CityDB> cities(Realm realm);

        void addCityToDB(City city, DBListener listener);

        void deleteCity(City city, DBListener listener);

        void cancelTransactions();
    }
}
