/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arabicTools;

import java.util.Arrays;
import java.util.Scanner;

/**
 *
 * @author dali
 */
public class StemmerTester {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in, "UTF-8");
        String word = "";
        Stem stem = new Stem();
        ArabicStemmerDefault stemmer = new ArabicStemmerDefault();
        while (!(word = scanner.nextLine()).equals("\\n")) {
            
            String[] ss=word.split("\\.");
            System.out.println(Arrays.asList(ss));
            
//            System.out.println(stem.StemWord(word));
//
//            System.out.println(stemmer.stemWord(word));
//            System.out.println(stemmer.stemWord(stem.StemWord(word)));
//            System.out.println("=====================================");
//        
        }

    }

}
