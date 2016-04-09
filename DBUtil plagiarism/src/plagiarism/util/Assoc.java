/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plagiarism.util;

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
 * @author Ali-Wassouf
 */
@Entity
@Table(name = "ASSOC")
public class Assoc implements Serializable {

    private int assoc_id;
    private Phrase phrase;
    private TestPhrase testphrase;

    private Double score;

    public Assoc() {
    }

    public Assoc(int assoc_id, Phrase phrase, TestPhrase testphrase, Double score) {
        this.assoc_id = assoc_id;
        this.phrase = phrase;
        this.testphrase = testphrase;
        this.score = score;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ASSOC_ID", nullable = false, unique = true)
    public int getAssoc_id() {
        return assoc_id;
    }

    public void setAssoc_id(int assoc_id) {
        this.assoc_id = assoc_id;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "PHRASE_ID")
    public Phrase getPhrase() {
        return phrase;
    }

    public void setPhrase(Phrase phrase) {
        this.phrase = phrase;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "TESTPHRASE_ID")
    public TestPhrase getTestphrase() {
        return testphrase;
    }

    public void setTestphrase(TestPhrase testphrase) {
        this.testphrase = testphrase;
    }

    @Column(name = "SCORE")
    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

}
