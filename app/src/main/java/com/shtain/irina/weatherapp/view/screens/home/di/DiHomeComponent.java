package com.shtain.irina.weatherapp.view.screens.home.di;

import com.shtain.irina.weatherapp.root.DiRootComponent;
import com.shtain.irina.weatherapp.view.screens.home.HomeActivity;
import com.shtain.irina.weatherapp.view.screens.home.cities.CityListFragment;
import com.shtain.irina.weatherapp.view.screens.home.cities.di.DiCitiesModule;
import com.shtain.irina.weatherapp.view.screens.home.details.DetailsFragment;
import com.shtain.irina.weatherapp.view.screens.home.details.di.DiDetailsModule;
import com.shtain.irina.weatherapp.view.screens.splash.di.DiSplashModule;

import dagger.Component;

/**
 * Created by Irina Shtain on 02.04.2018.
 */
@MainScope
@Component(modules = {DiSplashModule.class,
        DiCitiesModule.class,
        DiDetailsModule.class
},
        dependencies = DiRootComponent.class)
public interface DiHomeComponent {

    void inject(HomeActivity activity);

    void inject(CityListFragment fragment);
    void inject(DetailsFragment fragment);
}
