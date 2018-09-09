package com.telstra.sunil.network;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.telstra.sunil.utility.Utils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Using the Retrofit with RxJava adapter
 */
public class ApiClient {
    private static Retrofit retrofit = null;
    private static ApiService apiService;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Utils.BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static ApiService getRetrofitService() {
        if (apiService == null) {
            apiService = ApiClient.getClient().create(ApiService.class);
        }
        return apiService;
    }
}
