/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package features;

import java.util.List;

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
    public double bleuMeasure()
    {
        double result = 0.0 ; 
        double firstpart = Math.exp((1.0/grams));
        for(int i = 1 ; i<= grams ; i++)
            result+= Math.log10(c(grams));
        return firstpart*result;
    }
    private double c(int n )
    {
        double result = 0.0 ;
        for(int i = 1 ;i<= n ;i++)
        {
            sen1Ngram = new NGram(sentence1, n);
            sen2Ngram = new NGram(sentence2, n);
            result +=count();
        }
        return result ; 
    }
    private double count()
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
        int countg = sen1Ngrams.size();
        double re = result/countg;
        return re ;
    }
    public static void main(String[] args)
    {
       BLEU b = new BLEU("this is my car","this is not your car",1);
       double d = b.bleuMeasure();
       System.out.println(d);
    }
}
