/*
 * HeapTest.java
 *
 * Created on October 15, 2002, 6:32 AM
 */
 // PLEASE REPORT ANY BUGS TO sbrinker@vtc.edu
 // CIS3050 WORKSHEET 4 TEST APP
/**
 *
 * @author  Stan Brinkerhoff
 */

// YOU WILL NEED TO ALTER YOUR FUNCTIONS FOR THIS TEST TO WORK:
// build NEEDS TO BE PUBLIC.
// insert NEEDS TO BE PUBLIC.
// extractMax NEEDS TO BE PUBLIC.

// public static void build(List passedList)
// public static Object extractMax(List passedList) throws Exception
// public static void insert(List passedList, Object toInsert)

/*
 * This utility is designed to test a users Heap.class file.  The conditions
 * that should be implemented here are:
 *
 * BuildHeap:
 * * Incremental tests (fully backwards) 1 - 10000
 * * Reverse Incremental tests (fully sorted) 1 - 10000
 * * Double/Triple/Fully identical lists 1 - 10000
 * * Random lists 1 - 10000
 *
 * extractMax:
 * * Removes max item
 * * Removes top level item from heap and rebuilds properly.
 *
 * insert:
 * * Inserts object correctly
 */
package edu.vtc.spica;

import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;


public class HeapTest {
    
    /** Creates a new instance of heapTest */
    public static void main(String[] argv){
        System.out.print("Testing Incremental (0..100) ----> ");
        incrementalTest();
        System.out.println("OK!");
        System.out.flush();
        System.out.print("Testing Reverse Incremental (100..0) ----> ");
        reverseIncrementalTest();
        System.out.println("OK!");
        System.out.flush();
        System.out.print("Testing Random Test (2..10..3..x) ----> ");
        randomTest();
        System.out.println("OK!");
        System.out.flush();
        
        /* you need exception handling to use this
        System.out.print("Testing extractMax ----> ");
        try {
            extractMaxTest();
        } 
        catch (Exception e){
            System.out.println("Exception!");
        }
        System.out.println("OK!");
        System.out.flush();
        System.out.print("Testing extractMax (on small lists) ----> ");
        try {
            extractMaxLimitTest();
        }
        catch (Exception e) {
            System.out.println("Exception!");
        }
         */
        System.out.print("Testing insertion (simple) ---->");
        insertionTest();
        System.out.println("OK!");
        System.out.flush();
        
        System.out.println("------- Visual Tests (You get to play too) --------");
        List tempList = new ArrayList();
        tempList = buildRandomList(10);
        System.out.println("Original: " + tempList + " Is it a heap (false expected)-> " +isValidHeap(tempList));
        Heap.build(tempList);
        System.out.println("Built: " +tempList + " Is it a heap (true expected) -> "+isValidHeap(tempList));
        Heap.sort(tempList);
        
        System.out.println("\n\n\nThis next line should have the lowest value on the left, and the highest value on the right");
        System.out.println("For example: 0, 5, 10, 20, 100");
        System.out.println("Sorted: " +tempList + " Is it a heap (false expected) -> "+isValidHeap(tempList));
        
    }
    
    private static void insertionTest()
    {
        List tempList = new ArrayList();
        
        Heap.insert(tempList, 3);
        Heap.insert(tempList, 5);
        Heap.insert(tempList, 8);
        Object alpha = 8;
        Object beta  = 5;
        Object c     = 3;
        
        if (tempList.size() != 3){
            System.out.println("Insertion size not as expected.  Expected 3, returned " +tempList.size());
            System.exit(1);
        }
        
        if (!(alpha.equals(tempList.get(0))) &&
             (beta.equals (tempList.get(1))) &&
             (c.equals    (tempList.get(2)))){
            System.out.println("Inserting 3,5,8 into a blank array returned: " + tempList);
            System.exit(1);
        }
        

    }
    private static void extractMaxLimitTest() throws Exception
    {
        List tempList = new ArrayList();
        Object output;
        for (int count = 4; count > 1; count--){
            tempList = buildReverseIncrementalList(count);
            
            List tempList2 = new ArrayList (tempList);
            try{
                output = Heap.extractMax(tempList);
            }
            catch(Exception e){
                throw(e);
            }
            
            if (!isValidHeap(tempList)){
                System.out.println("Error During extractMax tests.");
                System.out.println("Passed: " + tempList2);
                System.out.println("Returned: " + tempList);
                System.exit(1);
            }
            if (!(output.equals(tempList2.get(0)))){
                System.out.println("Error During extractMax tests.");
                System.out.println("Passed: " + tempList2);
                System.out.println("Returned: " + tempList);
                System.out.println("Expected a max return of: " + tempList2.get(0));
                System.out.println("Returned a max of: " + output);
                System.exit(1);
            }
        }
        List tempList3 = new ArrayList();
        tempList3.add(10);
        try{
            output = Heap.extractMax(tempList3);
        }
        catch (Exception e){
            System.out.println( "Caught Exception where I shouldnt have, YOU FAIL." + e );
            throw new Exception(e);
        }
        
        
        if (!(tempList3.size() == 0)){
            System.out.println("Error During extractMax tests.. expected a 0 size array, got " + tempList3.size());
            System.exit(1);
        }
        
        if (!(output.equals(10))){
            System.out.println("Error During extractMax tests from 1 entry array.. did not get expected output");
            System.exit(1);
        }
        List tempList4 = new ArrayList();
        int error = 0;
        try{
            output = Heap.extractMax(tempList4);
        }
        catch (Exception e){
            System.out.println("Ok!! (Exceptions Present)");
            error = 1;
        }
        if (error == 0) {
            System.out.println("I should have seen an exception here!!!  You FAIL.");
        }
            
           
    }
    
