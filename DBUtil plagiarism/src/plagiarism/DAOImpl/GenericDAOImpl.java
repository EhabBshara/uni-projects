/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plagiarism.DAOImpl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import plagiarism.IDAO.IGenericDAO;

public class GenericDAOImpl<T> implements IGenericDAO<T> {

    private Logger LOGGER;
    private SessionFactory sessionFactory;

    public GenericDAOImpl(Class<T> cl, SessionFactory sessionFactory) {
        this.LOGGER = Logger.getLogger(cl.getName() + "GenericDAO");
        this.sessionFactory = sessionFactory;
        if (sessionFactory == null) {
            throw new RuntimeException("Session factory is null!!!");
        }
    }

    @Override
    public T get(Class<T> cl, Integer id) {
        LOGGER.info("STARTED - get");
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        @SuppressWarnings("unchecked")
        T element = (T) session.get(cl, id);
        session.getTransaction().commit();
        LOGGER.info("FINISHED - get");
        return element;
    }

    @Override
    public T save(T object) {
        LOGGER.info("STARTED - save");
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.save(object);
        session.getTransaction().commit();
        LOGGER.info("FINISHED - save");
        return object;
    }

    @Override
    public void update(T object) {
        LOGGER.info("STARTED - update");
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.update(object);
        session.getTransaction().commit();
        LOGGER.info("FINISHED - update");
    }

    @Override
    public void delete(T object) {
        LOGGER.info("STARTED - delete");
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.delete(object);
        session.getTransaction().commit();
        LOGGER.info("FINISHED - delete");
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<T> query(String hsql, String where, Map<String, Object> params) {
        LOGGER.info("STARTED - query");
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        if (where != null) {
            hsql = hsql + " " + where;
        }
        Query query = session.createQuery(hsql);

        if (params != null) {
            for (String i : params.keySet()) {
                query.setParameter(i, params.get(i));
            }
        }
        if (hsql.toUpperCase().indexOf("DELETE") != -1) {
            query.executeUpdate();
        }
        List<T> result = null;
        if ((hsql.toUpperCase().indexOf("DELETE") == -1)
                && (hsql.toUpperCase().indexOf("UPDATE") == -1)
                && (hsql.toUpperCase().indexOf("INSERT") == -1)) {
            result = query.list();
            LOGGER.info("FINISHED - query. Result size=" + result.size());
        } else {
            LOGGER.info("FINISHED - query. ");
        }
        session.getTransaction().commit();
        return result;
    }
}
