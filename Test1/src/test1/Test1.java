/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test1;
import java.util.*;


/**
 *
 * @author Ben
 */

//a finite set is either:
    //empty... {}
    //has some finite arbitrary value n integer components { a, b, c, ... n}
public class Test1 {
    
    static Integer[] intermed = new Integer[]{1,2,3,4,5}; 
    static ArrayList<Integer> listy = new ArrayList<Integer>(Arrays.asList(1,2,3,4,5));
    static ArrayList<Integer> mt_listy = new ArrayList<Integer>();
    static FiniteSet mt_set = new FiniteSet(mt_listy);
    static FiniteSet not_mt_set = new FiniteSet(listy);
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        System.out.println("This is a test.");
//        listy.addAll(Arrays.asList(intermed));
//        not_mt_set.FiniteSet(listy);
        System.out.println(mt_set.sety);
        System.out.println(not_mt_set.sety);
        System.out.println(mt_set.cardinality());
        System.out.println(not_mt_set.cardinality());
        System.out.println(mt_set.isEmptyHuh());
        System.out.println(not_mt_set.isEmptyHuh());
        System.out.println(mt_set.member(1));
        System.out.println(not_mt_set.member(1));
        System.out.println(not_mt_set.member(7));
    }
}
