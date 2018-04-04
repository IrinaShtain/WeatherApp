package com.shtain.irina.weatherapp.view.screens.home.cities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.shtain.irina.weatherapp.R;
import com.shtain.irina.weatherapp.model.City;
import com.shtain.irina.weatherapp.utils.Constants;
import com.shtain.irina.weatherapp.view.base.BaseFragment;
import com.shtain.irina.weatherapp.view.base.BasePresenter;
import com.shtain.irina.weatherapp.view.screens.home.HomeActivity;
import com.shtain.irina.weatherapp.view.screens.home.cities.adapter.CityAdapter;
import com.shtain.irina.weatherapp.view.screens.home.cities.adapter.CityDH;
import com.shtain.irina.weatherapp.view.screens.home.cities.adapter.OnCityClicklistener;
import com.shtain.irina.weatherapp.view.screens.home.details.DetailsFragment;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Irina Shtain on 02.04.2018.
 */
public class CityListFragment extends BaseFragment implements CityListContract.View,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        OnCityClicklistener {
    @BindView(R.id.fabAdd_FH)
    protected FloatingActionButton fabAdd_FH;
    @BindView(R.id.rvItems_FH)
    protected RecyclerView rvItems_FH;
    @BindView(R.id.content_FH)
    protected CoordinatorLayout content_FH;
    @BindView(R.id.rlPlaceHolder)
    protected RelativeLayout rlPlaceHolder;
    @BindView(R.id.pbMain_FH)
    protected ProgressBar pbMain_FH;
    @Inject
    CityListPresenter mPresenter;

    private CityAdapter mAdapter;
    private GoogleApiClient mGoogleApiClient;
    private Snackbar snackbar;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_cities, container, false);
        bindView(this, parent);
        initGoogleClient();
        ((HomeActivity) mActivity).mToolbar.setTitle(R.string.title_cities);
        ((HomeActivity) mActivity).mToolbar.setLogo(R.drawable.ic_cloud);

        return parent;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initSnackbar();
        setupRecyclerView();
        setupSwipeToRemove();
        mPresenter.setView(this);
        mPresenter.subscribe();
    }

    private void setupRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity);
        mAdapter = new CityAdapter();
        mAdapter.addListener(this);
        rvItems_FH.setAdapter(mAdapter);
        rvItems_FH.setLayoutManager(layoutManager);
    }

    private void setupSwipeToRemove() {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int pos = viewHolder.getAdapterPosition();
                mPresenter.removeCity(mAdapter.getItem(pos), pos);
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                Bitmap icon = getVectorBitmap(R.drawable.ic_delete_white);
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    View itemView = viewHolder.itemView;
                    float iconSize = getResources().getDisplayMetrics().density * 32;
                    float margin = getResources().getDisplayMetrics().density * 24;
                    float yCenter = itemView.getTop() + itemView.getHeight() / 2;
                    float paddingBottomPx = 8 * getResources().getDisplayMetrics().density;
                    Paint p = new Paint();

                    if (dX < 0) {
                        p.setColor(Color.parseColor("#f44336"));
                        RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(), (float) itemView.getRight(), (float) itemView.getBottom() - (8 * getResources().getDisplayMetrics().density));
                        c.drawRect(background, p);
                        c.clipRect(background);
                        RectF icon_dest = new RectF(itemView.getRight() - margin - iconSize,
                                (yCenter - iconSize / 2) - paddingBottomPx / 2,
                                itemView.getRight() - margin,
                                (yCenter + iconSize / 2) - paddingBottomPx / 2);
                        c.drawBitmap(icon, null, icon_dest, p);
                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(rvItems_FH);
    }

    private Bitmap getVectorBitmap(@DrawableRes int drawableId) {
        Drawable drawable = ContextCompat.getDrawable(mActivity, drawableId);
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    private void initSnackbar() {
        snackbar = Snackbar.make(content_FH, "", Snackbar.LENGTH_SHORT);
        TextView textView = snackbar.getView().findViewById(android.support.design.R.id.snackbar_text);
        textView.setMaxLines(5);  // show multiple line
    }

    private void initGoogleClient() {
        if (mGoogleApiClient != null) return;
        try {
            mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .addApi(Places.GEO_DATA_API)
                    .addApi(Places.PLACE_DETECTION_API)
                    .enableAutoManage(getActivity(), this)
                    .build();
        } catch (Exception e) {
            Log.e("Location", "GoogleApiClient error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        } else
            showMessage(snackbar, getString(R.string.err_msg_not_connected), true);
    }

    @Override
    public void initGraph() {
        mActivity.mObjectGraph.getHomeComponent().inject(this);
    }

    @Override
    public void showCities(ArrayList<CityDH> cityDHs) {
        rlPlaceHolder.setVisibility(View.GONE);
        mAdapter.addCities(cityDHs);
    }

    @Override
    protected BasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void deleteCity(int position) {
        mAdapter.removeItem(position);
        mPresenter.checkCapacity(mAdapter.getItemCount());
    }

    @Override
    public void addCity(CityDH cityDH, int position) {
        mAdapter.addCity(cityDH, position);
        if (rlPlaceHolder.getVisibility() == View.VISIBLE)
            rlPlaceHolder.setVisibility(View.GONE);
    }

    @Override
    public void showMessage(Constants.MessageType messageType) {
        showMessage(snackbar, getString(messageType.getMessageRes()), messageType.isDangerous());
    }

    @Override
    public void showPlaceHolder() {
        rlPlaceHolder.setVisibility(View.VISIBLE);
    }

    @Override
    public void showProgress() {
        pbMain_FH.setVisibility(View.VISIBLE);
        rvItems_FH.setVisibility(View.GONE);
        rlPlaceHolder.setVisibility(View.GONE);
    }

    @Override
    public void hideProgress() {
        pbMain_FH.setVisibility(View.GONE);
        rvItems_FH.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.fabAdd_FH)
    public void addCity() {
        try {
            Intent intent =
                    new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                            .setFilter(new AutocompleteFilter.Builder()
                                    .setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES)
                                    .build())
                            .build(getActivity());
            this.startActivityForResult(intent, Constants.REQUEST_PLACE_AUTOCOMPLETE_CODE);
        } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
            showMessage(snackbar, getString(R.string.err_msg_no_known_location), true);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Constants.REQUEST_PLACE_AUTOCOMPLETE_CODE:
                if (resultCode == RESULT_OK) {
                    Place place = PlaceAutocomplete.getPlace(getActivity(), data);
                    mPresenter.handlePlace(place);
                }
                break;
        }
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
    }

    @Override
    public void onConnectionSuspended(int i) {
        showMessage(snackbar, getString(R.string.err_msg_suspended_connection), true);
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        showMessage(snackbar, getString(R.string.err_msg_failed_connection), true);
    }

    @Override
    public void onCityClick(City city) {
        mActivity.replaceFragment(DetailsFragment.newInstance(city));
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.disconnect();
            mGoogleApiClient.stopAutoManage(getActivity());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ((HomeActivity) mActivity).mToolbar.setLogo(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
    }
}
