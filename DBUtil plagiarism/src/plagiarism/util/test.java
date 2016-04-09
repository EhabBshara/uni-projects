/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plagiarism.util;

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

        IGenericService<Phrase> phraseService = new GenericServiceImpl<>(Phrase.class, HibernateUtil.getSessionFactory());
        IGenericService<TestPhrase> testphraseService = new GenericServiceImpl<>(TestPhrase.class, HibernateUtil.getSessionFactory());
        IGenericService<Assoc> assocService = new GenericServiceImpl<>(Assoc.class, HibernateUtil.getSessionFactory());

//        phrase = new Phrase();
//        phrase.setPathname("Ali wassouf");
//        phrase.setFilename("Aliano Paco");
//        phrase.setOriginal("I hat you ");
//        phrase.setTokens("jj jj jj ");
//        
//        tp = new TestPhrase();
//        tp.setFilename("Aliano");
//        tp.setPathname("Georgina");
//        tp.setPhrase("I love you");
//        
//        Assoc ass = new Assoc();
//        ass.setPhrase(phrase);
//        ass.setTestphrase(tp);
//        ass.setScore(99.0);
//        assocService.save(ass);
////        Map<String , Object> params = new HashMap<>();
////        params.put("scoreId", 99.0);
////        String where = "where score=:scoreId";
////        assocService.deleteByWhere(where, params);
        Phrase p = phraseService.get(Phrase.class, 17);
        TestPhrase tp = testphraseService.get(TestPhrase.class, 2);
        
        Assoc assoc = new Assoc();
        assoc.setPhrase(p);
        assoc.setTestphrase(tp);
        assoc.setScore(8.0);
        
        assocService.save(assoc);
        

    }

}
