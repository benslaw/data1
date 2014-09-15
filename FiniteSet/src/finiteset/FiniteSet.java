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

    interface Listy {
        
        public int cardinality();
        
        public boolean isEmptyHuh();
        
        public boolean member( int thing );
        
        public Listy add( int thing );
        
        public Listy remove( int thing );
        
        public Listy union( Listy set );
        
        public Listy inter( Listy set );
        
    }
    
    static class Empty implements Listy {
        
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
        
        public Listy add( int thing ) {
            return new notEmpty(thing,new Empty());
        }
        
        public Listy remove( int thing ) {
            return new Empty();
        }
        
        public Listy union(Listy set) {
            return set;
        }
        
        public Listy inter(Listy set) {
            return new Empty();
        }
        
    }
    
    static class notEmpty implements Listy {
        
        int element;
        Listy restList;
        
        notEmpty ( int element, Listy restList) {
            this.element = element;
            this.restList = restList;
        }
        
        public int cardinality() {
            return 1 + restList.cardinality();
        }
        
        public boolean isEmptyHuh() {
            return false;
        }
        
        public boolean member( int thing ) {
            return (element == thing || restList.member(thing));
        }
        
        public Listy add(int thing) {
            if (this.member(thing)) {
                return this;
            } else {
                return new notEmpty(thing, this);
            }
        }
        
        public Listy remove(int thing) {
            if (this.element != thing) {
                return new notEmpty(this.element, this.restList.remove(thing));
            } else {
                return this.restList;
            }
        }
        
        public Listy union(Listy set) {
            if(this.isEmptyHuh()) {
                return set;
            } else {
                return this.restList.union(set.add(this.element));
            }
        }
        
        public Listy inter(Listy set) {
            return set;
        }
        
    }

    static Random rand = new Random();

    public static int randomInt(int min, int max) {
        return rand.nextInt((max - min) + 1) + min;
    }
    
    public static Listy randomListy( int lengthy ) {
        if(lengthy == 0) {
            return new Empty();
        } else {
            return new notEmpty(randomInt(0,10),randomListy(lengthy - 1));
        }
    }
    
    public static boolean check_union( Listy set1, Listy set2 ) {
        int elt = randomInt(0,100);
        if ((set1.add(elt)).union(set2).member(elt) &&
                set1.union(set2.add(elt)).member(elt)) {
            return true;
        } else {
            return false;
        }
    }

    public static void main(String[] args) {
        
        Listy mt = new Empty();
        Listy not_mt5 = new notEmpty(5, (new Empty()));
        for(int i = 0; i < 10; i++) {
            int lengthy = randomInt(0,10);
            int lengthy2 = randomInt(0,10);
            Listy x = randomListy(lengthy);
            Listy y = randomListy(lengthy2);
            System.out.println(check_union(x, y));
        }
        
        
    }
    

}

