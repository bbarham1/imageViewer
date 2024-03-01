import java.util.Iterator;

/**
 * This iterator class to iterate through the image.
 * 
 * @param <T> generaic type.
 * @author basel Barham.
 */
public class ImageIterator<T extends Comparable<T>> implements Iterator<Node<T>> {
	/**
	 * image object.
	 */
	private Image<T> image;

	/**
	 * The direction of the iterator.
	 */
	private Direction direction;

	/**
	 * The head of the image.
	 */
	private Node<T> head;

	/**
	 * The current node of the iterator.
	 */
	private Node<T> current;

	/**
	 * The next row of the image to iterate.
	 */
	private Node<T> nextrow;

	/**
	 * The next column of the image to iterate.
	 */
	private Node<T> nextCol;

	/**
	 * To check if it is the first node to be iterated.
	 */
	private boolean firstNode = false;

	/**
	 * Constructor that uses the default direction.
	 * 
	 * @param image that needs to be iterated.
	 */
	public ImageIterator(Image<T> image) {
		this.image = image;
		this.direction = Direction.HORIZONTAL;
		if (image.getHead() != null) {
			head = image.getHead();
			current = head;
			nextrow = head;
			nextCol = head;
		}

	}

	/**
	 * Constructor that uses the specific direction.
	 * 
	 * @param image that needs to be iterated.
	 * @param dir   the direction of the iterator.
	 */
	public ImageIterator(Image<T> image, Direction dir) {
		this.image = image;
		this.direction = dir;
		// this.current = (direction == Direction.HORIZONTAL) ? image.getHead() :
		// image.getHead().getRight();
		// this.current=image.getHead();
		if (image != null && image.getHead() != null) {
			head = image.getHead();
			current = head;
			nextrow = head;
			nextCol = head;
		}

	}

	/**
	 * Method to check if the iterator has any neighbors.
	 * 
	 * @return true if there are neighbors.
	 */
	public boolean hasNext() {

		// System.out.println("hasNext"+);
		// count=count+1;
		// return current != null;
		if (direction.equals(Direction.HORIZONTAL)) {
			return (current != null && (current.getRight() != null || nextrow.getDown() != null));
		} else {
			return (current != null && (current.getDown() != null || nextCol.getRight() != null));
		}

	}

	/**
	 * Method to move the next node of the image.
	 * 
	 * @return the next node.
	 */
	public Node<T> next() {
		if (!hasNext()) {
			throw new UnsupportedOperationException("No more elements to iterate");
		}

		/*
		 * if (direction.equals(Direction.HORIZONTAL)) { current = (current.getRight()
		 * != null) ? current.getRight() : current.getDown();
		 * 
		 * } else { current = (current.getDown() != null) ? current.getDown() :
		 * current.getRight(); }
		 */
		Node<T> nextNode;

		if (direction.equals(Direction.HORIZONTAL)) {
			// if(wc<w ) {
			if (!firstNode) {
				firstNode = true;
				return head;
			}

			if (current.getRight() != null /* && wc<w && hc<h */) {
				current = current.getRight();
				// System.out.println("HR");
				// wc++;
			} else {
				if (nextrow.getDown() != null/* &&hc<h */) {
					// hc++;
					// wc=0;
					current = nextrow.getDown();
					// System.out.println("HD");

					// if (current.getDown() != null) {
					nextrow = nextrow.getDown();
					// System.out.println("&&&&nextRow");

					// }

				}
			}
			// wc++;
			// }
		} else {
			if (!firstNode) {
				firstNode = true;
				return head;
			}
			if (current.getDown() != null) {
				current = current.getDown();
				// System.out.println("VD");

			} else {
				if (nextCol.getRight() != null) {
					current = nextCol.getRight();
					// System.out.println("VR");

					// if (current.getRight() != null) {
					nextCol = nextCol.getRight();
					// }

				}

			}
		}

		nextNode = current;
		return nextNode;
	}
}
