import java.util.Stack;
import java.util.LinkedList;
import java.util.*;

public class Derivatives {
	static String operators = "+-*/^"; //no mods for now
	
	public static TreeNode partialDerivativeTree(TreeNode exp, String depen){
		if(exp.str.equals("+") || exp.str.equals("-")){
			return new TreeNode(exp.str, partialDerivativeTree(exp.left, depen), partialDerivativeTree(exp.right, depen));
		}
		if(exp.str.equals("*")){
			//productRule: (f*g)' = (f' * g) + (f * g')
			TreeNode f_prime_g = new TreeNode("*", partialDerivativeTree(exp.left, depen), exp.right);
			TreeNode f_g_prime = new TreeNode("*", exp.left, partialDerivativeTree(exp.right, depen));
			return new TreeNode("+", f_prime_g, f_g_prime);
		}
		if(exp.str.equals("/")){
			//quotientRule: (f/g)' = [(f' * g) - (f * g')]/[g ^ 2] 
			TreeNode f_prime_g = new TreeNode("*", partialDerivativeTree(exp.left, depen), exp.right);
			TreeNode f_g_prime = new TreeNode("*", exp.left, partialDerivativeTree(exp.right, depen));
			
			TreeNode top = new TreeNode("-", f_prime_g, f_g_prime);
			TreeNode bottom = new TreeNode("^", exp.right, new TreeNode("2", null, null));
			
			return new TreeNode("/", top, bottom);
		}
		if(exp.str.equals("^")){
			//exponents Rule: (f ^ g)' = [f ^ g] * [g' * ln(f) + (g / f) * f']
			//ln function needs to be implemented
			TreeNode g_prime_ln_f = new TreeNode("*", partialDerivativeTree(exp.right, depen), new TreeNode("ln", null , exp.left));
			TreeNode g_over_f_f_prime = new TreeNode("*", new TreeNode("/", exp.right, exp.left), partialDerivativeTree(exp.left, depen));
			TreeNode rightProd = new TreeNode("+", g_prime_ln_f, g_over_f_f_prime);
			return new TreeNode("*", exp, rightProd);
		}
		if(exp.str.equals("ln")){
			//ln(x), x is always stored in exp.right
			//derivative of ln(f): (1/f)*f'
			TreeNode quotient = new TreeNode("/", new TreeNode("1", null, null), exp.right);
			return new TreeNode("*", quotient, partialDerivativeTree(exp.right, depen));
		}
		else{
			if(exp.str.equals(depen))
				return new TreeNode("1", null, null);
			else
				return new TreeNode("0", null, null);
		}
	}
	
	public static TreeNode cleanTree(TreeNode exp){
		if(exp.right != null && exp.left != null){
			exp.left = cleanTree(exp.left);
			exp.right = cleanTree(exp.right);
		}
		if(exp.str.equals("*") && (exp.right.str.equals("0") || exp.left.str.equals("0")))
			return new TreeNode("0", null, null);
		if(exp.str.equals("*") && exp.right.str.equals("1"))
			return exp.left;
		if(exp.str.equals("*") && exp.left.str.equals("1"))
			return exp.right;
		if(exp.str.equals("+") && exp.right.str.equals("0"))
			return exp.left;
		if(exp.str.equals("+") && exp.left.str.equals("0"))
			return exp.right;
		if(exp.str.equals("-") && exp.right.str.equals("0"))
			return exp.left;
		return exp;
	}
	
	public static boolean isOperator(String str){
		return (operators.indexOf(str) != -1 || str.equals("ln"));
	}
	
	public static String Derivative(String expression, String depen, String indepen){
		String[] postfixExp = expression.split(" ");
		Stack<TreeNode> stack = new Stack<TreeNode>();
		
		for(String str : postfixExp){
			if(!isOperator(str)){
				stack.push(new TreeNode(str, null, null));
			}else{
				TreeNode rightNode = stack.pop();
				TreeNode leftNode = stack.pop();
				stack.push(new TreeNode(str, leftNode, rightNode));
			}
		}
		
		TreeNode output = stack.pop();
		//System.out.println(output.postOrder());
		
		//System.out.println(partialDerivativeTree(output, "X").postOrder());
		TreeNode d_dx = cleanTree(partialDerivativeTree(output, indepen));
		//System.out.println(d_dx.postOrder());
		
		TreeNode d_dy = cleanTree(partialDerivativeTree(output, depen));
		//System.out.println(d_dy.postOrder());
		
		TreeNode neg_dy_dx = new TreeNode("/", d_dx, d_dy);
		TreeNode dy_dx = cleanTree(new TreeNode("-", new TreeNode("0", null, null), neg_dy_dx));
		//System.out.println(dy_dx.postOrder());
		return dy_dx.postOrder();
	}
	
	public static void main(String[] args){
		//String[] postfixExp = {"X", "2", "^", "3", "X", "*", "+", "1", "-", "Y", "-"};
		String[] postfixExp = {"X", "Y", "*"};
		Stack<TreeNode> stack = new Stack<TreeNode>();
		
		for(String str : postfixExp){
			if(!isOperator(str)){
				stack.push(new TreeNode(str, null, null));
			}else{
				TreeNode rightNode = stack.pop();
				TreeNode leftNode = stack.pop();
				stack.push(new TreeNode(str, leftNode, rightNode));
			}
		}
		
		TreeNode output = stack.pop();
		//System.out.println(output.postOrder());
		
		//System.out.println(partialDerivativeTree(output, "X").postOrder());
		TreeNode d_dx = cleanTree(partialDerivativeTree(output, "X"));
		System.out.println(d_dx.postOrder());
		
		TreeNode d_dy = cleanTree(partialDerivativeTree(output, "Y"));
		System.out.println(d_dy.postOrder());
		
		TreeNode neg_dy_dx = new TreeNode("/", d_dx, d_dy);
		TreeNode dy_dx = cleanTree(new TreeNode("-", new TreeNode("0", null, null), neg_dy_dx));
		System.out.println(dy_dx.postOrder());
	}
	
}



class TreeNode implements Comparable<TreeNode> {
	String str;
	int nNodes;
	TreeNode left, right;
	
	public TreeNode(String s, TreeNode leftNode, TreeNode rightNode) {
		str = s;
		left = leftNode;
		right = rightNode;
	}
	
	public String toString() {
		return str;
	}
	
	public int compareTo(TreeNode other) {
		return this.str.compareTo(other.str);
	}
	
	public TreeNode insert(TreeNode n) {
		if(this.compareTo(n) >= 0)
			if(this.left != null)
				this.left.insert(n);
			else
				this.left = n;
		else
			if(this.right != null)
				this.right.insert(n);
			else
				this.right = n;
		return this;
	}
	
	public String postOrder() {
		String output = "";
		if(this.left != null)
			output += this.left.postOrder();
		if(this.right != null)
			output += this.right.postOrder();
		output += this.str + " ";
		return output;
	}
}