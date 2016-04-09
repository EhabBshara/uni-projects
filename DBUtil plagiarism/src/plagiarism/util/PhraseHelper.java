/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plagiarism.util;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Ali-Wassouf
 */
public class PhraseHelper {

    private SessionFactory factory = null;
    

    public PhraseHelper() {
        factory = HibernateUtil.getSessionFactory();
    }

    
    public List<Phrase> getPhrase(Phrase phrase) {
        List<Phrase> p = null;
        Session session = factory.getCurrentSession();
        Transaction tx = session.getTransaction();
        tx.begin();
        Criteria criteria = session.createCriteria(Assoc.class);
        if (phrase.getId() != 0) {
            criteria.add(Restrictions.eq("id", phrase.getId()));
        }
        if (phrase.getPathname() != null) {
            criteria.add(Restrictions.eq("pathname", phrase.getPathname()));
        }

        if (phrase.getFilename() != null) {
            criteria.add(Restrictions.eq("filename", phrase.getFilename()));
        }

        if (phrase.getOriginal() != null) {
            criteria.add(Restrictions.eq("original", phrase.getOriginal()));
        }
        if (phrase.getTokens() != null) {
            criteria.add(Restrictions.eq("tokens", phrase.getTokens()));
        }
        p = criteria.list();
        tx.commit();
        return p;
    }

//    public void addAssociation(Phrase phrase, Assoc assoc){
//        phrase.getAssocs().add(assoc);
//    }
    public List<Phrase> getPhrase(long id, String pathname, String filename, String original, String tokens) {
        List<Phrase> phrases = null;
        Session session = factory.getCurrentSession();
        Transaction tx = session.getTransaction();
        tx.begin();
        Criteria criteria = session.createCriteria(Assoc.class);
        if (id != 0) {
            criteria.add(Restrictions.eq("id", id));
        }
        if (pathname != null) {
            criteria.add(Restrictions.eq("pathname", pathname));
        }

        if (filename != null) {
            criteria.add(Restrictions.eq("filename", filename));
        }

        if (original != null) {
            criteria.add(Restrictions.eq("original", original));
        }
        if (tokens != null) {
            criteria.add(Restrictions.eq("tokens", tokens));
        }
        phrases = criteria.list();
        tx.commit();
        return phrases;
    }

    public void deletePhrase(Phrase phrase) {
        List<Phrase> p = getPhrase(phrase);
        Session session = factory.getCurrentSession();
        Transaction tx = session.getTransaction();
        tx.begin();
        if (p != null) {
            for (Phrase pr : p) {
                session.delete(pr);
            }
        }
        tx.commit();
    }

    public void deletePhrase(Integer id, String pathname, String filename, String original, String tokens) {
        List<Phrase> phrases = getPhrase(id, pathname, filename, original, tokens);
        Session session = factory.getCurrentSession();
        Transaction tx = session.getTransaction();
        tx.begin();
        for (Phrase p : phrases) {
            session.delete(p);
        }
        tx.commit();
    }

