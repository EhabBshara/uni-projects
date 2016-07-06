/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Evaluation;

import AWN.AWN;
import Utils.Constants;
import features.FuzzySimilarity1;
import features.Intersection;
import features.LCSwords;
import features.SkipGram;
import plagiarism.util.pojos.Phrase;
import plagiarism.util.pojos.TestPhrase;

/**
 *
 * @author dali
 */
public class Evaluater {

    public boolean evaluate(Phrase phrase, TestPhrase testphrase, boolean withWordShuffling, boolean withSymantic) {
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

        if (withWordShuffling) {
            double intersect = new Intersection(phrase.getStemmed(), testphrase.getStemmed()).IntersectionScore();
            if (intersect > Constants.INTERSECTION_COEF) {
                return true;
            }
        }
        if (withSymantic) {
            double sim = new FuzzySimilarity1(phrase.getStemmed(), testphrase.getStemmed()).getSimilarityOfSentences();
            if (sim > Constants.SYMANTIC_COEF) {
                return true;
            }
        }
        return false;
    }

}
