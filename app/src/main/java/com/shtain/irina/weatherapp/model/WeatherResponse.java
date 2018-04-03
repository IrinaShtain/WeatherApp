package com.shtain.irina.weatherapp.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Irina Shtain on 02.04.2018.
 */
public class WeatherResponse {
    @SerializedName("weather")
    public Weather[] weather;
    @SerializedName("main")
    public Temperature temperature;
    @SerializedName("wind")
    public Wind wind;
    @SerializedName("dt")
    public long timeStamp;
    @SerializedName("sys")
    public SunInfo sun;
}
