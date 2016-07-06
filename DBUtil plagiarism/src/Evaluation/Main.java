/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Evaluation;

import Utils.Constants;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import plagiarism.DAOImpl.GenericServiceImpl;
import plagiarism.IDAO.IGenericService;
import plagiarism.util.pojos.Annotation;
import plagiarism.util.pojos.Assoc;
import plagiarism.util.pojos.CandidateDocs;
import plagiarism.util.pojos.HibernateUtil;
import plagiarism.util.pojos.Phrase;
import plagiarism.util.pojos.Source_doc;
import plagiarism.util.pojos.Suspicious_doc;

/**
 *
 * @author dali
 */
public class Main {

    public static void main(String[] args) {

//        String myquery = "SELECT  a.annotation_id ,  a.source_offset ,  a.source_length ,  a.suspicious_offset ,  a.suspicious_length ,  a.obfuscation ,  a.type , MAX(  a.source_doc ) AS  source_doc , MAX(  a.suspicious_doc ) AS suspicious_doc "
//                + "FROM Annotation as a "
//                + "GROUP BY  a.suspicious_doc ,  a.source_doc ";
//
//        IGenericService<Annotation> annotationService
//                = new GenericServiceImpl<>(Annotation.class, HibernateUtil.getSessionFactory());
//        Map<String, Object> params1 = new HashMap<>();
//        List<Annotation> annotations = (List<Annotation>) annotationService.query(myquery, null, null);

        String hsql = "SELECT distinct  a.suspicious_doc , a.source_doc from Annotation a ";

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
//        Query query = session.createQuery(myquery);

        List<Object[]> tuples = (List<Object[]>) session.createQuery(hsql).list();
        session.getTransaction().commit();
//        for (Object[] tuple : tuples) {
//            Source_doc ue = (Source_doc) tuple[1];
//            Suspicious_doc roleId = (Suspicious_doc) tuple[0];
//        }

        Evaluater evaluater = new Evaluater();
        test t = new test();
        List<Assoc> assocs = new ArrayList<>();
        for (int a = 0; a < tuples.size(); a++) {
            Source_doc source_doc = (Source_doc) tuples.get(a)[1];
            Suspicious_doc suspicious_doc = (Suspicious_doc) tuples.get(a)[0];
            List<CandidatePair> candidateSentences = t.assosiatePairs(source_doc, suspicious_doc);
            for (CandidatePair pair : candidateSentences) {
                if (evaluater.evaluate(pair.getPhrase(), pair.getTestPhrase(), true, false)) {
                    assocs.add(new Assoc(pair.getPhrase(), pair.getTestPhrase(), 0.0));
                }
            }
        }

//                IGenericService<Annotation> annotationService
//                = new GenericServiceImpl<>(Annotation.class, HibernateUtil.getSessionFactory());
//        Map<String, Object> params1 = new HashMap<String, Object>();
//            params1.put("ANNOTATION_ID", 723);
//            Annotation a = annotationService.getByWhere("where annotation_id = :ANNOTATION_ID", params1).get(0);
//
//            System.out.println(a.getSource_doc().getSource_doc_text().substring((int) a.getSource_offset(), (int) a.getSource_offset() + (int) a.getSource_length()));
//            System.out.println("");
//            System.out.println(a.getSuspicious_doc().getSuspicious_doc_text().substring((int) a.getSuspicious_offset(), (int) a.getSuspicious_offset() + (int) a.getSuspicious_length()));
//        test t = new test();
//        Evaluater evaluater = new Evaluater();
//        List<Assoc> assocs = new ArrayList<>();
//        for (int i = 0; i < candidates.size(); i++) {
//            CandidateDocs candidate = candidates.get(i);
////        List<CandidatePair> sentencesPairsList = t.getCandidatePair(candidates.getSource_doc(), candidates.getSuspicious_doc());
//            List<CandidatePair> sentencesPairsList = t.assosiatePairs(candidate.getSource_doc(), candidate.getSuspicious_doc());
//            for (CandidatePair pair : sentencesPairsList) {
//                if (evaluater.evaluate(pair.getPhrase(), pair.getTestPhrase(),false,false)) {
//                    assocs.add(new Assoc(pair.getPhrase(), pair.getTestPhrase(), 0.0));
//                }
//            }
//        }
//
        IGenericService<Assoc> assocDocsService
                = new GenericServiceImpl<>(Assoc.class, HibernateUtil.getSessionFactory());
//        assocDocsService.bulkSave(assocs);
//        sentencesPairsList = t.calculateFeatures(sentencesPairsList);
//
//        
////        System.out.println(sentencesPairsList);
        System.out.println("done");
    }

}
