package cs310hartigansort;


import java.io.*;
import java.util.Scanner;

/**
 * The PrintImpl class is used to house the method for generating the CS310 
 * Week 7 output report.
 * 
 * @author Matthew Hartigan
 * @version Week 7
 */
public class PrintImpl {
    
    // Constructors
    PrintImpl() {
    }
    
    
    // Methods
    /**
     * generateReport
     * Method used to generate output report per spec.
     * 
     * @param outputFilename
     * @param resultsMatrix
     */
    public static void generateReport (String outputFilename, double [] [] resultsMatrix) {
        PrintWriter outputFileObject = null;
        int i = 0;    // loop counter
        double timeSum = 0;    // variable to hold total time for all runs in order to calculate average

        try {
            
            // Open output file
            outputFileObject = new PrintWriter (outputFilename);
            
            // Write header
            outputFileObject.println("SORTING RESULTS (in nanoseconds)");
            outputFileObject.println("------------------------------");
            outputFileObject.printf("%30s", " ");

            for (i = 0; i < resultsMatrix[0].length; ++i) {
                outputFileObject.printf("Run %d%30s", (i + 1), " ");
            }
            
            outputFileObject.print("Average\n");
            
            // Write selectionSort results
            outputFileObject.printf("%-23s", "Selection Sort");
            
            timeSum = 0;
            for (i = 0; i < resultsMatrix[0].length; ++i) {
                outputFileObject.printf("%-34.1f", resultsMatrix[0][i]);
                timeSum += resultsMatrix[0][i];
            }
  
            outputFileObject.printf("%-9.1f\n", (timeSum / i));    // Average
            
            
            // Write insertionSort results
            outputFileObject.printf("%-24s", "Insertion Sort");
            
            timeSum = 0;
            for (i = 0; i < resultsMatrix[1].length; ++i) {
                outputFileObject.printf("%-34.1f", resultsMatrix[1][i]);
                timeSum += resultsMatrix[1][i];
            }
  
            outputFileObject.printf("%-9.1f\n", (timeSum / i));    // Average 
            
            // Write mergeSort results
            outputFileObject.printf("%-24s", "Merge Sort");
            
            timeSum = 0;
            for (i = 0; i < resultsMatrix[2].length; ++i) {
                outputFileObject.printf("%-34.1f", resultsMatrix[2][i]);
                timeSum += resultsMatrix[2][i];
            }
  
            outputFileObject.printf("%-9.1f\n", (timeSum / i));    // Average

            outputFileObject.close();   // Close ouptut file
            
        }
        catch (Exception excpt) {
            outputFileObject.println("ERROR: Trouble opening output file.");
        }
        
    }
}
