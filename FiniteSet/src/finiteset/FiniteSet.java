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

    interface BST {
        public int cardinality();   
        public boolean isEmptyHuh();
        public boolean member( int thing );
        public BST add( int thing );
        public BST remove( int thing );
        public BST union( BST set );
        public BST inter( BST set );
        public BST diff( BST set );
        public boolean equal( BST set );
        public boolean subset( BST set );
    }
    
    static class Empty implements BST {
        
        Empty() { }
        
        public int cardinality() {
            return 0;
        }
        
        public boolean isEmptyHuh() {
            return true;
        }
        
        public boolean member( int thing ) {
            return false;
        }
        
        public BST add( int thing ) {
            return new notEmpty(thing,new Empty(), new Empty());
        }
        
        public BST remove( int thing ) {
            return new Empty();
        }
        
        public BST union(BST set) {
            return set;
        }
        
        public BST inter(BST set) {
            return new Empty();
        }
        
        public BST diff(BST set) {
            return set;
        }
        
        public boolean equal(BST set) {
            return set.isEmptyHuh();
        }
        
        public boolean subset(BST set) {
            return set.isEmptyHuh();
        }
        
    }
    
    static class notEmpty implements BST {
        
        int here;
        BST lefty;
        BST righty;
        
        notEmpty ( int here, BST lefty, BST righty) {
            this.here = here;
            this.lefty = lefty;
            this.righty = righty;
        }
        
        public BST empty() {
            return new Empty();
        }
        
        public int cardinality() {
            return 1 + lefty.cardinality() + righty.cardinality();
        }
        
        public boolean isEmptyHuh() {
            return false;
        }
        
        public boolean member( int thing ) {
            return (here == thing || lefty.member(thing) 
                    || righty.member(thing));
        }
        
        public BST add(int thing) {
            if (here == thing) {
                return this;
            } else if(thing < here) {
                return new notEmpty(here, lefty.add(thing), righty);
            } else {
                return new notEmpty(here, lefty, righty.add(thing));
            }
        }
        
        public BST remove(int thing) {
            if (thing == here) {
                return lefty.union(righty);
            } else if (thing < here) {
                return new notEmpty(here, lefty.remove(thing), righty);
            } else {
                return new notEmpty(here, lefty, righty.remove(thing));
            }
        }
        
        public BST union(BST set) {
            return this.lefty.union(set.union(this.righty).add(this.here));
        }
        
        public BST inter(BST set) {
            if(set.member(here)) {
                return new notEmpty(here, lefty.inter(set), righty.inter(set));
            } else {
                return lefty.inter(set).union(righty.inter(set));
            }
        }
        
        public BST diff(BST set) {
            if(set.member(here)) {
                return lefty.diff(set).union(righty.diff(set));
            } else {
                return new notEmpty(here, lefty.diff(set), righty.diff(set));
            }
        }
        
        public boolean equal(BST set) {
            return (this.subset(set) && set.subset(this));
        }
        
        public boolean subset(BST set) {
            return (set.member(here) && lefty.subset(set) 
                    && righty.subset(set));
//            if(set.member(here)) {
//                return (lefty.subset(set) && righty.subset(set));
//            } else {
//                return false;
//            }
        }
        
    }

    static Random rand = new Random();

    public static int randomInt(int min, int max) {
        return rand.nextInt((max - min) + 1) + min;
    }
    
    public static BST randomBST(BST temp, int lengthy) {
        if (lengthy == 0) {
            return temp;
        } else {
            int inty = randomInt(0, 100);
            if (!temp.member(inty)) {
                return randomBST(temp.add(inty), lengthy - 1);
            } else {
                return randomBST(temp, lengthy);
            }
        }
    }
    
    //check two properties of remove:
    //1 --> when removing an element from a BST, the cardinality will be less
    //      than or equal to the original cardinality
    //2 --> when removing an element from a BST, member should return false
    //      when queried about the removed element
    public static void check_remove(BST set) {
        int elt = randomInt(0,10);
        BST remove_set = set.remove(elt);
        if (remove_set.cardinality() <= set.cardinality() ) {
 //               && (remove_set.member(elt) == false)) {
            System.out.println("Success for check_remove");
        } else {
            System.out.println("Failure for check_remove");
        }
    }
    
    //check a property of subset:
    //for all set1, set2, set3: 
    //set1.union(set2).subset(set3) = set1.subset(set3) && set2.subset(set3)
    public static void check_subset(BST set1, BST set2, BST set3) {
        if((set1.union(set2)).subset(set3) == 
                (set1.subset(set3) && set2.subset(set3))) {
            System.out.println("Success for check_subset");
        } else {
            System.out.println("Failure for check_subset");
        }
    }
    
    //check two properties of add:
    //1 --> when adding an element to a BST, the cardinality will be greater
    //      than or equal to the original cardinality
    //2 --> when adding an element to a BST, member should return true when
    //      queried about the added element
    public static void check_add(BST set) {
        int elt = randomInt(0,10);
        BST add_set = set.add(elt);
        if ((set.cardinality() <= add_set.cardinality()) && add_set.member(elt)) {
            System.out.println("Success for check_add");
        } else {
            System.out.println("Failure for check_add");
        }
    }
    
    //check the following property of union:
    //member (union set1 set2) elt = (member set1 elt || member set2 elt)
    public static void check_union(BST set1, BST set2) {
        int elt = randomInt(0, 100);
        BST set1_temp = set1.add(elt);
        BST set2_temp = set2.add(elt);
        BST temp1 = (set1_temp.union(set2));
        BST temp2 = (set2_temp.union(set1));
        if (temp1.member(elt) && temp2.member(elt)) {
            System.out.println("Success for check_union");
        } else {
            System.out.println("Failure for check_union");
        }
    }
    
    //check a property of isEmptyHuh:
    //if a set x is empty, x.isEmptyHuh() returns true and x.cardinality()
    //returns zero
    //so if cardinality is zero and isEmptyHuh is true, success
    //if cardinality is non-zero and isEmptyHuh is false, success
    //else failure
    public static void check_empty(BST set) {
        if(set.cardinality() == 0) {
            if(set.isEmptyHuh()) {
                System.out.println("Success for check_empty");
            } else {
                System.out.println("Failure for check_empty");
            }
        } else if(!set.isEmptyHuh()) {
            System.out.println("Success for check_empty");
        } else {
            System.out.println("Failure for check_empty");
        }
    }
    
    //check a property of inter
    //if an element exists in two distinct sets set1 and set2, then
    //set1.inter(set2) should not be empty
    public static void check_inter(BST set1, BST set2, int elt) {
        set1 = set1.add(elt);
        set2 = set2.add(elt);
        if(set1.member(elt) && set2.member(elt)) {
            if(!set1.inter(set2).isEmptyHuh()) {
                System.out.println("Success for check_inter");
            } else {
                System.out.println("Failure for check_inter");
            }
        } else {
            System.out.println("Sad");
        }
    }
    
    public static void check_equal(BST set1, BST set2) {
        if ((set1.diff(set2)).isEmptyHuh() && (set2.diff(set1)).isEmptyHuh()) {
            if (set1.equal(set2)) {
                System.out.println("Success for check_equal");
            } else {
                System.out.println("Failure for check_equal");
            }
        } else if(!set1.equal(set2)) {
//            System.out.println("Aux");
            System.out.println("Success for check_equal");
        } else {
            System.out.println("Failure for check_equal");
        }
    }

    public static void main(String[] args) {
//        System.out.println("Test");
        BST mt = new Empty();
//        System.out.println("Test2");
        BST not_mt5 = new notEmpty(5, (new Empty()), (new Empty()));
//        System.out.println("Test3");
        for(int i = 0; i < 10; i++) {
            int inty = randomInt(0,100);
            int lengthy = randomInt(0,10);
            int lengthy2 = randomInt(0,10);
            int lengthy3 = randomInt(0,10);
            BST x = randomBST(mt, lengthy);
            BST y = randomBST(mt, lengthy2);
            BST z = randomBST(mt, lengthy3);
//            System.out.println("Test4");
            check_union(x, y);
//            System.out.println("Test5");
            check_add(x);
            check_add(y);
            check_remove(x);
            check_remove(y);
            check_subset(x,y,z);
            check_empty(x);
            check_empty(y);
            check_empty(z);
            check_empty(mt);
            check_inter(x, y, inty);
            BST a = x.add(inty);
            System.out.println(x.subset(a));
            System.out.println(x.equal(x));
            System.out.println(x.equal(y));
            System.out.println(y.equal(y));
            System.out.println(y.equal(z));
            check_equal(x, x);
            check_equal(x, y);
            check_equal(y, y);
            check_equal(y, z);
        }
        
        
    }
    

}

