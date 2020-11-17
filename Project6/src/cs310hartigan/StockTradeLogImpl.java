package cs310hartigan;


import java.util.TreeMap;
import java.util.Iterator;


/**
 * The StockTradeLogImpl class uses the TreeMap class from the Java
 * Collections framework to create a log of StockTrades that is a
 * binary search tree.
 * 
 * @author Matthew Hartigan
 * @version Week 6
 */
public class StockTradeLogImpl implements LogInterfaceImpl {
    
    // Data Fields
    private TreeMap<String, StockTrade> stockTradeTreeMap = null;
    
    
    // Constructors
    /**
     *
     */
    public StockTradeLogImpl() {
        this.stockTradeTreeMap = new TreeMap<String, StockTrade>();
    }
    
    
    // Getters and Setters
    /**
     *
     * @return
     */
    public TreeMap<String, StockTrade> getStockTradeTreeMap() {
        return stockTradeTreeMap;
    }

    /**
     *
     * @param stockTradeTreeMap
     */
    public void setStockTradeTreeMap(TreeMap<String, StockTrade> stockTradeTreeMap) {
        this.stockTradeTreeMap = stockTradeTreeMap;
    }


    // Methods
    /**
     * addToLog
     * This method adds an element to the StockTrade data structure
     * 
     * @param obj
     * @return addToLogSuccessful
     */
    public boolean addToLog(Object obj) {
        
        // Cast input parameter to FundManager object
        StockTrade inputStockTrade = (StockTrade) obj;
        
        // Put into binary search tree
        this.stockTradeTreeMap.put(inputStockTrade.getStockSymbol(), inputStockTrade); 
        
        return true;
    }
    
    /**
     * removeFromLog
     * This method removes an element from the StockTrade linked list
     * data structure.  
     * 
     * Note: This method is not utilized during the Week 6 assignment.  
     * However, it was left in to ensure that the FundManagerLogImpl class
     * still correctly implements the LogInterfaceImpl class.
     * 
     * @param obj
     * @return boolean
     */    
    public boolean removeFromLog (Object obj) {
        StockTrade inputStockTrade = (StockTrade) obj;    // Downcast to StockTrade
        
        // Method not implemented per Week 6 spec
        // Left in order to ensure LogInterfaceImpl is correctly implemented
        
        return true;
    }
    
    /**
     * traverseDisplay
     * Traverses entire binary search tree in-order and displays contents to screen.
     * 
     * @param it
     */
    public void traverseDisplay(Iterator it) {

        if (it.hasNext()) {
            System.out.println(it.next());
            traverseDisplay(it);
        }
    }
    
    /**
     * find
     * Searches binary search tree for input target value, returns value if found.
     * Otherwise returns null.
     * 
     * @param key
     * @return
     */
    public StockTrade find(String key) {
        return this.stockTradeTreeMap.get(key);
    }
}
