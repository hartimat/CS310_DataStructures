package cs310hartigan;

/**
 * Binary search tree node class for the FundManagerLogImpl class.
 * 
 * @author Matthew Hartigan
 * @version Week 6
 */
public class FundManagerTreeNode {
    
    // Data Fields
    private FundManager data = null;
    private FundManagerTreeNode leftNode = null;
    private FundManagerTreeNode rightNode = null;
    
    
    // Constructors
    /**
     * Default
     */
    public FundManagerTreeNode() {
    }
    
    
    /**
     *
     * @param root
     */
    public FundManagerTreeNode(FundManager root) {
        this.data = root;
    }
    
    
    // Getters and Setters
    /**
     *
     * @return
     */
    public FundManager getData() {
        return data;
    }

    /**
     *
     * @param data
     */
    public void setData(FundManager data) {
        this.data = data;
    }

    /**
     *
     * @return
     */
    public FundManagerTreeNode getLeftNode() {
        return leftNode;
    }

    /**
     *
     * @param leftNode
     */
    public void setLeftNode(FundManagerTreeNode leftNode) {
        this.leftNode = leftNode;
    }

    /**
     *
     * @return
     */
    public FundManagerTreeNode getRightNode() {
        return rightNode;
    }

    /**
     *
     * @param rightNode
     */
    public void setRightNode(FundManagerTreeNode rightNode) {
        this.rightNode = rightNode;
    }    
}
