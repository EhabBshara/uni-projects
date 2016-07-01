/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plagiarism.util.pojos;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author dali
 */
@Entity
@Table(name = "CANDIDATE")
public class CandidateDocs  implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "candidate_id", nullable = false, unique = true)
    int id;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "source_doc_id")
    Source_doc source_doc;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "suspicious_doc_id")
    Suspicious_doc suspicious_doc;
    
    @Column(name = "correlation_value")
    float correlation_value;

    public CandidateDocs(Source_doc source_doc, Suspicious_doc suspicious_doc, float correlation_value) {
        this.source_doc = source_doc;
        this.suspicious_doc = suspicious_doc;
        this.correlation_value = correlation_value;
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
     public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getCorrelation_value() {
        return correlation_value;
    }

    public void setCorrelation_value(float correlation_value) {
        this.correlation_value = correlation_value;
    }

    @Override
    public String toString() {
        return "CandidateDocs{" + "source_doc=" + source_doc + ", suspicious_doc=" + suspicious_doc + '}';
    }
    
    
}
