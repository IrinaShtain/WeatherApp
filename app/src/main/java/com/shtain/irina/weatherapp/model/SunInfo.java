package com.shtain.irina.weatherapp.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Irina Shtain on 02.04.2018.
 */
public class SunInfo {
    @SerializedName("sunrise")
    private long sunrise;
    @SerializedName("sunset")
    private long sunset;

    public long getSunrise() {
        return sunrise;
    }

    public long getSunset() {
        return sunset;
    }
}
