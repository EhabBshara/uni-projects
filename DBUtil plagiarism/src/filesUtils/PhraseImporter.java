
package filesUtils;

import Utils.Helpers;
import arabicTools.Stem;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.util.Pair;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.ar.ArabicAnalyzer;
import org.apache.lucene.analysis.ar.ArabicStemFilter;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import plagiarism.DAOImpl.GenericServiceImpl;
import plagiarism.IDAO.IGenericService;
import plagiarism.util.pojos.HibernateUtil;
import plagiarism.util.pojos.Phrase;
import plagiarism.util.pojos.Source_doc;

/**
 *
 * @author Ehab Bshara
 * @since 2016 - 04 - 11
 * @version 1.0
 */
public class PhraseImporter implements Importer {
    
    Map <String,Object>file = null ;
    //String path = null ;
    Source_doc source = null ;

    IGenericService<Phrase> phraseService ;

    /**
     * Default constructor.
     */
    public PhraseImporter() {
       phraseService = new GenericServiceImpl<>(Phrase.class, HibernateUtil.getSessionFactory());
    }

    /**
     * 
     * @param path the path where files are located. 
     */
    public PhraseImporter(Source_doc source)
    {
        file = new HashMap<String, Object>();
        phraseService = new GenericServiceImpl<>(Phrase.class, HibernateUtil.getSessionFactory());
        setSource_doc(source);
    }

    /**
     * 
     * @return  List of HashMaps containing files from the specified directory.
     */
    public Map<String, Object> getFiles() {
        return file;
    }

    /**
     *
     * @param files List of HashMaps containing files from the specified directory.
     */
    public void setFiles(Map<String, Object> file) {
        this.file = file;
    }

    /**
     *
     * @return the path where the files are located.
     */
    public Source_doc getPath() {
        return source;
    }

    /**
     *
     * @param path the path where the files are located.
     */
    public void setSource_doc(Source_doc source) {
        this.source = source;
    }
    
 
    @Override
    public void import_()
    {
        String content = source.getSource_doc_text();
        file.put("filename", source.getSource_doc_name());
        file.put("content",content );
        file.put("pathname", null); //TODO delete pathname from table phrase
      
    }
    @Override
    public void save()
    {
//        Phrase p = null;
//        String tokens;
//
//        String[] phrases = splitter((String) file.get("content"));
//
//        for (String phrase : phrases) {
//            tokens = getTokens(phrase);
//            p = new Phrase(file.get("pathname"), file.get("filename"),phrase, source ,tokens);
//            phraseService.save(p);
//    }
          int offset = 0 , length ;
        String[] sentences = splitter((String) file.get("content"));
        Stem stem = new Stem();
        for (int i = 0; i < sentences.length; i++) {
            if (i == 0) {
                offset = 0;
            } else {
                offset += sentences[i - 1].length() + 2;
            }
            length = sentences[i].length() + 1;
            if (length > 1) {
                String cleanedSentence = Helpers.cleanSentence(sentences[i]);
                String stemmedSentence = Helpers.stemCleanedSentence(cleanedSentence, stem);
                Phrase p = new Phrase((String) file.get("pathname"), (String) file.get("filename"),
                        sentences[i], source, null, cleanedSentence, stemmedSentence, offset, length);
                phraseService.save(p);
            }
        }
        
      
        

    }
    @Override
    public String[] splitter(String content)
    {
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
                tokenizedTerms+=stream.getAttribute(CharTermAttribute.class).toString()+" ";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        analyzer.close();
        return tokenizedTerms;
    }
    
}
