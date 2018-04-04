package com.shtain.irina.weatherapp.view.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.shtain.irina.weatherapp.root.ObjectGraph;

import javax.inject.Inject;

import io.realm.Realm;

/**
 * Created by Irina Shtain on 02.04.2018.
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected abstract int getContainerId();

    protected abstract void initGraph();

    public ObjectGraph mObjectGraph;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mObjectGraph = ObjectGraph.getInstance(getApplicationContext());
        initGraph();
    }

    public void replaceFragment(BaseFragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(getContainerId(), fragment)
                .addToBackStack(fragment.getClass().getSimpleName())
                .commit();
    }

    public void replaceFragmentClearBackstack(BaseFragment fragment) {
        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        replaceFragment(fragment);
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStackImmediate();
        } else
            finish();
    }
}
