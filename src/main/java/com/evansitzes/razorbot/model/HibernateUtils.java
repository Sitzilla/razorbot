package com.evansitzes.razorbot.model;


import com.ullink.slack.simpleslackapi.SlackUser;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.util.List;

/**
 * Created by evan on 2/27/17.
 */
public class HibernateUtils {

    //Annotation based configuration
    private SessionFactory sessionAnnotationFactory;

    public HibernateUtils() {
        this.sessionAnnotationFactory = buildSessionAnnotationFactory();
    }

    public SwearWordEntity getUser(final SlackUser user) {
        final Session session = sessionAnnotationFactory.getCurrentSession();
        session.beginTransaction();
        SwearWordEntity entity = (SwearWordEntity) session.get(SwearWordEntity.class, user.getId());

        // Create entity if doesnt exist
        if (entity == null) {
            entity = new SwearWordEntity();
            entity.setId(user.getId());
            entity.setName(user.getRealName());
            entity.setSwearWords("{}");
        }

        session.getTransaction().commit();
        sessionAnnotationFactory.close();

        return entity;
    }

    public void updateUser(final SwearWordEntity entity) {
        final Session session = sessionAnnotationFactory.getCurrentSession();
        session.beginTransaction();
        //Save the Model object
        session.saveOrUpdate(entity);
        session.getTransaction().commit();
        sessionAnnotationFactory.close();
    }

    public List<SwearWordEntity> getAllUsers() {
        final Session session = sessionAnnotationFactory.getCurrentSession();
        session.beginTransaction();

        final List<SwearWordEntity> users = session.createCriteria(SwearWordEntity.class).list();

        sessionAnnotationFactory.close();
        return users;
    }


    private static SessionFactory buildSessionAnnotationFactory() {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
            final Configuration configuration = new Configuration();
            configuration.configure("hibernate-annotation.cfg.xml");
            System.out.println("Hibernate Annotation Configuration loaded");

            final ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
            System.out.println("Hibernate Annotation serviceRegistry created");

            final SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);

            return sessionFactory;
        }
        catch (final Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

}
