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
    double fuzzySim;
    double intersection;
    boolean isPlagirised;

    public Features(double bleuPrec, double bleuRec, double skipgram2, double skipgram3, double lcs, double fuzzySim,double intersection, boolean isPlagirised) {
        this.bleuPrec = bleuPrec;
        this.bleuRec = bleuRec;
        this.skipgram2 = skipgram2;
        this.skipgram3 = skipgram3;
        this.lcs = lcs;
        this.fuzzySim = fuzzySim;
        this.intersection = intersection;
        this.isPlagirised = isPlagirised;
    }

    public double getIntersection() {
        return intersection;
    }

    public void setIntersection(double intersection) {
        this.intersection = intersection;
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

    public double getFuzzySim() {
        return fuzzySim;
    }

    public void setFuzzySim(double fuzzySim) {
        this.fuzzySim = fuzzySim;
    }

    
    public String toMLString() {
        return bleuPrec + "," + bleuRec + "," + skipgram2 + "," + skipgram3 + "," + lcs + "," +fuzzySim + ","+intersection+"," + (isPlagirised?"yes":"no");
    }
    public static String getMLHeaders()
    {
        String result="";
        result+="@relation 'Plagiarism data set'\n";
        result+="@attribute bleuPrec numeric\n";
        result+="@attribute bleuRec numeric\n";
        result+="@attribute skipgram2 numeric\n";
        result+="@attribute skipgram3 numeric\n";
        result+="@attribute lcs numeric\n";
        result+="@attribute fuzzy numeric\n";
        result+="@attribute intersection\n";
        result+="@attribute isPlagirised {yes,no}\n";
        result+="@data\n";
        return result;
    }

}
