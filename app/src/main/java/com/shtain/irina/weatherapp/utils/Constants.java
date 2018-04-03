package com.shtain.irina.weatherapp.utils;

import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

import com.shtain.irina.weatherapp.R;

/**
 * Created by Irina Shtain on 02.04.2018.
 */
public abstract class Constants {
    public static final int CLICK_DELAY = 600;
    public static final long TIMEOUT = 30; //seconds
    public static final long TIMEOUT_READ = 30; //seconds
    public static final long TIMEOUT_WRITE = 60; //seconds

    public static final int REQUEST_PERMISSION_CALLBACK_CODE = 101;
    public static final int REQUEST_PERMISSION_SETTING = 102;
    public static final int REQUEST_PLACE_AUTOCOMPLETE_CODE = 103;
    public static final int REQUEST_CHECK_SETTINGS = 100;

    public static final String BASE_URL = "http://api.openweathermap.org";
    public static final String BASE_URL_IMAGE = "http://api.openweathermap.org/img/w/";

    public enum MessageType {
        CONNECTION_PROBLEMS(R.string.err_msg_connection_problem, true),
        CITY_ALREADY_EXIST(R.string.err_msg_city_already_exists, true),
        CITY_REMOVED(R.string.err_msg_city_removed, true),
        CITY_ADDED(R.string.msg_city_added, false),
        UNKNOWN(R.string.err_msg_something_goes_wrong, true);

        @StringRes
        private int messageRes;
        private boolean isDangerous;

        MessageType(@StringRes int messageRes, boolean isDangerous) {
            this.messageRes = messageRes;
            this.isDangerous = isDangerous;
        }

        public int getMessageRes() {
            return messageRes;
        }

        public boolean isDangerous() {
            return isDangerous;
        }
    }

    public enum PlaceholderType {
        NETWORK(R.string.err_msg_connection_problem, R.drawable.ic_cloud_off),
        UNKNOWN(R.string.err_msg_something_goes_wrong, R.drawable.ic_sentiment_dissatisfied),
        EMPTY(R.string.err_msg_no_data, R.drawable.ic_no_data);

        @StringRes
        private int messageRes;
        @DrawableRes
        private int iconRes;

        PlaceholderType(@StringRes int messageRes, @DrawableRes int iconRes) {
            this.messageRes = messageRes;
            this.iconRes = iconRes;
        }

        public int getMessageRes() {
            return messageRes;
        }

        public int getIconRes() {
            return iconRes;
        }
    }

}