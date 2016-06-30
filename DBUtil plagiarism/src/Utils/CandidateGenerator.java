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
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
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

    public CandidateGenerator() {

    }

    public boolean isSimilar(Source_doc source_doc, Suspicious_doc suspicious_doc) {
        HashSet<String> sourceHashSet = new HashSet<>();
        HashSet<String> susHashSet = new HashSet<>();

        Iterator<Phrase> pIterator = source_doc.getPhrases().iterator();
        while (pIterator.hasNext()) {
            Phrase p = pIterator.next();
            String[] words = p.getStemmed().split(" ");
            sourceHashSet.addAll(Arrays.asList(words));
        }
        Set<TestPhrase> testphrases = suspicious_doc.getTestPhrases();
        Iterator<TestPhrase> tpIterator = testphrases.iterator();
        while (tpIterator.hasNext()) {
            TestPhrase tp = tpIterator.next();
            String[] words = tp.getStemmed().split(" ");
            susHashSet.addAll(Arrays.asList(words));
        }
        return Constants.DOCUMENT_SIMILARITY_COEF < ((float) Sets.intersection(sourceHashSet, susHashSet).size() / (float) Math.min(sourceHashSet.size(), susHashSet.size()));
    }

    public List<CandidateDocs> generateCandidates(List<Source_doc> sources, List<Suspicious_doc> suspiciouses) {
        List<CandidateDocs> candidateDocses = new ArrayList<>();
        for (Suspicious_doc sus : suspiciouses) {
            for (Source_doc source : sources) {
                if (isSimilar(source, sus)) {
                    candidateDocses.add(new CandidateDocs(source, sus));
                }
            }
        }

        return candidateDocses;
    }

    public static void main(String[] args) {

        IGenericService<Source_doc> sourceService
                = new GenericServiceImpl<>(Source_doc.class, HibernateUtil.getSessionFactory());
        IGenericService<Suspicious_doc> SuspiciousService
                = new GenericServiceImpl<>(Suspicious_doc.class, HibernateUtil.getSessionFactory());
        IGenericService<Annotation> AnnotationService
                = new GenericServiceImpl<>(Annotation.class, HibernateUtil.getSessionFactory());
        List<Source_doc> sources = sourceService.getAll();
        List<Suspicious_doc> suspiciouses = SuspiciousService.getAll();
        List<Annotation> annotations = AnnotationService.getAll();
        CandidateGenerator candidateGenerator = new CandidateGenerator();
        
        List<CandidateDocs> candidateDocses=candidateGenerator.generateCandidates(sources, suspiciouses);
        System.out.println("done");

    }

}
