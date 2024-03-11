package ru.aston.hw4.utils;


import org.hibernate.Cache;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class HibernateSessionFactoryUtil {

    private static final SessionFactory sessionFactory = buildSessionFactory();
    private static final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();


    public static SessionFactory buildSessionFactory() {
        try {
            return new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            buildSessionFactory();
            startSecondLevelCacheReset();
        }
        return sessionFactory;
    }

    private static void startSecondLevelCacheReset() {
        executor.scheduleWithFixedDelay(() -> {
            SessionFactory sessionFactory = getSessionFactory();
            Cache cache = sessionFactory.getCache();
            cache.evictDefaultQueryRegion();
            System.out.println("кэш очищен");
        }, 30, 30, TimeUnit.SECONDS);
    }

    public static Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    public static void close() {
        getSessionFactory().close();
        executor.close();
    }
}

