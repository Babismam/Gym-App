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
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();

            User person = new User(
                    "Μπάμπης",               // firstName
                    "Μαμουζέλλος",           // lastName
                    "babis@example.com",     // email
                    "6900000000",            // phone
                    "mypassword",            // password
                    "babis",                 // username
                    "admin",                 // role
                    null                     // trainerId (ή π.χ. 1L)
            );

            session.persist(person);
            tx.commit();

            resp.setContentType("text/html");
            resp.getWriter().write("<h1>Ο χρήστης προστέθηκε με επιτυχία!</h1>");
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            resp.getWriter().write("<h1>Σφάλμα: " + e.getMessage() + "</h1>");
        } finally {
            session.close();
        }
    }

}

