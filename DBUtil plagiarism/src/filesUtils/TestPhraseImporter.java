/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filesUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import plagiarism.DAOImpl.GenericServiceImpl;
import plagiarism.IDAO.IGenericService;
import plagiarism.util.pojos.HibernateUtil;
import plagiarism.util.pojos.TestPhrase;

/**
 *
 * @author Ehab Bshara
 */
public class TestPhraseImporter implements Importer{    

    List<Map <String,String>> files = null ;
    String path = null ;
    IGenericService<TestPhrase> testphraseService ;
    public TestPhraseImporter()
    {
         testphraseService = new GenericServiceImpl<>(TestPhrase.class, HibernateUtil.getSessionFactory());
    }
    public TestPhraseImporter(String path)
    {
        setPath(path);
        testphraseService = new GenericServiceImpl<>(TestPhrase.class, HibernateUtil.getSessionFactory());
    }

    public List<Map<String, String>> getFiles() {
        return files;
    }

    public void setFiles(List<Map<String, String>> files) {
        this.files = files;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
    
    @Override
    public void import_() {
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
    public void save() {
       TestPhrase tp = null;
        for (Map file : files) {
            String[] phrases = splitter((String) file.get("content"));
            for (String phrase : phrases) {
                tp = new TestPhrase(path, (String) file.get("filename"), phrase);
                testphraseService.save(tp);
            }

        }
    }

    @Override
    public String[] splitter(String content) {
        //TODO do your magic dali 
        return content.split("\\.|\\?|!");
    }
}
