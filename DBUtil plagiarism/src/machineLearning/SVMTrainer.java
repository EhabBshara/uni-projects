/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package machineLearning;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Pair;
import libsvm.svm;
import weka.classifiers.functions.LibSVM;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;

/**
 *
 * @author dali
 */
public class SVMTrainer {

    public static LibSVM trainSVM(String pathToTrainData) {
        try {
            ConverterUtils.DataSource source = new ConverterUtils.DataSource(pathToTrainData);
            Instances data = source.getDataSet();
            data.setClassIndex(data.numAttributes() - 1);

            LibSVM svm = new LibSVM();

            svm.buildClassifier(data);
            return svm;
        } catch (Exception ex) {
            Logger.getLogger(SVMTrainer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static List<Pair<String,String>> printOutput(LibSVM svm, List<CandidateSentencesWithOriginal> candidateSentences, String pathToTestUnit) {
        List<Pair<String,String>> result=new ArrayList<>();
        try {
//            svm=(LibSVM)weka.core.SerializationHelper.read("D:\\model.model");
            ConverterUtils.DataSource source2 = new ConverterUtils.DataSource(pathToTestUnit);
            Instances data2 = source2.getDataSet();
            data2.setClassIndex(data2.numAttributes() - 1);
            for (CandidateSentencesWithOriginal candidate : candidateSentences) {

                double[] attValues = new double[data2.numAttributes()];

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
                data2.add(instance);
                double value = svm.classifyInstance(data2.lastInstance());
                String prediction = data2.classAttribute().value((int) value);
                if (prediction.equals("yes")) {
                    System.out.println(candidate.getOriginalSource() + "\n" + candidate.getOriginalSuspicious());
                    result.add(new Pair<>(candidate.getOriginalSource(),candidate.getOriginalSuspicious()));
                }

            }
        } catch (Exception ex) {
            Logger.getLogger(SVMTrainer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
}
