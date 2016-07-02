/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Utils.Constants;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import plagiarism.DAOImpl.GenericServiceImpl;
import plagiarism.IDAO.IGenericService;
import plagiarism.util.pojos.Annotation;
import plagiarism.util.pojos.Assoc;
import plagiarism.util.pojos.CandidateDocs;
import plagiarism.util.pojos.HibernateUtil;
import plagiarism.util.pojos.Phrase;

/**
 *
 * @author dali
 */
public class Main {

    public static void main(String[] args) {

//                IGenericService<Annotation> annotationService
//                = new GenericServiceImpl<>(Annotation.class, HibernateUtil.getSessionFactory());
//        Map<String, Object> params1 = new HashMap<String, Object>();
//            params1.put("ANNOTATION_ID", 723);
//            Annotation a = annotationService.getByWhere("where annotation_id = :ANNOTATION_ID", params1).get(0);
//
//            System.out.println(a.getSource_doc().getSource_doc_text().substring((int) a.getSource_offset(), (int) a.getSource_offset() + (int) a.getSource_length()));
//            System.out.println("");
//            System.out.println(a.getSuspicious_doc().getSuspicious_doc_text().substring((int) a.getSuspicious_offset(), (int) a.getSuspicious_offset() + (int) a.getSuspicious_length()));
        IGenericService<CandidateDocs> candidateDocsService
                = new GenericServiceImpl<>(CandidateDocs.class, HibernateUtil.getSessionFactory());
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("correlation_value", 0.14f);
//        params.put("suspicious_doc_id", 339);
        params.put("min", 169);
        params.put("max", 339);
        List<CandidateDocs> candidates = candidateDocsService.getByWhere("where correlation_value>:correlation_value and suspicious_doc_id<:max and suspicious_doc_id>:min", params);
        test t = new test();
        Evaluater evaluater = new Evaluater();
        List<Assoc> assocs = new ArrayList<>();
        for (int i = 0; i < candidates.size(); i++) {
            CandidateDocs candidate = candidates.get(i);
//        List<CandidatePair> sentencesPairsList = t.getCandidatePair(candidates.getSource_doc(), candidates.getSuspicious_doc());
            List<CandidatePair> sentencesPairsList = t.assosiatePairs(candidate.getSource_doc(), candidate.getSuspicious_doc());
            for (CandidatePair pair : sentencesPairsList) {
                if (evaluater.evaluate(pair.getPhrase(), pair.getTestPhrase(),false,false)) {
                    assocs.add(new Assoc(pair.getPhrase(), pair.getTestPhrase(), 0.0));
                }
            }
        }

        IGenericService<Assoc> assocDocsService
                = new GenericServiceImpl<>(Assoc.class, HibernateUtil.getSessionFactory());
        assocDocsService.bulkSave(assocs);
//        sentencesPairsList = t.calculateFeatures(sentencesPairsList);
//
//        
////        System.out.println(sentencesPairsList);
//        for (CandidatePair pair : sentencesPairsList) {
//            if (pair.getLSC() > Constants.LCS_COEF) {
//                System.out.println("LCS:\n"+pair.getPhrase().getOriginal() + "\n" + pair.getTestPhrase().getPhrase() + "\n-------");
//            }
//            if (pair.getSkipgram2() > Constants.SKIP_GRAM2_COEF) {
//                System.out.println("skip2:\n"+pair.getPhrase().getOriginal() + "\n" + pair.getTestPhrase().getPhrase() + "\n-------");
//            }
//            if (pair.getSkipgram3() > Constants.SKIP_GRAM3_COEF) {
//                System.out.println("skip3:\n"+pair.getPhrase().getOriginal() + "\n" + pair.getTestPhrase().getPhrase() + "\n-------");
//            }
//
//        }
        System.out.println("done");
    }

}
