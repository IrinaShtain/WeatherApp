package com.shtain.irina.weatherapp.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Irina Shtain on 02.04.2018.
 */
public class Wind {
    @SerializedName("speed")
    private String speed;

    public String getSpeed() {
        return speed;
    }
}
