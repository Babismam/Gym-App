package com.example.gymmanagement;

import com.example.gymmanagement.entities.User;
import com.example.gymmanagement.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class HibernateTest {
    public static void main(String[] args) {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            User person = new User();
            person.setFirstName("Test");
            person.setLastName("User");
            person.setEmail("testuser@example.com");
            person.setRole("member");

            session.persist(person); // Αντικατάσταση του deprecated save()

            tx.commit();
            System.out.println("Εισαγωγή επιτυχής! ID: " + person.getId());
        } catch (Exception e) {
            System.err.println("Απέτυχε η εισαγωγή: " + e.getMessage());
        } finally {
            HibernateUtil.shutdown();
        }
    }
}
