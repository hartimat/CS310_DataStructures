package cs310hartigan;

import java.io.*;
import java.util.*;

/**
 * The PrintImpl class contains a single method (printReport)  for printing a 
 * formatted report based on the fund manager and stock trade logs that are 
 * passed to it.
 * 
 * @author Matthew Hartigan
 * @version Week 3
 */
public class PrintImpl {
       
    /**
     * printReport
     * Creates an output file containing a formatted report based on the contents
     * of the input fund manager and stock trade logs.  Output file based on input
     * parameter to method.
     * 
     * @param fundManagerLogImpl
     * @param stockTradeLogImpl
     * @param outputFilename
     */
    public void printReport (FundManagerLogImpl fundManagerLogImpl, StockTradeLogImpl stockTradeLogImpl, String outputFilename) {
        FundManagerNode currentNode = fundManagerLogImpl.getListHead();    // Initialize to list head
        LinkedList<StockTrade> stockTradeLog = stockTradeLogImpl.getStockTradeLog();
        Iterator iterator = stockTradeLog.iterator();    // Iterator for looping through StockTrade log
        StockTrade stockTradeLogEntry = null;
        PrintWriter outputFileObject = null;
        
        // Attempt to open and manipulate an output file
        try {
            outputFileObject = new PrintWriter (outputFilename);
            
            while (currentNode != null) {
               
                // Loop through every fund manager in the log   
                outputFileObject.printf("%s  %s,  %s", currentNode.getFundManager().getBrokerLicense(), currentNode.getFundManager().getLastName(), currentNode.getFundManager().getFirstName());
                outputFileObject.println();
                outputFileObject.println();
                
                // Summarize every stock trade from the broker      
                iterator = stockTradeLog.iterator();    // Reset iterator to beginning of log
                while (iterator.hasNext()) {
                    stockTradeLogEntry = (StockTrade) iterator.next();
                    if ( stockTradeLogEntry.getBrokerLicense().equals(currentNode.getFundManager().getBrokerLicense())) {
                        outputFileObject.printf("        %s       %s       %s       %s", stockTradeLogEntry.getStockSymbol(), stockTradeLogEntry.getPricePerShare(), stockTradeLogEntry.getWholeShares(), stockTradeLogEntry.getTaxable());
                        outputFileObject.println();
                        outputFileObject.println();
                    }
                 }
                    
               // Output summary of individual stock trade, fund manager totals
               outputFileObject.printf("   Number of StockTrade listings for FundManager: %d", stockTradeLogImpl.numberOfStockTrades(currentNode.getFundManager().getBrokerLicense()));
               outputFileObject.println();
               outputFileObject.printf("   Total sales value of StockTrade listings for FundManager %s: $ %.2f", currentNode.getFundManager().getBrokerLicense(), stockTradeLogImpl.totalStockTradeValue(currentNode.getFundManager().getBrokerLicense()));
               outputFileObject.println();
               outputFileObject.println();            
               
               currentNode = currentNode.getNextNode();
            }

            // Output summary of all stock trade, fund manager totals
            outputFileObject.printf("Total Number of StockTrade listings for ALL FundManagers = %d", stockTradeLog.size());
            outputFileObject.println();
            outputFileObject.printf("Total sales value of StockTrade listings for ALL FundManagers = $ %.2f", stockTradeLogImpl.totalStockTradeValue());

            outputFileObject.close();   // Close ouptut file
        }
        
        // Handle exceptions thrown while working with output file
        catch (Exception excpt) {
           System.out.println("Error: Unexpected exception thrown while attempting to access " + outputFilename);
           System.out.println(excpt.getMessage());
           System.out.println("Exiting program.");
           System.exit(1);
        }
        
        System.out.println("Report is complete -- located in file: " + outputFilename);
        System.out.println();
    }
}
