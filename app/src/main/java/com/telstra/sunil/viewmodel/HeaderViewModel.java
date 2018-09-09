package com.telstra.sunil.viewmodel;

import android.content.Context;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;

import com.telstra.sunil.R;
import com.telstra.sunil.model.HeaderListData;
import com.telstra.sunil.model.RowItem;
import com.telstra.sunil.network.ApiCallHelper;
import com.telstra.sunil.network.ApiCallback;
import com.telstra.sunil.network.ApiClient;
import com.telstra.sunil.network.ApiService;
import com.telstra.sunil.utility.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * ViewModel for handling the Main Activity data,
 * In this class we initiate the network call to fetch the JSON data.
 * Based on the response we update the MainActivity Title & also the data for RecyclerView
 * In this case holds reference to the UI handling.
 */
public class HeaderViewModel extends Observable {

    private static final String TAG = HeaderViewModel.class.getSimpleName();

    public ObservableInt progressBar;
    public ObservableInt rowRecycler;
    public ObservableInt rowLabel;
    public ObservableField<String> messageLabel;

    private List<RowItem> rowItemList;
    private String title;
    private Context context;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public HeaderViewModel(@NonNull Context context) {
        this.context = context;
        this.rowItemList = new ArrayList<>();
        progressBar = new ObservableInt(View.GONE);
        rowRecycler = new ObservableInt(View.GONE);
        rowLabel = new ObservableInt(View.VISIBLE);
        messageLabel = new ObservableField<>(context.getString(R.string.load_data));
    }

    public void onClickFabToLoad(View view) {
        initializeViews();
        if (!Utils.isNetworkAvailable(context)) {
            Snackbar.make(view, R.string.network_error, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return;
        }
        fetchData();
    }

    public void initializeViews() {
        rowLabel.set(View.GONE);
        rowRecycler.set(View.GONE);
        progressBar.set(View.VISIBLE);
    }

    private void fetchData() {
        io.reactivex.Observable<HeaderListData> responseObservable = ApiClient
                .getClient()
                .create(ApiService.class)
                .fetchRows();

        Disposable disposable = ApiCallHelper.call(responseObservable, new ApiCallback<HeaderListData>() {
            @Override
            public void onSuccess(HeaderListData headerListData) {
                Log.d(TAG, "List of data = " + headerListData);
                updateRowDataList(headerListData.getRows(), headerListData.getTitle());
                progressBar.set(View.GONE);
                rowLabel.set(View.GONE);
                rowRecycler.set(View.VISIBLE);
            }

            @Override
            public void onFailed(Throwable throwable) {
                Log.d(TAG, "Error = " + throwable.getMessage());
                messageLabel.set(context.getString(R.string.callback_error));
                progressBar.set(View.GONE);
                rowLabel.set(View.VISIBLE);
                rowRecycler.set(View.GONE);
            }
        });

        compositeDisposable.add(disposable);
    }

    private void updateRowDataList(List<RowItem> peoples, String title) {
        rowItemList.addAll(peoples);
        this.title = title;

        setChanged();
        notifyObservers();
    }

    public String getTitle() {
        return title;
    }

    public List<RowItem> getRowItemList() {
        return rowItemList;
    }

    private void unSubscribeFromObservable() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }

    public void reset() {
        unSubscribeFromObservable();
        compositeDisposable = null;
        context = null;
    }
}

