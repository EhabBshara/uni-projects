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

            Map<String, Object> params = new HashMap<String, Object>();
            params.put("source_doc_id", 291);
//        Annotation annotation=annotationService.getByWhere("where annotation_id = :ANNOTATION_ID", params).get(0);
//       List<Source_doc> s=sourceDocService.getAll();
//        List<Suspicious_doc> sus=suspiciousDocService.getAll();
            List<CandidateSentencesWithOriginal> candidateSentences = new ArrayList<>();
            ArabicStemmerDefault stemmer = new ArabicStemmerDefault();
            List<Annotation> a = annotationService.getAll();

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
            LibSVM svm= SVMTrainer.trainSVM("d:\\train3.arff");
            SVMTrainer.printOutput(svm, candidateSentences, "d:\\testunitdata.arff");

//        List<Annotation> a = annotationService.getAll();
//        for (int i = 1470; i < 1490; i++) {
//            candidateSentences.addAll(PhaseI.getCandidateSentences( a.get(i), stemmer));
//        }
//        candidateSentences=Helpers.cleanCandidateList(candidateSentences);
//        Helpers.saveObjectToFile(candidateSentences, "D:\\candidateSentences1470_1490.out");
//        System.out.println("-----------------");
//        candidateSentences.addAll(Helpers.loadCandidateSentencesFromFile("d:\\candidateSentences.out"));
//        candidateSentences.addAll(Helpers.loadCandidateSentencesFromFile("d:\\candidateSentences690_710.out"));
//        candidateSentences.addAll(Helpers.loadCandidateSentencesFromFile("d:\\candidateSentences1470_1490.out"));
//        
//        List<Features> features = PhaseI.extractFeatures(candidateSentences);
//        PhaseI.writeARFFfile(features, "d:\\testData3.arff");
            System.out.println("-----------------");
        } catch (Exception ex) {
            Logger.getLogger(test.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
