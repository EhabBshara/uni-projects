/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package features;

import java.util.List;
import javafx.util.Pair;

/**
 *
 * @author Ehab Bshara
 */
public class BLEU {
    
    private String sentence1 ;
    private String sentence2 ;
    private NGram sen1Ngram ;
    private NGram sen2Ngram;
    private int grams ;
    public BLEU (String sen1 , String sen2,int grams )
    {
        this.sentence1 = sen1 ;
        this.sentence2 = sen2 ;
        this.grams = grams ;
    }
    public Pair<Double,Double> bleuMeasure()
    {
        double precision = 0.0 ;
        double recall  = 0.0 ;
        double firstpart = Math.exp((1.0/grams));
        for(int i = 1 ; i<= grams ; i++){
            Pair<Double,Double> res = c(grams);
            precision+= Math.log10(res.getKey());
            recall += Math.log10(res.getValue());
        }
        return new Pair<>(firstpart*precision,firstpart*recall);
    }
    private Pair<Double,Double> c(int n )
    {
        double precision = 0.0 ;
        double recall = 0.0 ;
        for(int i = 1 ;i<= n ;i++)
        {
            sen1Ngram = new NGram(sentence1, n);
            sen2Ngram = new NGram(sentence2, n);
            Pair<Double,Double> res = count();
            precision+= res.getKey();
            recall += res.getValue();
        }
        return new Pair<>(precision,recall) ; 
    }
    private Pair<Double,Double> count()
    {  
        double result = 0.0 ;
        List<String> sen1Ngrams = sen1Ngram.list();
        List<String> sen2Ngrams = sen2Ngram.list();
        for(String s1 : sen1Ngrams)
            for(String s2: sen2Ngrams)
            {
               if(s1.equals(s2))
                   result+= 1.0;
            }
       // return result/sen1Ngram .list().size() ;
        int countSentence1 = sen1Ngrams.size();
        int countSentence2 = sen2Ngrams.size();
        double precsion = result/countSentence1;
        double recall = result/countSentence2;
        return (new Pair<>(precsion,recall));
    }
    public static void main(String[] args)
    {
      
       BLEU precision  = new BLEU("this is my car","this is not your car",1);
       BLEU recall = new BLEU("this is not your car","this is my car",1);
       
       System.out.println(precision.bleuMeasure());
    }
}
