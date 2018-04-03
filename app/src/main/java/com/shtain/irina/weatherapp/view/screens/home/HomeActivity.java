package com.shtain.irina.weatherapp.view.screens.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import com.shtain.irina.weatherapp.R;
import com.shtain.irina.weatherapp.view.base.BaseActivity;
import com.shtain.irina.weatherapp.view.screens.home.cities.CityListFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

/**
 * Created by Irina Shtain on 02.04.2018.
 */
public class HomeActivity extends BaseActivity {
    @BindView(R.id.toolbar_MA)
    public Toolbar mToolbar;
    @Inject
    Realm mRealm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        mToolbar.setTitle("hello");
        replaceFragment(new CityListFragment());
    }

    @Override
    protected int getContainerId() {
        return R.id.content_MA;
    }

    @Override
    protected void initGraph() {
        mObjectGraph.getHomeComponent().inject(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRealm.close();
    }
}
