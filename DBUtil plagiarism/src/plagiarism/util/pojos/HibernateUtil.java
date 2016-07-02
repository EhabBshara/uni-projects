/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plagiarism.util.pojos;

import java.util.Properties;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 * Hibernate Utility class with a convenient method to get Session Factory
 * object.
 *
 * @author Ali-Wassouf
 */
public class HibernateUtil {

    private static final SessionFactory sessionFactory;

    static {
        try {
            // Create the SessionFactory from standard (hibernate.cfg.xml) 
            // config file.
            //sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
            Configuration config = new Configuration().configure("hibernate.cfg.xml");
            Properties properties = config.getProperties();
            config.addAnnotatedClass(Assoc.class);
            config.addAnnotatedClass(TestPhrase.class);
            config.addAnnotatedClass(Phrase.class);
            //config.addAnnotatedClass(Token.class);
            config.addAnnotatedClass(Source_doc.class);
            config.addAnnotatedClass(Suspicious_doc.class);
            config.addAnnotatedClass(Annotation.class);
            config.addAnnotatedClass(CandidateDocs.class);
            StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder().applySettings(properties);
            ServiceRegistry registry = registryBuilder.build();
            sessionFactory = config.buildSessionFactory(registry);
        } catch (Throwable ex) {
            // Log the exception. 
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     * Gets a <b>SessionFactory</b>
     *
     * @return sessionFactory instance.
     */
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
