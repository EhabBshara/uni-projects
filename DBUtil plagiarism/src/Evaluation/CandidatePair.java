/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Evaluation;

import plagiarism.util.pojos.Phrase;
import plagiarism.util.pojos.TestPhrase;

/**
 *
 * @author dali
 */
public class CandidatePair {
    Phrase phrase;
    TestPhrase testPhrase;
    double LSC;
    double skipgram2;
    double skipgram3;

    public CandidatePair(Phrase phrase, TestPhrase testPhrase) {
        this.phrase = phrase;
        this.testPhrase = testPhrase;
    }

    
    public CandidatePair(Phrase phrase, TestPhrase testPhrase, double LSC, double skipgram2, double skipgram3) {
        this.phrase = phrase;
        this.testPhrase = testPhrase;
        this.LSC = LSC;
        this.skipgram2 = skipgram2;
        this.skipgram3 = skipgram3;
    }

    public Phrase getPhrase() {
        return phrase;
    }

    public void setPhrase(Phrase phrase) {
        this.phrase = phrase;
    }

    public TestPhrase getTestPhrase() {
        return testPhrase;
    }

    public void setTestPhrase(TestPhrase testPhrase) {
        this.testPhrase = testPhrase;
    }

    public double getLSC() {
        return LSC;
    }

    public void setLSC(double LSC) {
        this.LSC = LSC;
    }

    public double getSkipgram2() {
        return skipgram2;
    }

    public void setSkipgram2(double skipgram2) {
        this.skipgram2 = skipgram2;
    }

    public double getSkipgram3() {
        return skipgram3;
    }

    public void setSkipgram3(double skipgram3) {
        this.skipgram3 = skipgram3;
    }

    @Override
    public String toString() {
        return "CandidatePair{" + "phrase=" + phrase + ", testPhrase=" + testPhrase + ", LSC=" + LSC + ", skipgram2=" + skipgram2 + ", skipgram3=" + skipgram3 + '}';
    }
    
}
