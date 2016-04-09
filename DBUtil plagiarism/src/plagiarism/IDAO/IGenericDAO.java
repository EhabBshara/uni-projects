/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plagiarism.IDAO;

import java.util.List;
import java.util.Map;

public interface IGenericDAO<T> {
    public T get(Class<T> cl, Integer id);
    public T save(T object);
    public void update(T object);
    public void delete(T object);
    public List<T> query(String hsql, String where, Map<String, Object> params);

}