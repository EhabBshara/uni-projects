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
 * <h2>Annotation POJO</h2>
 * <p>
 * This class is a POJO representing an entity in the database generated by
 * NetBeans' Hibernate plugin</p>
 *
 * @author Ali Wassouf
 * @version 1.0
 * @since 2016-04-10
 */
@Entity
@Table(name = "ANNOTATION")
public class Annotation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ANNOTATION", nullable = false, unique = true)
    private int annotation_id;

    @Column(name = "SOURCE_OFFSET")
    private long source_offset;
    
    @Column(name = "SOURCE_LENGTH")
    private long source_length;
    
    @Column(name = "SUSPICIOUS_OFFSET")
    private long suspicious_offset;
    
    @Column(name = "SUSPICIOUS_LENGTH")
    private long suspicious_length;
    
    @Column(name = "OBFUSCATION")
    private String obfuscation;
    
    @Column(name = "TYPE")
    private String type;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "SOURCE_DOC_ID")
    private Source_doc source_doc;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "SUSPICIOUS_DOC_ID")
    private Suspicious_doc suspicious_doc;

    public Annotation() {
    }

    public Annotation(long source_offset, long source_length, 
            long suspicious_offset, long suspicious_length, String obfuscation, 
            String type, Source_doc source_doc, Suspicious_doc suspicious_doc) {
        this.source_offset = source_offset;
        this.source_length = source_length;
        this.suspicious_offset = suspicious_offset;
        this.suspicious_length = suspicious_length;
        this.obfuscation = obfuscation;
        this.type = type;
        this.source_doc = source_doc;
        this.suspicious_doc = suspicious_doc;
    }

    public int getAnnotation_id() {
        return annotation_id;
    }

    public void setAnnotation_id(int annotation_id) {
        this.annotation_id = annotation_id;
    }

    public long getSource_offset() {
        return source_offset;
    }

    public void setSource_offset(long source_offset) {
        this.source_offset = source_offset;
    }
    
    

    public long getSource_length() {
        return source_length;
    }

    public void setSource_length(long source_length) {
        this.source_length = source_length;
    }

    public long getSuspicious_offset() {
        return suspicious_offset;
    }

    public void setSuspicious_offset(long suspicious_offset) {
        this.suspicious_offset = suspicious_offset;
    }

    public long getSuspicious_length() {
        return suspicious_length;
    }

    public void setSuspicious_length(long suspicious_length) {
        this.suspicious_length = suspicious_length;
    }

    public String getObfuscation() {
        return obfuscation;
    }

    public void setObfuscation(String obfuscation) {
        this.obfuscation = obfuscation;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
    
    
    

}
