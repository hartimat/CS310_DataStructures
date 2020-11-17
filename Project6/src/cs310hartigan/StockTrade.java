package cs310hartigan;

import java.util.Objects;

/**
 * The StockTrade class is part of the cs310hartigan package.  It is intended to
 * be instantiated to represent individual stock trades at a brokerage.  It features
 * attributes to keep track of a given trade's stock symbol, stock price,
 * number of whole shares, broker license number and whether or not it is taxable.
 * Standard constructors, getters and setters are also included.  Finally, there are three 
 * helper functions (checkStockSymbolIsValid, checkPricePerShareIsValid, checkWholeSharesIsValid) 
 * to evaluate whether a given trade symbol, price per share or number of shares
 * are valid.  For the Week 5 assignment, a hashCode method was added.
 * 
 * @author Matthew Hartigan
 * @version Week 6
 */
public class StockTrade {
    
// Data fields
     private String stockSymbol = "";    // 3 or 4 char string, all uppercase
     private double pricePerShare = 0.0;    // USD
     private int wholeShares = 0;    // assume only whole shares are traded
     private String brokerLicense = "";    // to match license numbers from FundManager class
     private boolean taxable = false;

     
    // Constructors
    /**
     * Default Constructor
     */
    public StockTrade() {
    }
    
    /**
     * Constructor to initialize every attribute in StockTrade class
     * 
     * @param stockSymbol
     * @param pricePerShare
     * @param wholeShares
     * @param brokerLicense
     * @param taxable
     */
    public StockTrade (String stockSymbol, double pricePerShare, int wholeShares, String brokerLicense, boolean taxable) {
        this.stockSymbol = stockSymbol;
        this.pricePerShare = pricePerShare;
        this.wholeShares = wholeShares;
        this.brokerLicense = brokerLicense;
        this.taxable = taxable;
    }

    /**
     * Constructor to initialize every data field based on an input String array parsed
     * from an input file.
     * 
     * @param inputLineParsed
     */
    public StockTrade (String [] inputLineParsed) {
        this.stockSymbol = inputLineParsed[2];
        this.pricePerShare = Double.parseDouble(inputLineParsed[3]);
        this.wholeShares = Integer.parseInt(inputLineParsed[4]);
        this.brokerLicense = inputLineParsed[5];
        setTaxable(inputLineParsed[6]);
    }
    
    
    // Getters and Setters
    /**
     * Get stock symbol
     * 
     * @return stockSymbol
     */
    public String getStockSymbol() {
        return stockSymbol;
    }

    /**
     * Set stock symbol
     * 
     * @param stockSymbol
     */
    public void setStockSymbol(String stockSymbol) {
        this.stockSymbol = stockSymbol;
    }

    /**
     * Get price per share
     * 
     * @return pricePerShare
     */
    public double getPricePerShare() {
        return pricePerShare;
    }

    /**
     * Set price per share
     *  
     * @param pricePerShare
     */
    public void setPricePerShare(double pricePerShare) {
        this.pricePerShare = pricePerShare;
    }

    /**
     * Get number of whole shares
     * 
     * @return wholeShares
     */
    public int getWholeShares() {
        return wholeShares;
    }

    /**
     * Set number of whole shares
     * 
     * @param wholeShares
     */
    public void setWholeShares(int wholeShares) {
        this.wholeShares = wholeShares;
    }

    /**
     * Get broker license number
     * 
     * @return brokerLicense
     */
    public String getBrokerLicense() {
        return brokerLicense;
    }

    /**
     * Set broker license number
     * 
     * @param brokerLicense
     */
    public void setBrokerLicense(String brokerLicense) {
        this.brokerLicense = brokerLicense;
    }

    /**
     * Get taxable (boolean)
     * 
     * @return taxable
     */
    public boolean getTaxable() {
        return taxable;
    }

    /**
     * Set taxable (boolean)
     * 
     * @param taxable
     */
    public void setTaxable(boolean taxable) {
        this.taxable = taxable;
    }
    
