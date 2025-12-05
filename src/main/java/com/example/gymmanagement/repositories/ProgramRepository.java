package com.example.gymmanagement.repositories;

import com.example.gymmanagement.entities.Program;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@ApplicationScoped
public class ProgramRepository {

    @PersistenceContext(unitName = "GymPU")
    private EntityManager em;

    public Program findById(Long id) {
        return em.find(Program.class, id);
    }

    public List<Program> findAll() {
        return em.createQuery("SELECT p FROM Program p ORDER BY p.name", Program.class).getResultList();
    }

    public long countAll() {
        return em.createQuery("SELECT COUNT(p) FROM Program p", Long.class).getSingleResult();
    }

    public void save(Program program) {
        if (program.getId() == null) {
            em.persist(program);
        } else {
            em.merge(program);
        }
    }

    public void delete(Program program) {
        if (!em.contains(program)) {
            program = em.merge(program);
        }
        em.remove(program);
    }
}