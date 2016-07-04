/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IR;

import Utils.Helpers;
import arabicTools.Stem;
import features.NGram;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import plagiarism.DAOImpl.GenericServiceImpl;
import plagiarism.IDAO.IGenericService;
import plagiarism.util.pojos.HibernateUtil;
import plagiarism.util.pojos.Suspicious_doc;

/**
 *
 * @author Ehab Bshara
 */
public class QueryExtractor {

    static Map<String, Integer> tdocFreq = new HashMap<>();
    private Suspicious_doc document;
    HashMap<String, Double> sentenceWeight = new HashMap();
    List<Entry<String, Double>> sorted;

    public QueryExtractor(Suspicious_doc document) {
        this.document = document;
    }

    public Suspicious_doc getDocument() {
        return document;
    }

    private void extract() {
        String suspicious = document.getSuspicious_doc_text();
        String[] sentences = Helpers.CleanFileContent(suspicious);
        String[] words;
        Stem stemmer = new Stem();
        List<String> result = new ArrayList<>();
        for (String sentence : sentences) {
            words = sentence.split(" ");
            String newsentence = "";
            for (String word : words) {
                if (stemmer.checkStopwords(word)) {
                    continue;
                }
                newsentence += word + " ";
                if (tdocFreq.containsKey(word)) {
                    int freq = ((Integer) tdocFreq.get(word)) + 1;
                    tdocFreq.put(word, freq);
                } else {
                    tdocFreq.put(word, 1);
                }
            }
            result.add(newsentence);

        }
        
        for (String sentence : result) {
            String[] nonStopwords = sentence.split(" ");
            if (nonStopwords.length == 0) {
                continue;
            }
            int totalFreq = 0;
            for (String word : nonStopwords) {
                totalFreq += tdocFreq.get(word);

            }
            double weight = totalFreq / nonStopwords.length;
            sentenceWeight.put(sentence, weight);
        }
        sorted = QueryExtractor.entriesSortedByValues(sentenceWeight);
    }

    public List<String> extractAllSentence()
    {
        extract();
        List<String> queries = new ArrayList<>();
        queries.add(sorted.get(0).getKey());
        queries.add(sorted.get(1).getKey());
        queries.add(sorted.get(2).getKey());
        return queries;
    }
    public List<String> extractBygrams(int n) {
        extract();
        List<String> queries = new ArrayList<>();
        NGram firstQuery = new NGram(sorted.get(0).getKey(), n);
        NGram secondQuery = new NGram(sorted.get(1).getKey(), n);
        NGram thirdQuery = new NGram(sorted.get(2).getKey(), n);
//        NGram forthQuery = new NGram(sorted.get(3).getKey(),2);
//        NGram fifthQuery = new NGram(sorted.get(4).getKey(),2);

        queries.addAll(firstQuery.list());
        queries.addAll(secondQuery.list());
        queries.addAll(thirdQuery.list());
        return queries;
    }
    private static <K, V extends Comparable<? super V>>
            List<Entry<K, V>> entriesSortedByValues(Map<K, V> map) {

        List<Entry<K, V>> sortedEntries = new ArrayList<Entry<K, V>>(map.entrySet());

        Collections.sort(sortedEntries,
                new Comparator<Entry<K, V>>() {
                    @Override
                    public int compare(Entry<K, V> e1, Entry<K, V> e2) {
                        return e2.getValue().compareTo(e1.getValue());
                    }
                }
        );

        return sortedEntries;
    }
             public static void main(String[] args) {
                IGenericService<Suspicious_doc> suspiciousDocService
                    = new GenericServiceImpl<>(Suspicious_doc.class, HibernateUtil.getSessionFactory());
                Suspicious_doc sus = suspiciousDocService.getAll().get(0);
                QueryExtractor q = new QueryExtractor(sus);
              //  List<String> result = q.extract();
                // System.out.println(result);

    }
}
