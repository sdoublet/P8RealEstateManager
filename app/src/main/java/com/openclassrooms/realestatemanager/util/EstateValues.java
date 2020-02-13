package com.openclassrooms.realestatemanager.util;

public class EstateValues {
    private static final EstateValues ourInstance = new EstateValues();
    private int mostValue = 0;
    private String lastSold = "";
    private String lastEntry = "";

    public static EstateValues getInstance() {
        return ourInstance;
    }

    private EstateValues() {
    }

    public int getMostValue() {
        return mostValue;
    }

    public void setMostValue(int mostValue) {
        this.mostValue = mostValue;
    }

    public String getLastSold() {
        return lastSold;
    }

    public void setLastSold(String lastSold) {
        this.lastSold = lastSold;
    }

    public String getLastEntry() {
        return lastEntry;
    }

    public void setLastEntry(String lastEntry) {
        this.lastEntry = lastEntry;
    }
}
