/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plagiarism.util;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Ali-Wassouf
 */
public class AssocHelper {

    private SessionFactory factory = null;

    AssocHelper() {

        factory = HibernateUtil.getSessionFactory();
    }


    
    /**
     * This method is used to delete data from table Assoc 
     * parameters that we do not want to add to the where clause must be passed as null
     * @param assocId the id column in the table
     * @param phrase String to represent the name of Phrase attribute in Assoc table
     * @param testphrase String to represent the name of attribute TestPhrase 
     * @param score Double
     */
    
    public void deleteAssociation(int assocId, Phrase phrase, TestPhrase testphrase, Double score){
        List<Assoc> associations = getAssociations(assocId, phrase, testphrase, score);
        Session session = factory.getCurrentSession();
        Transaction tx = session.getTransaction();
        tx.begin();
        for(Assoc assoc :associations)
            session.delete(assoc);
        tx.commit();
    }
    
    
    
    /**
     * This method is used to delete data from table Assoc with attribute score between two given values
     * parameters that we do not want to add to the where clause must be passed as null
     * @param assocId the id column in the table
     * @param phrase String to represent the name of Phrase attribute in Assoc table
     * @param testphrase String to represent the name of attribute TestPhrase 
     * @param maxScore Double the maximum value
     * @param minScore Double the minimum value
     */
    
    public void deleteAssociation(int assocId, Phrase phrase, TestPhrase testphrase, Double maxScore,Double minScore){
        List<Assoc> associations = getAssociations(assocId, phrase, testphrase, maxScore, minScore);
        Session session = factory.getCurrentSession();
        Transaction tx = session.getTransaction();
        tx.begin();
        for(Assoc assoc :associations)
            session.delete(assoc);
        tx.commit();
    }
    
    
    /**
     * This method is used to delete data from table Assoc with attribute score smaller than a given values
     * parameters that we do not want to add to the where clause must be passed as null
     * @param maxScore Double the maximum value
     * @param assocId the id column in the table
     * @param phrase String to represent the name of Phrase attribute in Assoc table
     * @param testphrase String to represent the name of attribute TestPhrase 
     */
    public void deleteAssociation(Double maxScore, int assocId, Phrase phrase, TestPhrase testphrase){
        List<Assoc> associations = getAssociations(maxScore, assocId, phrase, testphrase);
        Session session = factory.getCurrentSession();
        Transaction tx = session.getTransaction();
        tx.begin();
        for(Assoc assoc :associations)
            session.delete(assoc);
        tx.commit();
    }
    
    /**
     * This method is used to delete data from table Assoc with attribute score greater than a given values
     * parameters that we do not want to add to the where clause must be passed as null
     * @param assocId the id column in the table
     * @param minScore Double the minimum value
     * @param phrase String to represent the name of Phrase attribute in Assoc table
     * @param testphrase String to represent the name of attribute TestPhrase 
     */
    public void deleteAssociation(int assocId, Double minScore, Phrase phrase, TestPhrase testphrase){
        List<Assoc> associations = getAssociations(assocId, minScore, phrase, testphrase);
        Session session = factory.getCurrentSession();
        Transaction tx = session.getTransaction();
        tx.begin();
        for(Assoc assoc :associations)
            session.delete(assoc);
        tx.commit();
    }
    /**
     * This method is used to retrieve data from table Assoc 
     * parameters that we do not want to add to the where clause must be passed as null
     * @param assocId the id column in the table
     * @param phrase String to represent the name of Phrase attribute in Assoc table
     * @param testphrase String to represent the name of attribute TestPhrase 
     * @param score Double
     * @return  list of all associations matching the requested criteria 
     */
    public List<Assoc> getAssociations(int assocId, Phrase phrase, TestPhrase testphrase, Double score) {
        List<Assoc> associations = null;
        Session session = factory.getCurrentSession();
        Transaction tx = session.getTransaction();
        tx.begin();
        Criteria criteria = session.createCriteria(Assoc.class);
        if (assocId != 0) {
            criteria.add(Restrictions.eq("assocId", new Integer(assocId)));
        }
        if (phrase != null) {
            criteria.add(Restrictions.eq("phrase", phrase));
        }

        if (testphrase != null) {
            criteria.add(Restrictions.eq("testPhrase", testphrase));
        }
        
        if(!Double.isNaN(score)){
            criteria.add(Restrictions.eq("score", score));
        }
        associations = criteria.list();
        
        tx.commit();
        return associations;
    }
    
    /**
     * This method is used to retrieve data from table Assoc withe scores between two given values
     * parameters that we do not want to add to the where clause must be passed as null
     * @param assocId the id column in the table
     * @param phrase String to represent the name of Phrase attribute in Assoc table
     * @param testphrase String to represent the name of attribute TestPhrase 
     * @param maxScore Double the maximum value
     * @param minScore Double the minimum value
     * @return  list of all associations matching the requested criteria 
     */
    
