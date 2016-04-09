/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plagiarism.IDAO;

import java.util.List;
import java.util.Map;

public interface IGenericService<T> extends IGenericDAO<T> {

    List<T> getAll();

    void deleteAll();

    List<T> getByWhere(String where, Map<String, Object> params);

    void deleteByWhere(String where, Map<String, Object> params);
}
