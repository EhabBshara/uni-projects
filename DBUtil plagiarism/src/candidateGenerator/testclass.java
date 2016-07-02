/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package candidateGenerator;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import plagiarism.util.pojos.HibernateUtil;
import plagiarism.util.pojos.Source_doc;

/**
 *
 * @author AliWAA
 */
public class testclass {

    private static SessionFactory factory = null;

    public static void main(String[] args) {
        try {
            factory = HibernateUtil.getSessionFactory();
            FullTextSession fullTextSession = Search.getFullTextSession(factory.getCurrentSession());
            fullTextSession.createIndexer().startAndWait();
            Transaction tx = fullTextSession.beginTransaction();

            // create native Lucene query unsing the query DSL
            // alternatively you can write the Lucene query using the Lucene query parser
            // or the Lucene programmatic API. The Hibernate Search DSL is recommended though
            QueryBuilder qb = fullTextSession.getSearchFactory()
                    .buildQueryBuilder().forEntity(Source_doc.class).get();
            org.apache.lucene.search.Query query = qb
                    .keyword()
                    .onFields("source_doc_text")
                    .matching("تفضحوا")
                    .createQuery();
            // wrap Lucene query in a org.hibernate.Query
            org.hibernate.Query hibQuery
                    = fullTextSession.createFullTextQuery(query,Source_doc.class);

// execute search
            List result = hibQuery.list();

            tx.commit();
            factory.getCurrentSession().close();
        } catch (InterruptedException ex) {
            Logger.getLogger(testclass.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
