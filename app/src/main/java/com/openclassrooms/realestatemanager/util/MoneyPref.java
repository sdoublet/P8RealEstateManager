package com.openclassrooms.realestatemanager.util;

public class MoneyPref {
    private static final MoneyPref ourInstance = new MoneyPref();
    private boolean euro = false;
    public static MoneyPref getInstance() {
        return ourInstance;
    }

    public boolean isEuro() {
        return euro;
    }

    public void setEuro(boolean euro) {
        this.euro = euro;
    }

    private MoneyPref() {


    }
}
