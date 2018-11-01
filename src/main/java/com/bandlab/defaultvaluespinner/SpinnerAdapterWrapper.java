package com.bandlab.defaultvaluespinner;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

public class SpinnerAdapterWrapper implements SpinnerAdapter {

    private final SpinnerAdapter adapter;
    private final Context context;
    private final String defaultText;
    private final int textViewId;
    private final LayoutInflater inflater;
    private boolean isStateDefault;

    public SpinnerAdapterWrapper(SpinnerAdapter adapter, Context context, String defaultText, int textViewId) {
        this.adapter = adapter;
        this.context = context;
        this.defaultText = defaultText;
        this.textViewId = textViewId;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
        adapter.registerDataSetObserver(observer);
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
        adapter.unregisterDataSetObserver(observer);
    }

    @Override
    public int getCount() {
        return adapter.getCount();
    }

    @Override
    public Object getItem(int position) {
        return adapter.getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return adapter.getItemId(position);
    }

    @Override
    public boolean hasStableIds() {
        return adapter.hasStableIds();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (!isStateDefault) {
            return adapter.getView(position, convertView, parent);
        }
        TextView view = (TextView) (convertView == null ?
                inflater.inflate(textViewId, parent, false) :
                convertView);

        view.setTextColor(ContextCompat.getColor(context, android.R.color.black));
        view.setText(defaultText);

        return view;
    }

    @Override
    public int getItemViewType(int position) {
        return adapter.getItemViewType(position);
    }

    @Override
    public int getViewTypeCount() {
        return adapter.getViewTypeCount();
    }

    @Override
    public boolean isEmpty() {
        return adapter.isEmpty();
    }

    public boolean isStateDefault() {
        return isStateDefault;
    }

    public void setStateDefault(boolean isStateDefault) {
        this.isStateDefault = isStateDefault;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return adapter.getDropDownView(position, convertView, parent);
    }

    SpinnerAdapter getInternalAdapter() {
        return adapter;
    }
}
