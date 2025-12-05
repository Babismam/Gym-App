package com.example.gymmanagement.dto;

import com.example.gymmanagement.entities.Attendance;
import com.example.gymmanagement.entities.Program;
import com.example.gymmanagement.entities.Schedule;
import com.example.gymmanagement.entities.User;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.Instant;
import java.time.LocalTime;

public class AttendanceDTO {
    private Long id;
    private Instant attendedOn;
    private String status;
    private String programName;
    private ScheduleInfo schedule;

    public AttendanceDTO(Attendance attendance) {
        this.id = attendance.getId();
        this.attendedOn = attendance.getAttendedOn();
        this.status = attendance.getStatus();

        if (attendance.getProgram() != null) {
            this.programName = attendance.getProgram().getName();
        }

        if (attendance.getSchedule() != null) {
            this.schedule = new ScheduleInfo(attendance.getSchedule());
        }
    }

    public static class ScheduleInfo {
        private Long id;
        private String dayOfWeek;
        @JsonFormat(pattern = "HH:mm")
        private LocalTime startTime;
        @JsonFormat(pattern = "HH:mm")
        private LocalTime endTime;
        private ProgramInfo program;
        private TrainerInfo instructor;

        public ScheduleInfo(Schedule schedule) {
            this.id = schedule.getId();
            this.dayOfWeek = schedule.getDayOfWeek();
            this.startTime = schedule.getStartTime();
            this.endTime = schedule.getEndTime();
            if (schedule.getProgram() != null) {
                this.program = new ProgramInfo(schedule.getProgram());
            }
            if (schedule.getInstructor() != null) {
                this.instructor = new TrainerInfo(schedule.getInstructor());
            }
        }

        public Long getId() { return id; }
        public String getDayOfWeek() { return dayOfWeek; }
        public LocalTime getStartTime() { return startTime; }
        public LocalTime getEndTime() { return endTime; }
        public ProgramInfo getProgram() { return program; }
        public TrainerInfo getInstructor() { return instructor; }
    }

    public static class ProgramInfo {
        private String name;
        public ProgramInfo(Program program) { this.name = program.getName(); }
        public String getName() { return name; }
    }

    public static class TrainerInfo {
        private String firstName;
        private String lastName;
        public TrainerInfo(User instructor) {
            this.firstName = instructor.getFirstName();
            this.lastName = instructor.getLastName();
        }
        public String getFirstName() { return firstName; }
        public String getLastName() { return lastName; }
    }

    public Long getId() { return id; }
    public Instant getAttendedOn() { return attendedOn; }
    public String getStatus() { return status; }
    public String getProgramName() { return programName; }
    public ScheduleInfo getSchedule() { return schedule; }
}