/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filesUtils;

import java.util.List;
import plagiarism.DAOImpl.GenericServiceImpl;
import plagiarism.IDAO.IGenericService;
import plagiarism.util.pojos.Annotation;
import plagiarism.util.pojos.HibernateUtil;
import plagiarism.util.pojos.Source_doc;
import plagiarism.util.pojos.Suspicious_doc;



/**
 *
 * @author Ehab Bshara
 */
public class test {

    public static void main(String[] args) {
        
//        FilesImporter filesImporter=new FilesImporter("D:\\plagiarism stuff\\ExAraPlagDet-10-08-2015");
//        
//        filesImporter.importSources();
//        filesImporter.importSuspicious();
//        filesImporter.importAnnotations();
         IGenericService<Source_doc> sourceService
                        = new GenericServiceImpl<>(Source_doc.class, HibernateUtil.getSessionFactory());
         IGenericService<Suspicious_doc> SuspiciousService
                        = new GenericServiceImpl<>(Suspicious_doc.class, HibernateUtil.getSessionFactory());
         IGenericService<Annotation> AnnotationService
                        = new GenericServiceImpl<>(Annotation.class, HibernateUtil.getSessionFactory());
        List<Source_doc> sources =  sourceService.getAll();
        List<Suspicious_doc> suspiciouses = SuspiciousService.getAll();
        for(Source_doc source : sources)
        {
            PhraseImporter phrase = new PhraseImporter(source);
            phrase.import_();
            phrase.save();
        }
        for(Suspicious_doc suspicious : suspiciouses)
        {
            TestPhraseImporter testPhrase = new TestPhraseImporter(suspicious);
            testPhrase.import_();
            testPhrase.save();
        }
//        List<Annotation> annotations = AnnotationService.getAll();
//        Annotation a=annotations.get(13);
//        System.out.println(a.getSource_offset()+" \\ "+ a.getSource_length()+" \\ "+a.getSource_doc().getSource_doc_text().substring((int)a.getSource_offset(), (int)a.getSource_offset()+(int)a.getSource_length()));
//        System.out.println(a.getSuspicious_offset()+" \\ "+ a.getSuspicious_length()+" \\ "+a.getSuspicious_doc().getSuspicious_doc_text().substring((int)a.getSuspicious_offset(), (int)a.getSuspicious_offset()+(int)a.getSuspicious_length()));
//        
//        TestPhraseImporter testPhraseImporter=new TestPhraseImporter(a.getSuspicious_doc());
//        testPhraseImporter.import_();
//        testPhraseImporter.save();
        System.out.println("done");

    }
    
}
