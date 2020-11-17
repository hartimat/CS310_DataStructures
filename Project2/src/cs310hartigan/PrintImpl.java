package cs310hartigan;

import java.util.ArrayList;
import java.io.*;

/**
 * The PrintImpl class contains a single method (printReport)  for printing a 
 * formatted report based on the fund manager and stock trade logs that are 
 * passed to it.
 * 
 * @author Matthew Hartigan
 * @version Week 2
 */
public class PrintImpl {
    // Data fields
    final String OUTPUT_FILENAME = "output/assn2report.txt";
    private ArrayList<FundManager> fundManagerLog = new ArrayList<>();
    private StockTrade [] stockTradeLog = new StockTrade[0]; 
    private int numStockTrades = 0;   
    
    /**
     * printReport
     * Creates an output file containing a formatted report based on the contents
     * of the input fund manager and stock trade logs.
     * 
     * @param fundManagerLogImpl
     * @param stockTradeLogImpl
     */
    public void printReport (FundManagerLogImpl fundManagerLogImpl, StockTradeLogImpl stockTradeLogImpl) {
        // Data fields
        fundManagerLog = fundManagerLogImpl.getFundManagerLog();
        stockTradeLog = stockTradeLogImpl.getStockTradeArray();
        numStockTrades = stockTradeLogImpl.getNumStockTrades();
        PrintWriter outputFileObject = null;
        int i = 0;   // loop counter
        
        // Attempt to open and manipulate an output file
        try {
            outputFileObject = new PrintWriter (OUTPUT_FILENAME);
            
            // Loop through every fund manager in the log           
            for (FundManager fundManagerEntry : fundManagerLog) {
            outputFileObject.printf("%s  %s,  %s", fundManagerEntry.getBrokerLicense(), fundManagerEntry.getLastName(), fundManagerEntry.getFirstName());
            outputFileObject.println();
            outputFileObject.println();
                
                // Summarize every stock trade from the broker
                for (i = 0; i < numStockTrades; ++i) {
                    if ( stockTradeLog[i].getBrokerLicense().equals(fundManagerEntry.getBrokerLicense())) {
                        
                        outputFileObject.printf("        %s       %s       %s       %s", stockTradeLog[i].getStockSymbol(), stockTradeLog[i].getPricePerShare(), stockTradeLog[i].getWholeShares(), stockTradeLog[i].getTaxable());
                        outputFileObject.println();
                        outputFileObject.println();
                    }
                }
                
           // Output summary of individual stock trade, fund manager totals
           outputFileObject.printf("   Number of StockTrade listings for FundManager: %d", stockTradeLogImpl.numberOfStockTrades(fundManagerEntry.getBrokerLicense()));
           outputFileObject.println();
           outputFileObject.printf("   Total sales value of StockTrade listings for FundManager %s: $ %.2f", fundManagerEntry.getBrokerLicense(), stockTradeLogImpl.totalStockTradeValue(fundManagerEntry.getBrokerLicense()));
           outputFileObject.println();
           outputFileObject.println();               
                
            }
            
            // Output summary of all stock trade, fund manager totals
            outputFileObject.printf("Total Number of StockTrade listings for ALL FundManagers = %d", numStockTrades);
            outputFileObject.println();
            outputFileObject.printf("Total sales value of StockTrade listings for ALL FundManagers = $ %.2f", stockTradeLogImpl.totalStockTradeValue());

            outputFileObject.close();   // Close ouptut file
        }
        
        // Handle exceptions thrown while working with output file
        catch (Exception excpt) {
           System.out.println("Error: Unexpected exception thrown while attempting to access " + OUTPUT_FILENAME);
           System.out.println(excpt.getMessage());
           System.out.println("Exiting program.");
           System.exit(1);
        }
    }
}
