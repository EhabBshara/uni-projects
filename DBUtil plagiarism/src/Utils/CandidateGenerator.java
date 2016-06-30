/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import plagiarism.util.pojos.CandidateDocs;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import plagiarism.DAOImpl.GenericServiceImpl;
import plagiarism.IDAO.IGenericService;
import plagiarism.util.pojos.Annotation;
import plagiarism.util.pojos.HibernateUtil;
import plagiarism.util.pojos.Phrase;
import plagiarism.util.pojos.Source_doc;
import plagiarism.util.pojos.Suspicious_doc;
import plagiarism.util.pojos.TestPhrase;

/**
 *
 * @author dali
 */
public class CandidateGenerator {

    IGenericService<Phrase> phraseService
            = new GenericServiceImpl<>(Phrase.class, HibernateUtil.getSessionFactory());
    IGenericService<TestPhrase> testphraseService
            = new GenericServiceImpl<>(TestPhrase.class, HibernateUtil.getSessionFactory());

    public CandidateGenerator() {

    }

    public float getSimilarityCoef(Source_doc source_doc, Suspicious_doc suspicious_doc) {
        HashSet<String> sourceHashSet = new HashSet<>();
        HashSet<String> susHashSet = new HashSet<>();

//        Map<String, Object> params = new HashMap<String, Object>();
//        params.put("SOURCE_DOC_ID", source_doc.getSource_doc_id());
//        List<Phrase> phrases = phraseService.getByWhere("where SOURCE_DOC_ID = :SOURCE_DOC_ID", params);

        Iterator<Phrase> pIterator = source_doc.getPhrases().iterator();
        while (pIterator.hasNext()) {
            Phrase p = pIterator.next();
            String[] words = p.getStemmed().split(" ");
            sourceHashSet.addAll(Arrays.asList(words));
        }

//        Map<String, Object> params2 = new HashMap<>();
//        params2.put("SUSPICIOUS_DOC_ID", suspicious_doc.getSuspicious_doc_id());
//        List<TestPhrase> testphrases = testphraseService.getByWhere("where SUSPICIOUS_DOC_ID = :SUSPICIOUS_DOC_ID", params2);

        Iterator<TestPhrase> tpIterator = suspicious_doc.getTestPhrases().iterator();
        while (tpIterator.hasNext()) {
            TestPhrase tp = tpIterator.next();
            String[] words = tp.getStemmed().split(" ");
            susHashSet.addAll(Arrays.asList(words));
        }
        return (float) Sets.intersection(sourceHashSet, susHashSet).size() / (float) Math.min(sourceHashSet.size(), susHashSet.size());
    }

    public void generateCandidates(List<Source_doc> sources, List<Suspicious_doc> suspiciouses) {
        System.out.println("started time:" + System.currentTimeMillis());
        IGenericService<CandidateDocs> candidateService
                = new GenericServiceImpl<>(CandidateDocs.class, HibernateUtil.getSessionFactory());
        List<CandidateDocs> candidateDocses = new ArrayList<>();
        for (int i = 200; i < suspiciouses.size(); i++) {
            Suspicious_doc sus = suspiciouses.get(i);
            System.out.println("new sus at time:" + System.currentTimeMillis());
            for (Source_doc source : sources) {
                float t = getSimilarityCoef(source, sus);
                candidateDocses.add(new CandidateDocs(source, sus, t));
//               candidateService.save(new CandidateDocs(source,sus,t));
            }
        }
        System.out.println("saving to DB start time:" + System.currentTimeMillis());
        candidateService.bulkSave(candidateDocses);
        System.out.println("saving to DB end time:" + System.currentTimeMillis());
        System.out.println("-------------");
    }

    public static void main(String[] args) {
        IGenericService<Source_doc> sourceDocService
                = new GenericServiceImpl<>(Source_doc.class, HibernateUtil.getSessionFactory());
        IGenericService<Suspicious_doc> suspiciousDocService
                = new GenericServiceImpl<>(Suspicious_doc.class, HibernateUtil.getSessionFactory());

        List<Source_doc> sources = sourceDocService.getAll();
        List<Suspicious_doc> Suspiciouses = suspiciousDocService.getAll();
        new CandidateGenerator().generateCandidates(sources, Suspiciouses);
        System.out.println("done");

    }

}
