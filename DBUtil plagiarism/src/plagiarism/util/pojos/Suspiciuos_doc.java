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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author AliWAA
 */
@Entity
@Table(name = "SUSPICIUOS_DOC")
public class Suspiciuos_doc implements Serializable {

    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "SUSPICIUOS_DOC_ID", nullable = false, unique = true)
    private int suspiciuos_doc_id;
    
    @Column(name = "SUSPICIUOS_DOC_TEXT")
    private String suspiciuos_doc_text;
    
    @Column(name = "SUSPICIUOS_DOC_NAME")
    private String suspiciuos_doc_name;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "SUSPICIUOS_DOC")
    private Set<TestPhrase> testPhrases = new HashSet<TestPhrase>(0);
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "SUSPICIUOS_DOC")
    private Set<Annotation> annotations = new HashSet<Annotation>(0);

    public Suspiciuos_doc() {
    }

    public Suspiciuos_doc(String suspiciuos_doc_text, String suspiciuos_doc_name) {
        this.suspiciuos_doc_text = suspiciuos_doc_text;
        this.suspiciuos_doc_name = suspiciuos_doc_name;
    }

    public int getSuspiciuos_doc_id() {
        return suspiciuos_doc_id;
    }

    public void setSuspiciuos_doc_id(int suspiciuos_doc_id) {
        this.suspiciuos_doc_id = suspiciuos_doc_id;
    }

    public String getSuspiciuos_doc_text() {
        return suspiciuos_doc_text;
    }

    public void setSuspiciuos_doc_text(String suspiciuos_doc_text) {
        this.suspiciuos_doc_text = suspiciuos_doc_text;
    }

    public String getSuspiciuos_doc_name() {
        return suspiciuos_doc_name;
    }

    public void setSuspiciuos_doc_name(String suspiciuos_doc_name) {
        this.suspiciuos_doc_name = suspiciuos_doc_name;
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
    
    
    
    
    
}
