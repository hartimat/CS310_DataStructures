package cs310hartigan;

import java.util.Objects;

/**
 * The FundManager class is part of the cs310hartigan package.  It is intended to
 * be instantiated to represent individual fund managers at a brokerage.  It features
 * attributes to keep track of a broker license number, first name, last name, 
 * department number, and commission rate.  Standard constructors, getters and 
 * setters are also included.  Finally, there are two helper functions 
 * (checkBrokerLicenseIsValid, checkDeptIsValid) to evaluate whether a given 
 * broker license or department number are valid.  For the Week 5 assignment,
 * a hashCode method was added.
 * 
 * @author Matthew Hartigan
 * @version Week 6
 */
public class FundManager {
    
    // Data fields
   private String brokerLicense = "";    // three letters followed by five digits
   private String firstName = "";    // broker's first name
   private String lastName = "";    // broker's last name
   private String dept = "";    // broker department number, 7 chars total: digits formatted with dash as: ###-###
   private double commissionRate = 0.0;    // represents a percentage

   
   // Constructors
    /**
     * Default Constructor
     */
    public FundManager() {
    }

    /**
     * Constructor to initialize every attribute in FundManager class
     * 
     * @param brokerLicense
     * @param firstName
     * @param lastName
     * @param dept
     * @param commissionRate
     */
    public FundManager(String brokerLicense, String firstName, String lastName, String dept, double commissionRate) {
        this.brokerLicense = brokerLicense;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dept = dept;
        this.commissionRate = commissionRate;
    }

    /**
     * Constructor to initialize every data field based on an input String array parsed
     * from an input file.
     * 
     * @param inputLineParsed
     */
    public FundManager(String [] inputLineParsed) {
        this.brokerLicense = inputLineParsed[2];
        this.firstName = inputLineParsed[3];
        this.lastName = inputLineParsed[4];
        this.dept = inputLineParsed[5];
        this.commissionRate = Double.parseDouble(inputLineParsed[6]);
    }
    
    
    // Getters and Setters
    /**
     * Get broker license
     * 
     * @return brokerLicense
     */
    public String getBrokerLicense() {
        return brokerLicense;
    }

    /**
     * Set broker license
     * 
     * @param brokerLicense
     */
    public void setBrokerLicense(String brokerLicense) {
        this.brokerLicense = brokerLicense;
    }

    /**
     * Get broker first name
     * 
     * @return firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Set broker first name
     * 
     * @param firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Get broker last name
     * 
     * @return lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Set broker last name
     * 
     * @param lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Get broker department number
     * 
     * @return dept
     */
    public String getDept() {
        return dept;
    }

    /**
     * Set broker department number
     * 
     * @param dept
     */
    public void setDept(String dept) {
        this.dept = dept;
    }

    /**
     * Get broker commission rate
     * 
     * @return commissionRate
     */
    public double getCommissionRate() {
        return commissionRate;
    }

    /**
     * Set broker commission rate
     * 
     * @param commissionRate
     */
    public void setCommissionRate(double commissionRate) {
        this.commissionRate = commissionRate;
    }

    
    // Equals
    /**
     * Check if two FundManager objects are equal.  Return true if they are equal.
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
        final FundManager other = (FundManager) obj;
        if (Double.doubleToLongBits(this.commissionRate) != Double.doubleToLongBits(other.commissionRate)) {
            return false;
        }
        if (!Objects.equals(this.brokerLicense, other.brokerLicense)) {
            return false;
        }
        if (!Objects.equals(this.firstName, other.firstName)) {
            return false;
        }
        if (!Objects.equals(this.lastName, other.lastName)) {
            return false;
        }
        if (!Objects.equals(this.dept, other.dept)) {
            return false;
        }
        return true;
    }
    
    // toString
    /**
     * Returns a string listing all attributes of a given instance of FundManager
     * 
     * @return String
     */
    @Override
    public String toString() {
        return "FundManager{" + "brokerLicense=" + brokerLicense + ", firstName=" + firstName + ", lastName=" + lastName + ", dept=" + dept + ", commissionRate=" + commissionRate + '}';
    }
    
    // hashCode
    /**
     * Generates and returns a hash code based on method given in Week 5 project spec.
     * 
     * @return int
     */
    @Override
    public int hashCode () {
        return Integer.parseInt(this.brokerLicense.substring(3, this.brokerLicense.length()));
    }
    
  
    // User-Defined Methods
    /**
     * checkBrokerLicenseIsValid
     * Checks to make sure the input
     * parameter has the following characteristics:
     * 1. Overall length is 8 characters
     * 2. First three characters in license are letters
     * 3. Last five characters in license are numbers
     * 
     * @return boolean
     */
    public boolean checkBrokerLicenseIsValid() {
        boolean brokerLicenseIsValid = true;
        int i = 0;    // counter variable
        
        if (brokerLicense.length() != 8) {    // Check eight characters long
            brokerLicenseIsValid = false;
        }
        
        if (brokerLicenseIsValid) {
            for (i = 0; i < 3; ++i) {    // Check first three characters in license are letters
                if (! Character.isLetter(brokerLicense.charAt(i))) {
                    brokerLicenseIsValid = false;
                    break;
                }
            }
        }
            
        if (brokerLicenseIsValid) {
            for (i = 3; i < 8; ++i) {    // Check last five characters in license are numbers
                if (! Character.isDigit(brokerLicense.charAt(i))) {
                    brokerLicenseIsValid = false;
                }
            }
        }
                
        return brokerLicenseIsValid;
    }

    /**
     * checkDeptIsValid
     * Checks to make sure
     * the input parameter has the following characteristics:
     * 1. Overall length is 7 characters
     * 2. The middle character is a dash
     * 3. The first three characters are a 1, 2 or 3
     * 4. The last three characters are digits
     * 
     * @return boolean
     */
    public boolean checkDeptIsValid() {
        boolean deptIsValid = true;
        int i = 0;    // counter variable
        
        if (dept.length() != 7) {    // Check length of dept number is 7 characters
            deptIsValid = false;
        }
        
        if (deptIsValid) {
            if (! (dept.indexOf('-') == 3)) {    // Check dash in middle of dept number
                deptIsValid = false;
            }
        }
        
        if (deptIsValid) {
            for (i = 0; i < 3; ++i) {    // Check first three characters are 1, 2 or 3
                if (dept.charAt(i) != '1') {
                    if (dept.charAt(i) != '2') {
                        if (dept.charAt(i) != '3') {
                            deptIsValid = false;
                        }
                    }
                }
            }
        }
        
        if (deptIsValid) {
            for (i = 4; i < dept.length(); ++i) {    // Check last three characters are digits
                if (! Character.isDigit(dept.charAt(i))) {
                    deptIsValid = false;
                }
            }
        }

        return deptIsValid;
    }
}
