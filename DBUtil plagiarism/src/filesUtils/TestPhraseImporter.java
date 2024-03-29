package filesUtils;

import Utils.Helpers;
import arabicTools.Stem;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.ar.ArabicAnalyzer;
import org.apache.lucene.analysis.ar.ArabicStemFilter;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import plagiarism.DAOImpl.GenericServiceImpl;
import plagiarism.IDAO.IGenericService;
import plagiarism.util.pojos.HibernateUtil;
import plagiarism.util.pojos.Phrase;
import plagiarism.util.pojos.Suspicious_doc;
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
    Suspicious_doc suspicious = null;

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
    public TestPhraseImporter(Suspicious_doc suspicious) {
        setSuspicious_doc(suspicious);
        tdocFreq = new HashMap();
        file = new HashMap();
        testphraseService = new GenericServiceImpl<>(TestPhrase.class, HibernateUtil.getSessionFactory());
    }

    /**
     *
     * @return List of HashMaps containing files from the specified directory.
     */
    public Map<String, String> getFiles() {
        return file;
    }

    /**
     *
     * @param files List of HashMaps containing files from the specified
     * directory.
     */
    public void setFiles(Map<String, String> file) {
        this.file = file;
    }

    /**
     *
     * @return the path where the files are located.
     */
    public Suspicious_doc getSuspicious_doc() {
        return suspicious;
    }

    /**
     *
     * @param path the path where the files are located.
     */
    public void setSuspicious_doc(Suspicious_doc suspicious) {
        this.suspicious = suspicious;
    }

    @Override
    public void import_() {
        file.put("filename", suspicious.getSuspicious_doc_name());
        file.put("content", suspicious.getSuspicious_doc_text());
        file.put("pathname", null); //TODO delete pathname from table phrase
    }

    @Override
    public void save() {
//         String tokens;
//        TestPhrase tp = null;
//        String[] phrases = splitter((String) file.get("content"));
//        for (String phrase : phrases) {
//           tokens = getTokens(phrase);
//           calculateTF(phrase);
//           tp = new TestPhrase((String)file.get("pathname"), (String)file.get("filename"), phrase, suspicious);
//           testphraseService.save(tp);

//        }
        List<TestPhrase> tpList = new ArrayList<>();
//        int offset = 0, length;
//        String[] sentences = splitter((String) file.get("content"));
//        Stem stem = new Stem();
//        for (int i = 0; i < sentences.length; i++) {
//            if (i == 0) {
//                offset = 0;
//            } else {
//                offset += sentences[i - 1].length() + 1;
//            }
//            length = sentences[i].length() ;
//            if (length > 1) {
//                int add=0;
//                if(sentences[i].startsWith("\\n")||sentences[i].startsWith(" "))
//                    add=1;
//                String cleanedSentence = Helpers.cleanSentence(sentences[i]);
//                String stemmedSentence = Helpers.stemCleanedSentence(cleanedSentence, stem);
//                tp = new TestPhrase((String) file.get("pathname"), (String) file.get("filename"),
//                        sentences[i], suspicious, cleanedSentence, stemmedSentence, offset+add, length);
//                testphraseService.save(tp);
//            }
//        }
        Stem stem = new Stem();
        BreakIterator iterator = BreakIterator.getSentenceInstance(new Locale("ar"));
        String source = (String) file.get("content");
        iterator.setText(source);
        int start = iterator.first();
        for (int end = iterator.next();
                end != BreakIterator.DONE;
                start = end, end = iterator.next()) {
            if (end - start < 4) {
                continue;
            }
            String sentence = source.substring(start, end);
            String cleanedSentence = Helpers.cleanSentence(sentence);
            if (cleanedSentence.length() < 10) {
                continue;
            }
            if(cleanedSentence.split(" ").length<2)
                continue;
            String stemmedSentence = Helpers.stemCleanedSentence(cleanedSentence, stem);
            tpList.add(new TestPhrase((String) file.get("pathname"), (String) file.get("filename"),
                    sentence, suspicious, cleanedSentence, stemmedSentence, start, end - start));
        }
        testphraseService.bulkSave(tpList);

    }

    private void calculateTF(String phrase) {
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
                tokenizedTerms += stream.getAttribute(CharTermAttribute.class).toString() + " ";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        analyzer.close();
        return tokenizedTerms;
    }
}
