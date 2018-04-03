package com.shtain.irina.weatherapp.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Irina Shtain on 02.04.2018.
 */
public class Temperature {
    @SerializedName("temp")
    private float temp;
    @SerializedName("humidity")
    private int humidity;
    @SerializedName("temp_min")
    private float tempMin;
    @SerializedName("temp_max")
    private float tempMax;

    public float getTemp() {
        return temp;
    }

    public int getHumidity() {
        return humidity;
    }

    public float getTempMin() {
        return tempMin;
    }

    public float getTempMax() {
        return tempMax;
    }
}
