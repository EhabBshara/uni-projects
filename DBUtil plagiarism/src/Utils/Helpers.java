/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import arabicTools.ArabicStemmerDefault;
import features.NGram;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import machineLearning.CandidateSentences;
import plagiarism.IDAO.IGenericService;
import plagiarism.util.pojos.Annotation;
import plagiarism.util.pojos.Source_doc;
import plagiarism.util.pojos.Suspicious_doc;

/**
 *
 * @author dali
 */
public class Helpers {

    /**
     * clean and split string into sentences.
     *
     * @param fileText file string that should be cleaned.
     * @return string array of sentences.
     */
    public static String[] CleanFileContent(String fileText) {
//        String cleandText=fileText.replaceAll("[^\\p{L}\\w\\s\\.\\!\\?]", " ");

        String cleandText = fileText.replaceAll("[^.\\?!\\d&&[\\P{InArabic}\\p{P}]]+", " ");
        cleandText = cleandText.replaceAll("\\s+", " ");
        cleandText = cleandText.replaceAll("\\.+", ".");
        cleandText = cleandText.replaceAll("\\?+", ".");
        cleandText = cleandText.replaceAll("!+", ".");
        cleandText = cleandText.replaceAll("\\n+", ".");
        if (cleandText.endsWith("\\n")) {
            cleandText = cleandText.substring(0, cleandText.length() - 1);
        }
        String[] sentences = cleandText.split("\\.|\\?|!\\n");
        for (int i = 0; i < sentences.length; i++) {
            if (sentences[i].startsWith(" ")) {
                sentences[i] = sentences[i].substring(1, sentences[i].length());
            }
            if (sentences[i].endsWith(" ")) {
                sentences[i] = sentences[i].substring(0, sentences[i].length() - 1);
            }
        }
        return sentences;
    }

    
    
    public static String[] getPlagiraisedSentecesFromSource(IGenericService<Annotation> annotationService, long sourceId, long susId) {

        List<String> sss = new ArrayList<>();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("SOURCE_DOC_ID", sourceId);
        params.put("SUSPICIOUS_DOC_ID", susId);

        List<Annotation> annotation = annotationService.getByWhere("where source_doc_id=:SOURCE_DOC_ID and suspicious_doc_id=:SUSPICIOUS_DOC_ID", params);
        for (Annotation a : annotation) {
            String ptext = a.getSource_doc().getSource_doc_text().substring((int) a.getSource_offset(), (int) (a.getSource_offset() + a.getSource_length()));
            sss.addAll(Arrays.asList(CleanFileContent(ptext)));
        }

        String[] results = new String[sss.size()];
        return sss.toArray(results);
    }

    public static String[] getPlagiraisedSentecesFromSource(Annotation annotation) {

        String ptext = annotation.getSource_doc().getSource_doc_text().substring((int) annotation.getSource_offset(), (int) (annotation.getSource_offset() + annotation.getSource_length()));
        return CleanFileContent(ptext);
    }

