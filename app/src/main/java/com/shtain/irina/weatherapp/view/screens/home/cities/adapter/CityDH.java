package com.shtain.irina.weatherapp.view.screens.home.cities.adapter;

import com.shtain.irina.weatherapp.model.City;

/**
 * Created by Irina Shtain on 03.04.2018.
 */
public class CityDH {

    private City mCity;

    public CityDH(City city) {
        mCity = city;
    }

    public City getCity() {
        return mCity;
    }

    public String getCityTitle() {
        return mCity.getAddress();
    }
}
