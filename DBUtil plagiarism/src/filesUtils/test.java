/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filesUtils;



/**
 *
 * @author Ehab Bshara
 */
public class test {

    public static void main(String[] args) {
        
        FilesImporter filesImporter=new FilesImporter("D:\\plagiarism data\\ExAraPlagDet-10-08-2015\\");
        
        filesImporter.importSources();
        filesImporter.importSuspicious();
        filesImporter.importAnnotations();

    }
    
}
