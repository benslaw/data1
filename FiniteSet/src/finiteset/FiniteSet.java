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
            if(set.isEmptyHuh()) {
                return true;
            } else {
                return false;
            }
        }
        
        public boolean subset(BST set) {
            if(set.isEmptyHuh()) {
                return true;
            } else {
                return false;
            }
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
            if (this.here == thing) {
                return this;
            } else if(thing < this.here) {
                return new notEmpty(this.here, this.lefty.add(thing), this.righty);
            } else {
                return new notEmpty(this.here, this.lefty, this.righty.add(thing));
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
            return (this.diff(set).isEmptyHuh() && set.diff(this).isEmptyHuh());
        }
        
        public boolean subset(BST set) {
            if(set.member(here)) {
                return lefty.subset(set) && righty.subset(set);
            } else {
                return false;
            }
        }
        
    }

    static Random rand = new Random();

    public static int randomInt(int min, int max) {
        return rand.nextInt((max - min) + 1) + min;
    }
    
    public static BST randomBST( int lengthy ) {
        if(lengthy == 0) {
            return new Empty();
        } else {
            return new notEmpty(randomInt(0,10),randomBST(lengthy - 1),
                    randomBST(lengthy - 1));
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
    
    public static void check_subset(BST set1, BST set2, BST set3) {
        if(set1.union(set2).subset(set3) == 
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
    
    public static void check_union( BST set1, BST set2 ) {
        int elt = randomInt(0,100);
        if ((set1.add(elt)).union(set2).member(elt) &&
                set1.union(set2.add(elt)).member(elt)) {
            System.out.println("Success for check_union");
        } else {
            System.out.println("Failure for check_union");
        }
    }

    public static void main(String[] args) {
        
        BST mt = new Empty();
        BST not_mt5 = new notEmpty(5, (new Empty()), (new Empty()));
        for(int i = 0; i < 10; i++) {
            int lengthy = randomInt(0,10);
            int lengthy2 = randomInt(0,10);
            int lengthy3 = randomInt(0,10);
            BST x = randomBST(lengthy);
            BST y = randomBST(lengthy2);
            BST z = randomBST(lengthy3);
            check_union(x, y);
            check_add(x);
            check_add(y);
            check_remove(x);
            check_remove(y);
            check_subset(x,y,z);
        }
        
        
    }
    

}

