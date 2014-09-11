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
    public static FiniteSet tester2 = new FiniteSet(new ArrayList<Integer>());
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
    
    public static FiniteSet removey (FiniteSet abcd, int x) {
        FiniteSet temp = new FiniteSet(new ArrayList<Integer>());
        for(int i = 0; i < abcd.theSet.size(); i++) {
            if(abcd.theSet.get(i) != x) {
                temp.theSet.add(abcd.theSet.get(i));
            }
        }
        return temp;
    }
    
    public static FiniteSet removeDupes (FiniteSet input) {
        FiniteSet answer = new FiniteSet(new ArrayList<Integer>());
        for(int i = 0; i < input.theSet.size(); i++) {
            int element = input.theSet.get(i);
            input = removey(input, element);
            if(FiniteSet_Member(input, element) == false) {
                answer.theSet.add(element);
            }
        }
        return answer;
    }
    
    public static FiniteSet uniony (FiniteSet set1, FiniteSet set2) {
        FiniteSet answer = new FiniteSet(new ArrayList<Integer>());
        for(int i = 0; i < set1.theSet.size(); i++) {
            answer = addy(answer, set1.theSet.get(i));
        }
        for(int j = 0; j < set2.theSet.size(); j++) {
            answer = addy(answer, set2.theSet.get(j));
        }
        return answer;
    }
    
    public static FiniteSet intery(FiniteSet set1, FiniteSet set2) {
        FiniteSet answer = new FiniteSet(new ArrayList<Integer>());
        for (int i = 0; i < set1.theSet.size(); i++) {
            if (FiniteSet_Member(set2, set1.theSet.get(i))) {
                answer = addy(answer, set1.theSet.get(i));
            }
        }
        return answer;
    }
    
    public static FiniteSet diffy (FiniteSet set1, FiniteSet set2) {
        FiniteSet answer = new FiniteSet(new ArrayList<Integer>());
        for( int i = 0; i < set1.theSet.size(); i++) {
            int tempvar = set1.theSet.get(i);
            if(FiniteSet_Member(set2, tempvar) == false) {
                answer = addy(answer, tempvar);
            }
        }
        return answer;
    }
    
    public static boolean equaly (FiniteSet set1, FiniteSet set2) {
        FiniteSet diffy1 = diffy(set1, set2);
        FiniteSet diffy2 = diffy(set2, set1);
        if((diffy1.isEmptyHuh()) && diffy2.isEmptyHuh()) {
            return true;
        } else {
            return false;
        }
    }
    
    public static boolean subsety (FiniteSet set1, FiniteSet set2) {
        int status = 0;
        for(int i = 0; i < set2.theSet.size(); i++) {
            int tempVar = set2.theSet.get(i);
            if(!FiniteSet_Member(set1, tempVar)) {
                status = 1;
            }
        }
        if (status == 0) {
            return true;
        } else {
            return false;
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
        System.out.println(memberVar 
                + " will now be added to the non-empty finite set:");
        tester = addy(tester, memberVar);
        System.out.println(FiniteSetPrint(tester));
        System.out.println(FiniteSet_Member(tester, memberVar) 
                + " should be " + true);
        int varPos2 = (int) Math.floor(Math.random()*tester.theSet.size());
        int memberVar2 = tester.theSet.get(varPos2);
        System.out.println(memberVar2 + " is element number " + varPos2 
                + " the non-empty finite set (starting from index 0).");
        System.out.println("This element will now be removed "
                + "from the non-empty finite set.");
        System.out.println(FiniteSetPrint(removey(tester, memberVar2)));
        FiniteSet dupeSet = new FiniteSet(new ArrayList<Integer>());
        dupeSet.theSet.add(memberVar2);
        dupeSet.theSet.add(memberVar2);
        dupeSet.theSet.add(memberVar2);
        dupeSet.theSet.add(memberVar);
        dupeSet.theSet.add(memberVar);
        dupeSet.theSet.add(memberVar2);
        dupeSet.theSet.add(memberVar);
        System.out.println("Testing for removeDupes:");
        System.out.println("The set with duplicates is " 
                + FiniteSetPrint(dupeSet));
        System.out.println("The set with dublicates removed is " 
                + FiniteSetPrint(removeDupes(dupeSet)));
        tester2 = create_and_populateFiniteSet(5);
        System.out.println(FiniteSetPrint(tester2));
        System.out.println(FiniteSetPrint(uniony(tester, tester2)));
        System.out.println(FiniteSetPrint(intery(tester, tester2)));
        System.out.println(FiniteSetPrint(diffy(tester, tester2)));
        System.out.println(equaly(tester, tester) + " should be " + true);
        System.out.println(equaly(tester2, tester2) + " should be " + true);
        System.out.println(equaly(tester, tester2) + " should be true only "
                + "if " + FiniteSetPrint(tester) + " is the same as "
                + FiniteSetPrint(tester2));
        System.out.println(subsety(tester, tester) + " should be " + true);
        System.out.println(subsety(dupeSet, removeDupes(dupeSet)) 
                + " should be " + true);
        System.out.println(subsety(tester, tester2));
    }
}
