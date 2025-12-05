package com.example.gymmanagement.repositories;

import com.example.gymmanagement.entities.MemberProgram;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@ApplicationScoped
public class MemberProgramRepository {
    @PersistenceContext(unitName = "GymPU")
    private EntityManager em;

    public List<MemberProgram> findByMemberId(Long memberId) {
        return em.createQuery("SELECT mp FROM MemberProgram mp JOIN FETCH mp.program WHERE mp.member.id = :memberId", MemberProgram.class)
                .setParameter("memberId", memberId)
                .getResultList();
    }
}