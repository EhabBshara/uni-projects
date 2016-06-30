/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package features;

import AWN.AWN;
import java.util.List;
import plagiarism.util.pojos.Phrase;
import plagiarism.util.pojos.TestPhrase;

/**
 *
 * @author Ehab Bshara
 */
public class FuzzySimilarity {

    private Phrase phrase;
    private TestPhrase testphrase;
    private String CleanedPhrase;
    private String CleanedTestphrase;
    private AWN wordnet;

    public FuzzySimilarity(Phrase phrase, TestPhrase testphrase, AWN wordnet) {
        this.phrase = phrase;
        this.testphrase = testphrase;
        this.wordnet = wordnet;
        this.CleanedPhrase = phrase.getStemmed();
        this.CleanedTestphrase = phrase.getStemmed();
    }

    private double computeFuzzySimilarity(String term1, String term2) {

        List<String> synonemosTerm1 = wordnet.getSynonyms(term1, true);
        List<String> synonemosTerm2 = wordnet.getSynonyms(term2, true);
        if (synonemosTerm1.contains(term2) || synonemosTerm2.contains(term1)) {
            return 0.5;
        }
        List<String> synonemosTerm1Asroot = wordnet.getSynonymsAsRoot(term1, true);
        List<String> synonemosTerm2Asroot = wordnet.getSynonymsAsRoot(term2, true);
        if (synonemosTerm1Asroot.contains(term2) || synonemosTerm2Asroot.contains(term1)) {
            return 0.5;
        }
        return 0;

    }

    private double computeMeu(String term, String[] sentence) {
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

    private double sentnceSimilarity(String[] sentece1, String[] sentece2) {
        float summation = 0;
        for (String word : sentece1) {
            summation += computeMeu(word, sentece2);

        }
        return summation / sentece1.length;
    }

    public double getSimilarityOfSentences() {
        String[] s1 = splitter(CleanedPhrase);
        String[] s2 = splitter(CleanedTestphrase);
        if (s1.length != s2.length) {
            return Math.min(sentnceSimilarity(s1, s2), sentnceSimilarity(s2, s1));
        }
        return sentnceSimilarity(s1, s2);
    }

    private String[] splitter(String sentence) {
        return sentence.split(" ");
    }

    public Phrase getPhrase() {
        return phrase;
    }

    public void setPhrase(Phrase phrase) {
        this.phrase = phrase;
    }

    public TestPhrase getTestphrase() {
        return testphrase;
    }

    public void setTestphrase(TestPhrase testphrase) {
        this.testphrase = testphrase;
    }

}
