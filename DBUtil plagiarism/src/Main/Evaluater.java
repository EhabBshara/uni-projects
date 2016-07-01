/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Utils.Constants;
import features.LCSwords;
import features.SkipGram;
import plagiarism.util.pojos.Phrase;
import plagiarism.util.pojos.TestPhrase;

/**
 *
 * @author dali
 */
public class Evaluater {

    public boolean evaluate(Phrase phrase, TestPhrase testphrase) {
        double lCSwords = new LCSwords(phrase.getStemmed(), testphrase.getStemmed()).lcsFeature();
        if (lCSwords > Constants.LCS_COEF) {
            return true;
        }
        double skipGram2 = new SkipGram(phrase.getStemmed(), testphrase.getStemmed(), 2, 4).skipGramFeature();
        if (skipGram2 > Constants.SKIP_GRAM2_COEF) {
            return true;
        }
        double skipGram3 = new SkipGram(phrase.getStemmed(), testphrase.getStemmed(), 3, 4).skipGramFeature();
        if (skipGram3 > Constants.SKIP_GRAM3_COEF) {
            return true;
        }
        return false;
    }

}
