package cs310hartigan;

import java.io.*;
import java.util.*;

/**
 * The PrintImpl class contains a single method (printReport)  that 
 * prints a formatted report summarizing the taxable status of stock trades
 * as requested by FundManagers
 * 
 * @author Matthew Hartigan
 * @version Week 6
 */
public class PrintImpl {
       
    /**
     * printReport
     * Creates an output file containing a formatted report based on the contents
     * of the input fund manager request file. 
     * 
     * @param fundManagerLogImpl
     * @param stockTradeLogImpl
     * @param inputFilename
     * @param outputFilename
     */
    public void printReport (FundManagerLogImpl fundManagerLogImpl, StockTradeLogImpl stockTradeLogImpl, String inputFilename, String outputFilename) {
       String inputLine = "";    // holds each line read from input file
       String [] inputLineParsed = null;    // holds parsed form of each line read from input file
       FileInputStream inputFileObject = null;
       Scanner inputFileScanner  = null;
       int lineCounter = 0;    // tallies how many lines have been read for input
        PrintWriter outputFileObject = null;
        int i = 0;    // loop counter
        
        // Attempt to open and manipulate input and output files
        try {
            
           // Open input file
           inputFileObject = new FileInputStream(inputFilename);
           inputFileScanner = new Scanner(inputFileObject);
           System.out.println("Creating sales report using request from file: " + inputFilename);
           
           // Open output file
            outputFileObject = new PrintWriter (outputFilename);
            
            // Process each line of input file
            while (inputFileScanner.hasNextLine()) {
                
                inputLine = inputFileScanner.nextLine();    // Read next input line from file
                inputLineParsed = inputLine.split(" ");    // Parse (space expected)                   
                
                // Check if FundManager exists in log
                if (fundManagerLogImpl.find(fundManagerLogImpl.getRoot(), inputLineParsed[0]) == null) {
                    outputFileObject.println("FundManager " + inputLineParsed[0] + " does not exist in the log");
                }
                    
                // Once FM is verified, write their name to the output file along with ST status
                else {
                    // Write name of fund manager
                    outputFileObject.println("FundManager " + inputLineParsed[0] + ", " + fundManagerLogImpl.find(fundManagerLogImpl.getRoot(), inputLineParsed[0]).getFirstName() + " " + fundManagerLogImpl.find(fundManagerLogImpl.getRoot(), inputLineParsed[0]).getLastName());
                    
                        // Use ST hash map to identify and output the taxable status of any requested stockes
                        for (i = 1; i < inputLineParsed.length; ++i) {    // For every stock symbol read from line in input file
                            if (stockTradeLogImpl.find(inputLineParsed[i]) == null) {    // If no hash map match
                                outputFileObject.println("      Requested StockTrade " + inputLineParsed[i] + " does not exist in the log");
                            }

                            // Disposition requested StockTrade as taxable or not
                            else {
                                if (stockTradeLogImpl.find(inputLineParsed[i]).getTaxable()) {

                                    outputFileObject.println("      Stocktrade " + stockTradeLogImpl.find(inputLineParsed[i]).getStockSymbol() + " is TAXABLE");
                                }
                                else {
                                    outputFileObject.println("      Stocktrade " + stockTradeLogImpl.find(inputLineParsed[i]).getStockSymbol() + " is NOT TAXABLE");
                                }
                            }
                        }
                }
            }    // Close while loop
            
            outputFileObject.close();   // Close ouptut file
            
            System.out.println("Sales report is complete -- located in file: " + outputFilename);
        }    // Close try
        
        // Handle exceptions thrown while working with output file
        catch (Exception excpt) {
            // FIXME include I and O?
           System.out.println("Error: Unexpected exception thrown while attempting to access " + outputFilename + " and " + inputFilename);
           System.out.println("Exiting program.");
           System.exit(1);
        }
        
        System.out.println("Report is complete -- located in file: " + outputFilename);
        System.out.println();
    }
}
