/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import plagiarism.util.pojos.Source_doc;
import plagiarism.util.pojos.Suspicious_doc;

/**
 *
 * @author dali
 */
public class CandidateDocs {
    Source_doc source_doc;
    Suspicious_doc suspicious_doc;

    public CandidateDocs(Source_doc source_doc, Suspicious_doc suspicious_doc) {
        this.source_doc = source_doc;
        this.suspicious_doc = suspicious_doc;
    }

    public CandidateDocs() {
    }

    public Source_doc getSource_doc() {
        return source_doc;
    }

    public void setSource_doc(Source_doc source_doc) {
        this.source_doc = source_doc;
    }

    public Suspicious_doc getSuspicious_doc() {
        return suspicious_doc;
    }

    public void setSuspicious_doc(Suspicious_doc suspicious_doc) {
        this.suspicious_doc = suspicious_doc;
    }

    @Override
    public String toString() {
        return "CandidateDocs{" + "source_doc=" + source_doc + ", suspicious_doc=" + suspicious_doc + '}';
    }
    
    
}
