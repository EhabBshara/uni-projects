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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 * <h2>TestPhrase POJO</h2>
 * <p>
 * This class is a POJO representing an entity in the database generated by
 * NetBeans' Hibernate plugin</p>
 *
 * @author Ali-Wassouf
 * @version 1.0
 * @since 2016 - 04 - 10
 *
 */
@Entity
@Table(name = "testphrase")
public class TestPhrase implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false, unique = true)
    private int id;

    @Column(name = "PATHNAME")
    private String pathname;

    @Column(name = "FILENAME")
    private String filename;

    @Column(name = "PHRASE")
    private String phrase;

    @Column(name = "CLEANED")
    private String cleaned;

    @Column(name = "STEMMED")
    private String stemmed;

    @Column(name = "OFFSET")
    private int offset;

    @Column(name = "LENGTH")
    private int length;

//    @OneToMany(mappedBy = "testphrase")
//    private Set<Assoc> associations = new HashSet(0);

    @ManyToOne(cascade =  {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @JoinColumn(name = "SUSPICIOUS_DOC_ID")
    private Suspicious_doc suspicious_doc;

    /**
     * Default constructor.
     */
    public TestPhrase() {
    }

    /**
     *
     * Overloaded constructor with parameters. Does not take <b>id</b>
     * parameter.
     *
     * @param pathname the path where the file containing the phrase is located
     * @param filename the file containing the phrase.
     * @param phrase the phrase as it was extracted from the file.
     * @param suspicious_doc
     */
    public TestPhrase(String pathname, String filename, String phrase, Suspicious_doc suspicious_doc, String cleaned, String stemmed, int offset, int length) {

        this.pathname = pathname;
        this.filename = filename;
        this.phrase = phrase;
        this.suspicious_doc = suspicious_doc;
        this.cleaned = cleaned;
        this.stemmed = stemmed;
        this.offset = offset;
        this.length = length;
    }

    /**
     * Overloaded constructor with parameters.
     *
     * @param id
     * @param pathname the path where the file containing the phrase is located
     * @param filename the file containing the phrase.
     * @param phrase the phrase as it was extracted from the file.
     * @param suspicious_doc
     */
    public TestPhrase(int id, String pathname, String filename, String phrase, Suspicious_doc suspicious_doc) {
        this.id = id;
        this.pathname = pathname;
        this.filename = filename;
        this.phrase = phrase;
        this.suspicious_doc = suspicious_doc;
    }

    public TestPhrase(int id, String pathname, String filename, String phrase, String cleaned, String stemmed, int offset, int length, Suspicious_doc suspicious_doc) {
        this.id = id;
        this.pathname = pathname;
        this.filename = filename;
        this.phrase = phrase;
        this.cleaned = cleaned;
        this.stemmed = stemmed;
        this.offset = offset;
        this.length = length;
        this.suspicious_doc = suspicious_doc;
    }

    /**
     * Getter of the <b>id</b> parameter
     *
     * @return int id
     */
    public int getId() {
        return id;
    }

    /**
     * Setter for the <b>id</b> parameter.
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter of the <b>pathname</b> parameter
     *
     * @return String pathname.
     */
    public String getPathname() {
        return pathname;
    }

    /**
     * Adds an Association instance to the Set of Associations
     *
     * @param assoc instance of type Association
     */
//    public void addAssociation(Assoc assoc) {
//        this.associations.add(assoc);
//    }

    /**
     * Setter for the <b>pathname</b> parameter.
     *
     * @param pathname
     */
    public void setPathname(String pathname) {
        this.pathname = pathname;
    }

    /**
     * Getter of the <b>filename</b> parameter
     *
     * @return String filename.
     */
    public String getFilename() {
        return filename;
    }

    /**
     * Setter of the <b>filename</b> parameter.
     *
     * @param filename
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }

    /**
     * Getter of the <b>phrase</b> parameter
     *
     * @return String phrase
     */
    public String getPhrase() {
        return phrase;
    }

    /**
     * Setter of the <b>phrase</b> parameter.
     *
     * @param phrase
     */
    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }

    /**
     * Getter of the <b>associations</b> parameter
     *
     * @return Set of Assoc associations
     */
//    public Set<Assoc> getAssociations() {
//        return associations;
//    }

    /**
     * Setter of the <b>associations</b> parameter which represent a set of
     * Association instances.
     *
     * @param associations
     */
//    public void setAssociations(Set<Assoc> associations) {
//        this.associations = associations;
//    }

    public Suspicious_doc getSuspicious_doc() {
        return suspicious_doc;
    }

    public void setSuspicious_doc(Suspicious_doc suspicious_doc) {
        this.suspicious_doc = suspicious_doc;
    }

    public String getCleaned() {
        return cleaned;
    }

    public void setCleaned(String cleaned) {
        this.cleaned = cleaned;
    }

    public String getStemmed() {
        return stemmed;
    }

    public void setStemmed(String stemmed) {
        this.stemmed = stemmed;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

}
