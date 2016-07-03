/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import plagiarism.DAOImpl.GenericServiceImpl;
import plagiarism.IDAO.IGenericService;
import plagiarism.util.pojos.Annotation;
import plagiarism.util.pojos.Assoc;
import plagiarism.util.pojos.HibernateUtil;
import plagiarism.util.pojos.Source_doc;
import plagiarism.util.pojos.Suspicious_doc;

/**
 *
 * @author dali
 */
public class ResultsEvaluator {

    public static void main(String[] args) {
        IGenericService<Assoc> assocDocsService
                = new GenericServiceImpl<>(Assoc.class, HibernateUtil.getSessionFactory());
        IGenericService<Annotation> annotationService
                = new GenericServiceImpl<>(Annotation.class, HibernateUtil.getSessionFactory());

        String hsql = "SELECT  a.suspicious_doc , a.source_doc from Annotation a group by  a.suspicious_doc ";
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        List<Object[]> tuples = (List<Object[]>) session.createQuery(hsql).list();
        session.getTransaction().commit();

        for (int i = 0; i < tuples.size(); i++) {
            Source_doc source_doc = (Source_doc) tuples.get(i)[1];
            Suspicious_doc suspicious_doc = (Suspicious_doc) tuples.get(i)[0];

            Map<String, Object> params = new HashMap<>();
            params.put("sus", (int) suspicious_doc.getSuspicious_doc_id());
            params.put("source", (int) source_doc.getSource_doc_id());
            List<Annotation> a = annotationService.getByWhere("where source_doc_id = :source and suspicious_doc_id = :sus ", params);
            session = sessionFactory.getCurrentSession();
            String sql = "SELECT assoc.phrase , assoc.testphrase "
                    + "FROM  Assoc AS assoc "
                    + "INNER JOIN  assoc.phrase AS ph "
                    + "INNER JOIN  assoc.testphrase AS t "
                    + "WHERE  ph.source_doc = " + source_doc.getSource_doc_id()
                    + " AND  t.suspicious_doc = " + suspicious_doc.getSuspicious_doc_id();
            session.beginTransaction();

            List<Object[]> assocs = (List<Object[]>) session.createQuery(sql).list();
            session.getTransaction().commit();

        }

    }

}
