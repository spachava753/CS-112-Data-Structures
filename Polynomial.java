package poly;

import java.io.IOException;
import java.util.Scanner;

/**
 * This class implements evaluate, add and multiply for polynomials.
 * 
 * @author runb-cs112
 *
 */
public class Polynomial {
	
	/**
	 * Reads a polynomial from an input stream (file or keyboard). The storage format
	 * of the polynomial is:
	 * <pre>
	 *     <coeff> <degree>
	 *     <coeff> <degree>
	 *     ...
	 *     <coeff> <degree>
	 * </pre>
	 * with the guarantee that degrees will be in descending order. For example:
	 * <pre>
	 *      4 5
	 *     -2 3
	 *      2 1
	 *      3 0
	 * </pre>
	 * which represents the polynomial:
	 * <pre>
	 *      4*x^5 - 2*x^3 + 2*x + 3 
	 * </pre>
	 * 
	 * @param sc Scanner from which a polynomial is to be read
	 * @throws IOException If there is any input error in reading the polynomial
	 * @return The polynomial linked list (front node) constructed from coefficients and
	 *         degrees read from scanner
	 */
	public static Node read(Scanner sc) 
	throws IOException {
		Node poly = null;
		while (sc.hasNextLine()) {
			Scanner scLine = new Scanner(sc.nextLine());
			poly = new Node(scLine.nextFloat(), scLine.nextInt(), poly);
			scLine.close();
		}
		return poly;
	}
	/**
	 * Returns the sum of two polynomials - DOES NOT change either of the input polynomials.
	 * The returned polynomial MUST have all new nodes. In other words, none of the nodes
	 * of the input polynomials can be in the result.
	 * 
	 * @param poly1 First input polynomial (front of polynomial linked list)
	 * @param poly2 Second input polynomial (front of polynomial linked list
	 * @return A new polynomial which is the sum of the input polynomials - the returned node
	 *         is the front of the result polynomial
	 */
	public static Node add(Node poly1, Node poly2) {
		Node poly3=null;
		Node tail=null;
		while(poly1!=null||poly2!=null){
			Node ptr=null;
			//Node ptr2=null;
			//if poly1 is empty, poly3 node is poly2 and iterate poly2 
			if(poly1==null){
				//if(poly2.term.coeff==0){
				//	continue;
				//}
				ptr=new Node(poly2.term.coeff, poly2.term.degree, null);
				poly2=poly2.next;
			}
			//if poly2 is empty, poly1 node is poly3 and iterate poly1
			else if(poly2==null){
				//if(poly1.term.coeff==0){
				//	continue;
				//}
				ptr=new Node(poly1.term.coeff, poly1.term.degree, null);
				poly1=poly1.next;
				
			}
			//if poly1 & 2 degree equal, add coeffs and set poly3's node as sum w/ same degree, iterate both
			else if(poly1.term.degree==poly2.term.degree){
				//if(poly1.term.coeff==0&&poly2.term.coeff==0){
				//	continue;
				//}
				ptr=new Node(poly1.term.coeff + poly2.term.coeff,poly1.term.degree,null);
				poly1=poly1.next;
				poly2=poly2.next;
			}
			//if poly1 degree > poly2 degree, set poly3's node to poly2, iterate poly2
			else if(poly1.term.degree>poly2.term.degree){
				//if(poly2.term.coeff==0){
				//	continue;
				//}
				ptr=new Node(poly2.term.coeff,poly2.term.degree,null);
				poly2=poly2.next;
			}
			//if poly1 degree < poly2 degree, set poly3's node to poly1, iterate poly1
			else if(poly1.term.degree<poly2.term.degree){
				//if(poly1.term.coeff==0){
				//	continue;
				//}
				ptr=new Node(poly1.term.coeff,poly1.term.degree,null);
				poly1=poly1.next;
			}
			if(ptr.term.coeff==0){
				continue;
			}
			//first iteration add
			if(poly3==null){
				poly3=ptr;
				tail=ptr;
			}
			//add to end of polynomial
			else{
			tail.next=ptr;
			tail=tail.next;
			}
			//ptr.next=poly3;
			//poly3=ptr;
		}
		return poly3;
	}
	/*private static Node sortByDegree(Node front){
		int highestDegree=0;
		for(Node ptr=front;ptr!=null;ptr=ptr.next){
			if(ptr.term.degree>highestDegree){
				highestDegree=ptr.term.degree;
			}
		}
		Node sortedFront=null;
		for(int i=highestDegree;i>=0;i--){
			for(Node ptr2=front;ptr2!=null;ptr2=ptr2.next){
			if(ptr2.term.degree==highestDegree){
				ptr2.next=sortedFront;
				sortedFront=ptr2;
			}
			}
		}
		return sortedFront;
	}*/
	private static int getHighestDegree(Node front){
		int highestDegree=-1;
		for(Node ptr=front;ptr!=null;ptr=ptr.next){
			if(ptr.term.degree>highestDegree){
				highestDegree=ptr.term.degree;
			}
		}
		return highestDegree;
	}
	private static Node combindLikeTerms(Node front){
		//sortByDegree(front);
		int tempSum=0;
		Node poly=null;
		Node ptr=null;
		Node tail=null;
		for(int i=0;i<=getHighestDegree(front);i++){
			for(Node ptr2=front;ptr2!=null;ptr2=ptr2.next){
				if(ptr2.term.degree==i){
					tempSum+=ptr2.term.coeff;
				}
			}
			if(tempSum==0){
				continue;
			}
			ptr=new Node(tempSum,i,null);
			if(poly==null){
				poly=ptr;
				tail=ptr;
			}
			else{
			tail.next=ptr;
			tail=tail.next;
			}
			tempSum=0;
		}
		return poly;
	}
	/**
	 * Returns the product of two polynomials - DOES NOT change either of the input polynomials.
	 * The returned polynomial MUST have all new nodes. In other words, none of the nodes
	 * of the input polynomials can be in the result.
	 * 
	 * @param poly1 First input polynomial (front of polynomial linked list)
	 * @param poly2 Second input polynomial (front of polynomial linked list)
	 * @return A new polynomial which is the product of the input polynomials - the returned node
	 *         is the front of the result polynomial
	 */
	public static Node multiply(Node poly1, Node poly2) {
		Node poly3=null;
		Node ptr=null;
		Node tail=null;
		for(Node iter=poly1;iter!=null;iter=iter.next){
			if(poly1.term.coeff==0){
				continue;
			}
			for(Node iter2=poly2;iter2!=null;iter2=iter2.next){
				if(poly2.term.coeff==0){
					continue;
				}
				ptr=new Node(iter.term.coeff*iter2.term.coeff, iter.term.degree+iter2.term.degree,null);
				if(poly3==null){
					poly3=ptr;
					tail=ptr;
				}
				else{
					tail.next=ptr;
					tail=tail.next;
				}
			}
		}
		return combindLikeTerms(poly3);
	}
		
