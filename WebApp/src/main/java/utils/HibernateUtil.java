package utils;

import entity.*;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static SessionFactory sessionFactory = null;
    static {
        try {
            sessionFactory = new Configuration()
                    .configure()
                    .addAnnotatedClass(clients.class)
                    .addAnnotatedClass(orders.class)
                    .addAnnotatedClass(stations.class)
                    .addAnnotatedClass(stations_on_trip.class)
                    .addAnnotatedClass(trips.class)
                    .buildSessionFactory();
        } catch (HibernateException ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}