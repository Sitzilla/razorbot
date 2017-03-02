package com.evansitzes.razorbot.model;


import com.ullink.slack.simpleslackapi.SlackUser;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

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
        final Session hibernateSession = sessionAnnotationFactory.getCurrentSession();
        hibernateSession.beginTransaction();
        SwearWordEntity entity = (SwearWordEntity) hibernateSession.get(SwearWordEntity.class, user.getId());

        // Create entity if doesnt exist
        if (entity == null) {
            entity = new SwearWordEntity();
            entity.setId(user.getId());
            entity.setName(user.getRealName());
            entity.setSwearWords("{}");
        }

        hibernateSession.getTransaction().commit();
        sessionAnnotationFactory.close();

        return entity;
    }

    public void updateUser(final SwearWordEntity entity) {
        final Session hibernateSession = sessionAnnotationFactory.getCurrentSession();
        hibernateSession.beginTransaction();
        //Save the Model object
        hibernateSession.saveOrUpdate(entity);
        hibernateSession.getTransaction().commit();
        sessionAnnotationFactory.close();
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
