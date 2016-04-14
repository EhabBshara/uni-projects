
package filesUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
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

/**
 *
 * @author Ehab Bshara
 * @since 2016 - 04 - 11
 * @version 1.0
 */
public class PhraseImporter implements Importer {
    
    List<Map <String,String>> files = null ;
    String path = null ;

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
    public PhraseImporter(String path)
    {
        phraseService = new GenericServiceImpl<>(Phrase.class, HibernateUtil.getSessionFactory());
        setPath(path);
    }

    /**
     * 
     * @return  List of HashMaps containing files from the specified directory.
     */
    public List<Map<String, String>> getFiles() {
        return files;
    }

    /**
     *
     * @param files List of HashMaps containing files from the specified directory.
     */
    public void setFiles(List<Map<String, String>> files) {
        this.files = files;
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
    public void import_()
    {
        String text = null;
        Map<String, String> file = null;
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
    }
    @Override
    public void save()
    {
        Phrase p = null ;
        for(Map file : files)
        {
           String[] phrases = splitter((String)file.get("content"));
           
           for(String phrase :phrases)
           {
           // String tokens = null; 
             //TODO Tokens = tokensize every phrase
            List<String> tokens = getTokens(phrase);
            p = new Phrase(path,(String)file.get("filename"),phrase,null);
            phraseService.save(p);           
           }
           
        }
    }
    @Override
    public String[] splitter(String content)
    {
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
