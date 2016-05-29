/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filesUtils;

import com.mchange.v2.async.StrandedTaskReporting;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.hibernate.SessionFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import plagiarism.DAOImpl.GenericServiceImpl;
import plagiarism.IDAO.IGenericService;
import plagiarism.util.pojos.Annotation;
import plagiarism.util.pojos.HibernateUtil;
import plagiarism.util.pojos.Source_doc;
import plagiarism.util.pojos.Suspiciuos_doc;
import plagiarism.DAOImpl.GenericServiceImpl;
import plagiarism.IDAO.IGenericService;

/**
 *
 * @author dali
 */
public class AnnotationImporter implements Importer {

    String path;
    IGenericService<Annotation> annotationService;
    List<Annotation> AnnotationFiles = null;

    public AnnotationImporter(String path) {
        this.path = path;
        annotationService = new GenericServiceImpl<>(Annotation.class, HibernateUtil.getSessionFactory());
        AnnotationFiles = new ArrayList<Annotation>();
    }

    @Override
    public void import_() {

        File folder = new File(path);
        String text = null;
        for (File f : folder.listFiles()) {
            if(f.isFile())
                AnnotationFiles.addAll(readXML(f));
           
        }
    }

    public String getPath() {
        return path;
    }

    @Override
    public void save() {
        for(Annotation annotation:AnnotationFiles)
                annotationService.save(annotation);

    }

    @Override
    public String[] splitter(String content) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<Annotation> readXML(File file) {
        List<Annotation> annotations = new ArrayList<>();
        IGenericService<Source_doc> sourceDocService
                = new GenericServiceImpl<>(Source_doc.class, HibernateUtil.getSessionFactory());
        String Obfuscation = "", SourceDocName = "", Type = "";
        long SuspiciuosOffset = 0, SuspiciuosLength = 0, SourceOffset = 0, SourceLength = 0;
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.parse(file);
            NodeList nodeList = document.getElementsByTagName("*");
            String SuspiciuosDocName = null;
            Suspiciuos_doc suspiciuos_doc = null;
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {

                    // do something with the current element
                    if (node.getNodeName().equals("document")) {
                        SuspiciuosDocName = node.getAttributes().item(0).getNodeValue();
                        IGenericService<Suspiciuos_doc> suspiciuosDocService
                                = new GenericServiceImpl<>(Suspiciuos_doc.class, HibernateUtil.getSessionFactory());

                        Map<String, Object> params = new HashMap<String, Object>();
                        params.put("SUSPICIUOS_DOC_NAME", SuspiciuosDocName);
                        List<Suspiciuos_doc> suspiciuos_docs = suspiciuosDocService.getByWhere("where", params);

                        if (!suspiciuos_docs.equals(null)) {
                            suspiciuos_doc = suspiciuos_docs.get(0);
                        } else {
                            System.out.println("the query to get the suspiciuos doc returned a null object");
                        }

                    } else if (node.getNodeName().equals("feature")) {
                        if (suspiciuos_doc == null) // not defined.
                        {
                            throw new Exception("suspiciuos doc is not found in the databse");
                        }
                        NamedNodeMap attributes = node.getAttributes();

                        for (int j = 0; j < node.getAttributes().getLength(); j++) {
                            Node att = attributes.item(j);
                            switch (att.getNodeName()) {
                                case "this_offset":
                                    SuspiciuosOffset = Long.parseLong(att.getNodeValue());
                                    break;
                                case "this_length":
                                    SuspiciuosLength = Long.parseLong(att.getNodeValue());
                                    break;
                                case "source_reference":
                                    SourceDocName = att.getNodeValue();
                                    break;
                                case "source_offset":
                                    SourceOffset = Long.parseLong(att.getNodeValue());
                                    break;
                                case "source_length":
                                    SourceLength = Long.parseLong(att.getNodeValue());
                                    break;
                                case "obfuscation":
                                    Obfuscation = att.getNodeValue();
                                    break;
                                case "type":
                                    Type = att.getNodeValue();
                                    break;

                            }

                        }
                        Map<String, Object> params = new HashMap<String, Object>();
                        params.put("SOURCE_DOC_NAME", SourceDocName);
                        List<Source_doc> source_docs = sourceDocService.getByWhere("where", params);
                        Source_doc source_doc=null;
                        if (!source_docs.equals(null)) {
                            source_doc = source_docs.get(0);
                        } else {
                            System.out.println("the query to get the source doc returned a null object");
                        }
                        Annotation annotation = new Annotation(SourceOffset, SourceLength, SuspiciuosOffset, SuspiciuosLength, Obfuscation, Type, source_doc, suspiciuos_doc);
                        System.out.println(annotation);
                        annotations.add(annotation);
                    }
                }
            }

        } catch (ParserConfigurationException ex) {
            Logger.getLogger(AnnotationImporter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(AnnotationImporter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AnnotationImporter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(AnnotationImporter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return annotations;
    }

}
