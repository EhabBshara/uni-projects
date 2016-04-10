/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plagiarism.util.pojos;

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
@Table(name = "testphrase")
public class TestPhrase implements Serializable {

    private int id;
    private String pathname;
    private String filename;
    private String phrase;

    private Set<Assoc> associations = new HashSet<Assoc>(0);

    public TestPhrase() {
    }

    public TestPhrase( String pathname, String filename, String phrase) {
        
        this.pathname = pathname;
        this.filename = filename;
        this.phrase = phrase;
    }

    public TestPhrase(int id, String pathname, String filename, String phrase) {
        this.id = id;
        this.pathname = pathname;
        this.filename = filename;
        this.phrase = phrase;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false, unique = true)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "PATHNAME")
    public String getPathname() {
        return pathname;
    }

    public void addAssociation(Assoc assoc) {
        this.associations.add(assoc);
    }

    public void setPathname(String pathname) {
        this.pathname = pathname;
    }

    @Column(name = "FILENAME")
    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    @Column(name = "PHRASE")
    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }

    @OneToMany(mappedBy = "testphrase")
    public Set<Assoc> getAssociations() {
        return associations;
    }

    public void setAssociations(Set<Assoc> associations) {
        this.associations = associations;
    }

}
