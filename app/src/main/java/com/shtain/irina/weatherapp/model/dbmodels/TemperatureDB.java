package com.shtain.irina.weatherapp.model.dbmodels;

import io.realm.RealmObject;

/**
 * Created by Irina Shtain on 04.04.2018.
 */
public class TemperatureDB extends RealmObject{
    private float temp;
    private int humidity;
    private CityDB city;

    public TemperatureDB(float temp, int humidity, CityDB city) {
        this.temp = temp;
        this.humidity = humidity;
        this.city = city;
    }

    public TemperatureDB() {
    }

    public float getTemp() {
        return temp;
    }

    public void setTemp(float temp) {
        this.temp = temp;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public CityDB getCity() {
        return city;
    }

    public void setCity(CityDB city) {
        this.city = city;
    }
}
