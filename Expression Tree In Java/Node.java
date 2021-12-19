package hw1;

public class Node {
    String data;
    Node left;
    Node right;
    long code = 0;
    int isLeftLeaf,isRightLeaf,isRoot,isRootLeftChild,isRootRightChild,parenthesisToPrint;
    
    public Node(String c){
        data = c;
        left = right = null;
       
        isLeftLeaf = 0;
        isRightLeaf = 0;
        isRoot = 0;
        isRootLeftChild=0;
        isRootRightChild=0;
        parenthesisToPrint=0;
    }
}

