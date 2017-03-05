package com.edwin.redditclient.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class MyUtil {
    public static boolean isConnectedToNetwork(Context contextParam) {
        ConnectivityManager cm = (ConnectivityManager) contextParam.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return (ni != null && (ni.isConnected()));
    }
}
