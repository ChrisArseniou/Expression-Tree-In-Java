package hw1;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class ArithmeticCalculator {
    String line,stringToString="";
    Node root;
    long hashCodeHelp=0;
    int EndProgram=0,parenthesis=0;
    
    public ArithmeticCalculator(String line){
        int i,j=0,k,l,parenthesis=0;
        String check = ". 0123456789()+-/*x^";
        String operators = "^+-x*/";
        String numbers = "0123456789";
        String restOf="",extendo="",ReverseStringToBeExtended="",StringToBeExtended="",operator="";
        double extension=0;
        Node n;
        
        for(i=0; i < line.length(); i++) {
            extendo="";
            ReverseStringToBeExtended="";
            StringToBeExtended="";
            
            if (line.charAt(i) == '\\'){ 
                if((i > (line.length()-3)) || (operators.indexOf(line.charAt(i+1)) == -1) || (numbers.indexOf(line.charAt(i+2)) == -1)){
                   System.out.println("[ERROR] Invalid expansion expression\n");
                   EndProgram = 1;
                   return;
                }

                k=i+2;//thetoume to k stin thesi tou akeraiou
               //pairnoume ton arithmo pou tha epanalifthei h ekfrash
                do{
                    extendo = extendo + line.charAt(k);
                    if(k < (line.length()-1)){
                        k++;
                    }
                    else {
                        break;
                    }
                }while(numbers.indexOf(line.charAt(k)) != -1 || (line.charAt(k) == ' ') || (line.charAt(k) == '.') );
               
                extension = Double.parseDouble(extendo);
                
                //elegxo an einai akeraios
                if(extension != (int)extension){
                    System.out.println("[ERROR] Invalid expansion expression\n");
                    EndProgram=1;
                    return;
                }
                
                if(k == (line.length()-1)){
                    restOf = "";
                }
                else {
                    restOf = line.substring(k);
                }
               
                operator = line.substring(i+1,i+2);
                
                if(extension > 1){
                    l = i - 1;//mia thesi prin to \
                    do{
                        ReverseStringToBeExtended = ReverseStringToBeExtended + line.charAt(l);

                        if (line.charAt(l)== ')'){
                            parenthesis--;
                        }
                        else if(line.charAt(l)== '('){
                            parenthesis++;
                        }

                        if(l>0){
                            if(line.charAt(l-1) == '.'){
                                l--;
                                continue;
                            }  
                            if((parenthesis == 0) && (numbers.indexOf(line.charAt(l-1))==-1)){
                                 break;
                            }
                        }
                        l--;
                    }while(l>=0);
                    
                   
                    line = line.substring(0,l) + "(" +  line.substring(l);
                    j++; //auxanw ton arithmo ton anixtwn parenthesewn
                                        
                    for(l=0;l<ReverseStringToBeExtended.length();l++){
                        StringToBeExtended = StringToBeExtended + ReverseStringToBeExtended.substring(ReverseStringToBeExtended.length()-l-1,ReverseStringToBeExtended.length()-l);
                    }

                    k = i+1;// to k einai h thesi pou to prosthetoume ston pinaka
                    for(l=1;l<extension;l++){
                        line = line.substring(0,k) + " " + operator + " " + StringToBeExtended;
                        k= k + 1 + 1 + 1 + StringToBeExtended.length();                  
                    
                    
                    }
                    line = line + ")" + restOf;

               }
               else if(extension == 1) {
                    if(i == (line.length()-3)){
                        line = line.substring(0,i);
                    }
                    else if(i < (line.length()-3)){
                        line = line.substring(0,i) + restOf;
                    }
               }
            }
            else if (line.charAt(i)== '('){
                if(i == (line.length() - 1)){
                    System.out.println("[ERROR] Not closing opened parenthesis\n");
                    EndProgram = 1;
                    return;
                }
                else if((operators.indexOf(line.charAt(i+1)) != -1) || (line.charAt(i+1) == '\\') ){
                    System.out.println("[ERROR] Operand appears after opening parenthesis\n");
                    EndProgram = 1;
                    return;
                }
                else if(line.charAt(i+1) == ')'){
                    System.out.println("[ERROR] Empty Parenthesis!\n");
                    EndProgram = 1;
                    return;
                }
                j=j+1;
            }
            else if (line.charAt(i)== ')'){
                if(i == 0){
                    System.out.println("[ERROR] Closing unopened parenthesis!\n");
                    EndProgram = 1;
                    return;
                }
                else if((operators.indexOf(line.charAt(i-1)) != -1) || (line.charAt(i-1) == '\\') ){
                    System.out.println("[ERROR] Operand appears before closing parenthesis\n");
                    EndProgram = 1;
                    return;
                }
                else if(line.charAt(i-1) == '('){
                    System.out.println("[ERROR] Empty Parenthesis!\n");
                    EndProgram = 1;
                    return;
                }
                j=j-1;
            }
            else if ((check.indexOf(line.charAt(i)) == -1) && (line.charAt(i) != '\\') ){
                //vriskei kapoion xaraktira poy den theloume
                System.out.println("[ERROR] Invalid character\n");
                EndProgram = 1;
                return;
            }
            else if((line.charAt(i) == '\\')  || (operators.indexOf(line.charAt(i)) != -1)){
                //vriskei kapoion telesti kai elengei an sthn epomeni thesi yparxei pali telestis
                if(i == (line.length() - 1)){
                        System.out.println("[ERROR] Operator at the end!\n");
                        EndProgram = 1;
                        return;
                }
                if((operators.indexOf(line.charAt(i+1)) != -1) /*|| (line.charAt(i) == '\\')*/){
                    System.out.println("[ERROR] Two consecutive operands\n");
                    EndProgram = 1;
                    return;
                }
            }         
        }

        if(j>0){
            //an oi anoixtes pantheseis einai parapanw apo tis kleistes
            System.out.println("[ERROR] Not closing opened parenthesis\n");
            EndProgram = 1;
            return;
        }
        else if(j<0){
            //an oi kleistes parentheseis einai parapanw apo tiw anoixtes
            System.out.println("[ERROR] Closing unopened parenthesis\n");
            EndProgram = 1;
            return;
        }
        n = NodeExpressionTreeBuilder(line);
    }
    
    public int EndProgramSignal(){
        return EndProgram;
    }
    
    public Node newNode(String c){
        //dimiourgei kainoirgio komvo
        Node n = new Node(c);
        return n;
    }
       
    public void postorder(Node root){
      
        if (root != null){
            postorder(root.left);
            postorder(root.right);
        }
    }
    
    public Node NodeExpressionTreeBuilder(String S){
        int i,k;
        String numbersDotSpace = " .0123456789";
        String numbers = "0123456789";
        String operators = "+-/*x";
        char operator;
                
        Stack<Node> stackNode = new Stack<>();
        Stack<Character> stackChar = new Stack<>();
        Node n, n1, n2;
        
        int priorities[] = new int[150];
        
        //orizoume tis proteraiotites twn telestwn
        priorities['+'] = priorities['-'] = 1;
        priorities['/'] = priorities['*'] = priorities['x'] = 2;
        priorities['^'] = 3;
        priorities[')'] = 0;
        
        for(i=0; i<S.length();){
            k=0;
            if(S.charAt(i) == '('){
                stackChar.add(S.charAt(i));
                i++;
            }
            else if (numbers.indexOf(S.charAt(i)) != -1 ){
                k=i+1;
                while(true){
                    if((k<S.length())){
                        //vriskei olo ton arithmo
                        if(numbersDotSpace.indexOf(S.charAt(k)) != -1){
                            k++;
                        }
                        else { 
                           n = newNode(S.substring(i,k));
                           stackNode.add(n);
                           i = k;
                           break;
                        }
                    }
                    else {
                        //EFTASE STO TELOS TOY STRING   
                        n = newNode(S.substring(i));
                        stackNode.add(n);
                        i = k;
                        if(i==(S.length())){
                            while(!stackChar.isEmpty()){
                                operator = stackChar.peek();
                                n = newNode(String.valueOf(operator));
                                stackChar.pop();

                                n1 = stackNode.peek();
                                stackNode.pop();

                                n2 = stackNode.peek();
                                stackNode.pop();

                                n.left = n2;
                                n2.isLeftLeaf = 1;
                                
                                n.right = n1;
                                n1.isRightLeaf = 1;

                               stackNode.add(n);
                            }
                        }                                             
                        break;
                    }
                }
            }
            else if(S.charAt(i) == ' ' || S.charAt(i) == '.'){
                i++;           
            }
            else if((priorities[S.charAt(i)] > 0)){        
                
                while(   (!stackChar.isEmpty()) && (stackChar.peek() != '(') && priorities[stackChar.peek()] >=  priorities[S.charAt(i)]    
                       /* sta sxolia vriskete o kwdikas gia na tirithei h swsth proteraiothta tou telesth ^
                        &&  ((S.charAt(i) != '^' && priorities[stackChar.peek()] >=  priorities[S.charAt(i)] )
                        || (S.charAt(i) == '^'
                        && priorities[stackChar.peek()] >  priorities[S.charAt(i)] )) */ ){
                   
                    operator = stackChar.peek();
                    n = newNode(String.valueOf(operator));
                    stackChar.pop();
                    
                    n1 = stackNode.peek();
                    stackNode.pop();
                    
                    n2 = stackNode.peek();
                    stackNode.pop();
                    
                    n.left = n2;
                    n2.isLeftLeaf = 1;
                    
                    n.right = n1;
                    n1.isRightLeaf = 1;                    
                    
                    stackNode.add(n);      
                }
                stackChar.push(S.charAt(i));
                i++;
            }
            else if(S.charAt(i) == ')'){
                while(!stackChar.isEmpty() && stackChar.peek() != '('){
                    
                    operator = stackChar.peek();
                    n = newNode(String.valueOf(operator));
                    stackChar.pop();    
                    
                    n1 = stackNode.peek();
                    stackNode.pop();
                    
                    n2 = stackNode.peek();
                    stackNode.pop();
                    
                    n.left = n2;
                    n2.isLeftLeaf = 1;
                    
                    n.right = n1;
                    n1.isRightLeaf = 1;
                    
                    stackNode.add(n);  
                }

                stackChar.pop();
                i++;
            }
        }
        
        while(!stackChar.isEmpty() && !stackNode.isEmpty() ){
                    
            operator = stackChar.peek();
            n = newNode(String.valueOf(operator));
            stackChar.pop();    
                    
            n1 = stackNode.peek();
            stackNode.pop();
                    
            n2 = stackNode.peek();
            stackNode.pop();
                    
            n.left = n2;
            n2.isLeftLeaf = 1;
                    
            n.right = n1;
            n1.isRightLeaf = 1;
            
            stackNode.add(n);  
        }
        
        n = stackNode.peek();
        n.isRoot = 1;
        n.left.isRootLeftChild = 1;
        n.right.isRootRightChild = 1;
        root = n;
        return n;
    }
    
    public String preOrderStringBuilder(Node root){
        String mainString = "";
        
        //Me mia preorder diaperash xtizoume to dotstring
        
        if(root == null){          
            return  "";
        }
        
        mainString = mainString + "    " + root.code + " [label=" + "\"" + root.data + "\"" + "]\n" ;
        
        if(root.left != null){
            mainString = mainString + "    " + root.code + " -- " + root.left.code + "\n";
        }
   
        mainString = mainString + preOrderStringBuilder(root.left);
        
        if(root.right != null){
            mainString = mainString + "    " + root.code + " -- " + root.right.code + "\n";
        }

        mainString = mainString + preOrderStringBuilder(root.right);
        return mainString;    
    }
    
    public void SetCode(Node root){
        //orizoume ta unique hashCode gia kathe komvo 
         if (root != null){
            hashCodeHelp = hashCodeHelp + 1;
            SetCode(root.left);
            hashCodeHelp = hashCodeHelp + 1;
            SetCode(root.right);
            hashCodeHelp = hashCodeHelp + 1;
            if(root.code == 0){
                root.code = hashCodeHelp;
            }
        }
    }

    public String toDotString(){
        String dotString,entryString,mainString;
        
        entryString = "graph ArithmeticExpressionTree {\n";
       
        hashCodeHelp = 0;
        SetCode(root);
        
        mainString = preOrderStringBuilder(root);
        
        dotString = entryString + mainString + "}";

        return dotString;
    
    }
    
    public void setParenthesis(Node root){
        //orizoume ton swsto arithmo parentheswn gia kathe komvo
        if(root!=null){
            
            if(root.isRoot==1){
              root.parenthesisToPrint = 0;
             parenthesis=0;
            }
            else if(root.isRootLeftChild == 1){
                root.parenthesisToPrint = 0;
                parenthesis=0;
            }
            else if(root.isRootRightChild == 1){
                root.parenthesisToPrint = 0;
                parenthesis=0;
            }
            else if(root.isLeftLeaf == 1){
                parenthesis++;
                root.parenthesisToPrint = parenthesis;                
            }
            else if(root.isRightLeaf == 1){
                parenthesis=0;
                root.parenthesisToPrint = 0;               
            }

            setParenthesis(root.left);
            setParenthesis(root.right);       
        }
    }
    
    public void postorderBuilder(Node root){
        //xtizoume thn epithematiki morfh
        String numbers = "0123456789";
        String operators = "+-*/x^";
        String parenthesisToPrint = "";
        int i;
                
        if (root != null){
            postorderBuilder(root.left);
            postorderBuilder(root.right);
            
            for(i=0;i<root.parenthesisToPrint;i++){
               parenthesisToPrint = parenthesisToPrint + "(";
           }
            
           if (operators.indexOf(root.data.charAt(0)) == -1){
                stringToString = stringToString + parenthesisToPrint;
                stringToString = stringToString + "(" + root.data + ")";
           }
           else if(root.isRoot == 1){
               stringToString = stringToString + root.data;
           }
           else if (operators.indexOf(root.data.charAt(0)) != -1){
                stringToString = stringToString + root.data + ")";
           }
        }
    }
    
    /**
     *
     * @return
     */
    @Override
    public String toString() {
        String ReturnString;
        
        stringToString = "";
        parenthesis = 0;
        setParenthesis(root);
        postorderBuilder(root);
        return stringToString;
    }
    
    public double value(Node root){
        //ypologiszoume thn double timh toy string
        double value=0,leftValue,rigthValue;
        int i;
        
        if(root == null){
            return(0);
        }
        
        if(root.left == null && root.right == null){
            value = Double.parseDouble(root.data);
            return (value);
        }
        
        leftValue = value(root.left);
        rigthValue = value(root.right);
        
        if(root.data.charAt(0) == '+'){
            return leftValue+rigthValue;
        
        }
        else if(root.data.charAt(0) == '-'){
            return leftValue-rigthValue;
        
        }
        else if(root.data.charAt(0) == '*' || root.data.charAt(0) == 'x' ){
            return leftValue*rigthValue;
        
        }
        else if(root.data.charAt(0) == '/'){
            return leftValue/rigthValue;
        
        }
        else if(root.data.charAt(0) == '^'){
          return Math.pow(leftValue,rigthValue);
        }
        return value;
    }
    
    public double calculate(){
        double result;
        
        result = value(root);

        return result;
    }
    
    public static void main(String[] args) throws IOException {
        ArithmeticCalculator Ar;
        Node n;
        String dotString;
        String toString;
        int EndProgram=0,i;
        double result;
        
        java.util.Scanner sc = new java.util.Scanner(System.in);
        System.out.print("Expression: \n");
        String line = sc.nextLine();

  
        line =  line.replaceAll(" ", "");
        line =  line.replaceAll("\t", "");

        Ar = new ArithmeticCalculator(line);

        EndProgram = Ar.EndProgramSignal(); 
        
        if(EndProgram == 1){           
            return;
        }
        
        String options = sc.nextLine();
        
        for(i=0;i<options.length()-1;i++){
            if("-d".equals(options.substring(i,i+2))){
                dotString = Ar.toDotString();
                System.out.println(dotString + "\n");
                File myDot = new File("mydot");
                BufferedWriter writer = new BufferedWriter(new FileWriter(myDot));
                writer.write(dotString);
                writer.close();
            }
            else if("-s".equals(options.substring(i,i+2))){
                toString = Ar.toString();
                System.out.println("Postfix: "  + toString + "\n");
            }
            else if("-c".equals(options.substring(i,i+2))){
                result = Ar.calculate();
                System.out.println("Result: " + result + "\n");
            }
        }   
    }
}
