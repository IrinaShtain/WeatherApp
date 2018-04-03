package com.shtain.irina.weatherapp.root.db;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;

/**
 * Created by Irina Shtain on 03.04.2018.
 */
@Module
public class DiRealmModule {
    @Provides
    @Singleton
    Realm provideRealm() {
        return Realm.getDefaultInstance();
    }
}
