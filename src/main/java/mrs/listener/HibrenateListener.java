/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mrs.listener;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 *
 * @author prathibha_s
 */
public class HibrenateListener implements ServletContextListener {

    private static Class clazz = HibrenateListener.class;
    private static ServiceRegistry serviceRegistry;
    public static final String KEY_NAME = clazz.getName();

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        ServletContext context = sce.getServletContext();

        try {
            HibernateInit hi= new HibernateInit();
            hi.initialize();
            sce.getServletContext().setAttribute(KEY_NAME, HibernateInit.sessionFactory);
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }

        context.setAttribute("sessionFactory", HibernateInit.sessionFactory);

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        try {
            HibernateInit.sessionFactory.close();
            System.out.println("Session Factory Destroyed.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
