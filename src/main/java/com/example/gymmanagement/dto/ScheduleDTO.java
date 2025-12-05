package com.example.gymmanagement.dto;

import com.example.gymmanagement.entities.Program;
import com.example.gymmanagement.entities.Schedule;
import com.example.gymmanagement.entities.User;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalTime;

public class ScheduleDTO {
    private Long id;
    private String dayOfWeek;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime startTime;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime endTime;

    private ProgramInfo program;
    private TrainerInfo instructor;
    private long attendanceCount;

    public ScheduleDTO(Schedule schedule, long attendanceCount) {
        this.id = schedule.getId();
        this.dayOfWeek = schedule.getDayOfWeek();
        this.startTime = schedule.getStartTime();
        this.endTime = schedule.getEndTime();
        this.program = new ProgramInfo(schedule.getProgram());
        this.instructor = new TrainerInfo(schedule.getInstructor());
        this.attendanceCount = attendanceCount;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getDayOfWeek() { return dayOfWeek; }
    public void setDayOfWeek(String dayOfWeek) { this.dayOfWeek = dayOfWeek; }
    public LocalTime getStartTime() { return startTime; }
    public void setStartTime(LocalTime startTime) { this.startTime = startTime; }
    public LocalTime getEndTime() { return endTime; }
    public void setEndTime(LocalTime endTime) { this.endTime = endTime; }
    public ProgramInfo getProgram() { return program; }
    public void setProgram(ProgramInfo program) { this.program = program; }
    public TrainerInfo getInstructor() { return instructor; }
    public void setInstructor(TrainerInfo instructor) { this.instructor = instructor; }
    public long getAttendanceCount() { return attendanceCount; }
    public void setAttendanceCount(long attendanceCount) { this.attendanceCount = attendanceCount; }

    public static class ProgramInfo {
        private String name;
        private Integer maxParticipants;
        public ProgramInfo(Program program) {
            this.name = program.getName();
            this.maxParticipants = program.getMaxParticipants();
        }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public Integer getMaxParticipants() { return maxParticipants; }
        public void setMaxParticipants(Integer maxParticipants) { this.maxParticipants = maxParticipants; }
    }

    public static class TrainerInfo {
        private String firstName;
        private String lastName;
        public TrainerInfo(User instructor) {
            this.firstName = instructor.getFirstName();
            this.lastName = instructor.getLastName();
        }
        public String getFirstName() { return firstName; }
        public void setFirstName(String firstName) { this.firstName = firstName; }
        public String getLastName() { return lastName; }
        public void setLastName(String lastName) { this.lastName = lastName; }
    }
}