    public List<Assoc> getAssociations(int assocId, Phrase phrase, TestPhrase testphrase, Double maxScore,Double minScore) {
        List<Assoc> associations = null;
        Session session = factory.getCurrentSession();
        Transaction tx = session.getTransaction();
        tx.begin();
        Criteria criteria = session.createCriteria(Assoc.class);
        if (assocId != 0) {
            criteria.add(Restrictions.eq("assocId", new Integer(assocId)));
        }
        if (phrase != null) {
            criteria.add(Restrictions.eq("phrase", phrase));
        }

        if (testphrase != null) {
            criteria.add(Restrictions.eq("testPhrase", testphrase));
        }
        
        if(!Double.isNaN(maxScore)&&(!Double.isNaN(minScore))){
            criteria.add(Restrictions.between("score", maxScore,minScore));
        }
        associations = criteria.list();
        
        tx.commit();
        return associations;
    }
    
    
    /**
     * This method is used to retrieve data from table Assoc withe scores smaller than a given values
     * parameters that we do not want to add to the where clause must be passed as null
     * @param maxScore Double the maximum value
     * @param assocId the id column in the table
     * @param phrase String to represent the name of Phrase attribute in Assoc table
     * @param testphrase String to represent the name of attribute TestPhrase 
     * @return  list of all associations matching the requested criteria 
     */
    public List<Assoc> getAssociations(Double maxScore,int assocId, Phrase phrase, TestPhrase testphrase) {
        List<Assoc> associations = null;
        Session session = factory.getCurrentSession();
        Transaction tx = session.getTransaction();
        tx.begin();
        Criteria criteria = session.createCriteria(Assoc.class);
        if (assocId != 0) {
            criteria.add(Restrictions.eq("assocId", new Integer(assocId)));
        }
        if (phrase != null) {
            criteria.add(Restrictions.eq("phrase", phrase));
        }

        if (testphrase != null) {
            criteria.add(Restrictions.eq("testPhrase", testphrase));
        }
        
        if(!Double.isNaN(maxScore)){
            criteria.add(Restrictions.lt("score", maxScore));
        }
        associations = criteria.list();
        
        tx.commit();
        return associations;
    }
    
    
    /**
     * This method is used to retrieve data from table Assoc withe scores greter than a given values
     * parameters that we do not want to add to the where clause must be passed as null
     * @param assocId the id column in the table
     * @param minScore Double the minimum value
     * @param phrase String to represent the name of Phrase attribute in Assoc table
     * @param testphrase String to represent the name of attribute TestPhrase 
     * @return  list of all associations matching the requested criteria 
     */
    public List<Assoc> getAssociations(int assocId,Double minScore, Phrase phrase, TestPhrase testphrase) {
        List<Assoc> associations = null;
        Session session = factory.getCurrentSession();
        Transaction tx = session.getTransaction();
        tx.begin();
        Criteria criteria = session.createCriteria(Assoc.class);
        if (assocId != 0) {
            criteria.add(Restrictions.eq("assocId", new Integer(assocId)));
        }
        if (phrase != null) {
            criteria.add(Restrictions.eq("phrase", phrase));
        }

        if (testphrase != null) {
            criteria.add(Restrictions.eq("testPhrase", testphrase));
        }
        
        if(!Double.isNaN(minScore)){
            criteria.add(Restrictions.gt("score", minScore));
        }
        associations = criteria.list();
        
        tx.commit();
        return associations;
    }
    
    
    
        public List<Assoc> viewAssoc() {
        Session session = factory.getCurrentSession();
        Transaction tx = session.getTransaction();
        tx.begin();
        List<Assoc> assoc = session.createQuery("from plagiarism.util.Assoc ").list();
        tx.commit();
        return assoc;
    }
//
//    public List<Assoc> selectWhere(Phrase phrase) {
//        Session session = factory.getCurrentSession();
//        Transaction tx = session.getTransaction();
//        tx.begin();
//        Query query = session.createQuery("from plagiarism.util.Assoc as assoc where assoc.phrase =:phrase");
//        query.setEntity("phrase", phrase);
//        List results = query.list();
//        tx.commit();
//        return results;
//    }
//
//    public List<Assoc> selectWhere(Testphrase testphrase) {
//        Session session = factory.getCurrentSession();
//        Transaction tx = session.getTransaction();
//        tx.begin();
//        Query query = session.createQuery("from plagiarism.util.Assoc as assoc where assoc.testphrase =:testphrase");
//        query.setEntity("testphrase", testphrase);
//        List results = query.list();
//        tx.commit();
//        return results;
//    }
//
//    public List<Assoc> selectWhere(Double score) {
//        Session session = factory.getCurrentSession();
//        Transaction tx = session.getTransaction();
//        tx.begin();
//        Query query = session.createQuery("from plagiarism.util.Assoc as assoc where assoc.score =:score");
//        query.setEntity("score", score);
//        List results = query.list();
//        tx.commit();
//        return results;
//    }

//    private void deleteWhere(Phrase phrase) {
//        Session session = factory.getCurrentSession();
//        Transaction tx = session.getTransaction();
//        tx.begin();
//        Query query = session.createQuery("delete plagiarism.util.Assoc as assoc where assoc.phrase =:phrase");
//        query.setEntity("phrase", phrase);
//        int success = query.executeUpdate();
//        System.out.println("delete success " + success);
//        tx.commit();
//    }
//
//    private void deleteWhere(Testphrase testphrase) {
//        Session session = factory.getCurrentSession();
//        Transaction tx = session.getTransaction();
//        tx.begin();
//        Query query = session.createQuery("delete plagiarism.util.Assoc as assoc where assoc.testphrase =:testphrase");
//        query.setEntity("testphrase", testphrase);
//        int success = query.executeUpdate();
//        System.out.println("delete success " + success);
//        tx.commit();
//    }
//
//    private void deleteWhere(Double score) {
//        Session session = factory.getCurrentSession();
//        Transaction tx = session.getTransaction();
//        tx.begin();
//        Query query = session.createQuery("delete plagiarism.util.Assoc as assoc where assoc.score =:score");
//        query.setEntity("score", score);
//        int success = query.executeUpdate();
//        System.out.println("delete success " + success);
//        tx.commit();
//    }
}
