package plagiarism.DAOImpl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import plagiarism.IDAO.IGenericDAO;

/**
 *
 * @author Ali-Wassouf
 * @param <T> generic Java type
 */
public class GenericDAOImpl<T> implements IGenericDAO<T> {

    private Logger LOGGER;
    private SessionFactory sessionFactory;

    /**
     *
     * @param cl class of generic type T
     * @param sessionFactory from <b>Hibernate</b> to create a session or
     * getting the current session.
     */
    public GenericDAOImpl(Class<T> cl, SessionFactory sessionFactory) {
        this.LOGGER = Logger.getLogger(cl.getName() + "GenericDAO");
        this.sessionFactory = sessionFactory;
        if (sessionFactory == null) {
            throw new RuntimeException("Session factory is null!!!");
        }
    }

    /**
     *
     * @param cl class of generic type T
     * @param id id of the record in the database of the entity class of type
     * <i>cl</i>
     * @return object of type T who's id matches the id passed to this method.
     */
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

    /**
     *
     * @param object Object of generic type T
     * @return An object of type T which has been persisted to database.
     */
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

    /**
     *
     * @param object Object of generic type T
     */
    @Override
    public void update(T object) {
        LOGGER.info("STARTED - update");
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.update(object);
        session.getTransaction().commit();
        LOGGER.info("FINISHED - update");
    }

    /**
     *
     * @param object Object of generic type T
     */
    @Override
    public void delete(T object) {
        LOGGER.info("STARTED - delete");
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.delete(object);
        session.getTransaction().commit();
        LOGGER.info("FINISHED - delete");
    }

    /**
     *
     * @param hsql Hibernate query
     * @param where parameterized <i>WHERE</i> clause if there is no such clause
     * in the query it is passed with null value and the function will not
     * append it to the HSQL.
     * @param params Map of parameters to set into the parameterized where
     * @return List of all records matching the condition in the <i>WHERE</i>
     * clause.
     */
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
