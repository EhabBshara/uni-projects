/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package features;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ehab Bshara
 */
public class SkipGram {
    
    private String sentence1 ;
    private String sentence2 ;
    private NGram sentenceGrams ;
    private int grams ; //n 
    private int skip ; // k
    
    public SkipGram(String sen1 , String sen2 , int n , int k)
    {
        this.sentence1 = sen1 ;
        this.sentence2 = sen2 ;
        if(n>3)
        {
         System.out.println("maximum grams is 3 so we consider n = 3 ");
         this.grams = 3;
        }
        else if(n<2)
        {
            System.out.println("minmum grams is 2 so we consider n =2");
            this.grams =2 ;
        }
        else this.grams = n ;
        this.skip = k ;
    }
    private List<String> getSentenceSkipGram(String sentence)
    {
        List<String> result = new ArrayList<>();
        sentenceGrams = new NGram(sentence, grams);
        List<String> ngrams = sentenceGrams.list();
        String[] sentenceWords = splitter(sentence);
         int numOfskipgrams = 0;
        if(grams ==3)
            numOfskipgrams= (((skip+1)*(skip+2)/6)*((3*sentenceWords.length)-(2*skip)-6))
                - ngrams.size();
        if(grams == 2)
            numOfskipgrams = sentenceWords.length-grams ;
        for(String s :ngrams)
            result.add(s);
        int j  ;
        for(int i = skip ; i>0 ; i--)
            for(j=0; j<numOfskipgrams;j++)
            {
                String tempresult = "";
               
                if (grams == 3) {
                    // fist part one gram after skip 2 grams
                    if (j < sentenceWords.length) {
                        tempresult += sentenceWords[j] + " ";
                        if ((j + i + 1 < sentenceWords.length) && (j + i + 2 < sentenceWords.length)) {
                            tempresult += sentenceWords[j + i + 1] + " " + sentenceWords[j + i + 2];
                        }
                        if ((splitter(tempresult).length == grams) && (!result.contains(tempresult))) {
                            result.add(tempresult);
                        }
                        //fist part two grams after skip one gram
                        tempresult = "";
                        if (j + 1 < sentenceWords.length) {
                            tempresult += sentenceWords[j] + " " + sentenceWords[j + 1] + " ";
                        }
                        if (j + i + 2 < sentenceWords.length) {
                            tempresult += sentenceWords[j + i + 2] + " ";
                        }
                        if ((splitter(tempresult).length == grams) && (!result.contains(tempresult))) {
                            result.add(tempresult);
                        }
                        // one skip one skip one 
                        tempresult = "";

                        tempresult += sentenceWords[j] + " ";
                        if ((i / 2) > 0) {
                            int temp = (i/2)+1 ;
                            if (j+temp < sentenceWords.length &&j+(2*temp)< sentenceWords.length) {
                                tempresult += sentenceWords[j+temp] + " " + sentenceWords[j+(2*temp)];
                            }
                        }
                        if ((splitter(tempresult).length == grams) && (!result.contains(tempresult))) {
                            result.add(tempresult);
                        }
                    }
                }
                if(grams==2)
                {
                    tempresult = "";
                    if(j<sentenceWords.length)
                    {
                        tempresult+= sentenceWords[j]+" ";
                        if(j+i+1<sentenceWords.length)
                            tempresult+= sentenceWords[j+i+1];
                    }
                    if(splitter(tempresult).length == grams)
                        result.add(tempresult);
                }
              
                    
            }
        
        return result ;
    }
    public double skipGramFeature()
    {
        double matchCount = 0.0;
        List<String> SkipgramsS1 = getSentenceSkipGram(sentence1);
        List<String> SkipgramsS2 = getSentenceSkipGram(sentence2);
        for(String s1:SkipgramsS1)
            for(String s2: SkipgramsS2)
            {
                if(s1.equals(s2))
                   matchCount+=1.0; 
            } 
        return matchCount/(SkipgramsS1.size()+SkipgramsS2.size());
    }
  
    private String[] splitter(String sentence)
    {
        return sentence.split(" ");
    }
    
    public static void main(String[] args)
    {
        SkipGram skip = new SkipGram("ehab is very good person now", "ehab was very stupid in the past", 2, 4);
        System.out.println(skip.skipGramFeature());
        List<String> res = skip.getSentenceSkipGram("we are the champions my friends and we keep on");
        for(String s : res)
            System.out.println(s);
        System.out.print(res.size());
        
        
    }
}
