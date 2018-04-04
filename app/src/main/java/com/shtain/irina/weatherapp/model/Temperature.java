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

    public float getTemp() {
        return temp;
    }

    public int getHumidity() {
        return humidity;
    }
}
