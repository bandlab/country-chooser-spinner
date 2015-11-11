package com.bandlab.countrychooser;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.Nullable;
import android.util.SparseArray;

import com.example.modulecountrychooser.R;

public final class Countries {
    private static final String RES_ID_FORMAT = "country_name_%03d";
    private static SparseArray<Country> countries;

    private Countries() {
        throw new IllegalStateException("No instances");
    }

    @Nullable
    public static Country getCountryByCode(Context context, int code) {
        final Resources resources = context.getResources();
        if (countries == null) {
            final String[] codes = resources.getStringArray(R.array.country_codes);
            countries = new SparseArray<>(codes.length);
            for (String codeStr : codes) {
                countries.append(Integer.parseInt(codeStr), null);
            }
        }
        if (countries.indexOfKey(code) < 0) {
            return null;
        } else {
            Country country = countries.get(code);
            if (country == null) {
                final String resourceName = String.format(RES_ID_FORMAT, code);
                int resId = resources.getIdentifier(resourceName, "string", context.getPackageName());
                final String name = resources.getString(resId);
                country = new Country(name, code);
                countries.put(code, country);
            }
        }
        return countries.get(code);
    }
}
