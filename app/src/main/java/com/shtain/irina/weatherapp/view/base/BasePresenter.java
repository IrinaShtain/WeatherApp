package com.shtain.irina.weatherapp.view.base;

/**
 * Created by Irina Shtain on 02.04.2018.
 */
public interface BasePresenter<T extends BaseView>  {
    void subscribe();
    void unsubscribe();
    void setView(T view);
}
