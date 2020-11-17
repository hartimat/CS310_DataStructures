package cs310hartigan;

/**
 * The StockTradeLogImpl class instantiates a hash map that stores input 
 * StockTrades in an underlying array.  The elements "buckets" are nodes so 
 * that collisions can be resolved by chaining method into linked lists.
 * 
 * @author Matthew Hartigan
 * @version Week 5
 */
public class StockTradeLogImpl implements LogInterfaceImpl {
    
    // Data fields
    private final int MAX_HASHMAP_SIZE = 17;
    private MapEntry [] stockTradeHash = null;
    
    
    // Constructors
    /**
     * Default constructor
     */
    public StockTradeLogImpl () {
        this.stockTradeHash = new MapEntry [this.MAX_HASHMAP_SIZE];
    }    
   
    
    // Getters and Setters
    /**
     *
     * @return
     */
    public MapEntry[] getStockTradeHash () {
        return stockTradeHash;
    }

    /**
     *
     * @param stockTradeHash
     */
    public void setStockTradeHash(MapEntry[] stockTradeHash) {
        this.stockTradeHash = stockTradeHash;
    }

    
    // User-defined Methods
    /**
     * addToLog
     * This method adds an element to the StockTrade hash map data structure
     * 
     * @param obj
     * @return addToLogSuccessful
     */
    public boolean addToLog(Object obj) {
        StockTrade inputStockTrade = (StockTrade) obj;    // Downcast to StockTrade 
        boolean addToLogSuccessful = false;
        MapEntry newMapEntry = null;
        
        // Check if hashed index is empty
        if (this.stockTradeHash[inputStockTrade.hashCode() % this.MAX_HASHMAP_SIZE] == null) {
            
            // Create new list head Map Entry and insert at given index
            newMapEntry = new MapEntry (inputStockTrade);
            this.stockTradeHash[inputStockTrade.hashCode() % this.MAX_HASHMAP_SIZE] = newMapEntry;
            addToLogSuccessful = true;
            
        }
        
        // If hashed index is occupied, add new element to front of linked list
        else {
            newMapEntry = new MapEntry (inputStockTrade, this.stockTradeHash[inputStockTrade.hashCode() % this.MAX_HASHMAP_SIZE]);
            this.stockTradeHash[inputStockTrade.hashCode() % this.MAX_HASHMAP_SIZE] = newMapEntry;
            addToLogSuccessful = true;
        }     
        
        return addToLogSuccessful;
    }
    
    /**
     * removeFromLog
     * This method removes an element from the StockTrade linked list
     * data structure.  
     * 
     * Note: This method is not utilized during the Week 5 assignment.  
     * However, it was left in to ensure that the FundManagerLogImpl class
     * still correctly implements the LogInterfaceImpl class.
     * 
     * @param obj
     * @return boolean
     */    
    public boolean removeFromLog (Object obj) {
        StockTrade inputStockTrade = (StockTrade) obj;    // Downcast to StockTrade
        
        // Method not implemented per Week 5 spec
        // Left in order to ensure LogInterfaceImpl is correctly implemented
        
        return true;
    }
    
    /**
     * displayHash
     * Method to output contents of hash map to screen.
     * 
     */
    public void displayHash() {
        int i = 0;    // loop counter
        MapEntry currentEntry = null;
        
        System.out.println("StockTrade Hash Table:");
        
        // Loop through contents of array and output messages to screen accordingly
        for (i = 0; i < this.MAX_HASHMAP_SIZE; ++i) {
            if (this.stockTradeHash[i] == null) {
                System.out.println("Index " + i + " is empty");
            }
            else {
                System.out.print("Index " + i + " contains StockTrades: ");
                
                // Travers entire linked list in a given bucket
                currentEntry = this.stockTradeHash[i];
                while (currentEntry != null) {
                    System.out.print (currentEntry.getStockTrade().getStockSymbol() + " ");
                    currentEntry = currentEntry.getNextNode();
                }
                
                System.out.println();
            }
        }
    }
    
    /**
     * find
     * Uses hash code of input stock trade symbol to search for and return matching
     * StockTrade.  If they are not in the log, returns null reference variable value.
     * 
     * @param stockSymbol
     * @return
     */
    public StockTrade find(String stockSymbol) {
        StockTrade logElement = null;
        MapEntry currentEntry = null;
        int hashSum = 0; 
        int i = 0;    // loop counter
        
        // Generate hash sum based on input stock symbol
        for (i = 0; i < stockSymbol.length(); ++i) {
            hashSum += (int) (stockSymbol.charAt(i));
        }
        
        // Access hash table using index key and determine if bin is empty or not
        if (this.stockTradeHash[hashSum % this.MAX_HASHMAP_SIZE] != null) {
            currentEntry = this.stockTradeHash[hashSum % this.MAX_HASHMAP_SIZE];
            
            // Search through linked list in bin until match found or end reached
            while (currentEntry != null) {
                    if (currentEntry.getStockTrade().getStockSymbol().equals(stockSymbol)) {
                        logElement = currentEntry.getStockTrade();
                        break;
                    }
                    
                    currentEntry = currentEntry.getNextNode();
                }
        }
        
        return logElement;
    }
}
