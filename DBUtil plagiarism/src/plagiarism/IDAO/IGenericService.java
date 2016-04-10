/**
 *
 * <h1>IGenericService<T> </h1>
 *
 *
 * <h2>All Known Implementing Classes:</h2> {@link GenericServiceImpl}
 *
 */
package plagiarism.IDAO;

import java.util.List;
import java.util.Map;

/**
 * It is an extention for the DAO concept which includes function with special
 * conditions like retrieving records with ids between two values. The purpose
 * of making it generic is to avoid duplication of code.
 *
 * @author Ali-Wassouf
 * @param <T> generic Java type.
 * @since 2016 - 04 - 11
 * @version 1.0
 */
public interface IGenericService<T> extends IGenericDAO<T> {

    /**
     * Incapsulate <i>SELECT *</i> query
     *
     * @return List of all the records in the table of type T.
     */
    List<T> getAll();

    /**
     * Incapsulates the <i>DELETE</i> operation on every record in the table of
     * the type T.
     */
    void deleteAll();

    /**
     * Incapsulates the <i>SELECT * </i> operation to retrieve records from
     * table of type T.
     *
     * @param where parameterized <i>WHERE</i> clause.
     * @param params parameters to be mapped with the parameterized where.
     * @return List of all records satisfying the <i>WHERE</i> clause.
     */
    List<T> getByWhere(String where, Map<String, Object> params);

    /**
     * Incapsulates the <i>DELETE</i> operation with a <i>WHERE</i> clause.
     * @param where parameterized <i>WHERE</i> clause.
     * @param params parameters to be mapped with the parameterized where.
     */
    void deleteByWhere(String where, Map<String, Object> params);
}
