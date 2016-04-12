/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filesUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import plagiarism.DAOImpl.GenericServiceImpl;
import plagiarism.IDAO.IGenericService;
import plagiarism.util.pojos.HibernateUtil;
import plagiarism.util.pojos.Phrase;

/**
 *
 * @author Ehab Bshara
 */
public class DatasetIndexer {

    private StandardAnalyzer analyzer;
    private Directory dir;
    IGenericService<Phrase> phraseService;

    public DatasetIndexer(StandardAnalyzer analyzer) {
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
        try {
            IndexWriterConfig config = new IndexWriterConfig(analyzer);
            IndexWriter iwriter = new IndexWriter(dir, config);
            List<Phrase> phrases = phraseService.getAll();
            for (Phrase p : phrases) {
                Document document = new Document();
                id = p.getId();
                filename = p.getFilename();
                content = p.getOriginal();
                document.add(new Field("id", String.valueOf(id), Field.Store.YES, Field.Index.NO));
                document.add(new Field("filename", filename, Field.Store.YES, Field.Index.NO));
                document.add(new Field("content", content, Field.Store.YES, Field.Index.ANALYZED, Field.TermVector.YES));
                iwriter.addDocument(document);
            }
            iwriter.close();
        } catch (IOException ex) {
            Logger.getLogger(DatasetIndexer.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
