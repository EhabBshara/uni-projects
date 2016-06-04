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

            ConverterUtils.DataSource source = new ConverterUtils.DataSource("D:\\testData3.arff");
            Instances data = source.getDataSet();
             data.setClassIndex(data.numAttributes() - 1);
             
            LibSVM svm=new LibSVM();
            svm.buildClassifier(data);
            
//            LibSVM cls = (LibSVM) weka.core.SerializationHelper.read("D:\\libsvmModel.model");

            ConverterUtils.DataSource source2 = new ConverterUtils.DataSource("D:\\testunitdata.arff");
            
            List<Annotation> a = annotationService.getAll();

            candidateSentences.addAll(PhaseI.getCandidateSentencesWithOriginal(a.get(62), stemmer));

            for (int i = 0; i < candidateSentences.size(); i++) {
                candidateSentences.get(i).setFeatures(PhaseI.extractFeature(candidateSentences.get(i).getSource(), candidateSentences.get(i).getSuspicious()));
            }

 
            
            for (CandidateSentencesWithOriginal candidate : candidateSentences) {
                Instances data2 = source2.getDataSet();
                data.setClassIndex(data.numAttributes() - 1);
                double[] attValues = new double[data.numAttributes()];

                double bleuPrec = candidate.getFeatures().getBleuPrec();
                double bleuRec = candidate.getFeatures().getBleuRec();
                double skipgram2 = candidate.getFeatures().getSkipgram2();
                double skipgram3 = candidate.getFeatures().getSkipgram3();
                double lcs = candidate.getFeatures().getLcs();

                attValues[0] = bleuPrec;
                attValues[1] = bleuRec;
                attValues[2] = skipgram2;
                attValues[3] = skipgram3;
                attValues[4] = lcs;

                Instance instance = new Instance(1.0, attValues);
                data.add(instance);
//                Object predictedClassValue = svm.classifyInstance(data2.lastInstance());
//            Object realClassValue = inst.classValue();
                double value = svm.classifyInstance(data.lastInstance());
                String prediction = data.classAttribute().value((int) value);
                if(prediction.equals("yes"))
                    System.out.println(candidate.getOriginalSource()+"\n"+candidate.getOriginalSuspicious());
                System.out.println("The predicted value of instance "
                        + ": " + prediction);

            }
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
