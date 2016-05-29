/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plagiarism.util.pojos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import plagiarism.DAOImpl.GenericServiceImpl;
import plagiarism.IDAO.IGenericService;

/**
 *
 * @author Ali-Wassouf
 */
public class test {

    private static SessionFactory factory = null;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

//        Phrase phrase = null;
//        TestPhrase tp = null;
        factory = HibernateUtil.getSessionFactory();

        IGenericService<Phrase> phraseService
                = new GenericServiceImpl<>(Phrase.class, HibernateUtil.getSessionFactory());
        IGenericService<TestPhrase> testphraseService
                = new GenericServiceImpl<>(TestPhrase.class, HibernateUtil.getSessionFactory());
        IGenericService<Assoc> assocService
                = new GenericServiceImpl<>(Assoc.class, HibernateUtil.getSessionFactory());
        IGenericService<Source_doc> sourceDocService = 
                new GenericServiceImpl<>(Source_doc.class, HibernateUtil.getSessionFactory());
        IGenericService<Suspicious_doc> suspiciousDocService = 
                new GenericServiceImpl<>(Suspicious_doc.class, HibernateUtil.getSessionFactory());
       IGenericService<Annotation> annotationService = 
                new GenericServiceImpl<>(Annotation.class, HibernateUtil.getSessionFactory());
       
       Map<String, Object> params = new HashMap<String, Object>();
                        params.put("ANNOTATION_ID", 3450);
       Annotation annotation=annotationService.getByWhere("where annotation_id = :ANNOTATION_ID", params).get(0);
        System.out.println(annotation.getSource_doc().getSource_doc_text().substring((int)annotation.getSource_offset(),(int)annotation.getSource_offset()+(int) annotation.getSource_length()));
         System.out.println(annotation.getSuspicious_doc().getSuspicious_doc_text().substring((int)annotation.getSuspicious_offset(),(int)annotation.getSuspicious_offset()+(int) annotation.getSuspicious_length()));
    }

}
