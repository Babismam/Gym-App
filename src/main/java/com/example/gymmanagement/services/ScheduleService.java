package com.example.gymmanagement.services;

import com.example.gymmanagement.dto.ScheduleDTO;
import com.example.gymmanagement.dto.ScheduleRequestDTO;
import com.example.gymmanagement.entities.Program;
import com.example.gymmanagement.entities.Schedule;
import com.example.gymmanagement.entities.User;
import com.example.gymmanagement.repositories.AttendanceRepository;
import com.example.gymmanagement.repositories.ProgramRepository;
import com.example.gymmanagement.repositories.ScheduleRepository;
import com.example.gymmanagement.repositories.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class ScheduleService {

    @Inject
    private ScheduleRepository scheduleRepository;
    @Inject
    private AttendanceRepository attendanceRepository;
    @Inject
    private UserRepository userRepository;
    @Inject
    private ProgramRepository programRepository;

    public List<ScheduleDTO> getScheduleByInstructorIdDTO(Long instructorId) {
        List<Schedule> schedules = scheduleRepository.findByInstructorId(instructorId);
        return schedules.stream()
                .map(schedule -> new ScheduleDTO(schedule, attendanceRepository.countByScheduleId(schedule.getId())))
                .collect(Collectors.toList());
    }

    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule> schedules = scheduleRepository.findAllOrdered();
        return schedules.stream()
                .map(schedule -> new ScheduleDTO(schedule, attendanceRepository.countByScheduleId(schedule.getId())))
                .collect(Collectors.toList());
    }

    public List<Program> getDistinctProgramsByInstructorId(Long instructorId) {
        return scheduleRepository.findDistinctProgramsByInstructorId(instructorId);
    }

    @Transactional
    public Schedule createSchedule(ScheduleRequestDTO dto, Long instructorId) {
        User instructor = userRepository.findById(instructorId);
        Program program = programRepository.findById(dto.getProgramId());
        if (instructor == null || program == null) {
            throw new WebApplicationException("Instructor or Program not found", Response.Status.NOT_FOUND);
        }
        Schedule schedule = new Schedule();
        schedule.setInstructor(instructor);
        schedule.setProgram(program);
        schedule.setDayOfWeek(dto.getDayOfWeek());
        schedule.setStartTime(dto.getStartTime());
        schedule.setEndTime(dto.getEndTime());
        scheduleRepository.save(schedule);
        return schedule;
    }

    @Transactional
    public Schedule updateSchedule(Long scheduleId, ScheduleRequestDTO dto, Long instructorId) {
        Schedule schedule = scheduleRepository.findById(scheduleId);
        if (schedule == null) {
            throw new WebApplicationException("Schedule not found", Response.Status.NOT_FOUND);
        }
        if (!schedule.getInstructor().getId().equals(instructorId)) {
            throw new WebApplicationException("You are not authorized to modify this schedule", Response.Status.FORBIDDEN);
        }
        Program program = programRepository.findById(dto.getProgramId());
        if (program == null) {
            throw new WebApplicationException("Program not found", Response.Status.NOT_FOUND);
        }
        schedule.setProgram(program);
        schedule.setDayOfWeek(dto.getDayOfWeek());
        schedule.setStartTime(dto.getStartTime());
        schedule.setEndTime(dto.getEndTime());
        return schedule;
    }

    @Transactional
    public void deleteSchedule(Long scheduleId, Long instructorId) {
        Schedule schedule = scheduleRepository.findById(scheduleId);
        if (schedule != null) {
            if (!schedule.getInstructor().getId().equals(instructorId)) {
                throw new WebApplicationException("You are not authorized to delete this schedule", Response.Status.FORBIDDEN);
            }
            scheduleRepository.delete(schedule);
        }
    }
}