package com.bandlab.defaultvaluespinner;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.AppCompatSpinner;
import android.util.AttributeSet;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;

import com.bandlab.countrychooser.R;

public class DefaultValueSpinner extends AppCompatSpinner {

    public static final int DEFAULT_POSITION = -1;
    private SpinnerAdapterWrapper adapterWrapper;
    private String defaultText;
    private int textViewId;

    public DefaultValueSpinner(Context context) {
        super(context);
    }

    public DefaultValueSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        initFromAttrs(attrs);
    }

    public DefaultValueSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initFromAttrs(attrs);
    }

    private void initFromAttrs(AttributeSet attrs) {
        TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.DefaultValueSpinner, 0, 0);
        try {
            defaultText = a.getString(R.styleable.DefaultValueSpinner_defaultText);
        } finally {
            a.recycle();
        }
    }

    @Override
    public void setSelection(int position) {
        if (position == DEFAULT_POSITION) {
            adapterWrapper.setStateDefault(true);
            super.setSelection(0);
        } else {
            final int selectedItemPosition = getSelectedItemPosition();
            adapterWrapper.setStateDefault(false);
            if (selectedItemPosition == 0 && position == 0) {
                if (adapterWrapper.getInternalAdapter() instanceof BaseAdapter) {
                    ((BaseAdapter) adapterWrapper.getInternalAdapter()).notifyDataSetChanged();
                }
            } else {
                super.setSelection(position);
            }
        }
    }

    @Override
    public void setAdapter(SpinnerAdapter adapter) {
        adapterWrapper = new SpinnerAdapterWrapper(adapter, getContext(), defaultText, textViewId);
        super.setAdapter(adapterWrapper);
    }

    public void setDefaultValueAdapter(SpinnerAdapter adapter, @LayoutRes int textViewResId) {
        this.textViewId = textViewResId;
        setAdapter(adapter);
    }

    public void selectDefault() {
        setSelection(DEFAULT_POSITION);
    }

    @Override
    public Object getSelectedItem() {
        if (adapterWrapper.isStateDefault()) {
            return null;
        }
        return super.getSelectedItem();
    }
}
