package com.shtain.irina.weatherapp.view.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.shtain.irina.weatherapp.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Irina Shtain on 02.04.2018.
 */
public abstract class BaseFragment extends Fragment implements BaseView{

    protected BaseActivity mActivity;
    protected Unbinder mUnbinder;

    protected abstract BasePresenter getPresenter();
    protected abstract void initGraph();

    protected void bindView(BaseFragment fragment, View view) {
        mUnbinder = ButterKnife.bind(fragment, view);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (BaseActivity) context;
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initGraph();
    }

    protected void hideKeyboard() {
        if (getView() != null) {
            ((InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getView().getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
        }
    }

    protected void disableUI(boolean disable) {
        if (getView() != null && getView() instanceof ViewGroup) {
            setEnabled((ViewGroup) getView(), !disable);
        }
    }

    protected  void showDialogProgress(){

    }

    protected void setEnabled(ViewGroup viewGroup, boolean enabled) {
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = viewGroup.getChildAt(i);
            view.setEnabled(enabled);
            if (view instanceof ViewGroup) {
                setEnabled((ViewGroup) view, enabled);
            }
        }
    }

    protected void showMessage(Snackbar snackbar, String message, boolean isError) {
        if (snackbar.isShown()) snackbar.dismiss();
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(getActivity(), isError
                ? R.color.colorRed
                : R.color.colorPrimary));
        snackbar.setText(message);
        snackbar.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
        if (getPresenter() != null) getPresenter().unsubscribe();
    }
}
