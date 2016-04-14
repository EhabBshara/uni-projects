package filesUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.ar.ArabicAnalyzer;
import org.apache.lucene.analysis.ar.ArabicStemFilter;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import plagiarism.DAOImpl.GenericServiceImpl;
import plagiarism.IDAO.IGenericService;
import plagiarism.util.pojos.HibernateUtil;
import plagiarism.util.pojos.TestPhrase;

/**
 *
 * @author Ehab Bshara
 * @since 2016 - 04 - 11
 * @version 1.0
 */
public class TestPhraseImporter implements Importer {

    Map<String, String> file = null;
    String path = null;
    HashMap tdocFreq = null;

    public HashMap getTdocFreq() {
        return tdocFreq;
    }

    public void setTdocFreq(HashMap tdocFreq) {
        this.tdocFreq = tdocFreq;
    }
    IGenericService<TestPhrase> testphraseService;

    /**
     * Default constructor.
     */
    public TestPhraseImporter() {
        testphraseService = new GenericServiceImpl<>(TestPhrase.class, HibernateUtil.getSessionFactory());
    }

    /**
     *
     * @param path the path where files are located.
     */
    public TestPhraseImporter(String path) {
        setPath(path);
        testphraseService = new GenericServiceImpl<>(TestPhrase.class, HibernateUtil.getSessionFactory());
    }

    /**
     *
     * @return List of HashMaps containing files from the specified directory.
     */
    public Map<String, String>getFiles() {
        return file;
    }

    /**
     *
     * @param files List of HashMaps containing files from the specified
     * directory.
     */
    public void setFiles(Map<String, String>file) {
        this.file = file;
    }

    /**
     *
     * @return the path where the files are located.
     */
    public String getPath() {
        return path;
    }

    /**
     *
     * @param path the path where the files are located.
     */
    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public void import_() {
        String text = null;
        File f = new File(path);
            try {
                FileInputStream fin = new FileInputStream(f);
                byte[] fileBytes = new byte[(int) f.length()];
                fin.read(fileBytes);
                text = new String(fileBytes);
                file.put("filename", f.getName());
                file.put("pathname", path);
                file.put("content", text);
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    @Override
    public void save() {
        TestPhrase tp = null;
        String[] phrases = splitter((String) file.get("content"));
        for (String phrase : phrases) {
            tp = new TestPhrase(path, (String) file.get("filename"), phrase);
            testphraseService.save(tp);
            calculateTF(phrase);
        }
    }
    private void calculateTF(String phrase)
    {
         List<String> tokens = getTokens(phrase);
         for (String token : tokens) {
                if (tdocFreq.containsKey(token)) {
                    int freq = ((Integer) tdocFreq.get(token)) + 1;
                    tdocFreq.put(token, freq);
                } else {
                    tdocFreq.put(token, 1);
                }
            }
        
    }
    
    @Override
    public String[] splitter(String content) {
        //TODO do your magic dali 
        return content.split("\\.|\\?|!");
    }

    @Override
    public List<String> getTokens(String sentence) {
        ArabicAnalyzer analyzer = new ArabicAnalyzer();
        TokenStream stream = analyzer.tokenStream("contents", new StringReader(sentence));
        stream = new ArabicStemFilter(stream);
        List<String> tokenizedTerms = new ArrayList<String>();
        try {
            stream.reset();
            while (stream.incrementToken()) {
                tokenizedTerms.add(stream.getAttribute(CharTermAttribute.class).toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        analyzer.close();
        return tokenizedTerms;
    }
}
