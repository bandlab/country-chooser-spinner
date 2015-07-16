package com.bandlab.countrychooser;

import android.content.Context;
import android.support.v7.widget.AppCompatSpinner;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;

import com.example.modulecountrychooser.R;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by vorobievilya on 22/12/14.
 */
public class CountryChooser extends AppCompatSpinner {

    private Country[] countries;

    public CountryChooser(Context context) {
        super(context);
        init();
    }

    public CountryChooser(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    public void init() {
        try {
            InputStream is = getContext().getAssets().open("countries.json");
            Gson gson = new Gson();
            countries = gson.fromJson(new JsonReader(new InputStreamReader(is)), Country[].class);
            ArrayAdapter<Country> adapter = new ArrayAdapter<>(getContext(), R.layout.country_item, countries);
            setAdapter(adapter);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public int getSelectedCountryCode() {
        Country country = (Country) getSelectedItem();
        return country.countryCodeNumbers;
    }

    public void selectCountry(int code) {
        for (int i = 0; i < countries.length; i++) {
            if (countries[i].countryCodeNumbers == code) {
                setSelection(i);
            }
        }
    }
}
