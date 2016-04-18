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
 * @author Ali-Wassouf
 */
@Entity
@Table(name = "TOKEN")
public class Token implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "TOKEN_ID", nullable = false, unique = true)
    private int token_id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "PHRASE_ID",nullable = false)
    private Phrase phrase;
    
    @Column(name="CONTENT")
    private String content;

    public Token() {
    }

    public Token(int token_id, Phrase phrase, String content) {
        this.token_id = token_id;
        this.phrase = phrase;
        this.content = content;
    }

    public Token(Phrase phrase, String content) {
        this.phrase = phrase;
        this.content = content;
    }
    public Token(String content) {
        this.phrase = phrase;
        this.content = content;
    }

    public void setToken_id(int token_id) {
        this.token_id = token_id;
    }

    public void setPhrase(Phrase phrase) {
        this.phrase = phrase;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getToken_id() {
        return token_id;
    }

    public Phrase getPhrase() {
        return phrase;
    }

    public String getContent() {
        return content;
    }

}
