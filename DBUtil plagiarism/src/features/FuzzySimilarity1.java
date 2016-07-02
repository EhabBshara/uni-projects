/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package features;

import AWN.AWN;
import arabicTools.Stem;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ehab Bshara
 */
public class FuzzySimilarity1 {

    private String phrase;
    private String testphrase;

    public FuzzySimilarity1(String phrase, String testphrase) {
        this.phrase = phrase;
        this.testphrase = testphrase;
    }

    private double computeFuzzySimilarity(String term1, String term2) {

        List<String> synonemosTerm1 = AWN.getSynonyms(term1, false);
        synonemosTerm1.addAll(AWN.getSynonymsAsRoot(term1, false));
        if (synonemosTerm1.contains(term2)) {
            return 0.5;
        }
        return 0;

    }

    private double computeMeu(String term, List<String> sentence) {
        float mult = 1;
        for (String wk : sentence) {
            if (term.equals(wk)) {
                return 1;
            } else {
                mult *= computeFuzzySimilarity(term, wk);
            }
        }
        return 1 - mult;
    }

    private double sentnceSimilarity(List<String> sentece1, List<String> sentece2) {
        float summation = 0;
        for (String word : sentece1) {
            summation += computeMeu(word, sentece2);

        }
        return summation / sentece1.size();
    }

    public double getSimilarityOfSentences() {
        Stem s = new Stem();
        String[] s1 = splitter(phrase);
        String[] s2 = splitter(testphrase);
        List<String> list1 = new ArrayList<>();
        for (String word : s1) {
            if (!s.checkStopwords(word)) {
                list1.add(word);
            }
        }
        List<String> list2 = new ArrayList<>();

        for (String word : s2) {
            if (!s.checkStopwords(word)) {
                list2.add(word);
            }
        }
        if (s1.length != s2.length) {
            return Math.min(sentnceSimilarity(list1, list2), sentnceSimilarity(list2, list1));
        }
        return sentnceSimilarity(list1, list2);
    }

    private String[] splitter(String sentence) {
        return sentence.split(" ");
    }

    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }

    public String getTestphrase() {
        return testphrase;
    }

    public void setTestphrase(String testphrase) {
        this.testphrase = testphrase;
    }

}
