package com.telstra.sunil.network;

import com.telstra.sunil.model.HeaderListData;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Interface defining all the API calling method signatures.
 */
public interface ApiService {

    // Fetch the data
    @GET("s/2iodh4vg0eortkl/facts.js")
    Observable<HeaderListData> fetchRows();

}