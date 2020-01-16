package com.openclassrooms.realestatemanager.util;

public class AgentId {
    private static final AgentId ourInstance = new AgentId();

    private long AgentId = 1;
    public static AgentId getInstance() {
        return ourInstance;
    }

    public long getAgentId() {
        return AgentId;
    }

    public void setAgentId(long agentId) {
        AgentId = agentId;
    }

    private AgentId() {
    }
}
