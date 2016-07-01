/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filesUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import plagiarism.DAOImpl.GenericServiceImpl;
import plagiarism.IDAO.IGenericService;
import plagiarism.util.pojos.HibernateUtil;
import plagiarism.util.pojos.Source_doc;

/**
 *
 * @author Ehab Bshara
 */
public class Source_docImporter implements Importer {

    String path;
    IGenericService<Source_doc> source_docService;
    List<Map<String, String>> files = null;

    public Source_docImporter(String path) {
        this.path = path;
        source_docService = new GenericServiceImpl<>(Source_doc.class, HibernateUtil.getSessionFactory());
        files = new ArrayList<Map<String, String>>();
    }

    public String getPath() {
        return path;
    }

    @Override
    public void import_() {

        File folder = new File(path);
        String text = null;
        for (File f : folder.listFiles()) {
            try {
                Map<String, String> file = new HashMap<>();
                FileInputStream fin = new FileInputStream(f);
                byte[] fileBytes = new byte[(int) f.length()];
                fin.read(fileBytes);
                text = new String(fileBytes);
                file.put("filename", f.getName());
                file.put("content", text);
                files.add(file);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void save() {
        PhraseImporter p = null;
        List<Source_doc> sources = new ArrayList<>();
        for (Map file : files) {
            sources.add(new Source_doc((String) file.get("content"), (String) file.get("filename")));
        }
        source_docService.bulkSave(sources);
    }

    @Override
    public String[] splitter(String content) {
        return content.split(" ");
    }

}
