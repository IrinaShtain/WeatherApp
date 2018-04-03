package com.shtain.irina.weatherapp.view.screens.home.details;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shtain.irina.weatherapp.R;
import com.shtain.irina.weatherapp.model.City;
import com.shtain.irina.weatherapp.view.base.BaseFragment;
import com.shtain.irina.weatherapp.view.base.BasePresenter;
import com.shtain.irina.weatherapp.view.screens.home.HomeActivity;
import com.shtain.irina.weatherapp.view.screens.home.cities.CityListPresenter;

import javax.inject.Inject;

/**
 * Created by Irina Shtain on 03.04.2018.
 */
public class DetailsFragment extends BaseFragment implements DetailsContract.View {
    public static final String CITY = "city";
    @Inject
    DetailsPresenter mPresenter;

    private City mCity;

    public static DetailsFragment newInstance(City city) {
        Bundle args = new Bundle();
        args.putParcelable(CITY, city);

        DetailsFragment fragment = new DetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_cities, container, false);
        bindView(this, parent);
        ((HomeActivity) mActivity).mToolbar.setTitle("Cities Details");
        return parent;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        assert getArguments() != null;
        mCity = getArguments().getParcelable(CITY);
        mPresenter.setView(this);
        mPresenter.subscribe();
    }

    @Override
    protected BasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    protected void initGraph() {
        mActivity.mObjectGraph.getHomeComponent().inject(this);
    }

    @Override
    public City getChosenCity() {
        return mCity;
    }
}
