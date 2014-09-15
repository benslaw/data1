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
            if (thing == this.here) {
                return this.lefty.union(this.righty);
            } else if (thing < this.here) {
                return new notEmpty(this.here, this.lefty.remove(thing), 
                        this.righty);
            } else {
                return new notEmpty(this.here, this.lefty, 
                        this.righty.remove(thing));
            }
        }
        
        public BST union(BST set) {
            return this.lefty.union(set.union(this.righty).add(this.here));
        }
        
        public BST inter(BST set) {
            if(set.member(here)) {
                
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
    
    public static boolean check_union( BST set1, BST set2 ) {
        int elt = randomInt(0,100);
        if ((set1.add(elt)).union(set2).member(elt) &&
                set1.union(set2.add(elt)).member(elt)) {
            return true;
        } else {
            return false;
        }
    }

    public static void main(String[] args) {
        
        BST mt = new Empty();
        BST not_mt5 = new notEmpty(5, (new Empty()), (new Empty()));
        for(int i = 0; i < 10; i++) {
            int lengthy = randomInt(0,10);
            int lengthy2 = randomInt(0,10);
            BST x = randomBST(lengthy);
            BST y = randomBST(lengthy2);
            System.out.println(check_union(x, y));
        }
        
        
    }
    

}

