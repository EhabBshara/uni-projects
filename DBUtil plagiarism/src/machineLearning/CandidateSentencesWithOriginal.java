/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package machineLearning;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author dali
 */
public class CandidateSentencesWithOriginal implements Serializable {

    String source;
    String originalSource;
    String suspicious;
    String originalSuspicious;
    boolean isPlagirised;
    Features features;

    public CandidateSentencesWithOriginal() {
    }

    public CandidateSentencesWithOriginal(String source, String suspicious) {
        this.source = source;
        this.suspicious = suspicious;
    }

    public CandidateSentencesWithOriginal(String source, String suspicious, boolean isPlagirised) {
        this.source = source;
        this.suspicious = suspicious;
        this.isPlagirised = isPlagirised;
    }

    public CandidateSentencesWithOriginal(String source, String originalSource, String suspicious, String originalSuspicious, boolean isPlagirised) {
        this.source = source;
        this.originalSource = originalSource;
        this.suspicious = suspicious;
        this.originalSuspicious = originalSuspicious;
        this.isPlagirised = isPlagirised;
    }

    public Features getFeatures() {
        return features;
    }

    public void setFeatures(Features features) {
        this.features = features;
    }
    

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSuspicious() {
        return suspicious;
    }

    public void setSuspicious(String suspicious) {
        this.suspicious = suspicious;
    }

    public boolean isIsPlagirised() {
        return isPlagirised;
    }

    public void setIsPlagirised(boolean isPlagirised) {
        this.isPlagirised = isPlagirised;
    }

    public String getOriginalSource() {
        return originalSource;
    }

    public void setOriginalSource(String originalSource) {
        this.originalSource = originalSource;
    }

    public String getOriginalSuspicious() {
        return originalSuspicious;
    }

    public void setOriginalSuspicious(String originalSuspicious) {
        this.originalSuspicious = originalSuspicious;
    }

    
    @Override
    public String toString() {
        return "CandidateSentences{" + "source=" + source + ", suspicious=" + suspicious + ", isPlagirised=" + isPlagirised + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CandidateSentencesWithOriginal other = (CandidateSentencesWithOriginal) obj;
        if (!Objects.equals(this.source, other.source)) {
            return false;
        }
        if (!Objects.equals(this.suspicious, other.suspicious)) {
            return false;
        }
        if (this.isPlagirised != other.isPlagirised) {
            return false;
        }
        return true;
    }

    public int equalsWithWrongResult(CandidateSentencesWithOriginal cs) {
        if (source.equals(cs.source) && suspicious.equals(cs.suspicious)) {
            if (!isPlagirised&&cs.isPlagirised) 
                return 1;
            if(!cs.isPlagirised&&isPlagirised)
                return 2;
            return 0;
        }

        return -1;
    }

}
