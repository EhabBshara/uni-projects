/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package features;

import java.util.ArrayList;
import java.util.List;


  public class NGram {

    private final int n;
    private final String text;

    private final int[] indexes;
    private int index = -1;
    private int found = 0;

    public NGram(String text, int n) {
        this.text = text;
        this.n = n;
        indexes = new int[n];
    }

    private boolean seek() {
        if (index >= text.length()) {
            return false;
        }
        push();
        while(++index < text.length()) {
            if (text.charAt(index) == ' ') {
                found++;
                if (found<n) {
                    push();
                } else {
                    return true;
                }
            }
        }
        return true;
    }

    private void push() {
        for (int i = 0; i < n-1; i++) {
            indexes[i] = indexes[i+1];
        }
        indexes[n-1] = index+1;
    }

    public List<String> list() {
        List<String> ngrams = new ArrayList<String>();
        while (seek()) {
            ngrams.add(get());
        }
        return  ngrams;
    }

    private String get() {
        return text.substring(indexes[0], index);
    }
 
    public static void main(String[] args)
    {
        NGram ngram = new NGram("أنا إيهاب الشرير بحب الأوساخ كتير",3);
        List<String> listOfGrams = ngram.list();
        for(String s :listOfGrams)
            System.out.println(s);
    }
}