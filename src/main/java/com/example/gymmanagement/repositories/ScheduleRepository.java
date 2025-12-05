package com.example.gymmanagement.repositories;

import com.example.gymmanagement.entities.Program;
import com.example.gymmanagement.entities.Schedule;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@ApplicationScoped
public class ScheduleRepository {

    @PersistenceContext(unitName = "GymPU")
    private EntityManager em;

    public Schedule findById(Long id) {
        try {
            return em.createQuery(
                            "SELECT s FROM Schedule s JOIN FETCH s.program WHERE s.id = :id",
                            Schedule.class
                    )
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public void save(Schedule schedule) {
        if (schedule.getId() == null) {
            em.persist(schedule);
        } else {
            em.merge(schedule);
        }
    }

    public void delete(Schedule schedule) {
        if (!em.contains(schedule)) {
            schedule = em.merge(schedule);
        }
        em.remove(schedule);
    }

    public List<Schedule> findByInstructorId(Long instructorId) {
        return em.createQuery(
                        "SELECT s FROM Schedule s JOIN FETCH s.program JOIN FETCH s.instructor WHERE s.instructor.id = :instructorId ORDER BY s.dayOfWeek, s.startTime",
                        Schedule.class
                )
                .setParameter("instructorId", instructorId)
                .getResultList();
    }

    public List<Schedule> findAllOrdered() {
        return em.createQuery(
                        "SELECT s FROM Schedule s JOIN FETCH s.program JOIN FETCH s.instructor ORDER BY s.startTime",
                        Schedule.class
                )
                .getResultList();
    }

    public List<Program> findDistinctProgramsByInstructorId(Long instructorId) {
        return em.createQuery(
                        "SELECT DISTINCT s.program FROM Schedule s WHERE s.instructor.id = :instructorId",
                        Program.class
                )
                .setParameter("instructorId", instructorId)
                .getResultList();
    }
}