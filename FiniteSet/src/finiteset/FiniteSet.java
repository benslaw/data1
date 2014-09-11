/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package finiteset;

import java.util.*;

/**
 *
 * @author Ben
 */
public class FiniteSet {

    ArrayList<Integer> theSet;
    public static FiniteSet tester = new FiniteSet(new ArrayList<Integer>());
    public static FiniteSet mt_tester = new FiniteSet(new ArrayList<Integer>());
    
    FiniteSet (ArrayList<Integer> theSet) {
        this.theSet = theSet;
    }
    
    public static FiniteSet create_and_populateFiniteSet (int x) {
        FiniteSet temp = new FiniteSet(new ArrayList<Integer>());
        for(int i = 0; i < x; i++) {
            int y = (int) (Math.random()*100);
            if (FiniteSet_Member(temp, y)) {
                temp.theSet.add((int) Math.random()*100 + (int) Math.random()*100);
            } else {
                temp.theSet.add(y);
            }
        }
        return temp;
    }
    
    public int cardinality () {
        return theSet.size();
    }
    
    public boolean isEmptyHuh () {
        if(theSet.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }
    
    public static boolean FiniteSet_Member (FiniteSet input, int x) {
        for(int i = 0; i < input.theSet.size(); i++) {
            if(input.theSet.get(i) == x) {
                return true;
            }
        }
        return false;
    }
  
    public static FiniteSet addy (FiniteSet abcd, int x) {
        FiniteSet temp = new FiniteSet(new ArrayList<Integer>());
        for(int i = 0; i < abcd.theSet.size(); i++) {
            temp.theSet.add(abcd.theSet.get(i));
        }
        if(FiniteSet_Member(abcd, x)) {
            return temp;
        } else {
            temp.theSet.add(x);
            return temp;
        }
    }
    
    public static ArrayList<Integer> FiniteSetPrint (FiniteSet abcd) {
        return abcd.theSet;
    }
    
    public static void main(String[] args) {
//        System.out.println("THIS IS A TEST");
        tester = create_and_populateFiniteSet(5);
        System.out.println("The empty finite set is " 
                + FiniteSetPrint(mt_tester));
        System.out.println("The non-empty finite set is " 
                + FiniteSetPrint(tester));
        System.out.println(mt_tester.cardinality() + " should be " + 0);
        System.out.println(tester.cardinality() + " should be " + 5);
        System.out.println(mt_tester.isEmptyHuh() + " should be " + true);
        System.out.println(tester.isEmptyHuh() + " should be " + false);
        System.out.println(FiniteSet_Member(mt_tester, (int) Math.random()*100)
                + " should be " + false);
        int memberVar = (int) (Math.random()*100);
        System.out.println(FiniteSet_Member(tester, memberVar)
                + " should be " + true + " only when " + memberVar 
                + " is in the non-empty finite set.");
    }
}
