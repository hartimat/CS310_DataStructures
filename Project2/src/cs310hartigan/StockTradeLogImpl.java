package cs310hartigan;

import java.util.Arrays;

/**
 * The StockTradeLogImpl class implements the Log interface and defines
 * an array data structure to hold StockTrade objects.  It contains methods
 * for adding and removing StockTrade elements from the array.  It also 
 * contains methods for identifying if a StockTrade already exists in the log,
 * removing stock trades from the log based on a broker license number, 
 * calculating the number or stock trades by a fund manager, and calculating
 * the value of stock trades by a fund manager(s).
 * 
 * @author Matthew Hartigan
 * @version Week 2
 */
public class StockTradeLogImpl implements LogInterfaceImpl {
    // Data fields
    private final int MAX_NUM_STOCK_TRADES = 1000;
    private StockTrade [] stockTradeLog = new StockTrade[MAX_NUM_STOCK_TRADES];    
    private int numStockTrades = 0;
    
    
    // Constructors
    /**
     * Default constructor
     */
    public StockTradeLogImpl () {
    }
    
    
    // Getters and setters
    /**
     * Get stock trade array data field.
     * @return StockTrade []
     */
    public StockTrade[] getStockTradeArray () {
        return stockTradeLog;
    }
    
    /**
     * Get the current number of stock trades.
     * 
     * @return int
     */
    public int getNumStockTrades () {
        return numStockTrades;
    }
    
    
    // User defined methods
    /**
     * This method implements the corresponding Log interface method.  It first 
     * creates a new, larger array, then it appends the new element to the end.
     * If the operation was successful, a boolean is returned accordingly.
     * 
     * @param obj
     * @return
     */
    public boolean addToLog (Object obj) {
        StockTrade inputStockTrade = (StockTrade) obj;
        boolean addToLogSuccessful = false;

        if (numStockTrades < MAX_NUM_STOCK_TRADES) {
            stockTradeLog[numStockTrades] = inputStockTrade;
            numStockTrades++;
            addToLogSuccessful = true;
        }
        
        return addToLogSuccessful;
    }
    
    /**
     * This method implements the corresponding Log interface method.  It first 
     * identifies the index of the stock trade element that is to be removed from the
     * log.  It then creates a new, smaller array that the current elements are transferred
     * to.  If the operation was successful, a boolean is returned accordingly.
     * 
     * @param obj
     * @return boolean
     */
    public boolean removeFromLog (Object obj) {
        StockTrade inputStockTrade = (StockTrade) obj;    // Downcast object to be removed into type StockTrade
        String inputStockSymbol = inputStockTrade.getStockSymbol();
        boolean operationWasSuccessful = false;
        String currentStockSymbol = "";   // hold current broker license while searching
        int i = 0;   // loop counter
        
        // Iterate through entire stock trade log.  If stock symbol match is found, remove that trade
        for (i = 0; i < numStockTrades; ++i) {
            currentStockSymbol = stockTradeLog[i].getStockSymbol();
            if (currentStockSymbol.equals(inputStockSymbol)) {
                if (i == numStockTrades - 1) {
                    stockTradeLog[i] = null;
                }
                else {
                    stockTradeLog[i] = stockTradeLog[numStockTrades - 1];
                    stockTradeLog[numStockTrades - 1] = null;
                }
                operationWasSuccessful = true;
                numStockTrades--;
                break;
            }
        }
                
        return operationWasSuccessful;
    }
    
    /**
     * removeStockTradeByFundManager
     * A broker license number is taken as input.  The existing stock trade array
     * is searched for matches and those instances are removed using the 
     * removeFromLog() method accordingly.  Returns a boolean indicating whether
     * or not stock trades were removed.
     * 
     * @param license
     * @return boolean
     */
    public boolean removeStockTradeByFundManager(String license) {
        boolean stockTradesSuccessfullyRemoved = false;
        String currentLicense = "";   // hold current broker license while searching
        int i = 0;   // loop counter
        
        // Iterate through entire stock trade log.  If license match found, remove that trade
        for (i = 0; i < numStockTrades; ++i) {
           currentLicense = stockTradeLog[i].getBrokerLicense();
           if (currentLicense.equals(license)) {
              if (i == numStockTrades - 1) {
                 stockTradeLog[i] = null;
              }
              else {
                 stockTradeLog[i] = stockTradeLog[numStockTrades - 1];
                 stockTradeLog[numStockTrades - 1] = null;
              }
                 stockTradesSuccessfullyRemoved = true;
                 numStockTrades--;
           }
        }
        
        return stockTradesSuccessfullyRemoved;
    }
    
    /**
     * isStockSymbolUnique
     * Takes a stock symbol string as input.  Searched the existing stock trade
     * array for a match and returns a boolean indicating whether or not the input
     * symbol is unique.
     * 
     * @param stockSymbol
     * @return boolean
     */
    public boolean isStockSymbolUnique (String stockSymbol) {
        boolean licenseIsUnique = true;
        int i = 0;     // loop counter
         
        // Iterate through entire stock trade log and identify first stock symbol match
        for (i = 0; i < this.getNumStockTrades(); ++i) {
            if (stockSymbol.equals(this.stockTradeLog[i].getStockSymbol())) {
                licenseIsUnique = false;
            }
        }
       
        return licenseIsUnique;
    }
    
    /**
     * numberOfStockTrades
     * Takes a broker license number as input and returns the number of 
     * stock trades with a matching license number.
     * 
     * @param String
     * @return int
     */
    public int numberOfStockTrades (String license) {
        int count = 0;   // counter for number of stock trades
        int i = 0;   // loop counter
        
        // Iterate through existing stock trade array and count the number of matches
        for (i = 0; i < numStockTrades; ++i) {
            if (stockTradeLog[i].getBrokerLicense().equals(license)) {
                ++count;
            }
        } 
        
        return count;
    }
    
    /**
     * totalStockTradeValue
     * Sums the total value of all stock trades in the current stock trade array
     * and returns the result.
     * 
     * @return double
     */
    public double totalStockTradeValue() {
        double sumOfStockTradeValues = 0.0;
        int i = 0;   // loop counter
        
        for (i = 0; i < numStockTrades; ++i) {
           sumOfStockTradeValues += (stockTradeLog[i].getPricePerShare() * stockTradeLog[i].getWholeShares());
            }
        
        return sumOfStockTradeValues;
    }
    
    /**
     * totalStockTradeValue
     * Sums the total value of a particular broker;s stock trades in the current 
     * stock trade array and returns the result.
     * 
     * @paramString
     * @return double
     */
    public double totalStockTradeValue(String license) {
        double sumOfStockTradeValues = 0.0;
        int i = 0;   // loop counter
        
        for (i = 0; i < numStockTrades; ++i) {
            if (stockTradeLog[i].getBrokerLicense().equals(license)) {
                
                sumOfStockTradeValues += (stockTradeLog[i].getPricePerShare() * stockTradeLog[i].getWholeShares());
            }
        } 
        
        return sumOfStockTradeValues;
    }
}
