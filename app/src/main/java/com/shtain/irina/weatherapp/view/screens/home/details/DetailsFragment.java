package com.shtain.irina.weatherapp.view.screens.home.details;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shtain.irina.weatherapp.R;
import com.shtain.irina.weatherapp.model.City;
import com.shtain.irina.weatherapp.utils.Constants;
import com.shtain.irina.weatherapp.view.base.BaseFragment;
import com.shtain.irina.weatherapp.view.base.BasePresenter;
import com.shtain.irina.weatherapp.view.custom.CustomWeatherComponent;
import com.shtain.irina.weatherapp.view.screens.home.HomeActivity;

import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by Irina Shtain on 03.04.2018.
 */
public class DetailsFragment extends BaseFragment implements DetailsContract.View {
    public static final String CITY = "city";
    @Inject
    DetailsPresenter mPresenter;
    @BindView(R.id.tvCityAddress_DF)
    TextView tvCityAddress_DF;
    @BindView(R.id.pbMain_DF)
    ProgressBar pbMain_DF;
    @BindView(R.id.rlContent_DF)
    RelativeLayout rlContent_DF;
    @BindView(R.id.mainComponent_DF)
    CustomWeatherComponent mainComponent_DF;
    @BindView(R.id.rlPlaceHolder)
    protected RelativeLayout rlPlaceHolder;
    @BindView(R.id.ivPlaceholderImage)
    protected ImageView ivPlaceholderImage;
    @BindView(R.id.tvPlaceholderMessage)
    protected TextView tvPlaceholderMessage;
    @BindView(R.id.tvOfflineMode_DF)
    protected TextView tvOfflineMode_DF;

    private City mCity;
    private Snackbar snackbar;

    public static DetailsFragment newInstance(City city) {
        Bundle args = new Bundle();
        args.putParcelable(CITY, city);

        DetailsFragment fragment = new DetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_weather_details, container, false);
        bindView(this, parent);
        setupToolbar(true);
        return parent;
    }

    private void setupToolbar(boolean need) {
        ((HomeActivity) mActivity).mToolbar.setTitle(R.string.title_weather_details);
        ActionBar actionBar = mActivity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(need);
            actionBar.setHomeButtonEnabled(need);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        assert getArguments() != null;
        mCity = getArguments().getParcelable(CITY);
        initSnackbar();
        tvCityAddress_DF.setText(mCity.getAddress());
        mPresenter.setView(this);
        mPresenter.subscribe();
    }

    private void initSnackbar() {
        snackbar = Snackbar.make(rlContent_DF, "", Snackbar.LENGTH_SHORT);
        TextView textView = snackbar.getView().findViewById(android.support.design.R.id.snackbar_text);
        textView.setMaxLines(5);  // show multiple line
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

    @Override
    public void showProgress() {
        pbMain_DF.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        pbMain_DF.setVisibility(View.GONE);
    }

    @Override
    public void showOfflineMode() {
        tvOfflineMode_DF.setVisibility(View.VISIBLE);
    }

    @Override
    public void setIcon(String icon) {
        mainComponent_DF.setIcon(icon);
    }

    @Override
    public void setDescription(String description) {
        mainComponent_DF.setDesc(description);
    }

    @Override
    public void setTemperature(float temp) {
        mainComponent_DF.setTemp(String.format(Locale.getDefault(), "%s %d%s",
                getString(R.string.title_temp), (int) temp, "°C"));
    }

    @Override
    public void setHumidity(String humidity) {
        mainComponent_DF.setHumidity(String.format(Locale.getDefault(), "%s %s%s",
                getString(R.string.title_humidity), humidity, "%"));
    }

    @Override
    public void setDate(String date) {
        mainComponent_DF.setDate(date);
    }

    @Override
    public void showMessage(Constants.MessageType messageType) {
        showMessage(snackbar, getString(messageType.getMessageRes()), messageType.isDangerous());
    }

    @Override
    public void showPlaceholder(Constants.PlaceholderType placeholderType) {
        rlPlaceHolder.setVisibility(View.VISIBLE);
        ivPlaceholderImage.setImageResource(placeholderType.getIconRes());
        tvPlaceholderMessage.setText(placeholderType.getMessageRes());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        setupToolbar(false);
    }
}
