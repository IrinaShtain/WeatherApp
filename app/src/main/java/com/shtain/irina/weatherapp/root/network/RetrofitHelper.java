package com.shtain.irina.weatherapp.root.network;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.shtain.irina.weatherapp.BuildConfig;
import com.shtain.irina.weatherapp.model.exceptions.ConnectionException;
import com.shtain.irina.weatherapp.model.exceptions.TimeoutException;
import com.shtain.irina.weatherapp.root.network.INetworkManager;
import com.shtain.irina.weatherapp.utils.Constants;

import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Irina Shtain on 02.04.2018.
 */
public class RetrofitHelper {

    private INetworkManager mNetworkManager;

    public RetrofitHelper(INetworkManager networkManager) {
        mNetworkManager = networkManager;
    }

    public <T> T createService(final Class<T> _class) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(Constants.TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(Constants.TIMEOUT_READ, TimeUnit.SECONDS)
                .writeTimeout(Constants.TIMEOUT_WRITE, TimeUnit.SECONDS)
                .addInterceptor(chain -> {
                    try {
                        if (!mNetworkManager.isConnected()) {
                            throw new ConnectionException();
                        } else {
                            Request request = chain.request();
                            HttpUrl url = request.url().newBuilder()
                                    .addQueryParameter("appid", BuildConfig.API_KEY)
                                    .addQueryParameter("units", BuildConfig.UNITS)
                                    .build();
                            request = request.newBuilder().url(url).build();
                            Log.e("myLog", "chain called " + chain.request().url());
                            return chain.proceed(request);
                        }
                    } catch (SocketTimeoutException e) {
                        throw new TimeoutException();
                    }
                });
        Gson gson = new GsonBuilder()
                .create();
        Log.e("myLog", "Rest called ");
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(builder.build())
                .baseUrl(Constants.BASE_URL)
                .build();

        return retrofit.create(_class);
    }

}
