package com.shtain.irina.weatherapp.model.dbmodels;

import io.realm.RealmObject;

/**
 * Created by Irina Shtain on 04.04.2018.
 */
public class WeatherDB extends RealmObject{
    private String description;
    private String icon;
    private CityDB city;

    public WeatherDB(String description, String icon, CityDB city) {
        this.description = description;
        this.icon = icon;
        this.city = city;
    }

    public WeatherDB() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public CityDB getCity() {
        return city;
    }

    public void setCity(CityDB city) {
        this.city = city;
    }
}