    private static void extractMaxTest() throws Exception
    {
        List tempList = new ArrayList();
        Object output;
        for (int count = 10; count < 100; count += 5){
            tempList = buildReverseIncrementalList(count);
            List tempList2 = new ArrayList (tempList);
            try{
                output = Heap.extractMax(tempList);
            }
            catch (Exception e){
                System.out.println( "Caught Exception" + e );
                throw new Exception(e);
            }
            if (!isValidHeap(tempList)){
                System.out.println("Error During extractMax tests.");
                System.out.println("Passed: " + tempList2);
                System.out.println("Returned: " + tempList);
                System.exit(1);
            }
            if (!(output.equals(tempList2.get(0)))){
                System.out.println("Error During extractMax tests.");
                System.out.println("Passed: " + tempList2);
                System.out.println("Returned: " + tempList);
                System.out.println("Expected a max return of: " + tempList2.get(0));
                System.out.println("Returned a max of: " + output);
                System.exit(1);
            }
        }
    }
            
    private static void incrementalTest()
    {
        List tempList = new ArrayList();
        
        
        for (int count = 1; count < 100; count += 5){
            tempList = buildIncrementalList(count);
            List tempList2 = new ArrayList(tempList);
            Heap.build(tempList);
            if (!isValidHeap(tempList)){
                System.out.println("Error During Incremental Testing Format");
                System.out.println("Passed: " + tempList2);
                System.out.println("Returned: " + tempList);
                System.exit(1);
            }
        }
    }
    private static void reverseIncrementalTest()
    {
        List tempList = new ArrayList();
        for (int count = 1; count < 100; count += 5){
            tempList = buildReverseIncrementalList(count);
            List tempList2 = new ArrayList (tempList);
            Heap.build(tempList);
            if (!isValidHeap(tempList)){
                System.out.println("Error During Reverse Incremental Testing Format");
                System.out.println("Passed: " + tempList2);
                System.out.println("Returned: " + tempList);
                System.exit(1);
            }
        }
    }
    private static void randomTest(){
        List tempList = new ArrayList();
        for (int count = 1; count < 100; count += 5){
            tempList = buildRandomList(count);
            List tempList2 = new ArrayList (tempList);
            Heap.build(tempList);
            if (!isValidHeap(tempList)){
                System.out.println("Error During Random Testing Format");
                System.out.println("Passed: " + tempList2);
                System.out.println("Returned: " + tempList);
                System.exit(1);
            }
        }
    }
    
    private static boolean isValidHeap(List passedList)
    {
        int current, end, child1, child2, bigChild;
        
        for (current = 0; current < passedList.size(); current++){
            child1 = (2*(current)+1);
            child2 = (2*(current)+2);
            
            if (child1 >= passedList.size()){
                return true;
            } else if ( child2 >= passedList.size()){
                bigChild = child1;
            } else {
                // Set bigChild equal to the larger of the two children.
                if (((Comparable)passedList.get(child1)).compareTo ( 
                     (Comparable)passedList.get(child2)) >= 0){
                    bigChild = child1;
                } else {
                    bigChild = child2;
                }
            }
            
            if (((Comparable)passedList.get(current)).compareTo (
                 (Comparable)passedList.get(bigChild)) < 0){
                return false;
            }
        }
        return false;
    }
    
    private static List buildRandomList(int listSize)
    {
        List tempList = new ArrayList();
        Random generator = new Random( System.currentTimeMillis() * listSize );
        
        for (int index = 0; index < listSize; index++){
            tempList.add(new Integer( generator.nextInt(100) ));
        }
        return tempList;
    }
    
    private static List buildIncrementalList(int listSize)
    {
        List tempList = new ArrayList();
        for (int count = 0; count < listSize; count++){
            tempList.add(new Integer(count));
        }
        return tempList;
    }
    private static List buildReverseIncrementalList(int listSize)
    {
        List tempList = new ArrayList();
        for (int count = 0; count < listSize; count++){
            tempList.add(new Integer((listSize -1 )-count));
        }
        return tempList;
    }
}

