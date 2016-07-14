/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Evaluation;

import AWN.AWN;
import Utils.Constants;
import features.FuzzySimilarity;
import features.Intersection;
import features.LCSwords;
import features.SkipGram;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import plagiarism.util.pojos.Phrase;
import plagiarism.util.pojos.Source_doc;
import plagiarism.util.pojos.Suspicious_doc;
import plagiarism.util.pojos.TestPhrase;

/**
 *
 * @author dali
 */
public class Evaluater {

    public boolean evaluate(Phrase phrase, TestPhrase testphrase, boolean Lexicals, boolean withWordShuffling, boolean withSymantic) {
        if (Lexicals) {
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
        }

        if (withWordShuffling) {
            double intersect = new Intersection(phrase.getStemmed(), testphrase.getStemmed()).IntersectionScore();
            if (intersect > Constants.INTERSECTION_COEF) {
                return true;
            }
        }
        if (withSymantic) {
            double sim = new FuzzySimilarity(phrase.getStemmed(), testphrase.getStemmed()).getSimilarityOfSentences();
            if (sim > Constants.SYMANTIC_COEF) {
                return true;
            }
        }
        return false;
    }

    public List<CandidatePair> assosiatePairs(Source_doc source, Suspicious_doc suspicious) {
        List<CandidatePair> res = new ArrayList<>();
        Iterator<Phrase> pIterator = source.getPhrases().iterator();

        while (pIterator.hasNext()) {
            Phrase p = pIterator.next();
            Iterator<TestPhrase> tpIterator = suspicious.getTestPhrases().iterator();
            while (tpIterator.hasNext()) {
                TestPhrase tp = tpIterator.next();
                res.add(new CandidatePair(p, tp));
            }
        }
        return res;
    }

    public List<CandidatePair> getPlagirised(Source_doc source, Suspicious_doc suspicious,boolean lexicals, boolean withWordShuffling, boolean withSymantic) {
        List<CandidatePair> result = new ArrayList<>();
        List<CandidatePair> candidatePairs = assosiatePairs(source, suspicious);

        for (CandidatePair candidatePair : candidatePairs) {
            if (evaluate(candidatePair.getPhrase(), candidatePair.getTestPhrase(), lexicals,withWordShuffling, withSymantic)) {
                result.add(candidatePair);
            }
        }

        return result;
    }

}
