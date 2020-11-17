package cs310hartigan;

import java.util.Scanner;
import java.io.*;

/**
 * The CS310Hartigan class is the main class of the cs310hartigan package.  For
 * Week 1 of CS310, it will execute a series of seven different tests on the 
 * FundManager and StockTrade classes that are also included in this package.
 * Detailed descriptions of each test are included directly above where they are
 * implemented below.  In addition to the main method, CS310Hartigan also 
 * contains four static helper functions (setFundManagerAttributes, 
 * displayFundManagerAttributes, setStockTradeAttributes, and 
 * displayStockTradeAttributes).  These are used in the implementation of Test 3
 * and are also described in detail below.
 * 
 * @author Matthew Hartigan
 * @version 1.0
 */
public class CS310Hartigan {

   /**
    * main
    * Implements the seven tests (1a, 1b, 2a, 2b, 2c, 2d, 3) that are described in
    * the CS310 Week 1 rubric.  Detailed descriptions of the requirements of each
    * test are included below directly before they are implemented.
    * 
    * @param args the command line arguments
    */
   public static void main(String[] args) {
       final String INPUT_FILENAME = "input/assn1input2.txt";    // designated input file name
       String inputLine = "";    // string to hold each line read from input file
       String [] inputLineParsed = null;    // string array to hold parsed form of each line read from input file
       FileInputStream inputFileObject = null;
       Scanner inputFileScanner  = null;
       FundManager inputFundManager = null;    // instance of FundManager used to process input file in Test 3
       StockTrade inputStockTrade = null;    // instance of StockTrade used to process input file in Test 3
       
       // Test 1a
       // 1. Instantiate a new fund manager with hard-coded attributes
       // 2. Display attributes to screen using the toString method
       System.out.println("Running Test 1a:");
       FundManager fundManager1 = new FundManager("AAA1111", "John", "Smith", "313-3333", 0.03);    // Instantiate new fund manager
       System.out.println("First FundManager object:");
       System.out.println(fundManager1.toString());    // Display new fund manager contents using toString method
       System.out.println();
       
       
       // Test 1b
       // 1. Instantiate a new stock trade with hard-coded attributes
       // 2. Display attributes to screen using the toString method
       System.out.println("Running Test 1b:");
       StockTrade stockTrade1 = new StockTrade("XXWW", 11.11, 33, "RTA33344", false);    // Instantiate new stock trade
       System.out.println("First StockTrade object:");
       System.out.println(stockTrade1.toString());    // Display new stock trade contents using toString method
       System.out.println();
       
       
       // Test 2a
       // 1. Instantiate a second fund manager  with the same hard-coded attributes as used in Test 1a
       // 2. Display a label identifying the new object that has been created
       // 3. Use the toString method to display the object attributes to screen
       // 4. Use the equals method to compare the first and second objects.  Display message to screen stating if they are equal or not
       System.out.println("Running Test 2a:");
       FundManager fundManager2 = new FundManager("AAA1111", "John", "Smith", "313-3333", 0.03);
       System.out.println("Second FundManager object:");
       System.out.println(fundManager2.toString());
       
       if (fundManager1.equals(fundManager2)) {    // Check if equal
           System.out.println("First and second FundManager objects ARE equal");
       }
       else {
           System.out.println("First and second FundManager objects are NOT equal");
       }
       System.out.println();
       
       
       // Test 2b
       // 1. Instantiate a third fund manager that differs from the first two (Test 1a, 2a) by one or more attributes
       // 2. Display a label identifying the new object that has been created
       // 3. Use the toString method to display the object attributes to screen
       // 4. Use the equals method to compare the first and third objects.  Display message to screen stating if they are equal or not
       System.out.println("Running Test 2b:");
       FundManager fundManager3 = new FundManager("AAA2222", "John", "Smith", "313-3333", 0.03);
       System.out.println("Third FundManager object:");
       System.out.println(fundManager3.toString());
       
       if (fundManager1.equals(fundManager3)) {    // Check if equal
           System.out.println("First and third FundManager objects ARE equal");
       }
       else {
           System.out.println("First and third FundManager objects are NOT equal");
       }
       System.out.println();
       
       // Test 2c
       // 1. Instantiate a second stock trade with the same hard-coded attributes as used in Test 1b
       // 2. Display a label identifying the new object that has been created
       // 3. Use the toString method to display the object attributes to screen
       // 4. Use the equals method to compare the first and second objects.  Display message to screen stating if they are equal or not
       System.out.println("Running Test 2c:");
       StockTrade stockTrade2 = new StockTrade("XXWW", 11.11, 33, "RTA33344", false);
       System.out.println("Second StockTrade object:");
       System.out.println(stockTrade2.toString());
       
       if (stockTrade1.equals(stockTrade2)) {    // Check if equal
           System.out.println("First and second StockTrade objects ARE equal");
       }
       else {
           System.out.println("First and second StockTrade objects are NOT equal");
       }       
       System.out.println();
       
       // Test 2d
       // 1. Instantiate a third stock trade that differs from the first two (Test 1b, 2c) by one or more attributes
       // 2. Display a label identifying the new object that has been created
       // 3. Use the toString method to display the object attributes to screen
       // 4. Use the equals method to compare the first and third objects.  Display message to screen stating if they are equal or not
       System.out.println("Running Test 2d:");
       StockTrade stockTrade3 = new StockTrade("XXWW", 11.11, 33, "RTA33344", true);
       System.out.println("Third StockTrade object:");
       System.out.println(stockTrade3.toString());
       
       if (stockTrade1.equals(stockTrade3)) {    // Check if equal
           System.out.println("First and third StockTrade objects ARE equal");
       }
       else {
           System.out.println("First and third StockTrade objects are NOT equal");
       }       
       System.out.println();
       
       
       // Test 3
       // 1. Open input file designated by INPUT_FILE constant above (catch any FileNotFoundExceptions and exit program after displaying message to screen)
       // 2. Loop through entire input file, processing each line according to its contents
       //
       // 3. If first item of input line is string "BROKER":
       //        - Display if fund manager is being added or deleted to screen
       //        - Instantiate a new FundManager
       //        - Call setFundManagerAttributes method to set new FundManager instance's attributes to those specified in the input file
       //        - Using the new FundManager instance, check broker's license number and department data are valid
       //        - If any data is invalid, display error message and attributes to screen using the toString method
       //        - If all data is valid, call displayFundManagerAttributes method to display attributes to screen
       //
       // 4. If first item of input line is string "TRADE":
       //        - Display if stock trade is being added or deleted to screen
       //        - Instantiate a new StockTrade
       //        - Call setStockTradeAttributes method to set new StockTrade instance's attributes to those specified in the input file
       //        - Using the new StockTrade instance, check stock symbol, stock price and number of shares are valid
       //        - If any data is invalid, display error message and attributes to screen using the toString method
       //        - If all data is valid, call displayStockTradeAttributes method to display attributes to screen
       System.out.println("Running Test 3:");
       
       // Try block contains all code required to open input file and all code required to perform Test 3 described above.
       try {
           // Open input file
           inputFileObject = new FileInputStream(INPUT_FILENAME);
           inputFileScanner = new Scanner(inputFileObject);
           
           // Loop through every line of input file until no more are found.  Process each line according to its contents
           while (inputFileScanner.hasNextLine()) {
               inputLine = inputFileScanner.nextLine();    // Read next input line from file
               inputLineParsed = inputLine.split(",");    // Parse (csv file expected)
               
               // Handle input if line contains BROKER details
               if (inputLineParsed[0].equals("BROKER")) {
                   if (inputLineParsed[1].equals("ADD")) {    // Display add broker message to screen
                       System.out.println("ADDING BROKER");
                   }
                   else if (inputLineParsed[1].equals("DEL")) {     // Display delete broker message to screen
                       System.out.println("DELETING BROKER");
                   }
               
                   // Instantiate a new FundManager, populate attributes using setFundManagerAttributes
                   inputFundManager = new FundManager();    
                   inputFundManager = setFundManagerAttributes(inputFundManager, inputLineParsed);   

                   // Validate broker license and dept attributes that were loaded from input file
                   if (! inputFundManager.checkBrokerLicenseIsValid()) {    // Validate broker license
                       System.out.println(inputFundManager.toString());
                       System.out.println("ERROR: Invalid broker license: " + inputFundManager.getBrokerLicense());
                       System.out.println();
                   }
                   else if (! inputFundManager.checkDeptIsValid(inputFundManager.getDept())) {    // Validate dept
                       System.out.println(inputFundManager.toString());
                       System.out.println("ERROR: Invalid dept: " + inputFundManager.getDept());
                       System.out.println();
                   }
                   else {    // If broker license and dept are valid, display all fund manager attributes to screen
                       displayFundManagerAttributes(inputFundManager);
                   }
               }
               
               
               // Handle input if line contains TRADE details
               else if (inputLineParsed[0].equals("TRADE")) {
                   if (inputLineParsed[1].equals("BUY")) {    // Dislplay buy stock message
                       System.out.println("BUYING STOCK");
                   }
                   else if (inputLineParsed[1].equals("SELL")) {    // Dispaly sell stock message
                       System.out.println("SELLING STOCK");
                   }
                   
                   // Instantiate a new StockTrade, populate attributes using setStockTradeAttributes
                   inputStockTrade = new StockTrade();  
                   inputStockTrade = setStockTradeAttributes(inputStockTrade, inputLineParsed);    
                   
                   // Validate stock symbol, stock price, number of shares data that were loaded from input file
                   if (! inputStockTrade.checkStockSymbolIsValid(inputStockTrade.getStockSymbol())) {    // Validate stock symbol
                       System.out.println(inputStockTrade.toString());
                       System.out.println("ERROR: Invalid stock symbol: " + inputStockTrade.getStockSymbol());
                       System.out.println();
                   }
                   else if (! inputStockTrade.checkPricePerShareIsValid(inputStockTrade.getPricePerShare())) {     // Validate stock price
                       System.out.println(inputStockTrade.toString());
                       System.out.println("ERROR: Invalid stock price: " + inputStockTrade.getPricePerShare());
                       System.out.println();
                   }
                   else if (! inputStockTrade.checkWholeSharesIsValid(inputStockTrade.getWholeShares())) {    // Validate number of shares
                       System.out.println(inputStockTrade.toString());
                       System.out.println("ERROR: Invalid number of shares: " + inputStockTrade.getWholeShares());
                       System.out.println();
                   }
                   else {    // If broker license and dept are valid, display all fund manager attributes to screen
                       displayStockTradeAttributes(inputStockTrade);
                   }
               }
           }    // Close while loop
           
           inputFileObject.close();    // Close the input file object
           
       }    // Close try block
       
       // Catch block for Test 3 exceptions 
       catch (FileNotFoundException excpt) {
           System.out.println("Error: " + INPUT_FILENAME + " could not be found.");
           System.out.println("Exiting program.");
           System.exit(1);
       }
       catch (Exception excpt) {
           System.out.println("Error: Unexpected exception thrown while attempting to access " + INPUT_FILENAME);
           System.out.println("Exiting program.");
           System.exit(1);
       } 
   }    // Close main method


