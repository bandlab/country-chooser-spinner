package com.bandlab.countrychooser;

import com.google.gson.annotations.SerializedName;

/**
 * Created by vorobievilya on 22/12/14.
 */
public class Country {
    @SerializedName("name")
    String countryName;
    @SerializedName("country-code")
    int countryCodeNumbers;
    @SerializedName("alpha-2")
    String countryCodeLetters;

    @Override
    public String toString() {
        return countryName;
    }
}
