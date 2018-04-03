package com.shtain.irina.weatherapp.root;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Irina Shtain on 02.04.2018.
 */
public class WeatherApp extends Application {

    @Override
    public final void onCreate() {
        super.onCreate();
        ObjectGraph.getInstance(this);  //initialize
        Realm.init(this);
        initRealmConfiguration();
    }

    private void initRealmConfiguration() {
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }
}