    public static String[] getNonPlagiraisedSentecesFromSource(IGenericService<Annotation> annotationService, long sourceId, long susId) {

        List<String> sss = new ArrayList<>();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("SOURCE_DOC_ID", sourceId);
        params.put("SUSPICIOUS_DOC_ID", susId);

        List<Annotation> annotation = annotationService.getByWhere("where source_doc_id=:SOURCE_DOC_ID and suspicious_doc_id=:SUSPICIOUS_DOC_ID", params);
        for (Annotation a : annotation) {
            try {
                String ptext = a.getSource_doc().getSource_doc_text().substring(0, (int) a.getSource_offset());
                sss.addAll(Arrays.asList(CleanFileContent(ptext)));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                String ptext = a.getSource_doc().getSource_doc_text().substring((int) (a.getSource_offset() + a.getSource_length()));
                sss.addAll(Arrays.asList(CleanFileContent(ptext)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        String[] results = new String[sss.size()];
        return sss.toArray(results);
    }

    public static String[] getNonPlagiraisedSentecesFromSource(Annotation a) {

        List<String> sss = new ArrayList<>();

        try {
            String ptext = a.getSource_doc().getSource_doc_text().substring(0, (int) a.getSource_offset());
            sss.addAll(Arrays.asList(CleanFileContent(ptext)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            String ptext = a.getSource_doc().getSource_doc_text().substring((int) (a.getSource_offset() + a.getSource_length()));
            sss.addAll(Arrays.asList(CleanFileContent(ptext)));
        } catch (Exception e) {
            e.printStackTrace();
        }

        String[] results = new String[sss.size()];
        return sss.toArray(results);
    }

    public static String[] getPlagiraisedSentecesFromSuspicous(IGenericService<Annotation> annotationService, long sourceId, long susId) {
        List<String> sss = new ArrayList<>();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("SOURCE_DOC_ID", sourceId);
        params.put("SUSPICIOUS_DOC_ID", susId);

        List<Annotation> annotation = annotationService.getByWhere("where source_doc_id=:SOURCE_DOC_ID and suspicious_doc_id=:SUSPICIOUS_DOC_ID", params);
        for (Annotation a : annotation) {
            String ptext = a.getSuspicious_doc().getSuspicious_doc_text().substring((int) a.getSuspicious_offset(), (int) (a.getSuspicious_offset() + a.getSuspicious_length()));
            sss.addAll(Arrays.asList(CleanFileContent(ptext)));
        }

        String[] results = new String[sss.size()];
        return sss.toArray(results);
    }

    public static String[] getPlagiraisedSentecesFromSuspicous(Annotation a) {

        String ptext = a.getSuspicious_doc().getSuspicious_doc_text().substring((int) a.getSuspicious_offset(), (int) (a.getSuspicious_offset() + a.getSuspicious_length()));
        return CleanFileContent(ptext);
    }

    public static String[] getNonPlagiraisedSentecesFromSuspicous(IGenericService<Annotation> annotationService, long sourceId, long susId) {
        List<String> sss = new ArrayList<>();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("SOURCE_DOC_ID", sourceId);
        params.put("SUSPICIOUS_DOC_ID", susId);

        List<Annotation> annotation = annotationService.getByWhere("where source_doc_id=:SOURCE_DOC_ID and suspicious_doc_id=:SUSPICIOUS_DOC_ID", params);
        for (Annotation a : annotation) {
            try {
                String ptext = a.getSuspicious_doc().getSuspicious_doc_text().substring(0, (int) a.getSuspicious_offset());
                sss.addAll(Arrays.asList(CleanFileContent(ptext)));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                String ptext = a.getSuspicious_doc().getSuspicious_doc_text().substring((int) a.getSuspicious_offset(), (int) (a.getSuspicious_offset() + a.getSuspicious_length()));
                sss.addAll(Arrays.asList(CleanFileContent(ptext)));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        String[] results = new String[sss.size()];
        return sss.toArray(results);
    }

    public static String[] getNonPlagiraisedSentecesFromSuspicous(Annotation a) {
        List<String> sss = new ArrayList<>();

        try {
            String ptext = a.getSuspicious_doc().getSuspicious_doc_text().substring(0, (int) a.getSuspicious_offset());
            sss.addAll(Arrays.asList(CleanFileContent(ptext)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            String ptext = a.getSuspicious_doc().getSuspicious_doc_text().substring((int) a.getSuspicious_offset(), (int) (a.getSuspicious_offset() + a.getSuspicious_length()));
            sss.addAll(Arrays.asList(CleanFileContent(ptext)));
        } catch (Exception e) {
            e.printStackTrace();
        }

        String[] results = new String[sss.size()];
        return sss.toArray(results);
    }

    public static String[] stemCleanedSentences(String[] sentences, ArabicStemmerDefault stemmer) {
        String[] resultSentences = new String[sentences.length];
        for (int i = 0; i < sentences.length; i++) {
            String[] words = sentences[i].split(" ");
            String resSentence = "";
            for (String word : words) {
                try {
                    resSentence += stemmer.stemWord(word) + " ";
                } catch (Exception e) {
//                    System.out.println(word);
//                    e.printStackTrace();
                    resSentence += word + " ";
                }
            }
            resultSentences[i] = resSentence.substring(0, resSentence.length() - 1);
        }
        return resultSentences;
    }

       public static String stemCleanedSentence(String sentence, ArabicStemmerDefault stemmer) {
        String resultSentence = "";
            String[] words = sentence.split(" ");
            String resSentence = "";
            for (String word : words) {
                try {
                    resSentence += stemmer.stemWord(word) + " ";
                } catch (Exception e) {
//                    System.out.println(word);
//                    e.printStackTrace();
                    resSentence += word + " ";
                }
            }
            resultSentence = resSentence.substring(0, resSentence.length() - 1);
        return resultSentence;
    }
    
    
    public static float getOverlapValue(String sentence1, String sentence2) {
        float result = 0;
        String[] wordsOfs1 = sentence1.split(" ");
        String[] wordsOfs2 = sentence2.split(" ");
        for (int i = 0; i < wordsOfs1.length; i++) {
            for (int j = 0; j < wordsOfs2.length; j++) {
                if (wordsOfs1[i].equals(wordsOfs2[j])) {
                    result++;
                }
            }
        }
        return result;
    }

    public static List<CandidateSentences> cleanCandidateList(List<CandidateSentences> candidateSentenceses) {
        for (int i = 0; i < candidateSentenceses.size() - 1; i++) {
            for (int j = i + 1; j < candidateSentenceses.size(); j++) {
                int cand = candidateSentenceses.get(i).equalsWithWrongResult(candidateSentenceses.get(j));
                if (cand == 1) {
                    candidateSentenceses.remove(i);
                    i--;
                } else if (cand == 2 || cand == 0) {
                    candidateSentenceses.remove(j);
                    j--;
                }
            }
        }

        return candidateSentenceses;
    }

    public static void saveObjectToFile(Object object,String filePath) {
        try {
            FileOutputStream out = new FileOutputStream(filePath);
            ObjectOutputStream oos = new ObjectOutputStream(out);
            oos.writeObject(object);
            oos.flush();
        } catch (Exception e) {
            System.out.println("Problem serializing: " + e);
        }
    }

    public static Object loadObjectFromFile(String filePath) {
        Object o=new Object();
        try {
            FileInputStream in = new FileInputStream("D://candidateSentences.out");
            ObjectInputStream ois = new ObjectInputStream(in);
           o=(ois.readObject());
        } catch (Exception e) {
            System.out.println("Problem serializing: " + e);
        }
        return o;
    }
    
    public static List<CandidateSentences> loadCandidateSentencesFromFile(String filepath)
    {
        return (List<CandidateSentences>)loadObjectFromFile(filepath);
    }
    
 

}