    /**
     * Set taxable (String)
     * Overloaded setter for data field taxable
     * 
     * @param taxable
     */
    public void setTaxable(String taxable) {
       this.taxable = false;
       if (taxable.equalsIgnoreCase("Y")) {
           this.taxable = true;
       }
    }
        
    
    // Equals
    /**
     * Check if two StockTrade objects are equal.  Return true if they are equal.
     * Return false if they are not.
     * 
     * @param obj
     * @return boolean
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final StockTrade other = (StockTrade) obj;
        if (Double.doubleToLongBits(this.pricePerShare) != Double.doubleToLongBits(other.pricePerShare)) {
            return false;
        }
        if (this.wholeShares != other.wholeShares) {
            return false;
        }
        if (this.taxable != other.taxable) {
            return false;
        }
        if (!Objects.equals(this.stockSymbol, other.stockSymbol)) {
            return false;
        }
        if (!Objects.equals(this.brokerLicense, other.brokerLicense)) {
            return false;
        }
        return true;
    }

    // toString
    /**
     * Returns a string listing all attributes of a given instance of StockTrade
     * 
     * @return String
     */
    @Override
    public String toString() {
        return "StockTrade{" + "stockSymbol=" + stockSymbol + ", pricePerShare=" + pricePerShare + ", wholeShares=" + wholeShares + ", brokerLicense=" + brokerLicense + ", taxable=" + taxable + '}';
    }
    
    // hashCode
    /**
     * hashCode
     * Generates and returns a hash code based on method given in Week 5 project spec.
     * 
     * @return
     */
    @Override
    public int hashCode() {
        int sum = 0;
        int i = 0;    // loop counter

        for (i = 0; i < this.stockSymbol.length(); ++i) {
            sum += (int) (this.stockSymbol.charAt(i));
        }
        
        return sum;
    }
        
    
    // Validity checking methods
    /**
     * checkStockSymbolIsValid
     * Checks to make sure the input parameter has the following characteristics:
     * 1. Overall length is 3 or 4 characters
     * 2. Each character is a letter
     * 3. Each character is upper case
     * 
     * @return boolean
     */
    public boolean checkStockSymbolIsValid() {
        boolean stockSymbolIsValid = true;
        int i = 0;
        
        if (stockSymbol.length() != 3 && stockSymbol.length() != 4) {    // Check overall length is 3 or 4
            stockSymbolIsValid = false;
            }
        
        if (stockSymbolIsValid) {
            for (i = 0; i < stockSymbol.length(); ++i) {
                if (! Character.isLetter(stockSymbol.charAt(i))) {    // Check each character is a letter
                    stockSymbolIsValid = false;
                }
            }
        }

        if (stockSymbolIsValid) {
            for (i = 0; i < stockSymbol.length(); ++i) {
                if (! Character.isUpperCase(stockSymbol.charAt(i))) {    // Check each character is upper case
                    stockSymbolIsValid = false;
                }
            }
        }
        
        return stockSymbolIsValid;
    }

    /**
     * checkPricePerShareIsValid
     * Checks to make sure price per share is less than 
     * the MAX_PRICE_PER_SHARE
     * 
     * @return boolean
     */
    public boolean checkPricePerShareIsValid() {
        final double MAX_SHARE_PRICE = 1000.0;
        boolean pricePerShareIsValid = true;
        
        if (pricePerShare >  MAX_SHARE_PRICE) {
            pricePerShareIsValid = false;
        }
        
        return pricePerShareIsValid;
    }

    /**
     * checkWholeSharesIsValid
     * Takes the number of whole shares as int input.  Checks to maek sure it is
     * less than the MAX_WHOLE_SHARES
     *  
     * @return boolean
     */
    public boolean checkWholeSharesIsValid() {
        final double MAX_WHOLE_SHARES = 100000;
        boolean wholeSharesIsValid= true;
        
        if (wholeShares >  MAX_WHOLE_SHARES) {
            wholeSharesIsValid = false;
        }

        return wholeSharesIsValid;
    }
}
