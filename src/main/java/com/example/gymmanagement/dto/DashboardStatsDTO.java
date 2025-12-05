package com.example.gymmanagement.dto;

public class DashboardStatsDTO {
    private long memberCount;
    private long trainerCount;
    private long programCount;

    public DashboardStatsDTO(long memberCount, long trainerCount, long programCount) {
        this.memberCount = memberCount;
        this.trainerCount = trainerCount;
        this.programCount = programCount;
    }

    public long getMemberCount() {
        return memberCount;
    }
    public void setMemberCount(long memberCount) {
        this.memberCount = memberCount;
    }
    public long getTrainerCount() {
        return trainerCount;
    }
    public void setTrainerCount(long trainerCount) {
        this.trainerCount = trainerCount;
    }
    public long getProgramCount() {
        return programCount;
    }
    public void setProgramCount(long programCount) {
        this.programCount = programCount;
    }
}