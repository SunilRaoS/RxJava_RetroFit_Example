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
import com.telstra.sunil.network.ApiClient;
import com.telstra.sunil.utility.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


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

    //It is "public" to show an example of test
    public void initializeViews() {
        rowLabel.set(View.GONE);
        rowRecycler.set(View.GONE);
        progressBar.set(View.VISIBLE);
    }

    private void fetchData() {

        Disposable disposable = ApiClient.getRetrofitService().fetchRows()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Consumer<HeaderListData>() {
                    @Override
                    public void accept(HeaderListData headerListData) throws Exception {
                        Log.d(TAG, "List of data = " + headerListData);
                        updateRowDataList(headerListData.getRows(), headerListData.getTitle());
                        progressBar.set(View.GONE);
                        rowLabel.set(View.GONE);
                        rowRecycler.set(View.VISIBLE);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d(TAG, "List of data = " + throwable.getMessage());
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

