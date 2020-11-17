package cs310hartigan;

import java.io.*;
import java.util.*;

/**
 * The PrintImpl class contains a single overloaded method (printReport)  that 
 * prints formatted reports for either FundManager and StockTrade logs or
 * go kart usage.
 * 
 * @author Matthew Hartigan
 * @version Week 4
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
    
    /**
     * printReport
     * Creates an output file containing a formatted report based on the contents
     * of the input go kart file.
     * 
     * @param goKartUsageImpl
     * @param racingGoKartStackImpl
     * @param basicGoKartStackImpl
     * @param topFundManagerQueueImpl
     * @param standardFundManagerQueueImpl
     * @param fundManagerLogImpl
     * @param stockTradeLogImpl
     * @param outputFilename
     */
    public void printReport (GoKartUsageImpl goKartUsageImpl, GoKartStackImpl racingGoKartStackImpl, GoKartStackImpl basicGoKartStackImpl, FundManagerQueueImpl topFundManagerQueueImpl, FundManagerQueueImpl standardFundManagerQueueImpl, FundManagerLogImpl fundManagerLogImpl, StockTradeLogImpl stockTradeLogImpl, String outputFilename) {
        PrintWriter outputFileObject = null;
        FundManagerNode currentNode = null;
        int i = 0;    // loop counter
        
        try {
            outputFileObject = new PrintWriter (outputFilename);
            
            // Set report header
            outputFileObject.println("GO-KART USAGE REPORT");
            
            // Summarize go kart usage
            for (i = 0; i < goKartUsageImpl.getGoKartUsageArray().length; ++i) {

                // Check for broker license holding place of go kart number in usage array.  If found, output accordingly
                if (goKartUsageImpl.getGoKartUsageArray()[i].length() == 8) {
                    outputFileObject.printf("%s %s is using go-kart number %d", fundManagerLogImpl.findFundManagerNode(goKartUsageImpl.getGoKartUsageArray()[i]).getFundManager().getFirstName(), fundManagerLogImpl.findFundManagerNode(goKartUsageImpl.getGoKartUsageArray()[i]).getFundManager().getLastName(), i + 1);
                    outputFileObject.println();
                }
            }
            outputFileObject.println();
            
            // Summarize available go karts
            outputFileObject.println("AVAILABLE GO-KARTS");
            
            // Summarize basic go karts
            outputFileObject.println("   BASIC GO-KARTS");
                if (basicGoKartStackImpl.isEmpty()) {
                    outputFileObject.println("      No basic go-karts are available");
                }
                else {
                    for (i = 0; i < basicGoKartStackImpl.getNumGoKartsInStack(); ++i) {
                        outputFileObject.println("      Basic go-kart number " + basicGoKartStackImpl.getGoKartStack()[i] + " is available");
                    }
                }
                outputFileObject.println();
            
            // Summarize racing go karts
            outputFileObject.println("   RACING GO-KARTS");
                if (racingGoKartStackImpl.isEmpty()) {
                    outputFileObject.println("      No racing go-karts are available");
                }
                else {
                    for (i = 0; i < racingGoKartStackImpl.getNumGoKartsInStack(); ++i) {
                        outputFileObject.println("      Racing go-kart number " + racingGoKartStackImpl.getGoKartStack()[i] + " is available");
                    }
                }
                outputFileObject.println();
            
            // Summarize queue status
            outputFileObject.println("TOP BROKER QUEUE");
                if (topFundManagerQueueImpl.isEmpty()) {
                    outputFileObject.println("There are no top selling brokers waiting");
                }
                else {
                    currentNode = topFundManagerQueueImpl.getFrontNode();
                    i = 0;
                    
                    while (currentNode != null 
                        && i < topFundManagerQueueImpl.getNumFundManagersInQueue()) {
                        
                        outputFileObject.printf("%s %s is waiting", currentNode.getFundManager().getFirstName(), currentNode.getFundManager().getLastName());
                        outputFileObject.println();
                        currentNode = currentNode.getNextNode();
                        ++i;    // increment loop counter
                    }
                }
                outputFileObject.println();
                
            outputFileObject.println("STANDARD BROKER QUEUE");
                if (standardFundManagerQueueImpl.isEmpty()) {
                    outputFileObject.println("There are no standard selling brokers waiting");
                }
                else {
                    currentNode = standardFundManagerQueueImpl.getFrontNode();
                    i = 0;
                    
                    while (currentNode != null 
                        && i < standardFundManagerQueueImpl.getNumFundManagersInQueue()) {
                        outputFileObject.printf("%s %s is waiting", currentNode.getFundManager().getFirstName(), currentNode.getFundManager().getLastName());
                        outputFileObject.println();
                        currentNode = currentNode.getNextNode();
                        ++i;
                    }
                }
                outputFileObject.println();
            
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
