/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package features;

import Utils.Helpers;
import arabicTools.Stem;
import com.google.common.collect.Sets;
import java.util.Arrays;
import java.util.HashSet;

/**
 *
 * @author Ehab Bshara
 */
public class Intersection {
    
    private String sentence1 ;
    private String sentence2 ;
    static final HashSet<String> set1 = new HashSet<>();
    static final HashSet<String> set2 = new HashSet<>();
    public Intersection(String sentence1, String sentence2) {
        this.sentence1 = sentence1;
        this.sentence2 = sentence2;
    }
    public double IntersectionScore()
    {
        
        String[] stemedS1 = sentence1.split(" ");
        String[] stemedS2 = sentence2.split(" ");
        set1.addAll(Arrays.asList(stemedS1));
        set2.addAll(Arrays.asList(stemedS2));
        int count = 0 ; 
        count = Sets.intersection(set1, set2).size();
        double dominator = Math.min(set1.size(), set2.size());
        set1.clear();
        set2.clear();
        return (count / dominator);
        
    }

    public String getSentence1() {
        return sentence1;
    }

    public void setSentence1(String sentence1) {
        this.sentence1 = sentence1;
    }

    public String getSentence2() {
        return sentence2;
    }

    public void setSentence2(String sentence2) {
        this.sentence2 = sentence2;
    }
    
}
