/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Evaluation;

import Utils.Helpers;
import features.LCSwords;
import features.SkipGram;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import machineLearning.CandidateSentences;
import plagiarism.util.pojos.Phrase;
import plagiarism.util.pojos.Source_doc;
import plagiarism.util.pojos.Suspicious_doc;
import plagiarism.util.pojos.TestPhrase;

/**
 *
 * @author dali
 */
public class Associator {

    public List<CandidatePair> getCandidatePair(Source_doc source, Suspicious_doc suspicious) {
        List<CandidatePair> res = new ArrayList<>();
        Iterator<Phrase> pIterator = source.getPhrases().iterator();

        while (pIterator.hasNext()) {
            float maxValue = 0;
            TestPhrase max = null;
            Phrase p = pIterator.next();
            Iterator<TestPhrase> tpIterator = suspicious.getTestPhrases().iterator();
            while (tpIterator.hasNext()) {
                TestPhrase tp = tpIterator.next();
                float overlap = Helpers.getOverlapValue(p.getStemmed(), tp.getStemmed());
                if (overlap > maxValue) {
                    maxValue = overlap;
                    max = tp;
                }
            }
            if (maxValue != 0) {
                res.add(new CandidatePair(p, max));
            }
        }

        return res;

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


}
