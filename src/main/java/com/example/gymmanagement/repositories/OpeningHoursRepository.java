package com.example.gymmanagement.repositories;

import com.example.gymmanagement.entities.OpeningHours;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@ApplicationScoped
public class OpeningHoursRepository {
    @PersistenceContext(unitName = "GymPU")
    private EntityManager em;

    public List<OpeningHours> findAllOrdered() {
        return em.createQuery(
                "SELECT oh FROM OpeningHours oh ORDER BY " +
                        "CASE oh.dayOfWeek " +
                        "WHEN 'MONDAY' THEN 1 WHEN 'TUESDAY' THEN 2 WHEN 'WEDNESDAY' THEN 3 " +
                        "WHEN 'THURSDAY' THEN 4 WHEN 'FRIDAY' THEN 5 WHEN 'SATURDAY' THEN 6 " +
                        "WHEN 'SUNDAY' THEN 7 END, oh.openTime",
                OpeningHours.class
        ).getResultList();
    }
}