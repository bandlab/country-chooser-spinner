package com.bandlab.countrychooser;

public final class Country {

    public final String name;
    public final int code;

    Country(String name, int code) {
        this.name = name;
        this.code = code;
    }

    @Override
    public String toString() {
        return name;
    }
}
