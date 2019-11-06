package com.openclassrooms.realestatemanager.feature.splash;

public class SpashModel {
    private int logo;
    private String estateName;

    public SpashModel(int logo, String estateName) {
        this.logo = logo;
        this.estateName = estateName;
    }

    public int getLogo() {
        return logo;
    }

    public void setLogo(int logo) {
        this.logo = logo;
    }

    public String getEstateName() {
        return estateName;
    }

    public void setEstateName(String estateName) {
        this.estateName = estateName;
    }
}
