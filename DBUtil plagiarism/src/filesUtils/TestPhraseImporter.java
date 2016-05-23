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
import plagiarism.util.pojos.Suspiciuos_doc;
import plagiarism.util.pojos.TestPhrase;

/**
 *
 * @author Ehab Bshara
 * @since 2016 - 04 - 11
 * @version 1.0
 */
public class TestPhraseImporter implements Importer {

    Map<String, String> file = null;
    HashMap tdocFreq = null;
    Suspiciuos_doc suspicious = null ;

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
    public TestPhraseImporter(Suspiciuos_doc suspiciuos) {
        setSuspicious_doc(suspiciuos);
        tdocFreq = new HashMap();
        file = new HashMap();
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
    public Suspiciuos_doc getSuspiciuos_doc() {
        return suspicious;
    }

    /**
     *
     * @param path the path where the files are located.
     */
    public void setSuspicious_doc(Suspiciuos_doc suspicious) {
        this.suspicious = suspicious;
    }

    @Override
    public void import_() {
       file.put("filename", suspicious.getSuspiciuos_doc_name());
        file.put("content", suspicious.getSuspiciuos_doc_text());
        file.put("pathname", null); //TODO delete pathname from table phrase
    }

    @Override
    public void save() {
         String tokens;
        TestPhrase tp = null;
        String[] phrases = splitter((String) file.get("content"));
        for (String phrase : phrases) {
           tokens = getTokens(phrase);
           calculateTF(phrase);
           tp = new TestPhrase((String)file.get("pathname"), (String)file.get("filename"), phrase, suspicious);
           testphraseService.save(tp);
           
        }
    }
    private void calculateTF(String phrase)
    {
         String[] tokens = splitter(getTokens(phrase));
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

    public String getTokens(String sentence) {
        ArabicAnalyzer analyzer = new ArabicAnalyzer();
        TokenStream stream = analyzer.tokenStream("contents", new StringReader(sentence));
        stream = new ArabicStemFilter(stream);
        String tokenizedTerms = "";
        try {
            stream.reset();
            while (stream.incrementToken()) {
                tokenizedTerms +=stream.getAttribute(CharTermAttribute.class).toString()+" ";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        analyzer.close();
        return tokenizedTerms;
    }
}
