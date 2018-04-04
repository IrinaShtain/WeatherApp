package com.shtain.irina.weatherapp.view.screens.home.cities.adapter;

import com.shtain.irina.weatherapp.model.City;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Irina Shtain on 03.04.2018.
 */
public class CityDH {

    private City mCity;
    private long timeStamp;
    private float temp;

    public CityDH(City city) {
        mCity = city;
    }

    public CityDH(City city, long timeStamp, float temp) {
        mCity = city;
        this.timeStamp = timeStamp;
        this.temp = temp;
    }

    public City getCity() {
        return mCity;
    }

    public String getCityTitle() {
        return mCity.getAddress();
    }

    public boolean hasTimeStamp() {
        return timeStamp != 0;
    }

    public boolean hasTemp() {
        return temp != 0;
    }

    public String getDate() {
        return convertToDate(timeStamp);
    }

    private String convertToDate(long input) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("'Updated at \n'dd/MM/yyyy '\n' HH:mm", Locale.getDefault());
        return simpleDateFormat.format(new Date(input * 1000));
    }

    public String getTemperature() {
        return (int)temp + " °C";
    }
}
