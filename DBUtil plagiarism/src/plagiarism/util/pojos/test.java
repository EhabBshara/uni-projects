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
        IGenericService<Token> tokenService = 
                new GenericServiceImpl<>(Token.class, HibernateUtil.getSessionFactory());
        Phrase p = new Phrase();
        List<Token> tokens = new ArrayList<Token>();
        tokens.add(new Token("Ali"));
        tokens.add(new Token("sam"));
        tokens.add(new Token("Ehab"));
        for(Token T : tokens){
            T.setPhrase(p);
            tokenService.save(T);
        }

    }

}
