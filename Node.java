/**
 * This is a generic type class creates a node with four pointers up, down, right, and left and it extends comparable.
 * @param <T> generaic type.
 * @author basel Barham.
 */
public final class Node<T extends Comparable<T>> implements Comparable<Node<T>> {

	/**
	 * Holds the data of the node.
	 */
	private T data;
	/**
	 * The pointer to the node above.
	 */
	private Node<T> up;
	/**
	 * The pointer to the node below.
	 */
	private Node<T> down;
	/**
	 * The pointer to the node on the right.
	 */
	private Node<T> right;
	/**
	 * The pointer to the node on the left.
	 */
	private Node<T> left;

	/**
	 * Setter method for the data .
	 * @param value generic type parameter value.
	 */
	public void setValue(T value) {
		this.data = value;
	}

	/**
	 * Getter method for the data field.
	 * @return the data of the node.
	 */
	public T getValue() {
		return data;
	}

	/**
	 * Getter method for the up field.
	 * @return the upper node.
	 */
	public Node<T> getUp() {
		return up;
	}

	/**
	 * Getter method for the down field.
	 * 
	 * @return the down node.
	 */
	public Node<T> getDown() {
		return down;
	}

	/**
	 * Getter method for the right field.
	 * 
	 * @return the right node.
	 */
	public Node<T> getRight() {
		return right;
	}

	/**
	 * Getter method for the left field.
	 * 
	 * @return the left node.
	 */
	public Node<T> getLeft() {
		return left;
	}

	/**
	 * Setter method for the up field.
	 * @param p up node.
	 */
	public void setUp(Node<T> p) {
		this.up = p;
	}

	/**
	 * Setter method for the down field.
	 * @param p down node.
	 */
	public void setDown(Node<T> p) {
		this.down = p;
	}

	/**
	 * Setter method for the right field.
	 * @param p right node.
	 */
	public void setRight(Node<T> p) {
		this.right = p;
	}

	/**
	 * Setter method for the left field.
	 * @param p left node.
	 */
	public void setLeft(Node<T> p) {
		this.left = p;
	}

	/**
	 * constructor that sets everything to null.
	 */
	public Node() {
		this.data = null;
		this.up = null;
		this.down = null;
		this.right = null;
		this.left = null;
	}

	/**
	 * constructor that sets the data field only and everything else to null.
	 * @param value of generic type.
	 */
	public Node(T value) {
		this.data = value;
	}

	/**
	 * CompareTo method to compare between nodes based on data.
	 * @param o node to be compared to.
	 * @return 0 if they are equal, positive number if o is greater.
	 */
	@Override
	public int compareTo(Node<T> o) {
		return this.data.compareTo(o.data);
	}

}
