package finiteset;

import java.util.*;

public class FiniteSet {

    interface BST {

        public int cardinality();

        public boolean isEmptyHuh();

        public boolean member(int thing);

        public BST add(int thing);

        public BST remove(int thing);

        public BST union(BST set);

        public BST inter(BST set);

        public BST diff(BST set);

        public boolean equal(BST set);

        public boolean subset(BST set);
    }

    static class Empty implements BST {

        //Empty constructor method
        Empty() {
        }
        
        //empty() --> called independently and takes no inputs
        //            returns an empty set
        public BST empty() {
            return new Empty();
        }

        //cardinality() --> called on a finite set and takes no inputs
        //                  returns the size of the finite set
        public int cardinality() {
            return 0;
        }

        //isEmptyHuh() --> called on a finite set, takes no inputs
        //                 returns a bool signifying whether a finite set
        //                 is empty
        public boolean isEmptyHuh() {
            return true;
        }

        //member --> called on a finite set, takes an integer as input
        //           returns a bool signifying whether the finite set containes 
        //           that integer
        public boolean member(int thing) {
            return false;
        }

        //add --> called on a finite set and takes an integer as input 
        //        returns the finite set after that integer is added
        public BST add(int thing) {
            return new notEmpty(thing, new Empty(), new Empty());
        }

        //remove --> called on a finite set, takes an integer as input
        //           returns the finite set with the input integer removed
        public BST remove(int thing) {
            return new Empty();
        }

        //union --> called on a finite set(1), takes another finite set(2) as input
        //          returns a finite set containing all elements in (1) and (2)
        public BST union(BST set) {
            return set;
        }

        //inter --> called on a finite set(1), takes another finite set(2) as input
        //          returns the intersection of (1) and (2) (or all elements
        //          contained in both sets
        public BST inter(BST set) {
            return new Empty();
        }

        //diff --> called on a finite set(1), takes another finite set(2) as input
        //         returns all a finite set containing all elements in (2) that
        //         are not in (1)
        public BST diff(BST set) {
            return set;
        }

        //equal --> called on a finite set(1), takes another finite set(2) as input
        //          returns a boolean signifying whether (1) and (2) contain 
        //          exactly the same elements
        public boolean equal(BST set) {
            return set.isEmptyHuh();
        }

        //subset --> called on a finite set(1), takes another finite set(2) as input
        //           returns a boolean signifying whether all elements in (1)
        //           are also contained in (2) (but (2) may contain other elements
        //           that are not contained in (1))
        public boolean subset(BST set) {
            return true;
        }
    }

    static class notEmpty implements BST {

        int here;
        BST lefty;
        BST righty;

        //notEmpty constructor method
        notEmpty(int here, BST lefty, BST righty) {
            this.here = here;
            this.lefty = lefty;
            this.righty = righty;
        }

        //empty() --> called independently and takes no inputs
        //            returns an empty set
        public BST empty() {
            return new Empty();
        }

        //cardinality() --> called on a finite set and takes no inputs
        //                  returns the size of the finite set
        public int cardinality() {
            return 1 + lefty.cardinality() + righty.cardinality();
        }

        //isEmptyHuh() --> called on a finite set, takes no inputs
        //                 returns a bool signifying whether a finite set
        //                 is empty
        public boolean isEmptyHuh() {
            return false;
        }

        //member --> called on a finite set, takes an integer as input
        //           returns a bool signifying whether the finite set containes 
        //           that integer
        public boolean member(int thing) {
            return (here == thing || lefty.member(thing)
                    || righty.member(thing));
        }

        //add --> called on a finite set and takes an integer as input 
        //        returns the finite set after that integer is added
        public BST add(int thing) {
            if (here == thing) {
                return this;
            } else if (thing < here) {
                return new notEmpty(here, lefty.add(thing), righty);
            } else {
                return new notEmpty(here, lefty, righty.add(thing));
            }
        }

        //remove --> called on a finite set, takes an integer as input
        //           returns the finite set with the input integer removed
        public BST remove(int thing) {
            if (thing == here) {
                return lefty.union(righty);
            } else if (thing < here) {
                return new notEmpty(here, lefty.remove(thing), righty);
            } else {
                return new notEmpty(here, lefty, righty.remove(thing));
            }
        }

        //union --> called on a finite set(1), takes another finite set(2) as input
        //          returns a finite set containing all elements in (1) and (2)
        public BST union(BST set) {
            return this.lefty.union(set.union(this.righty).add(this.here));
        }

        //inter --> called on a finite set(1), takes another finite set(2) as input
        //          returns the intersection of (1) and (2) (or all elements
        //          contained in both sets
        public BST inter(BST set) {
            if (set.member(here)) {
                return new notEmpty(here, lefty.inter(set), righty.inter(set));
            } else {
                return lefty.inter(set).union(righty.inter(set));
            }
        }

        //diff --> called on a finite set(1), takes another finite set(2) as input
        //         returns all a finite set containing all elements in (2) that
        //         are not in (1)
        public BST diff(BST set) {
            if (set.member(here)) {
                return lefty.union(righty).diff(set.remove(here));
            } else {
                return new notEmpty(here, lefty.diff(set), righty.diff(set));
            }
        }

        //equal --> called on a finite set(1), takes another finite set(2) as input
        //          returns a boolean signifying whether (1) and (2) contain 
        //          exactly the same elements
        public boolean equal(BST set) {
            return (this.subset(set) && set.subset(this));
        }

        //subset --> called on a finite set(1), takes another finite set(2) as input
        //           returns a boolean signifying whether all elements in (1)
        //           are also contained in (2) (but (2) may contain other elements
        //           that are not contained in (1))
        public boolean subset(BST set) {
            return (set.member(here) && lefty.subset(set)
                    && righty.subset(set));
        }
    }
    
