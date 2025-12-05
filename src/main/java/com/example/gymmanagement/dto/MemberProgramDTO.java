package com.example.gymmanagement.dto;

import com.example.gymmanagement.entities.MemberProgram;
import java.time.LocalDate;

public class MemberProgramDTO {
    private Long id;
    private LocalDate enrollmentDate;
    private String status;
    private String programName;
    private String programDescription;

    public MemberProgramDTO(MemberProgram memberProgram) {
        this.id = memberProgram.getId();
        this.enrollmentDate = memberProgram.getEnrollmentDate();
        this.status = memberProgram.getStatus();
        if (memberProgram.getProgram() != null) {
            this.programName = memberProgram.getProgram().getName();
            this.programDescription = memberProgram.getProgram().getDescription();
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public LocalDate getEnrollmentDate() { return enrollmentDate; }
    public void setEnrollmentDate(LocalDate enrollmentDate) { this.enrollmentDate = enrollmentDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getProgramName() { return programName; }
    public void setProgramName(String programName) { this.programName = programName; }
    public String getProgramDescription() { return programDescription; }
    public void setProgramDescription(String programDescription) { this.programDescription = programDescription; }
}