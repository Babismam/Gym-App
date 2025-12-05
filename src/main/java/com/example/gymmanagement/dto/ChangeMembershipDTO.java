package com.example.gymmanagement.dto;

import java.time.LocalDate;

public class ChangeMembershipDTO {
    private String membershipType;
    private String membershipDuration;
    private LocalDate customStartDate;
    private LocalDate customEndDate;

    public String getMembershipType() {
        return membershipType;
    }

    public void setMembershipType(String membershipType) {
        this.membershipType = membershipType;
    }

    public String getMembershipDuration() {
        return membershipDuration;
    }

    public void setMembershipDuration(String membershipDuration) {
        this.membershipDuration = membershipDuration;
    }

    public LocalDate getCustomStartDate() {
        return customStartDate;
    }

    public void setCustomStartDate(LocalDate customStartDate) {
        this.customStartDate = customStartDate;
    }

    public LocalDate getCustomEndDate() {
        return customEndDate;
    }

    public void setCustomEndDate(LocalDate customEndDate) {
        this.customEndDate = customEndDate;
    }
}