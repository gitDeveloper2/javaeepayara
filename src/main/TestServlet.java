package com.yourcompany;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.UserTransaction;
import java.io.IOException;

@WebServlet("/test")
public class TestServlet extends HttpServlet {

    @PersistenceContext(unitName = "MSpaceSMSServicePUX")
    private EntityManager entityManager;

    @Resource
    private UserTransaction userTransaction;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        try {
            // Begin the transaction
            userTransaction.begin();

            // Perform database operations
            // You can use entityManager here

            // Commit the transaction
            userTransaction.commit();

            response.getWriter().println("Transaction successful!");
        } catch (Exception e) {
            // Handle exceptions
            e.printStackTrace();
            response.getWriter().println("Transaction failed: " + e.getMessage());
        }
    }
}
