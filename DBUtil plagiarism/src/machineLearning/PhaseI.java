/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package machineLearning;

import AWN.AWN;
import Utils.Helpers;
import arabicTools.ArabicStemmerDefault;
import arabicTools.Stem;
import features.BLEU;
import features.FuzzySimilarity;
import features.FuzzySimilarity1;
import features.LCSwords;
import features.SkipGram;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
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

    public static List<CandidateSentences> getCandidateSentences(IGenericService<Annotation> annotationSvs, Source_doc source_doc, Suspicious_doc suspicious_doc, Stem stemmer) {
        List<CandidateSentences> result = new ArrayList<>();
        String[] plagsource = Helpers.stemCleanedSentences(Helpers.getPlagiraisedSentecesFromSource(annotationSvs, source_doc.getSource_doc_id(), suspicious_doc.getSuspicious_doc_id()), stemmer);
        String[] plagsus = Helpers.stemCleanedSentences(Helpers.getPlagiraisedSentecesFromSuspicous(annotationSvs, source_doc.getSource_doc_id(), suspicious_doc.getSuspicious_doc_id()), stemmer);

        String[] nonPlagsource = Helpers.stemCleanedSentences(Helpers.getNonPlagiraisedSentecesFromSource(annotationSvs, source_doc.getSource_doc_id(), suspicious_doc.getSuspicious_doc_id()), stemmer);
        String[] nonPlagsus = Helpers.stemCleanedSentences(Helpers.getNonPlagiraisedSentecesFromSuspicous(annotationSvs, source_doc.getSource_doc_id(), suspicious_doc.getSuspicious_doc_id()), stemmer);

        for (String sour : plagsource) {
            float maxValue = 0;
            String susp = "";
            for (String su : plagsus) {
                float overlap = Helpers.getOverlapValue(sour, su);
                if (overlap > maxValue) {
                    maxValue = overlap;
                    susp = su;
                }
            }
            if (maxValue != 0) {
                result.add(new CandidateSentences(sour, susp, true));
            }
        }

        for (String sour : nonPlagsource) {
            float maxValue = 0;
            String susp = "";
            for (String su : nonPlagsus) {
                float overlap = Helpers.getOverlapValue(sour, su);
                if (overlap > maxValue) {
                    maxValue = overlap;
                    susp = su;
                }
            }
            if (maxValue != 0) {
                result.add(new CandidateSentences(sour, susp, false));
            }
        }

        return result;
    }

    public static List<CandidateSentences> getCandidateSentences(Annotation a, Stem stemmer) {
        List<CandidateSentences> result = new ArrayList<>();
        String[] plagsource = Helpers.stemCleanedSentences(Helpers.getPlagiraisedSentecesFromSource(a), stemmer);
        String[] plagsus = Helpers.stemCleanedSentences(Helpers.getPlagiraisedSentecesFromSuspicous(a), stemmer);

        String[] nonPlagsource = Helpers.stemCleanedSentences(Helpers.getNonPlagiraisedSentecesFromSource(a), stemmer);
        String[] nonPlagsus = Helpers.stemCleanedSentences(Helpers.getNonPlagiraisedSentecesFromSuspicous(a), stemmer);
        for (String sour : plagsource) {
            float maxValue = 0;
            String susp = "";
            for (String su : plagsus) {
                float overlap = Helpers.getOverlapValue(sour, su);
                if (overlap > maxValue) {
                    maxValue = overlap;
                    susp = su;
                }
            }
            if (maxValue != 0) {
                result.add(new CandidateSentences(sour, susp, true));
            }
        }

        for (String sour : nonPlagsource) {
            float maxValue = 0;
            String susp = "";
            for (String su : nonPlagsus) {
                float overlap = Helpers.getOverlapValue(sour, su);
                if (overlap > maxValue) {
                    maxValue = overlap;
                    susp = su;
                }
            }
            if (maxValue != 0) {
                result.add(new CandidateSentences(sour, susp, false));
            }
        }

        return result;
    }

    public static List<CandidateSentencesWithOriginal> getCandidateSentencesWithOriginal(Annotation a, Stem stemmer) {
        List<CandidateSentencesWithOriginal> result = new ArrayList<>();
        List<Pair<String, String>> sourcelist = new ArrayList<>();
        String[] sourceSentences = Helpers.CleanFileContent(a.getSource_doc().getSource_doc_text());
        for (String source : sourceSentences) {
            sourcelist.add(new Pair<>(source, Helpers.stemCleanedSentence(source, stemmer)));
        }
        List<Pair<String, String>> suspiciouslist = new ArrayList<>();
        String[] suspiciousSentences = Helpers.CleanFileContent(a.getSuspicious_doc().getSuspicious_doc_text());

        for (String sus : suspiciousSentences) {
            suspiciouslist.add(new Pair<>(sus, Helpers.stemCleanedSentence(sus, stemmer)));
        }

        for (Pair<String, String> sour : sourcelist) {
            float maxValue = 0;
            Pair<String, String> susp = null;
            for (Pair<String, String> su : suspiciouslist) {
                float overlap = Helpers.getOverlapValue(sour.getValue(), su.getValue());
                if (overlap > maxValue) {
                    maxValue = overlap;
                    susp = su;
                }
            }
            if (maxValue != 0 && susp != null) {
                result.add(new CandidateSentencesWithOriginal(sour.getValue(), sour.getKey(), susp.getValue(), susp.getKey(), true));
            }
        }

        return result;
    }

    public static List<Features> extractFeatures(List<CandidateSentences> candidateSentenceses) {
        AWN awn = new AWN();
        List<Features> result = new ArrayList<>();
        for (CandidateSentences candidatesentences : candidateSentenceses) {
            if (candidatesentences.getSource().isEmpty() || candidatesentences.getSuspicious().isEmpty()) {
                continue;
            }
            try {
                BLEU bl = new BLEU(candidatesentences.getSource(), candidatesentences.getSuspicious(), 1);
                Pair<Double, Double> bluepair = bl.bleuMeasure();
                double lCSwords = new LCSwords(candidatesentences.getSource(), candidatesentences.getSuspicious()).lcsFeature();
                double skipGram2 = new SkipGram(candidatesentences.getSource(), candidatesentences.getSuspicious(), 2, 4).skipGramFeature();
                double skipGram3 = new SkipGram(candidatesentences.getSource(), candidatesentences.getSuspicious(), 3, 4).skipGramFeature();
                double fuzz = new FuzzySimilarity1(candidatesentences.getSource(), candidatesentences.getSuspicious(), awn).getSimilarityOfSentences();
                result.add(new Features(bluepair.getKey(), bluepair.getValue(), skipGram2, skipGram3, lCSwords, fuzz, candidatesentences.isIsPlagirised()));
            } catch (Exception e) {
                System.out.println(candidatesentences);
                e.printStackTrace();
            }
        }
        return result;
    }

    public static Features extractFeature(String source, String suspicious) {
        AWN awn = new AWN();
        Features result = new Features();
        if (source.isEmpty() || suspicious.isEmpty()) {
            return result;
        }
        try {
            BLEU bl = new BLEU(source, suspicious, 1);
            Pair<Double, Double> bluepair = bl.bleuMeasure();
            double lCSwords = new LCSwords(source, suspicious).lcsFeature();
            double skipGram2 = new SkipGram(source, suspicious, 2, 4).skipGramFeature();
            double skipGram3 = new SkipGram(source, suspicious, 3, 4).skipGramFeature();
            double fuzz = new FuzzySimilarity1(source, suspicious, awn).getSimilarityOfSentences();
            result = new Features(bluepair.getKey(), bluepair.getValue(), skipGram2, skipGram3, lCSwords, fuzz, true);
        } catch (Exception e) {
            System.out.println(source + " " + suspicious);
            e.printStackTrace();
        }
        return result;
    }

    public static void writeARFFfile(List<Features> features, String path) {
        BufferedWriter writer = null;
        try {
            //create a temporary file
            File logFile = new File(path);

            // This will output the full path where the file will be written to...
            System.out.println(logFile.getCanonicalPath());

            writer = new BufferedWriter(new FileWriter(logFile));
            writer.write(Features.getMLHeaders());
            for (Features feature : features) {
                writer.write(feature.toMLString() + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // Close the writer regardless of what happens...
                writer.close();
            } catch (Exception e) {
            }
        }
    }

}
