package com.telstra.sunil.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.telstra.sunil.R;
import com.telstra.sunil.model.RowItem;

/**
 * This class is responsible to update the individual Rows of the recycler View
 * We get the data from the RecyclerView Adapter class
 */
public class ItemRowViewModel extends BaseObservable {

    private RowItem rowItem;
    private Context context;

    public ItemRowViewModel(RowItem rowItem, Context context) {
        this.rowItem = rowItem;
        this.context = context;
    }

    public String getDescription() {
        return rowItem.getDescription();
    }

    // Loading Image using Glide Library.
    @BindingAdapter("imageUrl")
    public static void setImageUrl(ImageView imageView, String url) {
        Glide.with(imageView.getContext())
                .load(url)
                .placeholder(R.drawable.ic_menu_gallery)
                .dontAnimate()
                .into(imageView);

        Glide.getPhotoCacheDir(imageView.getContext());
    }

    public String getTitle() {
        return rowItem.getTitle();
    }

    public String getProfileThumb() {
        return rowItem.getImageHref();
    }

    public void setRowItem(RowItem rowItem) {
        this.rowItem = rowItem;
        notifyChange();
    }
}
