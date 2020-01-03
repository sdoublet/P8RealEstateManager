package com.openclassrooms.realestatemanager.util;

public class MoneyPref {
    private static final MoneyPref ourInstance = new MoneyPref();
    private boolean euro = false;
    private double dollar = 0;
    public static MoneyPref getInstance() {
        return ourInstance;
    }

    public double getDollar() {
        return dollar;
    }

    public void setDollar(double dollar) {
        this.dollar = dollar;
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
