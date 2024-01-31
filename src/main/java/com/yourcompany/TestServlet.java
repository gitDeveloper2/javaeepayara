package com.yourcompany;

import static com.yourcompany.HibernateUtil.closeSession;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@WebServlet("/test")
public class TestServlet extends HttpServlet {

 

//    @Resource
//    private UserTransaction userTransaction;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        List<Object> tables = new ArrayList<Object>();
        Session session=null;
       try (SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(PersonEntity.class).buildSessionFactory()) {
            session = factory.openSession();
            // Create CriteriaBuilder
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

            // Create CriteriaQuery
            CriteriaQuery<PersonEntity> criteriaQuery = criteriaBuilder.createQuery(PersonEntity.class);
            criteriaQuery.from(PersonEntity.class);

            // Execute query and get results
            List<PersonEntity> persons = session.createQuery(criteriaQuery).getResultList();

            // Print the results
            for (PersonEntity person : persons) {
                tables.add(person);
                System.out.println(person);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
           closeSession(session);
       }
        
        
        
        response.getWriter().println("Tables Present: " + tables);

    }
    private static void savePerson(Session session, String name, int age) {
        try {
            session.beginTransaction();

            PersonEntity person = new PersonEntity();
            person.setName(name);
            person.setAge(age);

            session.save(person);

            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        }
    }

}
