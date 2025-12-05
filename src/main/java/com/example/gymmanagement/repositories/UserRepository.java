package com.example.gymmanagement.repositories;

import com.example.gymmanagement.entities.User;
import com.example.gymmanagement.entities.UserRole;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class UserRepository {

    @PersistenceContext(unitName = "GymPU")
    private EntityManager em;

    public List<User> findAllTrainers(boolean includeInactive) {
        String queryString = "SELECT u FROM User u WHERE u.role = :role";
        if (!includeInactive) {
            queryString += " AND u.isActive = true";
        }
        TypedQuery<User> query = em.createQuery(queryString, User.class);
        query.setParameter("role", UserRole.TRAINER);
        return query.getResultList();
    }

    public User findById(Long id) { return em.find(User.class, id); }
    public List<User> findAll() { return em.createQuery("SELECT u FROM User u", User.class).getResultList(); }

    public User findByUsername(String username) {
        try {
            return em.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<User> findAllMembers() {
        return em.createQuery("SELECT u FROM User u WHERE u.role = :role", User.class)
                .setParameter("role", UserRole.MEMBER)
                .getResultList();
    }

    @Transactional
    public void save(User user) {
        if (user.getId() == null) {
            em.persist(user);
        } else {
            em.merge(user);
        }
    }

    @Transactional
    public void deleteById(Long id) {
        User user = findById(id);
        if (user != null) {
            em.remove(user);
        }
    }

    public List<User> findByRole(UserRole role) {
        return em.createQuery("SELECT u FROM User u WHERE u.role = :role", User.class)
                .setParameter("role", role)
                .getResultList();
    }

    public long countByRole(UserRole role) {
        return em.createQuery("SELECT count(u) FROM User u WHERE u.role = :role", Long.class)
                .setParameter("role", role)
                .getSingleResult();
    }
}