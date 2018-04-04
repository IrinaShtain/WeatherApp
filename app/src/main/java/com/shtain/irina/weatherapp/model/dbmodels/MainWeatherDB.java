package com.shtain.irina.weatherapp.model.dbmodels;

import io.realm.RealmObject;

/**
 * Created by Irina Shtain on 04.04.2018.
 */
public class MainWeatherDB extends RealmObject{

    private CityDB city;
    private WeatherDB weather;
    private TemperatureDB temperature;
    private long timeStamp;

    public MainWeatherDB() {
    }

    public MainWeatherDB(CityDB city, WeatherDB weather, TemperatureDB temperature, long timeStamp) {
        this.city = city;
        this.weather = weather;
        this.temperature = temperature;
        this.timeStamp = timeStamp;
    }

    public CityDB getCity() {
        return city;
    }

    public void setCity(CityDB city) {
        this.city = city;
    }

    public WeatherDB getWeather() {
        return weather;
    }

    public void setWeather(WeatherDB weather) {
        this.weather = weather;
    }

    public TemperatureDB getTemperature() {
        return temperature;
    }

    public void setTemperature(TemperatureDB temperature) {
        this.temperature = temperature;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
