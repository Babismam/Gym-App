package com.example.gymmanagement.repositories;

import com.example.gymmanagement.entities.Attendance;
import com.example.gymmanagement.entities.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class AttendanceRepository {
    @PersistenceContext(unitName = "GymPU")
    private EntityManager em;

    public void save(Attendance attendance) {
        em.persist(attendance);
    }

    public void delete(Attendance attendance) {
        if (!em.contains(attendance)) {
            attendance = em.merge(attendance);
        }
        em.remove(attendance);
    }

    public Optional<Attendance> findByMemberAndSchedule(Long memberId, Long scheduleId) {
        try {
            return Optional.of(em.createQuery("SELECT a FROM Attendance a WHERE a.member.id = :memberId AND a.schedule.id = :scheduleId", Attendance.class)
                    .setParameter("memberId", memberId)
                    .setParameter("scheduleId", scheduleId)
                    .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public boolean hasBooking(Long memberId, Long scheduleId) {
        return findByMemberAndSchedule(memberId, scheduleId).isPresent();
    }

    public List<Attendance> findByMemberId(Long memberId) {
        return em.createQuery(
                        "SELECT a FROM Attendance a " +
                                "LEFT JOIN FETCH a.program " +
                                "LEFT JOIN FETCH a.schedule " +
                                "LEFT JOIN FETCH a.schedule.program " +
                                "LEFT JOIN FETCH a.schedule.instructor " +
                                "WHERE a.member.id = :memberId ORDER BY a.attendedOn DESC",
                        Attendance.class
                )
                .setParameter("memberId", memberId)
                .getResultList();
    }

    @Transactional
    public void deleteByMemberId(Long memberId) {
        em.createQuery("DELETE FROM Attendance a WHERE a.member.id = :memberId")
                .setParameter("memberId", memberId)
                .executeUpdate();
    }

    public long countByScheduleId(Long scheduleId) {
        return em.createQuery("SELECT COUNT(a) FROM Attendance a WHERE a.schedule.id = :scheduleId", Long.class)
                .setParameter("scheduleId", scheduleId)
                .getSingleResult();
    }

    public List<User> findMembersByScheduleId(Long scheduleId) {
        return em.createQuery(
                        "SELECT a.member FROM Attendance a WHERE a.schedule.id = :scheduleId",
                        User.class
                )
                .setParameter("scheduleId", scheduleId)
                .getResultList();
    }
}