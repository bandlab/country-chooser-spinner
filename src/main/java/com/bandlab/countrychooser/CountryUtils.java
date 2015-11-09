package com.bandlab.countrychooser;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by vorobievilya on 30/01/15.
 */
public class CountryUtils {
    private static CountryUtils mInstance;
    private Country[] countries = new Country[0];

    private CountryUtils(Context context) {
        InputStream is = null;
        try {
            is = context.getAssets().open("countries.json");
            Gson gson = new Gson();
            countries = gson.fromJson(new JsonReader(new InputStreamReader(is)), Country[].class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static CountryUtils newInstance(Context context) {
        if (mInstance == null) {
            mInstance = new CountryUtils(context);
        }
        return mInstance;
    }

    public String getByCode(int code) {
        for (Country country : countries) {
            if (country.countryCodeNumbers == code) {
                return country.countryName;
            }
        }
        return null;
    }
}