    /**
     * setFundManagerAttributes
     * This method is called from main during Test 3.  It receives a fund manager
     * object as well as a parsed line of input file data.  The method then sets
     * the values of the fund manager object using the parsed input file data and
     * returns the object to main.
     * 
     * @param inputFundManager
     * @param inputLineParsed
     * @return inputFundManager
     */
   public static FundManager setFundManagerAttributes(FundManager inputFundManager, String [] inputLineParsed) {
       inputFundManager.setBrokerLicense(inputLineParsed[2]);
       inputFundManager.setFirstName(inputLineParsed[3]);
       inputFundManager.setLastName(inputLineParsed[4]);
       inputFundManager.setDept(inputLineParsed[5]);
       inputFundManager.setCommissionRate(Double.parseDouble(inputLineParsed[6]));
       
       return inputFundManager;
   }


    /**
     * displayFundManagerAttributes
     * This method is called from main during Test 3.  It receives a fund manager
     * object whose attributes it then displays to screen one line at a time.
     * 
     * @param inputFundManager
     */
   public static void displayFundManagerAttributes(FundManager inputFundManager) {
       System.out.println(inputFundManager.getBrokerLicense());
       System.out.println(inputFundManager.getFirstName());
       System.out.println(inputFundManager.getLastName());
       System.out.println(inputFundManager.getDept());
       System.out.println(inputFundManager.getCommissionRate());
       System.out.println();
   }

   
    /**
     * setStockTradeAttributes
     * This method is called from main during Test 3.  It receives a stock trade
     * object as well as a parsed line of input file data.  The method then sets
     * the values of the stock trade object using the parsed input file data and
     * returns the object to main.
     * 
     * @param inputStockTrade
     * @param inputLineParsed
     * @return inputStockTrade
     */
   public static StockTrade setStockTradeAttributes(StockTrade inputStockTrade, String [] inputLineParsed) {
       inputStockTrade.setStockSymbol(inputLineParsed[2]);
       inputStockTrade.setPricePerShare(Double.parseDouble(inputLineParsed[3]));
       inputStockTrade.setWholeShares(Integer.parseInt(inputLineParsed[4]));
       inputStockTrade.setBrokerLicense(inputLineParsed[5]);
       inputStockTrade.setTaxable(inputLineParsed[6]);

       return inputStockTrade;
   } 

   
    /**
     * displayStockTradeAttributes
     * This method is called from main during Test 3.  It receives a stock trade
     * object whose attributes it then displays to screen one line at a time.
     * 
     * @param inputStockTrade
     */
   public static void displayStockTradeAttributes(StockTrade inputStockTrade) {
       System.out.println(inputStockTrade.getStockSymbol());
       System.out.println(inputStockTrade.getPricePerShare());
       System.out.println(inputStockTrade.getWholeShares());
       System.out.println(inputStockTrade.getBrokerLicense());
       System.out.println(inputStockTrade.getTaxable());
       System.out.println();
   }
}
