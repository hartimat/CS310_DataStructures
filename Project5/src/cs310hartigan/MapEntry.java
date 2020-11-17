package cs310hartigan;

/**
 * MapEntry is the node class for StockTrade elements that will be added
 * to the stock trade hash map.  The stock trade log hash map resolves
 * collisions by chaining MapEntry nodes together in linked lists.
 * 
 * @author Matthew Hartigan
 * @version Week 5
 */
public class MapEntry {
    
    // Data fields
    private StockTrade stockTrade;
    private MapEntry nextNode;
    
    
    // Constructors
    /**
     * Default constructor
     */
    public MapEntry() {
        this.stockTrade = null;
        this.nextNode = null;
    }

    /**
     *
     * @param inputStockTrade
     */
    public MapEntry(StockTrade inputStockTrade) {
        this.stockTrade = inputStockTrade;
        this.nextNode = null;
    }
    
    /**
     *
     * @param inputStockTrade
     * @param nextNode
     */
    public MapEntry(StockTrade inputStockTrade, MapEntry nextNode) {
        this.stockTrade = inputStockTrade;
        this.nextNode = nextNode;
    }
    
    
    // Getters and Setters

    /**
     *
     * @return
     */
    public StockTrade getStockTrade() {
        return stockTrade;
    }

    /**
     *
     * @param stockTrade
     */
    public void setStockTrade(StockTrade stockTrade) {
        this.stockTrade = stockTrade;
    }

    /**
     *
     * @return
     */
    public MapEntry getNextNode() {
        return nextNode;
    }

    /**
     *
     * @param nextNode
     */
    public void setNextNode(MapEntry nextNode) {
        this.nextNode = nextNode;
    }
  
}
