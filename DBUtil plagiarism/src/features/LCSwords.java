/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package features;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Ehab Bshara
 */
public class LCSwords {

    private String sentence1;
    private String sentence2;
    private String[] sen1 ;
    private String[] sen2 ;
    List<String> commonSubsequence ;
    String[] lcs = null ;

    public LCSwords(String sen1, String sen2) {
        this.sentence1 = sen1;
        this.sentence2 = sen2;
        this.sen1 = sen1.split(" ");
        this.sen2 = sen2.split(" ");
        commonSubsequence  = new ArrayList<>();
        allCS();
    }

    private void allCS() {
       // int m = wordCount(sentence1);
        // int n = wordCount(sentence2);
        int m = sen1.length;
        int n = sen2.length;
        String result = "";
        int[][] opt = new int[m + 1][n + 1];
        int i = 0, j = 0;
        while (i < m && j < n) {
            if (sen1[i].equals( sen2[j])) {
                result+=(sen1[i]+" ");
                
                i++;
                j++;
            } else if (opt[i + 1][j] >= opt[i][j + 1]) {
                j++;
                if(!result.equals(""))
                {
                commonSubsequence.add(result);
                result = "";
                }
            } else {
                i++;
               if(!result.equals(""))
                {
                commonSubsequence.add(result);
                result = "";
                }
            }
            if(j>=n)
            {
               j = i+1;
               i++;
               if(!result.equals(""))
                {
                commonSubsequence.add(result);
                result = "";
                }
            }
        }

    }

    public double lcsFeature() {
        return lcs_length() / shortestSentenceLength();
    }
    private double lcs_length()
    {
        int max = 0 ;
        for(String s :commonSubsequence)
        {
            String[]split = s.split(" ");
            if(split.length> max){
                max = split.length;
                lcs = split;
            }
        }
        return (double)lcs.length ;
    }
    private double shortestSentenceLength() {
        if (sen1.length <= sen2.length) {
            return sen1.length;
        } else {
            return sen1.length;
        }
    }

    public List<String> getAllCs() {
        return commonSubsequence;
    }
    public String[] getLcs()
    {
        return lcs ;
    }

    public static void main(String[] args) {
        LCSwords l = new LCSwords("إيهاب الشرير", "انا إيهاب الشرير");
        l.allCS();
        System.out.println(l.lcsFeature()+" "+Arrays.toString(l.getLcs()));
    }

}