    public void update(Integer id, String oldPathname, String oldFilename, String oldOriginal, String oldTokens,
            String newPathname, String newFilename, String newOriginal, String newTokens) {

        List<Phrase> phrases = getPhrase(id, oldPathname, oldFilename, oldOriginal, oldTokens);
        Session session = factory.getCurrentSession();
        Transaction tx = session.getTransaction();
        tx.begin();
        if (phrases != null) {
            for (Phrase p : phrases) {
                Phrase p1 = p;
                if (newPathname != null) {
                    p1.setPathname(newPathname);
                } else {
                    p1.setPathname(oldPathname);
                }
                if (newFilename != null) {
                    p1.setFilename(newFilename);
                } else {
                    p1.setFilename(oldFilename);
                }
                if (newOriginal != null) {
                    p1.setOriginal(newOriginal);
                } else {
                    p1.setOriginal(oldOriginal);
                }
                if (newTokens != null) {
                    p1.setTokens(newTokens);
                } else {
                    p1.setTokens(oldTokens);
                }
                p1.setAssociations(p.getAssociations());
                session.delete(p);
                session.save(p1);
            }
        }
        tx.commit();
    }
    
    
    public void update(Phrase oldPhrase, Phrase newPhrase) {

        List<Phrase> phrases = getPhrase(oldPhrase.getId(), oldPhrase.getPathname(), oldPhrase.getFilename(), oldPhrase.getOriginal(), 
                oldPhrase.getTokens());
        Session session = factory.getCurrentSession();
        Transaction tx = session.getTransaction();
        tx.begin();
        if (phrases != null) {
            for (Phrase p : phrases) {
                Phrase p1 = p;
                if (newPhrase.getPathname() != null) {
                    p1.setPathname(newPhrase.getPathname());
                } else {
                    p1.setPathname(oldPhrase.getPathname());
                }
                if (newPhrase.getFilename() != null) {
                    p1.setFilename(newPhrase.getFilename());
                } else {
                    p1.setFilename(oldPhrase.getFilename());
                }
                if (newPhrase.getOriginal() != null) {
                    p1.setOriginal(newPhrase.getOriginal());
                } else {
                    p1.setOriginal(oldPhrase.getOriginal());
                }
                if (newPhrase.getTokens() != null) {
                    p1.setTokens(newPhrase.getTokens());
                } else {
                    p1.setTokens(oldPhrase.getTokens());
                }
                p1.setAssociations(p.getAssociations());
                session.delete(p);
                session.save(p1);
            }
        }
        tx.commit();
    }

    public void insertPhrase(Phrase phrase) {
        List<Phrase> p = getPhrase(phrase);
        Session session = factory.getCurrentSession();
        Transaction tx = session.getTransaction();
        tx.begin();
        Phrase p2 = new Phrase();
        if (p == null) {
                p2.setTokens(phrase.getTokens());
                p2.setPathname(phrase.getPathname());
                p2.setFilename(phrase.getFilename());
                p2.setOriginal(phrase.getOriginal());
                session.save(p2);
        }
        tx.commit();
    }

    public void insertPhrase(String pathname, String filename, String original, String tokens) {
        List<Phrase> pharses = getPhrase(0, pathname, filename, original, tokens);
        Session session = factory.getCurrentSession();
        Transaction tx = session.getTransaction();
        tx.begin();
        Phrase phrase = new Phrase();
        if (pharses == null) {
            phrase.setTokens(tokens);
            phrase.setPathname(pathname);
            phrase.setFilename(filename);
            phrase.setOriginal(original);
            session.save(phrase);
        }
        tx.commit();
    }

    public List<Phrase> view() {
        Session session = factory.getCurrentSession();
        Transaction tx = session.getTransaction();
        tx.begin();
        List<Phrase> results = session.createQuery("from plagiarism.util.Phrase").list();
        tx.commit();
        return results;
    }
//    public List<Phrase> selectWherePathName(String pathname){
//        Session session = factory.getCurrentSession();
//        Transaction tx = session.getTransaction();
//        tx.begin();
//        Query query = session.createQuery("from plagiarism.util.Phrase as phrase where phrase.pathname =:pathname");
//        query.setEntity("pathname", pathname);
//        List results = query.list();
//        tx.commit();
//        return results;
//    }
//    
//    public List<Phrase> selectWhereFilename(String filename){
//        Session session = factory.getCurrentSession();
//        Transaction tx = session.getTransaction();
//        tx.begin();
//        Query query = session.createQuery("from plagiarism.util.Phrase as phrase where phrase.filename =:filename");
//        query.setEntity("filename", filename);
//        List results = query.list();
//        tx.commit();
//        return results;
//    }
//    public List<Phrase> selectWhereOriginal(String original){
//        Session session = factory.getCurrentSession();
//        Transaction tx = session.getTransaction();
//        tx.begin();
//        Query query = session.createQuery("from plagiarism.util.Phrase as phrase where phrase.original =:original");
//        query.setEntity("original", original);
//        List results = query.list();
//        tx.commit();
//        return results;
//    }
//    
//    
//    public List<Phrase> selectWhereTokens(String tokens){
//        Session session = factory.getCurrentSession();
//        Transaction tx = session.getTransaction();
//        tx.begin();
//        Query query = session.createQuery("from plagiarism.util.Phrase as phrase where phrase.tokens =:tokens");
//        query.setEntity("tokens", tokens);
//        List results = query.list();
//        tx.commit();
//        return results;
//    }
}
