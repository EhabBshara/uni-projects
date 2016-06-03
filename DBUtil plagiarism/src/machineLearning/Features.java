/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package machineLearning;

/**
 *
 * @author dali
 */
public class Features {
    double bleuPrec;
    double bleuRec;
    double skipgram2;
    double skipgram3;
    double lcs;
    boolean isPlagirised;

    public Features(double bleuPrec, double bleuRec, double skipgram2, double skipgram3, double lcs, boolean isPlagirised) {
        this.bleuPrec = bleuPrec;
        this.bleuRec = bleuRec;
        this.skipgram2 = skipgram2;
        this.skipgram3 = skipgram3;
        this.lcs = lcs;
        this.isPlagirised = isPlagirised;
    }

    public Features() {
    }

    public double getBleuPrec() {
        return bleuPrec;
    }

    public void setBleuPrec(double bleuPrec) {
        this.bleuPrec = bleuPrec;
    }

    public double getBleuRec() {
        return bleuRec;
    }

    public void setBleuRec(double bleuRec) {
        this.bleuRec = bleuRec;
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

    public double getLcs() {
        return lcs;
    }

    public void setLcs(double lcs) {
        this.lcs = lcs;
    }

    public boolean isIsPlagirised() {
        return isPlagirised;
    }

    public void setIsPlagirised(boolean isPlagirised) {
        this.isPlagirised = isPlagirised;
    }

    @Override
    public String toString() {
        return "Features{" + "bleuPrec=" + bleuPrec + ", bleuRec=" + bleuRec + ", skipgram2=" + skipgram2 + ", skipgram3=" + skipgram3 + ", lcs=" + lcs + ", isPlagirised=" + isPlagirised + '}';
    }
    
     
    
    
}
