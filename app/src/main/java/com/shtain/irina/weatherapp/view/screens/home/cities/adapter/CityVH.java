package com.shtain.irina.weatherapp.view.screens.home.cities.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.shtain.irina.weatherapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Irina Shtain on 03.04.2018.
 */
public class CityVH extends RecyclerView.ViewHolder {
    @BindView(R.id.tvCityAddress)
    protected TextView tvCityAddress;
    @BindView(R.id.tvCityTemp)
    protected TextView tvCityTemp;
    @BindView(R.id.tvCityLast)
    protected TextView tvCityLast;

    public CityVH(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bindData(CityDH cityDH) {
        tvCityAddress.setText(cityDH.getCityTitle());
        if (cityDH.hasTimeStamp())
            tvCityLast.setText(cityDH.getDate());
        if (cityDH.hasTemp())
            tvCityTemp.setText(cityDH.getTemperature());
    }
}
