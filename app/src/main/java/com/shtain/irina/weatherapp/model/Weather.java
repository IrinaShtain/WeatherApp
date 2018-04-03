package com.shtain.irina.weatherapp.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Irina Shtain on 02.04.2018.
 */
public class Weather {
    @SerializedName("description")
    private String description;
    @SerializedName("icon")
    private String icon;

    public String getDescription() {
        return description;
    }

    public String getIcon() {
        return icon;
    }
}
