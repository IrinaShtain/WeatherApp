package com.shtain.irina.weatherapp.root.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.shtain.irina.weatherapp.root.network.INetworkManager;

/**
 * Created by Irina Shtain on 02.04.2018.
 */
public class NetworkManagerImpl implements INetworkManager {

    private ConnectivityManager mCm;

    public NetworkManagerImpl(final Context context) {
        mCm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    @Override
    public boolean isConnected() {
        final NetworkInfo activeNetwork = mCm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

}