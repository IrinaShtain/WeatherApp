package com.shtain.irina.weatherapp.root.network;

import com.shtain.irina.weatherapp.model.WeatherResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Irina Shtain on 03.04.2018.
 */
public interface WeatherService {
    @GET("/data/2.5/weather")
    Observable<WeatherResponse> getDayWeather(@Query("lat") String lat,
                                              @Query("lon") String lon);
}
