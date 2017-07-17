/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mrs.listener;


import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
/**
 *
 * @author prathibha_s
 */
public class HibernateInit {
    public static SessionFactory sessionFactory;

    public SessionFactory initialize() {
        //            SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory(); //for hibernate 3.0
        if (sessionFactory == null || sessionFactory.isClosed()) {
            Configuration configuration = new Configuration();
            configuration.configure();
            sessionFactory = new Configuration().configure().buildSessionFactory();
        }
        return sessionFactory;
    }
}
