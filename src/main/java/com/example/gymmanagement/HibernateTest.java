package com.example.gymmanagement;

import com.example.gymmanagement.entities.User;
import com.example.gymmanagement.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class HibernateTest {
    public static void main(String[] args) {

        // Άνοιγμα Hibernate session
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();


            User person = new User("Test User", "testuser@example.com", "member");

            session.save(person);

            tx.commit();
            System.out.println("Εισαγωγή επιτυχής! ID: " + person.getId());
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            System.out.println("Απέτυχε η εισαγωγή.");
        } finally {
            session.close();
            HibernateUtil.shutdown();
        }
    }
}
