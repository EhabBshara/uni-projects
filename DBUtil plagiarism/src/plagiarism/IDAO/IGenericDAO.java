/**
 *
 * <h1>IGenericDAO<T> </h1>
 *
 *
 * <h2>Type Parameters:</h2> T 
 * <h2>All Known Implementing Classes:</h2> {@link GenericDAOImpl}
 *
 */
package plagiarism.IDAO;

import java.util.List;
import java.util.Map;
import plagiarism.DAOImpl.GenericDAOImpl;

/**
 *
 * DAO stands for <i>Data Access Object</i> this interface is used to reduce the
 * amount of code written so we don't duplicate it for each entity class. That's
 * the idea behind making it generic. Along with <b>Hibernate</b> this interface
 * incapsulates the CRUD operations.
 *
 * @author Ali-Wassouf
 * @param <T>
 * @since 2016 - 04 - 10
 * @version 1.0
 */
public interface IGenericDAO<T> {

    /**
     * Incapsulates the <i>SELECT</i> operation on a database.
     *
     * @param cl class of the object we want to query
     * @param id id of the record we query
     * @return T Generic type for the object of class cl
     */
    public T get(Class<T> cl, Integer id);

    /**
     * Incapsulates the <i>INSERT</i> operation on a database.
     *
     * @param object of generic type to be persisted
     * @return The persisted object.
     */
    public T save(T object);

    /**
     * Incapsulate the <i>UPDATE</i> operation.
     *
     * @param object of generic type to be updated.
     */
    public void update(T object);

    /**
     * Incapsulates the <i>DELETE</i> operation.
     *
     * @param object of generic type to be deleted.
     */
    public void delete(T object);

    /**
     * Incapsulates a query to the database, using <i>HQL</i> "Hibernate query
     * language".
     *
     * @param hsql Hibernate query
     * @param where parameterized where clause
     * @param params Map of parameters to set into the parameterized where
     * clause.
     * @return {@link List}<T> list of the generic objects returned by the
     * query.
     */
    public List<T> query(String hsql, String where, Map<String, Object> params);

}
