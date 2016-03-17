package com.bandlab.countrychooser;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.Nullable;
import android.util.SparseArray;

import java.util.Locale;

public final class Countries {
    private static final String RES_ID_FORMAT = "country_name_%03d";
    private static final String COUNTRY_CODE_FORMAT = "%03d";
    private static SparseArray<Country> countries;

    private Countries() {
        throw new IllegalStateException("No instances");
    }

    public static String formatAsCountryCode(int code) {
        return String.format(Locale.ENGLISH, COUNTRY_CODE_FORMAT, code);
    }

    private static synchronized void initCountries(Context context) {
        final String[] codes = context.getResources().getStringArray(R.array.country_codes);
        countries = new SparseArray<>(codes.length);
        for (String codeStr : codes) {
            countries.append(Integer.parseInt(codeStr), null);
        }
    }

    @SuppressWarnings("unused")
    public static Country getCountryByCode(Context context, String code) {
        if (code == null) {
            return null;
        }
        try {
            return getCountryByCode(context, Integer.parseInt(code));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Nullable
    public static Country getCountryByCode(Context context, int code) {
        final Resources resources = context.getResources();
        if (countries == null) {
            initCountries(context);
        }
        if (countries.indexOfKey(code) < 0) {
            return null;
        } else {
            Country country = countries.get(code);
            if (country == null) {
                String resourceName = String.format(Locale.ENGLISH, RES_ID_FORMAT, code);
                int resId = resources.getIdentifier(resourceName, "string", context.getPackageName());
                if (resId == 0) {
                    return null;
                }
                String name = resources.getString(resId);
                country = new Country(name, code);
                countries.put(code, country);
            }
        }
        return countries.get(code);
    }
}
