package cs310hartigansort;


import java.util.ArrayList;


/**
 * The CS310HartiganSort class is the main class for the CS310 Week 7 
 * programming assignment.  It's main method uses methods from the
 * UtilitiesImpl and SortImpl classes to perform the necessary sorts on input 
 * data, as well as verify that the sorts were performed correctly.  Once finished,
 * the generateReport method is used to created an output report.
 * 
 * @author Matthew Hartigan
 * @version Week 7
 */
public class CS310HartiganSort {
  
    // Instantiations
    static UtilitiesImpl utilitiesImpl = new UtilitiesImpl();
    static SortImpl sortImpl = new SortImpl();
    static PrintImpl printImpl = new PrintImpl();
    
    
    /**
     * main
     * Main method that defines values for input data arrays and calls the 
     * necessary sorting methods.  Once complete, it calls generateReport to 
     * produce the output report.
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        final int NUM_RUNS = 3;    // per spec
        final int NUM_LISTS = 3;    
        final int NUM_ELEMENTS = 50000;    // per spec
        final int MAX_ELEMENT_VALUE = 100000;    // per spec
        final String OUTPUT_FILENAME = "output/sortResults.txt";    
        
        long startTime = 0;    // for use in calculating elapsed time
        long endTime = 0;    // for use in calculating elapsed time
        boolean listsAreValid = false; 
        double [] [] resultsMatrix = new double[3] [NUM_RUNS];
        int i = 0;    // loop counter
        
        // Perform specified number of sort runs
        for (i = 0; i < NUM_RUNS; ++i) {
            // Display run number
            System.out.println("Starting sort # " + (i + 1) + "...");

            // Create identical lists of random numbers
            int [] [] listOfLists = utilitiesImpl.generateLists(NUM_LISTS, NUM_ELEMENTS, MAX_ELEMENT_VALUE);
            listsAreValid = utilitiesImpl.validateLists(listOfLists, NUM_ELEMENTS);

            // Perform desired sorts if lists are valid, measure time to completion
            if (listsAreValid) {

                // Selection Sort
                startTime = System.nanoTime();    // Get startTime
                listOfLists[0] = sortImpl.selectionSort(listOfLists[0]);    // Perform sort
                endTime = System.nanoTime();    // Get endTime
                if (utilitiesImpl.validateSorted(listOfLists[0])) {    // Validate list was sorted properly, then record runtime
                    System.out.printf("        Selection Sort time %.1f nanoseconds.\n", (double)(endTime - startTime));    // Display runtime for sort
                    resultsMatrix[0][i] = (double)(endTime - startTime);
                }

                // Insertion Sort
                startTime = System.nanoTime();    // Get startTime
                listOfLists[1] = sortImpl.insertionSort(listOfLists[1]);
                endTime = System.nanoTime();    // Get endTime
                if (utilitiesImpl.validateSorted(listOfLists[1])) {    // Validate list was sorted properly, then record runtime
                    System.out.printf("        Insertion Sort time %.1f nanoseconds.\n", (double)(endTime - startTime));    // Display runtime for sort
                    resultsMatrix[1][i] = (double)(endTime - startTime);
                    }

                // Merge Sort
                startTime = System.nanoTime();    // Get startTime
                listOfLists[2] = sortImpl.mergeSort(listOfLists[2]);  
                endTime = System.nanoTime();    // Get endTime      
                if (utilitiesImpl.validateSorted(listOfLists[2])) {    // Validate list was sorted properly, then record runtime
                    System.out.printf("        Merge Sort time %.1f nanoseconds.\n", (double)(endTime - startTime));    // Display runtime for sort
                    resultsMatrix[2][i] = (double)(endTime - startTime);
                }  
            }
            
            System.out.println("           Sorts validated.");
        }    // Close for loop
        
        
        // Generate output report
        generateReport(OUTPUT_FILENAME, resultsMatrix);
    }
    
    
    /**
     * generateReport
     * Method for generating output report.
     * 
     * @param outputFilename
     * @param resultsMatrix
     */
    public static void generateReport(String outputFilename, double [] [] resultsMatrix) {
        
        printImpl.generateReport(outputFilename, resultsMatrix);
    }
}
