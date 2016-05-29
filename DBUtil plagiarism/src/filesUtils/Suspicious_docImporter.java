/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filesUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import plagiarism.DAOImpl.GenericServiceImpl;
import plagiarism.IDAO.IGenericService;
import plagiarism.util.pojos.HibernateUtil;
import plagiarism.util.pojos.Suspicious_doc;

/**
 *
 * @author Ehab Bshara
 */
public class Suspicious_docImporter implements Importer {
     String path = null;
     IGenericService<Suspicious_doc> suspicious_docService = null ;
     Map<String,String> file = null ;
     public Suspicious_docImporter(String path)
     {
         suspicious_docService = new GenericServiceImpl<>(Suspicious_doc.class, HibernateUtil.getSessionFactory());
         file = new HashMap<>();
         setPath(path);
     }
     public void setPath(String path)
     {
         this.path = path ;
     }
     public String getPath()
     {
         return path ;
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
                file.put("content", text);
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    @Override
    public void save() {
        TestPhraseImporter t = null;
        Suspicious_doc suspicious = new Suspicious_doc((String)file.get("content"), (String)file.get("filename"));
        suspicious_docService.save(suspicious);
//            t = new TestPhraseImporter(suspicious);
//            t.import_();
//            t.save();
    }

    @Override
    public String[] splitter(String content) {
      return content.split(" ");
    }
    
    
}
