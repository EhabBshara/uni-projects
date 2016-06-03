/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plagiarism.util.pojos;

import Utils.Helpers;
import arabicTools.ArabicStemmer;
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
import arabicTools.*;
import features.BLEU;
import features.LCSwords;
import features.SkipGram;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import javafx.util.Pair;
import machineLearning.CandidateSentences;
import machineLearning.Features;
import machineLearning.PhaseI;

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
        IGenericService<Source_doc> sourceDocService
                = new GenericServiceImpl<>(Source_doc.class, HibernateUtil.getSessionFactory());
        IGenericService<Suspicious_doc> suspiciousDocService
                = new GenericServiceImpl<>(Suspicious_doc.class, HibernateUtil.getSessionFactory());
        IGenericService<Annotation> annotationService
                = new GenericServiceImpl<>(Annotation.class, HibernateUtil.getSessionFactory());

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("source_doc_id", 291);
//        Annotation annotation=annotationService.getByWhere("where annotation_id = :ANNOTATION_ID", params).get(0);
//       List<Source_doc> s=sourceDocService.getAll();
//        List<Suspicious_doc> sus=suspiciousDocService.getAll();

        List<Annotation> a = annotationService.getAll();
        ArabicStemmerDefault stemmer = new ArabicStemmerDefault();
        List<CandidateSentences> candidateSentences = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            candidateSentences.addAll(PhaseI.getCandidateSentences(annotationService, a.get(i).getSource_doc(), a.get(i).getSuspicious_doc(), stemmer));
        }
        System.out.println("-----------------");
        candidateSentences = Helpers.cleanCandidateList(candidateSentences);
        System.out.println("-----------------");

        Helpers.saveCandidateListToFile(candidateSentences);
        System.out.println("--------------ssssss---");
//        List<Features> features = new ArrayList<>();
//        for (CandidateSentences candidatesentences : candidateSentences) {
//            BLEU bl = new BLEU(candidatesentences.getSource(), candidatesentences.getSuspicious(), 1);
//            Pair<Double, Double> bluepair = bl.bleuMeasure();
//            double lCSwords = new LCSwords(candidatesentences.getSource(), candidatesentences.getSuspicious()).lcsFeature();
//            double skipGram2 = new SkipGram(candidatesentences.getSource(), candidatesentences.getSuspicious(), 2, 4).skipGramFeature();
//            double skipGram3 = new SkipGram(candidatesentences.getSource(), candidatesentences.getSuspicious(), 3, 4).skipGramFeature();
//            features.add(new Features(bluepair.getKey(), bluepair.getValue(), skipGram2, skipGram3, lCSwords, candidatesentences.isIsPlagirised()));
//        }

//        System.out.println(Arrays.asList(Helpers.stemCleanedSentences(Helpers.getPlagiraisedSentecesFromSource(annotationService, 312, 176), stemmer)  ));
//        System.out.println(Arrays.asList(Helpers.stemCleanedSentences(Helpers.getPlagiraisedSentecesFromSuspicous(annotationService, 312, 176), stemmer) ));
//
//        System.out.println(Arrays.asList(Helpers.getNonPlagiraisedSentecesFromSource(annotationService, 312, 176)));
//        System.out.println(Arrays.asList(Helpers.getNonPlagiraisedSentecesFromSuspicous(annotationService, 312, 176)));
//System.out.println("-----------------");
//        String []source=Helpers.stemCleanedSentences(Helpers.CleanFileContent(a.get(3).getSource_doc().getSource_doc_text()), stemmer);
//      System.out.println("-----------------");
//        String []sus=Helpers.stemCleanedSentences(Helpers.CleanFileContent(a.get(3).getSuspicious_doc().getSuspicious_doc_text()), stemmer);
//        System.out.println("-----------------");
//        List<Pair<String,String>> myList=new ArrayList<>();
//        for (String sour : source) {
//            float maxValue=0;
//              String susp="";
//            for (String su : sus) {
//                float overlap=Helpers.getOverlabValue(sour, su);
//                if(overlap>maxValue)
//                {
//                    maxValue=overlap;
//                    susp=su;
//                }
//            }
//            if(maxValue!=0)
//                myList.add(new Pair<>(sour,susp));
//            System.out.println("-----------------");
//        }
//        System.out.println(myList);
//        System.out.println(s.get(5).getSource_doc_text());
//        ArabicStemmerDefault stemmer=new ArabicStemmerDefault();
//        String[] sentences=Helpers.CleanFileContent(s.get(5).getSource_doc_text());
//        for(String sentence:sentences)
//        {
//            System.out.println(sentence);
//            String[]words=sentence.split(" ");
//            for(String word:words)
//            {
//                try{
//                System.out.println(word);
//                System.out.println(stemmer.stemWord(word));
//                }catch(Exception e)
//                {
//                    System.out.println(word);
//                    e.printStackTrace();
//                }
//            }
//            
//        }
        //System.out.println(annotation.getSource_doc().getSource_doc_text().substring((int)annotation.getSource_offset(),(int)annotation.getSource_offset()+(int) annotation.getSource_length()));
        // System.out.println(annotation.getSuspicious_doc().getSuspicious_doc_text().substring((int)annotation.getSuspicious_offset(),(int)annotation.getSuspicious_offset()+(int) annotation.getSuspicious_length()));
    }

}
