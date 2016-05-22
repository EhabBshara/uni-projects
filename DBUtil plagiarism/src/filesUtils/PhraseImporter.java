
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
import plagiarism.util.pojos.Phrase;
import plagiarism.util.pojos.Source_doc;

/**
 *
 * @author Ehab Bshara
 * @since 2016 - 04 - 11
 * @version 1.0
 */
public class PhraseImporter implements Importer {
    
    Map <String,String>file = null ;
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
        file = new HashMap<String, String>();
        phraseService = new GenericServiceImpl<>(Phrase.class, HibernateUtil.getSessionFactory());
        setSource_doc(source);
    }

    /**
     * 
     * @return  List of HashMaps containing files from the specified directory.
     */
    public Map<String, String> getFiles() {
        return file;
    }

    /**
     *
     * @param files List of HashMaps containing files from the specified directory.
     */
    public void setFiles(Map<String, String> file) {
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
        file.put("filename", source.getSource_doc_name());
        file.put("content", source.getSource_doc_text());
        file.put("pathname", null); //TODO delete pathname from table phrase
        /*
        String text = null;
        Map<String, String> file = new HashMap<>();
        File folder = new File(path);
        for (File f : folder.listFiles()) {
            try {
                FileInputStream fin = new FileInputStream(f);
                byte[] fileBytes = new byte[(int) f.length()];
                fin.read(fileBytes);
                text = new String(fileBytes);
                file.put("filename", f.getName());
                file.put("pathname", path);
                file.put("content", text);
                files.add(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            
        }
                */
    }
    @Override
    public void save()
    {
        Phrase p = null;
        String tokens;

        String[] phrases = splitter((String) file.get("content"));

        for (String phrase : phrases) {
            tokens = getTokens(phrase);
            p = new Phrase(file.get("pathname"), file.get("filename"),phrase, source ,tokens);
            phraseService.save(p);
           // String tokens = null; 
            //TODO Tokens = tokensize every phrase 
            //TODO tokens now are not objects
//            List<String> tokens = getTokens(phrase);
//            p = new Phrase(path,(String)file.get("filename"),phrase,null);
//            phraseService.save(p);           
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
