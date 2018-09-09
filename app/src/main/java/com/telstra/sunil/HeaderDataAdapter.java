package com.telstra.sunil;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.telstra.sunil.databinding.ItemRowsBinding;
import com.telstra.sunil.model.RowItem;
import com.telstra.sunil.viewmodel.ItemRowViewModel;

import java.util.Collections;
import java.util.List;


public class HeaderDataAdapter extends RecyclerView.Adapter<HeaderDataAdapter.RowItemAdapterViewHolder> {

    private List<RowItem> rowItems;

    public HeaderDataAdapter() {
        this.rowItems = Collections.emptyList();
    }

    @Override
    public RowItemAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ItemRowsBinding itemRowsBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_rows, parent, false);
        return new RowItemAdapterViewHolder(itemRowsBinding);
    }

    @Override
    public void onBindViewHolder(RowItemAdapterViewHolder holder, int position) {
        holder.bindRowView(rowItems.get(position));

        if(rowItems.get(position).getDescription() == null) {
            holder.itemRowsBinding.imageItem.setVisibility(View.GONE);
            holder.itemRowsBinding.labelDescription.setVisibility(View.GONE);
        } else {
            holder.itemRowsBinding.imageItem.setVisibility(View.VISIBLE);
            holder.itemRowsBinding.labelDescription.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return rowItems.size();
    }

    public void setRowItems(List<RowItem> rowItems) {
        this.rowItems = rowItems;
        notifyDataSetChanged();
    }

    public static class RowItemAdapterViewHolder extends RecyclerView.ViewHolder {

        ItemRowsBinding itemRowsBinding;

        public RowItemAdapterViewHolder(ItemRowsBinding itemRowsBinding) {
            super(itemRowsBinding.itemRowCardview);
            this.itemRowsBinding = itemRowsBinding;
        }

        void bindRowView(RowItem rowItem) {
            if (itemRowsBinding.getHeaderViewModel() == null) {
                itemRowsBinding.setHeaderViewModel(new ItemRowViewModel(rowItem, itemView.getContext()));
            } else {
                itemRowsBinding.getHeaderViewModel().setRowItem(rowItem);
            }
        }
    }
}
