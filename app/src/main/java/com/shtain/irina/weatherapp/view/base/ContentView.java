package com.shtain.irina.weatherapp.view.base;

import com.shtain.irina.weatherapp.utils.Constants;

/**
 * Created by Irina Shtain on 03.04.2018.
 */
public interface ContentView  extends BaseView{
    void showMessage(Constants.MessageType messageType);
}
