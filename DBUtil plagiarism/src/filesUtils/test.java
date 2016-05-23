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
        Suspiciuos_docImporter p = new Suspiciuos_docImporter("D://files//Second.txt");
        p.import_();
        p.save();

    }
}
