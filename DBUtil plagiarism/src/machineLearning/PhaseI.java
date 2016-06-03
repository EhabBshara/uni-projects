/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package machineLearning;

import Utils.Helpers;
import arabicTools.ArabicStemmerDefault;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.util.Pair;
import plagiarism.IDAO.IGenericService;
import plagiarism.util.pojos.Annotation;
import plagiarism.util.pojos.Source_doc;
import plagiarism.util.pojos.Suspicious_doc;

/**
 *
 * @author dali
 */
public class PhaseI {

    public static List<CandidateSentences> getCandidateSentences(IGenericService<Annotation> annotationSvs ,Source_doc source_doc,Suspicious_doc suspicious_doc,ArabicStemmerDefault stemmer) {
        List<CandidateSentences> result = new ArrayList<>();
        String[] plagsource=Helpers.stemCleanedSentences(Helpers.getPlagiraisedSentecesFromSource(annotationSvs, source_doc.getSource_doc_id(), suspicious_doc.getSuspicious_doc_id()), stemmer);
        String[] plagsus=Helpers.stemCleanedSentences(Helpers.getPlagiraisedSentecesFromSuspicous(annotationSvs, source_doc.getSource_doc_id(), suspicious_doc.getSuspicious_doc_id()), stemmer);
        
        String[] nonPlagsource=Helpers.stemCleanedSentences(Helpers.getNonPlagiraisedSentecesFromSource(annotationSvs, source_doc.getSource_doc_id(), suspicious_doc.getSuspicious_doc_id()), stemmer);
        String[] nonPlagsus=Helpers.stemCleanedSentences(Helpers.getNonPlagiraisedSentecesFromSuspicous(annotationSvs, source_doc.getSource_doc_id(), suspicious_doc.getSuspicious_doc_id()), stemmer);
        
        
        for (String sour : plagsource) {
            float maxValue=0;
              String susp="";
            for (String su : plagsus) {
                float overlap=Helpers.getOverlabValue(sour, su);
                if(overlap>maxValue)
                {
                    maxValue=overlap;
                    susp=su;
                }
            }
            if(maxValue!=0)
                result.add(new CandidateSentences(sour,susp,true));
        }
        
        for (String sour : nonPlagsource) {
            float maxValue=0;
              String susp="";
            for (String su : nonPlagsus) {
                float overlap=Helpers.getOverlabValue(sour, su);
                if(overlap>maxValue)
                {
                    maxValue=overlap;
                    susp=su;
                }
            }
            if(maxValue!=0)
                result.add(new CandidateSentences(sour,susp,false));
        }
        
        
        return result;
    }

}