    //used in random finite set generation
    static Random rand = new Random();

    //random integer function used for testing
    public static int randomInt(int min, int max) {
        return rand.nextInt((max - min) + 1) + min;
    }

    //randomBST --> takes an empty finite set temp and an integer lengthy as input
    //              returns a non-empty set of lengthy random elements with 
    //              no repeats (used for testing)
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
        int elt = randomInt(0, 10);
        BST remove_set = set.remove(elt);
        if ((remove_set.cardinality() <= set.cardinality())
                && (remove_set.member(elt) == false)) {
            System.out.println("Success for check_remove");
        } else {
            System.out.println("Failure for check_remove");
        }
    }

    //check a property of subset:
    //for all set1, set2, set3: 
    //set1.union(set2).subset(set3) = set1.subset(set3) && set2.subset(set3)
    public static void check_subset(BST set1, BST set2, BST set3) {
        if ((set1.union(set2)).subset(set3)
                == (set1.subset(set3) && set2.subset(set3))) {
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
        int elt = randomInt(0, 10);
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
        if (set.cardinality() == 0) {
            if (set.isEmptyHuh()) {
                System.out.println("Success for check_empty");
            } else {
                System.out.println("Failure for check_empty");
            }
        } else if (!set.isEmptyHuh()) {
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
        if (set1.member(elt) && set2.member(elt)) {
            if (!set1.inter(set2).isEmptyHuh()) {
                System.out.println("Success for check_inter");
            } else {
                System.out.println("Failure for check_inter");
            }
        } else {
            System.out.println("Sad");
        }
    }

    //check a property of equal:
    //if the difference between two arbitrary sets set1 and set2 is null, and 
    //the difference between set2 and set1 is also null, the sets must be equal
    // --
    //the converse is also true: if the difference between set1 and set2 or
    //set2 and set1 is NOT null, if equal returns false, then it's still working
    //properly (Auxiliary condition)
    public static void check_equal(BST set1, BST set2) {
        if ((set1.diff(set2)).isEmptyHuh() && (set2.diff(set1)).isEmptyHuh()) {
            if (set1.equal(set2)) {
                System.out.println("Success for check_equal");
            } else {
                System.out.println("Failure for check_equal");
            }
        } else if (!set1.equal(set2)) {
            System.out.println("Auxiliary condition");
            System.out.println("Success for check_equal");
        } else {
            System.out.println("Auxiliary condition");
            System.out.println("Failure for check_equal");
        }
    }

    //effectively the converse to check_equal:
    //if two sets are equal, the differences between them should be empty
    //also, if the sets are not equal, and the differences are not empty, then
    //the diff function is still running correctly (Auxiliary condition)
    public static void check_diff(BST set1, BST set2) {
        if (set1.equal(set2)) {
            if (set1.diff(set2).isEmptyHuh() && set2.diff(set1).isEmptyHuh()) {
                System.out.println("Success for check_diff");
            } else {
                System.out.println("Failure for check_diff");
            }
        } else if (!set1.diff(set2).isEmptyHuh()
                || !set2.diff(set1).isEmptyHuh()) {
            System.out.println("Auxiliary condition");
            System.out.println("Success for check_diff");
        } else {
            System.out.println("Auxiliary condition");
            System.out.println("Failure for check_diff");
        }
    }

    //checks a property of member:
    //for an arbitrary integer not contained within a set, the member function
    //should return false when the input set is queried, and true when 
    //set.add(arbitrary integer) is queried
    public static void check_member(BST set) {
        int inty = randomInt(101, 200);
        BST set_add = set.add(inty);
        if (!set.member(inty) && set_add.member(inty)) {
            System.out.println("Success for check_member");
        } else {
            System.out.println("Failure for check_member");
        }
    }

    //check a property of cardinality:
    //beginning with an empty set, if the add function is called an arbitrary
    //integer i number of times, the cardinality of the set should be equal to i
    public static void check_cardinality(int inty) {
        BST temp = new Empty();
        for (int i = 0; i < inty; i++) {
            temp = temp.add(i);
        }
        if (temp.cardinality() == inty) {
            System.out.println("Success for check_cardinality");
        } else {
            System.out.println("Failure for check_cardinality");
        }
    }

    public static void main(String[] args) {
        BST mt = new Empty();
        for (int i = 0; i < 10; i++) {
            int inty = randomInt(0, 100);
            int lengthy = randomInt(0, 10);
            int lengthy2 = randomInt(0, 10);
            int lengthy3 = randomInt(0, 10);
            BST x = randomBST(mt, lengthy);
            BST y = randomBST(mt, lengthy2);
            BST z = randomBST(mt, lengthy3);
            check_union(x, y);
            check_union(y, z);
            check_union(x, x);
            check_union(y, y);
            check_add(x);
            check_add(y);
            check_add(z);
            check_remove(x);
            check_remove(y);
            check_remove(z);
            check_subset(x, y, z);
            check_subset(y, z, x);
            check_subset(z, x, y);
            check_empty(x);
            check_empty(y);
            check_empty(z);
            check_empty(mt);
            check_inter(x, y, inty);
            check_inter(y, z, inty);
            check_inter(x, z, inty);
            check_equal(x, x);
            check_equal(x, y);
            check_equal(y, y);
            check_equal(y, z);
            check_diff(x, x);
            check_diff(x, y);
            check_diff(y, y);
            check_diff(y, z);
            check_member(x);
            check_member(y);
            check_member(z);
            check_cardinality(inty);
            check_cardinality(randomInt(0, 100));
            check_cardinality(randomInt(0, 100));
        }


    }
}
