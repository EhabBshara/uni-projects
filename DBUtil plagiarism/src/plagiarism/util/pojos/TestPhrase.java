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

    private int id;
    private String pathname;
    private String filename;
    private String phrase;

    private Set<Assoc> associations = new HashSet<Assoc>(0);

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
     */
    public TestPhrase(String pathname, String filename, String phrase) {

        this.pathname = pathname;
        this.filename = filename;
        this.phrase = phrase;
    }

    /**
     * Overloaded constructor with parameters.
     *
     * @param id
     * @param pathname the path where the file containing the phrase is located
     * @param filename the file containing the phrase.
     * @param phrase the phrase as it was extracted from the file.
     */
    public TestPhrase(int id, String pathname, String filename, String phrase) {
        this.id = id;
        this.pathname = pathname;
        this.filename = filename;
        this.phrase = phrase;
    }

    /**
     * Getter of the <b>id</b> parameter
     *
     * @return int id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false, unique = true)
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
    @Column(name = "PATHNAME")
    public String getPathname() {
        return pathname;
    }

    /**
     * Adds an Association instance to the Set of Associations
     *
     * @param assoc instance of type Association
     */
    public void addAssociation(Assoc assoc) {
        this.associations.add(assoc);
    }

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
    @Column(name = "FILENAME")
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
    @Column(name = "PHRASE")
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
    @OneToMany(mappedBy = "testphrase")
    public Set<Assoc> getAssociations() {
        return associations;
    }

    /**
     * Setter of the <b>associations</b> parameter which represent a set of
     * Association instances.
     *
     * @param associations
     */
    public void setAssociations(Set<Assoc> associations) {
        this.associations = associations;
    }

}
