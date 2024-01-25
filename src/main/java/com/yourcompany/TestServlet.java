package com.yourcompany;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.UserTransaction;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/test")
public class TestServlet extends HttpServlet {

    @PersistenceContext(unitName = "MSpaceSMSServicePUX")
    private EntityManager entityManager;

//    @Resource
//    private UserTransaction userTransaction;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        List<Object> tables = new ArrayList<Object>();
        try {

            Query nativeQuery = entityManager.createNativeQuery("SHOW TABLES");
            tables = nativeQuery.getResultList();

        } catch (Exception e) {
            e.printStackTrace();
        }
        response.getWriter().println("Tables Present: " + tables);

    }
}
