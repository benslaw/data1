/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test1;

/**
 *
 * @author Ben
 */
public class FiniteSetFxns {
    
//    public interface FiniteSetFxns {
    
    FiniteSet theSet = new FiniteSet();
    
    public int cardinality () {
        return theSet.sety.size();
    }
    
    public boolean isEmptyHuh () {
        return theSet.sety.isEmpty();
    }
    
    public boolean member (int x) {
        for(int i=0; i < theSet.sety.size(); i++) {
            if(theSet.sety.get(i).equals(x)) {
                return true;
            }
        }
        return false;
    }
    
//    public ArrayList add (int x) {
//        mySet.theSet.add(x);
//        return mySet.theSet;
//    }
    
    public FiniteSet add (int x) {
        return theSet;
    }
    
    
}
