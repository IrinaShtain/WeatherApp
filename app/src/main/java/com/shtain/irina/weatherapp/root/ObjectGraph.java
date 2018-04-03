package com.shtain.irina.weatherapp.root;

import android.content.Context;

import com.shtain.irina.weatherapp.view.screens.home.di.DaggerDiHomeComponent;
import com.shtain.irina.weatherapp.view.screens.home.di.DiHomeComponent;
import com.shtain.irina.weatherapp.view.screens.splash.di.DaggerDiSplashComponent;
import com.shtain.irina.weatherapp.view.screens.splash.di.DiSplashComponent;

/**
 * Created by Irina Shtain on 02.04.2018.
 */
public class ObjectGraph {

    private static ObjectGraph graph;

    public static ObjectGraph getInstance(Context context) {
        if (graph == null) {
            graph = new ObjectGraph(context);
        }
        return graph;
    }

    private final DiSplashComponent mSplashComponent;
    private DiHomeComponent mHomeComponent;

    private ObjectGraph(final Context context) {
        DiRootComponent rootComponent = DaggerDiRootComponent.builder().diAppModule(new DiAppModule(context)).build();
        mSplashComponent = DaggerDiSplashComponent.builder().diRootComponent(rootComponent).build();
        mHomeComponent = DaggerDiHomeComponent.builder().diRootComponent(rootComponent).build();
    }

    public final DiSplashComponent getSplashComponent() {
        return mSplashComponent;
    }

    public final DiHomeComponent getHomeComponent() {
        return mHomeComponent;
    }
}
