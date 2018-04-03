package com.shtain.irina.weatherapp.view.screens.splash;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.shtain.irina.weatherapp.R;
import com.shtain.irina.weatherapp.utils.Constants;
import com.shtain.irina.weatherapp.view.base.BaseActivity;
import com.shtain.irina.weatherapp.view.screens.home.HomeActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class SplashActivity extends BaseActivity implements SplashContract.SplashView {

    private AnimatorSet animatorSet = new AnimatorSet();
    @Inject
    SplashPresenter presenter;
    @BindView(R.id.ivLogo)
    protected ImageView ivLogo;

    @Override
    protected void initGraph() {
        mObjectGraph.getSplashComponent().inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        presenter.setView(this);
        presenter.subscribe();
    }

    @Override
    protected int getContainerId() {
        return 0;
    }


    @Override
    public void runSplashAnimation() {
        ValueAnimator fadeLogo = ObjectAnimator.ofFloat(ivLogo, View.ALPHA, 0f, 0.2f, 0.4f, 0.6f, 1f);
        fadeLogo.addUpdateListener(animation -> {
            if (ivLogo.getVisibility() == View.INVISIBLE)
                ivLogo.setVisibility(View.VISIBLE);
        });

        ValueAnimator scaleX = ObjectAnimator.ofFloat(ivLogo, View.SCALE_X, 0.8f, 1.2f, 1f, 1.1f);
        ValueAnimator scaleY = ObjectAnimator.ofFloat(ivLogo, View.SCALE_Y, 0.8f, 1.2f, 1f, 1.1f);

        animatorSet.setDuration(1500);
        animatorSet.play(fadeLogo)
                .with(scaleX)
                .with(scaleY)
                .before(ValueAnimator.ofInt(0, 0).setDuration(800));
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                presenter.startNextScreen();
            }
        });

        animatorSet.start();

    }

    @Override
    public void startHomeScreen() {
        Intent intent =new Intent(this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (animatorSet.isRunning()) animatorSet.cancel();
    }
}
