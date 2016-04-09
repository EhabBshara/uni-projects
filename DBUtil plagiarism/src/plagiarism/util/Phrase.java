/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plagiarism.util;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Ali-Wassouf
 */
@Entity
@Table(name = "PHRASE")
public class Phrase implements Serializable {

    private int id;
    private String pathname;
    private String filename;
    private String original;
    private String tokens;

    private Set<Assoc> associations = new HashSet<Assoc>(0);

    public Phrase() {
    }
public Phrase( String pathname, String filename, String original, String tokens) {
        
        this.pathname = pathname;
        this.filename = filename;
        this.original = original;
        this.tokens = tokens;
    }
    public Phrase(int id, String pathname, String filename, String original, String tokens) {
        this.id = id;
        this.pathname = pathname;
        this.filename = filename;
        this.original = original;
        this.tokens = tokens;
    }

    public void addAssociation(Assoc assoc) {
        this.associations.add(assoc);
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPathname(String pathname) {
        this.pathname = pathname;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public void setTokens(String tokens) {
        this.tokens = tokens;
    }

    public void setAssociations(Set<Assoc> associations) {
        this.associations = associations;
    }

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "ID",nullable = false, unique=true)
    public int getId() {
        return id;
    }
    @Column(name = "PATHNAME")
    public String getPathname() {
        return pathname;
    }
    @Column(name = "FILENAME")
    public String getFilename() {
        return filename;
    }
    @Column(name = "ORIGINAL")
    public String getOriginal() {
        return original;
    }
    @Column(name = "TOKENS")
    public String getTokens() {
        return tokens;
    }
    @OneToMany(mappedBy = "phrase")
    public Set<Assoc> getAssociations() {
        return associations;
    }

}
