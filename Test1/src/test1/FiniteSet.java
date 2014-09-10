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
public class FiniteSet extends FiniteSetFxns {
    
    ArrayList<Integer> sety;
    
    public FiniteSet () {
        this.sety = null;
    }
    
    public FiniteSet(ArrayList<Integer> input) {
        this.sety = input;
    } 
//}
    
    //    ArrayList<Integer> theSet = new ArrayList<Integer>();
    
//    public ArrayList FiniteSet () {
//        return theSet;
//    }
    
//    public ArrayList FiniteSet_build (ArrayList<Integer> input) {
//        for (int i=0; i < input.size(); i++) {
//            sety.add(input.get(i));
//        }
//        return sety;
//    }
    
//class FiniteSetFxns {
    
//    FiniteSet theSet = new FiniteSet(null);
//    
//    public int cardinality () {
//        return theSet.sety.size();
//    }
//    
//    public boolean isEmptyHuh () {
//        return theSet.sety.isEmpty();
//    }
//    
//    public boolean member (int x) {
//        for(int i=0; i < theSet.sety.size(); i++) {
//            if(theSet.sety.get(i).equals(x)) {
//                return true;
//            }
//        }
//        return false;
//    }
//    
////    public ArrayList add (int x) {
////        mySet.theSet.add(x);
////        return mySet.theSet;
////    }
//    
//    public FiniteSet add (int x) {
//        return theSet;
//    }
//    
}

