package com.telstra.sunil.utility;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Common Utility method & constants
 */
public class Utils {
    public static final String BASE_URL = "https://dl.dropboxusercontent.com/";

    public static final String FETCH_DATA = BASE_URL + "s/2iodh4vg0eortkl/facts.js";
    //https://dl.dropboxusercontent.com/s/2iodh4vg0eortkl/facts.js

    /**
     * Method to check if Internet connections available before trying to
     * making a HTTP GET call,
     *
     * @param context
     * @return
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        assert cm != null;
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        return isConnected;
    }
}
