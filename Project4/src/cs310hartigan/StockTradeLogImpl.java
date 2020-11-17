package cs310hartigan;

import java.util.*;

/**
 * The StockTradeLogImpl class instantiates the doubly linked list that is 
 * implemented by the LinkedList class in java.util package.  The linked list
 * contains StockTrade objects as well as methods for adding and removing 
 * elements.  It also contains methods for identifying if a StockTrade already 
 * exists in the log, removes stock trades from the log based on a broker 
 * license number, calculates number or monetary value of stocks belonging to 
 * a given broker, and even provides a method for displaying the log contents to
 * screen.
 * 
 * @author Matthew Hartigan
 * @version Week 4
 */
public class StockTradeLogImpl implements LogInterfaceImpl {
    
    // Data fields
    private LinkedList<StockTrade> stockTradeLog = null;
    
    
    // Constructors
    /**
     * Default constructor
     */
    public StockTradeLogImpl () {
        this.stockTradeLog = new LinkedList<> ();
    }    
    
    
    // Getters and Setters
    /**
     * Get stock trade log
     * 
     * @return stockTradeLog
     */
    public LinkedList<StockTrade> getStockTradeLog() {
        return stockTradeLog;
    }

    /**
     * Set stock trade log
     * 
     * @param stockTradeLog
     */
    public void setStockTradeLog(LinkedList<StockTrade> stockTradeLog) {
        this.stockTradeLog = stockTradeLog;
    }
    
        
    // User-defined Methods
    /**
     * addToLog
     * This method adds an element to the StockTrade linked list
     * data structure.  
     * 
     * @param obj
     * @return addToLogSuccessful
     */   
    public boolean addToLog (Object obj) {
        StockTrade inputStockTrade = (StockTrade) obj;    // Downcast to StockTrade 
        return this.stockTradeLog.add(inputStockTrade);
    }
    
    /**
     * removeFromLog
     * This method removes an element from the StockTrade linked list
     * data structure.  
     * 
     * @param obj
     * @return boolean
     */    
    public boolean removeFromLog (Object obj) {
        StockTrade inputStockTrade = (StockTrade) obj;    // Downcast to StockTrade
        return this.stockTradeLog.remove(inputStockTrade);
    }
    
    /**
     * traverseDisplay
     * Loops through entire FundManager log and displays contents to screen
     * using toString().
     */    
    public void traverseDisplay () {
        Iterator iterator = this.stockTradeLog.iterator();
        
        System.out.println("StockTrade Log:");
        
        // Display entire contents of log to screen
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }        
    }
    
    /**
     * isStockSymbolUnique
     * Takes a stock symbol string as input.  Searched the existing stock trade
     * log for a match and returns a boolean indicating whether or not the input
     * symbol is unique.
     * 
     * @param stockSymbol
     * @return boolean
     */
    public boolean isStockSymbolUnique (String stockSymbol) {
        Iterator iterator = this.stockTradeLog.iterator();
        StockTrade logEntry = null;
        boolean stockSymbolIsUnique = true;
        
        // Loop through entire log
        while (iterator.hasNext()) {
            logEntry = (StockTrade) iterator.next();
            if (logEntry.getStockSymbol().equals(stockSymbol)) {
                stockSymbolIsUnique = false;
            }
        }
        
        return stockSymbolIsUnique;
    }
    
    /**
     * removeStockTradeByFundManager
     * A broker license number is taken as input.  The existing stock trade log
     * is searched for matches and those instances are removed. 
     * Returns a boolean indicating whether
     * or not stock trades were removed.
     * 
     * @param license
     * @return boolean
     */
    public boolean removeStockTradeByFundManager (String license) {
        Iterator iterator = this.stockTradeLog.iterator();            
        StockTrade logEntry = null;
        boolean operationWasSuccessful = false;
        
        // Loop through entire log
        while (iterator.hasNext()) {
             logEntry = (StockTrade) iterator.next();
            if (logEntry.getBrokerLicense().equals(license)) {    // Check for license match
                iterator.remove();
                operationWasSuccessful = true;
            }
        }
        return operationWasSuccessful;
    }

    /**
     * numberOfStockTrades
     * Takes a broker license number as input and returns the number of 
     * stock trades with a matching license number.
     * 
     * @param license
     * @return int
     */
    public int numberOfStockTrades (String license) {
        Iterator iterator = stockTradeLog.iterator();
        StockTrade logEntry = null;
        int numTrades = 0;
        
        // Loop through entire list
        while (iterator.hasNext()) {
            logEntry = (StockTrade) iterator.next();
            if (logEntry.getBrokerLicense().equals(license)) {
                numTrades++;
            }
        }
        
        return numTrades;
    }
    
    /**
     * totalStockTradeValue
     * Sums the total value of all stock trades in the current stock trade log
     * and returns the result.
     * 
     * @return double
     */
    public double totalStockTradeValue () {
        Iterator iterator = stockTradeLog.iterator();
        StockTrade logEntry = null;
        double totalValue = 0.0;    // USD
        
        while (iterator.hasNext()) {
            logEntry = (StockTrade) iterator.next();
            totalValue += logEntry.getWholeShares() * logEntry.getPricePerShare();
            }
        
        return totalValue;
    }
    
    /**
     * totalStockTradeValue
     * Sums the total value of a particular brokers stock trades in the current 
     * stock trade log and returns the result.
     * 
     * @param license
     * @paramString
     * @return double
     */
    public double totalStockTradeValue (String license) {
        Iterator iterator = stockTradeLog.iterator();
        StockTrade logEntry = null;
        double totalValue = 0.0;    // USD
        
        while (iterator.hasNext()) {
            logEntry = (StockTrade) iterator.next();
            if (logEntry.getBrokerLicense().equals(license)) {
                totalValue += logEntry.getWholeShares() * logEntry.getPricePerShare();
            }
        }
        
        return totalValue;
    }

    /**
     * cleanUp
     * Searches stock trade log for invalid stock symbols  Removed those
     * that it finds.
     * 
     */
    public void cleanUp () {
        Iterator iterator = this.stockTradeLog.iterator();            
        StockTrade logEntry = null;
        
        while (iterator.hasNext()) {
             logEntry = (StockTrade) iterator.next();
             
             // Loop through entire log
            if (! logEntry.checkStockSymbolIsValid()) {    // Check for invalid stock symbol
                System.out.println("Invalid stock symbol for StockTrade " 
                    + logEntry.getStockSymbol() + " -- Deleting StockTrade from log");
                this.stockTradeLog.remove(logEntry);
                iterator = this.stockTradeLog.iterator();
            }
        }
    }
}
