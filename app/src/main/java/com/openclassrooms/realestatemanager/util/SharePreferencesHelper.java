package com.openclassrooms.realestatemanager.util;

public class SharePreferencesHelper {
    private static final SharePreferencesHelper ourInstance = new SharePreferencesHelper();

    public static SharePreferencesHelper getInstance() {
        return ourInstance;
    }
    private long AgentId = 1;
    private boolean euro = false;
    private double dollar = 0;
    private SharePreferencesHelper() {
    }

    public long getAgentId() {
        return AgentId;
    }

    public void setAgentId(long agentId) {
        AgentId = agentId;
    }

    public boolean isEuro() {
        return euro;
    }

    public void setEuro(boolean euro) {
        this.euro = euro;
    }

    public double getDollar() {
        return dollar;
    }

    public void setDollar(double dollar) {
        this.dollar = dollar;
    }
}
