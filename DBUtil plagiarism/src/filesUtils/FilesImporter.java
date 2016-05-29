/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filesUtils;

import java.io.File;
import org.hibernate.annotations.Source;

/**
 *
 * @author dali
 */
public class FilesImporter {

    private String path;

    public FilesImporter(String path) {
        this.path = path;
    }

    public void importSources() {
        String sourcePath = "\\source-documents\\";
        Source_docImporter source_docImporter = new Source_docImporter(path + sourcePath);
        source_docImporter.import_();
        source_docImporter.save();
    }

    public void importSuspicious() {
        String suspiciousPath = "\\suspicious-documents\\";
        for (File suspicious : new File(path + suspiciousPath).listFiles()) {
            Suspicious_docImporter suspicious_docImporter = new Suspicious_docImporter(suspicious.getAbsolutePath());
            suspicious_docImporter.import_();
            suspicious_docImporter.save();
        }

    }

    public void importAnnotations() {
        String basePath = "\\plagiarism-annotation\\";
        File baseFolder = new File(path + basePath);
        for (File folder : baseFolder.listFiles()) {
            if (folder.isDirectory()) {
                AnnotationImporter annotationImporter = new AnnotationImporter(folder.getAbsolutePath() + "\\");
                annotationImporter.import_();
                annotationImporter.save();
            }
        }

    }

}
