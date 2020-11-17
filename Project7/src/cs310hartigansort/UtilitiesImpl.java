package cs310hartigansort;


import java.util.Arrays;
import java.util.Random;
    
    
/**
 * The UtilitiesImpl class houses several utility methods that are utilized by 
 * the Week 7 main program in order to generate input data lists, validate that
 * their input is correct, validate that they were sorted correctly, and finally display
 * the lists as a debugging tool.
 * 
 * @author Matthew Hartigan
 * @version Week 7
 */
public class UtilitiesImpl {
    
    // Constructors
    UtilitiesImpl() {
    }
    
    // Methods
    /**
     * Generate an int [] [] where each element is a list of random int
     * values of length / range specified by the method input parameters.
     * 
     * @param numLists
     * @param numElements
     * @param maxValue
     * @return listOfLists
     */
    public static int [] [] generateLists(int numLists, int numElements, int maxValue) {
        int [] [] listOfLists = new int [numLists] [];
        int nextValue = 0;
        Random random = new Random();    // Create new random number generator
        int i = 0;    // loop counter
        int j = 0;    // nested loop counter
               
        // Populate listOfLists with empty int[] of length numElements
        for (i = 0; i < numLists; ++i) {
            listOfLists[i] = new int[numElements];
        }
        
        // Populate each individual list with identical random values
        for (i = 0; i < numElements; ++i) {
            nextValue = random.nextInt(maxValue);
            
            for (j = 0; j < numLists; ++j) {
                listOfLists[j] [i] = nextValue;
            }
        }
       
        return listOfLists;
    }
    

    /**
     * validateLists
     * Checks to make sure lists are correct length and identical.  Returns boolean
     * with result.
     * 
     * @param listOfLists
     * @param numElements
     * @return
     */
    public static boolean validateLists(int [] [] listOfLists, int numElements) {
        int i = 0;    // loop counter
        boolean listsAreValid = true;

        // Validate lists are correct length
        for (i = 0; i < listOfLists.length; ++i) {
            if (listOfLists[i].length != numElements) {
                listsAreValid = false;
                System.out.println("ERROR: Lists are not the correct length");
            }
        }
        
        // Validate lists are identical
        for (i = 1; i < listOfLists.length; ++i) {
            if (! Arrays.equals(listOfLists[i], (listOfLists[i - 1]))) {
                listsAreValid = false;
                System.out.println("ERROR: Lists are not equal");
            }
        }
        
      return listsAreValid; 
    }
    
    
    /**
     * validateSorted
     * Confirms whether or not the input array is sorted in ascending order and
     * returns a boolean representing the verdict.
     * 
     * @param inputArray
     * @return arrayIsSorted
     */
    public static boolean validateSorted(int [] inputArray) {
        boolean arrayIsSorted = true;
        int i = 0;    // loop counter
        
        for (i = 1; i < inputArray.length; ++i) {
            if (inputArray[i - 1] > inputArray[i]) {
                arrayIsSorted = false;
                System.out.println("Sort NOT validated.  See index  " + i + " for the first instance of elements out of order.");
                break;
            }
        }
        
        return arrayIsSorted;
    }
    
    /**
     * displayList
     * Outputs list contents to screen.
     * 
     * @param inputArray
     */
    public static void displayList(int [] inputArray) {
        int i = 0;
        
        System.out.println("Displaying array: ");
        
        for (i = 0; i < inputArray.length; ++i) {
            System.out.print(inputArray[i] + " ");
        }
        
        System.out.println();
    }
}
