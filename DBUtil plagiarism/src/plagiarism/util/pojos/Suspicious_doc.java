/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plagiarism.util.pojos;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;

/**
 *
 * @author AliWAA
 */
@Entity
@Indexed
@Table(name = "SUSPICIOUS_DOC")
public class Suspicious_doc implements Serializable {

    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "SUSPICIOUS_DOC_ID", nullable = false, unique = true)
    private int suspicious_doc_id;
    
    @Column(name = "SUSPICIOUS_DOC_TEXT")
    @Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
    private String suspicious_doc_text;
    
    @Column(name = "SUSPICIOUS_DOC_NAME")
    @Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
    private String suspicious_doc_name;
    
    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, mappedBy = "suspicious_doc")
    private Set<TestPhrase> testPhrases = new HashSet<>(0);
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "suspicious_doc")
    private Set<Annotation> annotations = new HashSet<>(0);

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "suspicious_doc")
    private Set<CandidateDocs> candidateDocs = new HashSet<>(0);

    public Set<CandidateDocs> getCandidateDocs() {
        return candidateDocs;
    }

    public void setCandidateDocs(Set<CandidateDocs> candidateDocs) {
        this.candidateDocs = candidateDocs;
    }
    
 
    public Suspicious_doc() {
    }

    public Suspicious_doc(String suspicious_doc_text, String suspicious_doc_name) {
        this.suspicious_doc_text = suspicious_doc_text;
        this.suspicious_doc_name = suspicious_doc_name;
    }

    public int getSuspicious_doc_id() {
        return suspicious_doc_id;
    }

    public void setSuspicious_doc_id(int suspicious_doc_id) {
        this.suspicious_doc_id = suspicious_doc_id;
    }

    public String getSuspicious_doc_text() {
        return suspicious_doc_text;
    }

    public void setSuspicious_doc_text(String suspicious_doc_text) {
        this.suspicious_doc_text = suspicious_doc_text;
    }

    public String getSuspicious_doc_name() {
        return suspicious_doc_name;
    }

    public void setSuspicious_doc_name(String suspicious_doc_name) {
        this.suspicious_doc_name = suspicious_doc_name;
    }

    public Set<TestPhrase> getTestPhrases() {
        return testPhrases;
    }

    public void setTestPhrases(Set<TestPhrase> testPhrases) {
        this.testPhrases = testPhrases;
    }

    public Set<Annotation> getAnnotations() {
        return annotations;
    }

    public void setAnnotations(Set<Annotation> annotations) {
        this.annotations = annotations;
    }

    @Override
    public String toString() {
        return "Suspicious_doc{" + "suspicious_doc_id=" + suspicious_doc_id + ", suspicious_doc_text=" + suspicious_doc_text + ", suspicious_doc_name=" + suspicious_doc_name + '}';
    }
    
    
    
    
    
}
