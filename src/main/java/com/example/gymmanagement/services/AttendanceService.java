package com.example.gymmanagement.services;

import com.example.gymmanagement.dto.AttendeeDTO;
import com.example.gymmanagement.dto.AttendanceDTO;
import com.example.gymmanagement.entities.Attendance;
import com.example.gymmanagement.entities.MembershipStatus;
import com.example.gymmanagement.entities.Schedule;
import com.example.gymmanagement.entities.User;
import com.example.gymmanagement.repositories.AttendanceRepository;
import com.example.gymmanagement.repositories.ScheduleRepository;
import com.example.gymmanagement.repositories.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class AttendanceService {

    @Inject
    private AttendanceRepository attendanceRepository;
    @Inject
    private ScheduleRepository scheduleRepository;
    @Inject
    private UserRepository userRepository;

    public List<AttendanceDTO> getAttendanceByMemberId(Long memberId) {
        return attendanceRepository.findByMemberId(memberId)
                .stream()
                .map(AttendanceDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public AttendanceDTO createAttendance(Long memberId, Long scheduleId) {
        User member = userRepository.findById(memberId);
        Schedule schedule = scheduleRepository.findById(scheduleId);

        validateBookingEligibility(member, schedule);

        Attendance newAttendance = new Attendance();
        newAttendance.setMember(member);
        newAttendance.setSchedule(schedule);
        newAttendance.setProgram(schedule.getProgram());
        attendanceRepository.save(newAttendance);

        return new AttendanceDTO(newAttendance);
    }

    @Transactional
    public void deleteAttendance(Long memberId, Long scheduleId) {
        Attendance attendance = attendanceRepository.findByMemberAndSchedule(memberId, scheduleId)
                .orElseThrow(() -> new WebApplicationException("Δεν βρέθηκε η κράτηση για ακύρωση.", Response.Status.NOT_FOUND));
        attendanceRepository.delete(attendance);
    }

    public List<AttendeeDTO> getAttendeesForSchedule(Long scheduleId) {
        return attendanceRepository.findMembersByScheduleId(scheduleId)
                .stream()
                .map(AttendeeDTO::new)
                .collect(Collectors.toList());
    }

    private void validateBookingEligibility(User member, Schedule schedule) {
        if (member == null) {
            throw new WebApplicationException("Το μέλος δεν βρέθηκε.", Response.Status.NOT_FOUND);
        }
        if (schedule == null) {
            throw new WebApplicationException("Το επιλεγμένο πρόγραμμα δεν βρέθηκε.", Response.Status.NOT_FOUND);
        }

        if (!member.getIsActive()) {
            throw createForbiddenException("Ο λογαριασμός του μέλους είναι ανενεργός.");
        }
        if (member.getMembershipStatus() == MembershipStatus.PAUSED) {
            throw createForbiddenException("Η συνδρομή του μέλους είναι σε παύση.");
        }
        if (member.getMembershipEndDate() != null && LocalDate.now().isAfter(member.getMembershipEndDate())) {
            throw createForbiddenException("Η συνδρομή του μέλους έχει λήξει.");
        }

        if (schedule.getProgram().getMaxParticipants() != null) {
            long currentCount = attendanceRepository.countByScheduleId(schedule.getId());
            if (currentCount >= schedule.getProgram().getMaxParticipants()) {
                throw createConflictException("Η τάξη είναι γεμάτη.");
            }
        }

        if (attendanceRepository.hasBooking(member.getId(), schedule.getId())) {
            throw createConflictException("Αυτό το μέλος είναι ήδη εγγεγραμμένο.");
        }
    }

    private WebApplicationException createForbiddenException(String message) {
        Response response = Response.status(Response.Status.FORBIDDEN)
                .entity(message)
                .type(MediaType.TEXT_PLAIN)
                .build();
        return new WebApplicationException(response);
    }

    private WebApplicationException createConflictException(String message) {
        Response response = Response.status(Response.Status.CONFLICT)
                .entity(message)
                .type(MediaType.TEXT_PLAIN)
                .build();
        return new WebApplicationException(response);
    }
}