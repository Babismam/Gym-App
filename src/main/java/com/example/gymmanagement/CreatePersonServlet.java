package com.example.gymmanagement;

import com.example.gymmanagement.entities.User;
import com.example.gymmanagement.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/create")
public class CreatePersonServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            User person = new User();
            person.setFirstName("Μπάμπης");
            person.setLastName("Μαμουζέλλος");
            person.setEmail("babis@example.com");
            person.setRole("admin");

            session.persist(person);

            tx.commit();

            resp.setContentType("text/html; charset=UTF-8");
            resp.getWriter().write("<h1>Ο χρήστης προστέθηκε με επιτυχία!</h1>");
        } catch (Exception e) {
            resp.setContentType("text/html; charset=UTF-8");
            resp.getWriter().write("<h1>Σφάλμα: " + e.getMessage() + "</h1>");
        }
    }
}
