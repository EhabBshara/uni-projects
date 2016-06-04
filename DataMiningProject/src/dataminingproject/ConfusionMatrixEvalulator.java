/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataminingproject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Pair;
import jdk.nashorn.internal.runtime.regexp.RegExp;

/**
 *
 * @author dali
 */
public class ConfusionMatrixEvalulator {
    
    public static void main(String [] args)
    {
             try {
            File f=new File("C:\\Users\\dali\\Desktop\\Data Mining\\testing.txt");
             FileReader fr=new FileReader(f);
                BufferedReader reader=new BufferedReader(fr);
                String line;
                int i=0;
                List<Float>output=new ArrayList<>();
                
                while ((line=reader.readLine())!=null){
                    String []s=line.split(" ++");
                    int res=0;
                    int sum=0;
                    for(int j=0;j<s.length;j++)
                    {
                        System.out.println(s[j]+" "+j);
                        res+=Math.abs(j-i)*Integer.parseInt(s[j]);
                        sum+=Integer.parseInt(s[j]);
                    }
                    output.add(((float)res)/((float)sum));
                    i++;
                }
                 System.out.println(output);
        
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ConfusionMatrixEvalulator.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ConfusionMatrixEvalulator.class.getName()).log(Level.SEVERE, null, ex);
            }

    }

}