	/**
	 * Evaluates a polynomial at a given value.
	 * 
	 * @param poly Polynomial (front of linked list) to be evaluated
	 * @param x Value at which evaluation is to be done
	 * @return Value of polynomial p at x
	 */
	public static float evaluate(Node poly, float x) {
		float result=0;
		float temp=1;
		for(Node ptr=poly;ptr!=null;ptr=ptr.next){
			for(int i=0;i<ptr.term.degree;i++){
				temp*=x;
				//System.out.println(temp);
			}
			if(ptr.term.coeff==0){
				continue;
			}
			if(ptr.term.degree==0){
				temp=1;
				result+=temp*ptr.term.coeff;
				//System.out.println("r" + result);
			}
			else{
				result+=temp*ptr.term.coeff;
				//System.out.println("r"+ result);
				temp=1;
			}
		}
		return result;
	}
	
	/**
	 * Returns string representation of a polynomial
	 * 
	 * @param poly Polynomial (front of linked list)
	 * @return String representation, in descending order of degrees
	 */
	public static String toString(Node poly) {
		if (poly == null) {
			return "0";
		} 
		
		String retval = poly.term.toString();
		for (Node current = poly.next ; current != null ;
		current = current.next) {
			retval = current.term.toString() + " + " + retval;
		}
		return retval;
	}	
}
