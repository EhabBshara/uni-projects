package plagiarism.DAOImpl;

import java.util.List;
import java.util.Map;
import org.hibernate.SessionFactory;
import plagiarism.IDAO.IGenericDAO;
import plagiarism.IDAO.IGenericService;

/**
 *
 * @author Ali-Wassouf
 * @param <T>
 */
public class GenericServiceImpl<T> implements IGenericService<T> {

    private IGenericDAO<T> dao;
    private Class<T> cl;

    /**
     * Constructor of the GenericServiceImpl
     *
     * @param cl class of generic object of type T
     * @param sessionFactory from <b>Hibernate</b> to create a session or
     * getting the current session.
     */
    public GenericServiceImpl(Class<T> cl, SessionFactory sessionFactory) {
        this.cl = cl;
        dao = new GenericDAOImpl<T>(cl, sessionFactory);
    }

    @Override
    public T get(Class<T> cl, Integer id) {
        //LOGGER.trace("STARTED - get");
        return (T) dao.get(cl, id);
    }

    @Override
    public T save(T object) {
        return (T) dao.save(object);
    }
    
    @Override
    public List<T> bulkSave(List<T> object)
    {
        return (List<T>)dao.bulkSave(object);
    }

    @Override
    public void update(T object) {
        dao.update(object);
    }

    @Override
    public void delete(T object) {
        dao.delete(object);
    }

    @Override
    public List<T> query(String hsql, String where, Map<String, Object> params) {
        return (List<T>) dao.query(hsql, where, params);
    }

    @Override
    public List<T> getAll() {
        return query("from " + cl.getName(), null, null);
    }

    @Override
    public void deleteAll() {
        query("delete from " + cl.getName(), null, null);
    }

    @Override
    public List<T> getByWhere(String where, Map<String, Object> params) {
        return query("from " + cl.getName(), where, params);
    }

    @Override
    public void deleteByWhere(String where, Map<String, Object> params) {
        query("delete from " + cl.getName(), where, params);
    }

}
