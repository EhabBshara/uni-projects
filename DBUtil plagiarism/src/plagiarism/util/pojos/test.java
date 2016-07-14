/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plagiarism.util.pojos;

import Utils.Helpers;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import plagiarism.DAOImpl.GenericServiceImpl;
import plagiarism.IDAO.IGenericService;
import arabicTools.*;
import features.BLEU;
import features.Intersection;
import features.SkipGram;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Pair;
import libsvm.svm;
import libsvm.svm_model;
import machineLearning.CandidateSentences;
import machineLearning.CandidateSentencesWithOriginal;
import machineLearning.Features;
import machineLearning.PhaseI;
import machineLearning.SVMTrainer;
import weka.classifiers.Classifier;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;
import weka.classifiers.functions.LibSVM;
import weka.core.converters.LibSVMLoader;

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

        try {
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
            IGenericService<CandidateDocs> candidateService
                    = new GenericServiceImpl<>(CandidateDocs.class, HibernateUtil.getSessionFactory());

            
            String hsql = "from Annotation a order by a.annotation_id DESC ";
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        List<Annotation> tuples = (List<Annotation>) session.createQuery(hsql).setMaxResults(1).list();
        session.getTransaction().commit();
            
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("OBFUSCATION", "word shuffling");
            List<Annotation> a = annotationService.getByWhere("where obfuscation = :OBFUSCATION", params);
            Stem stemmer = new Stem();
            String sourceSentence;
            String suspiciousSentence;
            String sourceStemmed;
            String suspiciousStemmed;
            List<Double> result = new ArrayList<>();
            Intersection s = null;
            for (Annotation an : a) {

                sourceSentence = an.getSource_doc().getSource_doc_text().substring((int) an.getSource_offset(), (int) an.getSource_offset() + (int) an.getSource_length());
                suspiciousSentence = an.getSuspicious_doc().getSuspicious_doc_text().substring((int) an.getSuspicious_offset(), (int) an.getSuspicious_offset() + (int) an.getSuspicious_length());
                sourceStemmed = Helpers.stemCleanedSentence(Helpers.cleanSentence(sourceSentence), stemmer);
                suspiciousStemmed = Helpers.stemCleanedSentence(Helpers.cleanSentence(suspiciousSentence), stemmer);

                s = new Intersection(sourceStemmed, suspiciousStemmed);
                result.add(s.IntersectionScore());

            }
            FileWriter file = new FileWriter(new File("D://res.txt"));

            for (double res : result) {
                file.write(res + "\n");
                if (res < 0.7) {
                    System.out.println(res);
                }
            }
            file.close();
            System.out.println("done");
//            
//       List<Source_doc> s=sourceDocService.getAll();
//        List<Suspicious_doc> sus=suspiciousDocService.getAll();
//            List<CandidateSentencesWithOriginal> candidateSentences = new ArrayList<>();
//            ArabicStemmerDefault stemmer = new ArabicStemmerDefault();
//            List<Annotation> a = annotationService.getAll();
//            for (int i = 20; i < 40; i++) {
//                candidateSentences.addAll(PhaseI.getCandidateSentences(a.get(i), stemmer));
//            }
//            System.out.println("-----------------");
//            Helpers.saveObjectToFile(candidateSentences, "D:\\candidateSentences20_40.out");
//            System.out.println("-----------------");
//            
//            List<Features>features=PhaseI.extractFeatures(candidateSentences);
//            System.out.println("-----------------");
//            PhaseI.writeARFFfile(features, "d:\\testUnit.arff");
//            System.out.println("-----------------");
//            LibSVM svm= SVMTrainer.trainSVM("d:\\train3.arff");
//            SVMTrainer.printOutput(svm, candidateSentences, "d:\\testunitdata.arff");
//        List<Annotation> a = annotationService.getAll();
//        for (int i = 1470; i < 1490; i++) {
//            candidateSentences.addAll(PhaseI.getCandidateSentences( a.get(i), stemmer));
//        }
//        candidateSentences=Helpers.cleanCandidateList(candidateSentences);
//        Helpers.saveObjectToFile(candidateSentences, "D:\\candidateSentences1470_1490.out");
//        System.out.println("-----------------");
//            List<CandidateSentences> candidateSentences = Helpers.loadCandidateSentencesFromFile("candidateSentences20_40.out");
//            candidateSentences.addAll(Helpers.loadCandidateSentencesFromFile("d:\\candidateSentences.out"));
//            candidateSentences.addAll(Helpers.loadCandidateSentencesFromFile("d:\\candidateSentences690_710.out"));
//            candidateSentences.addAll(Helpers.loadCandidateSentencesFromFile("d:\\candidateSentences1470_1490.out"));
////        
//        List<Features> features = PhaseI.extractFeatures(candidateSentences);
//        PhaseI.writeARFFfile(features, "d:\\testData5.arff");
//            System.out.println("-----------------");
        } catch (Exception ex) {
            Logger.getLogger(test.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
