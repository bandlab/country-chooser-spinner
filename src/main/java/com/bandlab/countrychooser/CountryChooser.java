package com.bandlab.countrychooser;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;

import com.bandlab.defaultvaluespinner.DefaultValueSpinner;

public class CountryChooser extends DefaultValueSpinner {

    private final Country[] countries;

    public CountryChooser(Context context) {
        this(context, null);
    }

    public CountryChooser(Context context, AttributeSet attrs) {
        super(context, attrs);
        final String[] codes = context.getResources().getStringArray(R.array.country_codes);
        countries = new Country[codes.length+1];
        countries[0] = new Country("--Country--", -1);
        for (int i = 1; i <= codes.length; i++) {
            countries[i] = Countries.getCountryByCode(context, Integer.parseInt(codes[i-1]));
        }
        ArrayAdapter<Country> adapter = new ArrayAdapter<>(getContext(), R.layout.country_item, countries);
        setDefaultValueAdapter(adapter, R.layout.country_item);
    }

    public final int getSelectedCountryCode() {
        Country country = (Country) getSelectedItem();
        if (country == null) {
            return DEFAULT_POSITION;
        }
        return country.code;
    }

    public final void selectCountry(String codeString) {
        try {
            int code = Integer.parseInt(codeString);
            for (int i = 0; i < countries.length; i++) {
                if (countries[i].code == code) {
                    setSelection(i);
                    return;
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        selectDefault();
    }
}
