/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IR;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import plagiarism.DAOImpl.GenericServiceImpl;
import plagiarism.IDAO.IGenericService;
import plagiarism.util.pojos.Annotation;
import plagiarism.util.pojos.HibernateUtil;
import plagiarism.util.pojos.Phrase;
import plagiarism.util.pojos.Suspicious_doc;

/**
 *
 * @author Ehab Bshara
 */
public class Lucene {

    private StandardAnalyzer analyzer;
    private Directory dir;

    public Directory getDir() {
        return dir;
    }

    public void setDir(Directory dir) {
        this.dir = dir;
    }
    IGenericService<Phrase> phraseService;

    public Lucene(StandardAnalyzer analyzer) {
        setAnalyzer(analyzer);
        setIndexingDirectory();
        phraseService = new GenericServiceImpl<>(Phrase.class, HibernateUtil.getSessionFactory());
    }

    public StandardAnalyzer getAnalyzer() {
        return analyzer;
    }

    public void setAnalyzer(StandardAnalyzer analyzer) {
        this.analyzer = analyzer;
    }

    private void setIndexingDirectory() {
        File f = new File(System.getProperty("user.dir") + "/documents");
        if (!f.exists()) {
            f.mkdir();
        }
        Path path = Paths.get(f.getPath());
        try {
            dir = FSDirectory.open(path);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void indexer() {
        int id;
        String filename;
        String content;
        int sourceFileId;
        try {
            IndexWriterConfig config = new IndexWriterConfig(analyzer);
            IndexWriter iwriter = new IndexWriter(dir, config);
            List<Phrase> phrases = phraseService.getAll();
            Document document;
            for (Phrase p : phrases) {
                document = new Document();
                id = p.getId();
                filename = p.getFilename();
                content = p.getCleaned();
                sourceFileId = p.getSource_doc().getSource_doc_id();
                document.add(new Field("id", String.valueOf(id), Field.Store.YES, Field.Index.NO));
                document.add(new Field("source_id",String.valueOf(sourceFileId),Field.Store.YES,Field.Index.NO));
                document.add(new Field("filename", filename, Field.Store.YES, Field.Index.NO));
                document.add(new Field("content", content, Field.Store.YES, Field.Index.ANALYZED, Field.TermVector.WITH_POSITIONS_OFFSETS));
                iwriter.addDocument(document);
            }
            iwriter.close();
        } catch (IOException ex) {
            Logger.getLogger(Lucene.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
     
    public void searcher(Directory dir,StandardAnalyzer analyzer,String term )
    {
        DirectoryReader ireader;
        try {
            ireader = DirectoryReader.open(dir);
            IndexSearcher isearcher = new IndexSearcher(ireader);
             PhraseQuery.Builder query = new PhraseQuery.Builder();
            String[] words = term.split(" ");
            for (String word : words) {
                query.add(new Term("content", word));
            }
            query.setSlop(10);
            PhraseQuery pq = query.build();
            ScoreDoc[] hits = isearcher.search(pq, null, 1000).scoreDocs;
            for(ScoreDoc hit :hits)
            {
                   Document hitDoc = isearcher.doc(hit.doc);
                   System.out.println(hitDoc.get("filename")+"  "+hitDoc.get("source_id")+" "+hitDoc.get("id")+" "+"\n");
            }
               ireader.close();
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
          
    }
    public static void main(String[] args)
    {
        StandardAnalyzer analyzer = new StandardAnalyzer();
        Lucene index = new Lucene(analyzer);
    //    index.indexer();
          IGenericService<Suspicious_doc> suspiciousDocService
                    = new GenericServiceImpl<>(Suspicious_doc.class, HibernateUtil.getSessionFactory());
              Map<String, Object> params = new HashMap<String, Object>();
            params.put("SUSPICIOUS_DOC_ID",598);
            List<Suspicious_doc> a = suspiciousDocService.getByWhere("where suspicious_doc_id = :SUSPICIOUS_DOC_ID", params);
                QueryExtractor q = new QueryExtractor(a.get(0));
                List<String> queries = q.extractAllSentence();
                Google go = new Google();
              
                for(String query :queries)
                {
                     index.searcher(index.getDir(),analyzer,query);
                     go.getDataFromGoogle(query);
                     go.getDatafromUrl();
                }
                System.out.println("done");
       
        
    }

}
