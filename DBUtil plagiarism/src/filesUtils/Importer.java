package filesUtils;

import java.util.List;

/**
 * The main purpose of this interface is to persist phrases from files in a
 * specific directory to the database in order to perform processing on them.
 *
 * @author Ehab Bshara
 * @since 2016 - 04 - 11
 * @version 1.0
 */
public interface Importer {

    /**
     * For each file in the specified directory, this function fills a List of
     * HashMaps. A HashMap for each file, with keys (filename, path, content)
     *
     */
    public void import_();

    /**
     * Persist phrases to database after pre-processing each phrase. The
     * pre-processing phase includes splitting the phrases using
     * {@link splitter} and then extracting tokens.
     */
    public void save();

    /**
     * Splits a phrase using a regular expression.
     * @param content content of file to be split into phrases using the reg-ex.
     * @return an array of String each element is a phrase.
     */
    public String[] splitter(String content);
}
