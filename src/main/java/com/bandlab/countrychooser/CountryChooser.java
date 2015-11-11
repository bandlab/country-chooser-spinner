package com.bandlab.countrychooser;

import android.content.Context;
import android.support.v7.widget.AppCompatSpinner;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;

import com.example.modulecountrychooser.R;

public class CountryChooser extends AppCompatSpinner {

    private final Country[] countries;

    public CountryChooser(Context context) {
        this(context, null);
    }

    public CountryChooser(Context context, AttributeSet attrs) {
        super(context, attrs);
        final String[] codes = context.getResources().getStringArray(R.array.country_codes);
        countries = new Country[codes.length];
        for (int i = 0; i < codes.length; i++) {
            String code = codes[i];
            countries[i] = Countries.getCountryByCode(context, Integer.parseInt(code));
        }
        ArrayAdapter<Country> adapter = new ArrayAdapter<>(getContext(), R.layout.country_item, countries);
        setAdapter(adapter);
    }

    public final int getSelectedCountryCode() {
        Country country = (Country) getSelectedItem();
        return country.code;
    }

    public final void selectCountry(int code) {
        for (int i = 0; i < countries.length; i++) {
            if (countries[i].code == code) {
                setSelection(i);
            }
        }
    }
}
