/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Evaluation;

import Utils.Helpers;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;
import machineLearning.Features;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import plagiarism.DAOImpl.GenericServiceImpl;
import plagiarism.IDAO.IGenericService;
import plagiarism.util.pojos.HibernateUtil;
import plagiarism.util.pojos.Suspicious_doc;

/**
 *
 * @author dali
 */
public class XMLResultsWriter {

    public static void main(String[] args) {
        XMLResultsWriter XMLwriter = new XMLResultsWriter();

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        IGenericService<Suspicious_doc> suspiciousDocService
                = new GenericServiceImpl<>(Suspicious_doc.class, HibernateUtil.getSessionFactory());
        List<Suspicious_doc> susps = suspiciousDocService.getAll();

        for (int i = 0; i < susps.size(); i++) {
            Suspicious_doc suspicious_doc = susps.get(i);
            String hsql = "SELECT sus.suspicious_doc_name , t.offset , t.length , s.source_doc_name , p.offset , p.length ,t.phrase ,p.original ,t.stemmed , p.stemmed "
                    + "FROM  Assoc AS assoc "
                    + "INNER JOIN  assoc.phrase as p  "
                    + "INNER JOIN p.source_doc as s "
                    + "INNER JOIN  assoc.testphrase as t "
                    + "INNER JOIN t.suspicious_doc as sus "
                    + "where sus.suspicious_doc_id=" + suspicious_doc.getSuspicious_doc_id();

            session = sessionFactory.getCurrentSession();
            session.beginTransaction();

            List<Object[]> results = (List<Object[]>) session.createQuery(hsql).list();
            session.getTransaction().commit();

            
            String outputXML = "";
            if (results.size() == 0) {
                outputXML = XMLwriter.justFirstRow(suspicious_doc.getSuspicious_doc_name()) + "\n";
                XMLwriter.writeXMLFile(outputXML, "D:\\results2\\" + suspicious_doc.getSuspicious_doc_name().substring(0, suspicious_doc.getSuspicious_doc_name().length() - 4) + ".xml");
                continue;
            }
            String XML_name = (String) results.get(0)[0];
            outputXML = XMLwriter.firstRow(XML_name) + "\n";

            for (int j = 0; j < results.size(); j++) {
                int susoffset = (int) results.get(j)[1];
                int suslength = (int) results.get(j)[2];
                String sourcename = (String) results.get(j)[3];
                int sourceoffset = (int) results.get(j)[4];
                int sourcelength = (int) results.get(j)[5];
                String susp=(String)results.get(j)[6];
                String sour=(String)results.get(j)[7];
                String susStemmed=(String)results.get(j)[8];
                String sourStemmed=(String)results.get(j)[9];
                
                if(susStemmed.split(" ").length<3||sourStemmed.split(" ").length<3)
                    continue;
                do{
                    if(!susp.startsWith(" "))
                        break;
                    susp=susp.substring(1, susp.length());
                    susoffset++;
                }while(true);
                do{
                    if(!susp.endsWith(" ") &&!susp.endsWith("\n") &&!susp.endsWith(".") &&!susp.endsWith("?") &&!susp.endsWith("!"))
                        break;
                    susp=susp.substring(0, susp.length()-1);
                    suslength--;
                }while(true);
                
                
                do{
                    if(!sour.startsWith(" "))
                        break;
                    sour=sour.substring(1, sour.length());
                    sourceoffset++;
                }while(true);
                 do{
                    if(!sour.endsWith(" ") &&!sour.endsWith("\n") &&!sour.endsWith(".") &&!sour.endsWith("?") &&!sour.endsWith("!"))
                        break;
                    sour=sour.substring(0, sour.length()-1);
                    sourcelength--;
                }while(true);
                
               
                
                
                
                outputXML += XMLwriter.featureExtracter(susoffset, suslength, sourcename, sourceoffset, sourcelength);
            }

            outputXML += "</document> ";
            XMLwriter.writeXMLFile(outputXML, "D:\\results2\\" + XML_name.substring(0, XML_name.length() - 4) + ".xml");
        }
        System.out.println("done");
    }

    String firstRow(String name) {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + "<document reference=\"" + name + "\">";
    }

    String justFirstRow(String name) {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + "<document reference=\"" + name + "\"/>";
    }

    String featureExtracter(int this_offset, int this_length, String source_reference, int source_offset, int source_length) {
        return "<feature name=\"detected-plagiarism\" this_offset=\"" + this_offset + "\" this_length=\"" + this_length + "\" source_reference=\"" + source_reference + "\" source_offset=\"" + source_offset + "\"   source_length=\"" + source_length + "\"  />\n";
    }

    public void writeXMLFile(String text, String path) {
        BufferedWriter writer = null;
        try {
            //create a temporary file
            File logFile = new File(path);
            // This will output the full path where the file will be written to...
            System.out.println(logFile.getCanonicalPath());
            writer = new BufferedWriter(new FileWriter(logFile));
            writer.write(text);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // Close the writer regardless of what happens...
                writer.close();
            } catch (Exception e) {
            }
        }
    }
    
}
