import java.util.*;
import java.io.*;

public class RelationInterpreter {

public static String[] preferences = {"(", ")", "+", "-", "*", "/", "%", "^", "ln"};
public static MyStack stackList;
	
	public static String emptyStack(String ending){
		//outputs every string in the stack up to (but not including) the ending string
		//ending string is popped out of the array but not returned
		String output = "";
		
		while(!stackList.isEmpty()){
			if(stackList.peek().equals("("))
				break;
			output += stackList.pop() + " ";
		}
		if(!ending.equals("end"))
			stackList.pop();
		return output;
	}
	public static int isOperator(String symbol){// O(n)
		for(int i = 0; i<preferences.length; i++){
			if(symbol.equals(preferences[i])){
				return i;
			}
		}
		return -1;
	}
	public static int compareOperators(String op1, String op2){
		//1 is for stack is ordered correctly and 0 is for empty stack and store value
		int opVal1 = isOperator(op1);
		int opVal2 = isOperator(op2);
		if(opVal1 <= 1 || opVal2 <=1 || opVal1>1 && opVal1 <=3 && opVal2>3 || opVal1 > 3 && opVal1<7 && opVal2 >=7)
			return 1;
		return 0;
	}
	
	public static String infixToPostfix(String expression){
		expression = expression.replaceAll(" ", "");
		stackList = new MyStack();
		//stack = new ArrayList<String>();
		String[] exp = makeSet(expression);
		String output = "";
		
		for(int i = 0; i<exp.length; i++){
			if(exp[i].equals(" "))
				continue;
			int operatorValue = isOperator(exp[i]);
			if(operatorValue >= 0){
				if(stackList.isEmpty() || compareOperators(stackList.peek(), exp[i]) == 1 && operatorValue !=1){
					stackList.push(exp[i]);
				}else if(operatorValue == 1){
					output +=emptyStack("(");
				}else if(compareOperators(stackList.peek(), exp[i]) == 0){
					output+=emptyStack("end");
					stackList.push(exp[i]);
				}
			
			}else output += exp[i] + " ";
		}output += emptyStack("end");
		return output.trim();
	}
	public static String[] makeSet(String expression){
		String exp = "";
		for(int i = 0; i < expression.length(); i+=1){
			if(expression.substring(i, i+1).equals(" "))
				continue;
			if(isOperator(expression.substring(i, i+1)) >=0)
				if(i>0 && isOperator(expression.substring(i-1, i)) >=0)
					exp += expression.substring(i, i+1) + " ";
				else exp += " " + expression.substring(i, i+1) + " ";
			else exp += expression.substring(i, i+1);
		}exp = exp.trim();
		
		String[] outputList = exp.split(" ");
		
		return outputList;
	}

	public static double[][] generateCoordinates(double[] domain, double[] range, String function, String depen){
		HashMap<String , Double> map = new HashMap<String, Double>();
		String z_xy = function.split("=")[1] + "-(" + function.split("=")[0] + ")";
		z_xy = infixToPostfix(z_xy);
		double[][] setCoordinates = new double[100000][2];
		
		for(double i = domain[0]; i<=domain[1]; i += (domain[1]-domain[0])/1000.0){
			map.put(depen, i);
			if(evaluateExpression(map, z_xy) == 0)
		}
		
		int j =1;
		for(double i = domain[0]; i <=domain[1]; i += (domain[1]-domain[0])/1000.0){
			map.put(depen, i);
			setCoordinates[j][0] = i;
			setCoordinates[j][1] = evaluateExpression(map, f_x);
			j++;
		}return setCoordinates;
	}
	
	public static double evaluateExpression(HashMap<String, Double> map, String f_x){
		String[] listF = f_x.split(" ");
		
		for(int i = 0; i<listF.length; i++){
			try{
				if(isOperator(listF[i]) < 0)
					Double.parseDouble(listF[i]);
			}catch(Exception e){
				//System.out.println(listF[i]);
				listF[i] = "" + map.get(listF[i]);
			}
		}
		String exp = "";
		for(int i = 0; i<listF.length; i++){
			exp += listF[i] + " ";
		}exp = exp.substring(0, exp.length()-1);
		
		String[] listExp = exp.split(" ");
		Stack<Double> s = new Stack<Double>();
		//System.out.println(exp);

		//will have to right my own ;-;
		for(int i = 0; i<listExp.length; i++){
			try{
				s.push(Double.parseDouble(listExp[i]));
			} catch(Exception e) {
				//System.out.println(listExp[i]);
				double num2 = s.pop();
				double num1 = s.pop();
				String op = listExp[i];
				
				if (op.equals("+")) {
					s.push(num1 + num2);
				} else if (op.equals("-")) {
					s.push(num1 - num2);
				} else if (op.equals("*")) {
					s.push(num1 * num2);
				} else if (op.equals("/")){
					s.push(num1 / num2);
				} else if (op.equals("%")){
					s.push(num1 % num2);
				} else if (op.equals("^")){
					s.push(Math.pow(num1, num2));
				} else if (op.equals("ln")){
					s.push(num1);
					s.push(Math.log(num2));
				}
				
			//  "+", "-", "*", "/", "%", "^"
			}
		}
		return s.pop();
	}
	
	/*public static void main(String[] args){
		Scanner kbg = new Scanner(System.in);
		
		String expression;
		while(kbg.hasNext()){
			expression = kbg.nextLine();
			System.out.println(infixToPostfix(expression));
		}
		
		kbg.close();
	}*/
}

class Node {
	String name;
	Node next;	

	public Node(String n) {
		name = n;
		next = null;
	}

	public String toString() {
		return name;
	}
}

class MyStack {
	Node top;

	public MyStack() {
		top = null;
	}
	
	public void push(String str) {
		Node n = new Node(str);
		n.next = top;
		top = n;
	}
	
	public String pop() {
		Node n = top;
		if (top != null){
			top = n.next;
			return n.name;
		}
		return ")";
	}	
	
	public boolean isEmpty() {
		return top == null;
	}
	
	public String peek() {
		return top.name;
	}

}