package com.example.gymmanagement.dto;

public class BookingRequestDTO {
    private Long memberId;
    private Long scheduleId;
    public Long getMemberId() { return memberId; }
    public void setMemberId(Long memberId) { this.memberId = memberId; }
    public Long getScheduleId() { return scheduleId; }
    public void setScheduleId(Long scheduleId) { this.scheduleId = scheduleId; }
}