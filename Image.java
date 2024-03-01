import java.util.Iterator;
/**
 * This generic class creates image using linked list.
 * @param <T> generaic type.
 * @author basel Barham.
 */
public class Image<T extends Comparable<T>> implements Iterable<Node<T>> {
	/**
	 * The head of the image.
	 */
	private Node<T> head;

	/**
	 * The width of the image.
	 */
	private int width;

	/**
	 * The height of the image.
	 */
	private int height;

	/**
	 * Constructor to initialize the image.
	 * 
	 * @param width  of type int.
	 * @param height of type int.
	 */
	public Image(int width, int height) {
		if (width <= 0 || height <= 0) {
			throw new RuntimeException("Invalid input for width or height");
		}
		this.width = width;
		this.height = height;
		this.head = new Node<>(null);
		Node<T> current = head;

		// Create the first row
		for (int col = 1; col < width; col++) {
			current.setRight(new Node<>(null));
			current.getRight().setLeft(current);
			current.setUp(null);

			current = current.getRight();

		}

		Node<T> rowStart = head;
		Node<T> prevRowStart = head;

		// Create subsequent rows
		for (int row = 0; row < height - 1; row++) {

			prevRowStart = rowStart;
			current = new Node<>(null);
			rowStart.setDown(current);
			current.setUp(rowStart);

			// Create the row with columns
			for (int col = 1; col < width; col++) {
				current.setRight(new Node<>(null));
				current.getRight().setLeft(current);
				current.getRight().setUp(prevRowStart.getRight());
				prevRowStart.getRight().setDown(current.getRight());
				current = current.getRight();
				prevRowStart = prevRowStart.getRight();
			}

			// Move to the next row
			rowStart = rowStart.getDown();
		}

	}

	/**
	 * Getter method to return the height.
	 * 
	 * @return height of the image.
	 */
	public int getHeight() {
		return this.height;
	}

	/**
	 * Getter method to return the width.
	 * 
	 * @return width of the image.
	 */
	public int getWidth() {
		return this.width;

	}

	/**
	 * Getter method to return the head.
	 * 
	 * @return the head of the image.
	 */
	public Node<T> getHead() {
		return this.head;
	}

	/**
	 * Insert method to insert the rows.
	 * 
	 * @param index of type int.
	 * @param value of generaic type.
	 */
	public void insertRow(int index, T value) {
		if (index < 0 || index > height) {
			throw new RuntimeException("Invalid row index");
		}

		Node<T> newRow = new Node<>(value);
		Node<T> current = head;

		// Traverse to the row before the insertion point
		for (int row = 0; row < index - 1; row++) {
			current = current.getDown();
		}

		// Insert the new row
		newRow.setDown(current.getDown());
		if (current.getDown() != null) {
			current.getDown().setUp(newRow);
		}
		current.setDown(newRow);
		newRow.setUp(current);

		// Update height
		height++;
	}

	/**
	 * Method to remove a column.
	 * 
	 * @param index of type int.
	 */
	public void removeColumn(int index) {
		if (index < 0 || index >= width) {
			throw new RuntimeException("Invalid column index");
		}

		Node<T> current = head;

		// Traverse to the column to be removed
		for (int col = 0; col < index; col++) {
			current = current.getRight();
		}

		// Update neighboring nodes to skip the column
		Node<T> prev = current.getLeft();
		Node<T> next = current.getRight();

		if (prev != null) {
			prev.setRight(next);
		}
		if (next != null) {
			next.setLeft(prev);
		}

		// Update width
		width--;
	}

	/**
	 * Method to check if two rows are equal then it compresses them to one.
	 * 
	 * @return number of nodes removed.
	 */
	public int compress() {
		int nodesRemoved = 0;

		// Traverse the image and check adjacent nodes for the same values
		Node<T> currentRow = head;
		Node<T> nextRow = head.getDown();

		while (nextRow != null) {
			Node<T> currentNode = currentRow;
			Node<T> nextNode = nextRow;

			while (nextNode != null) {
				if (currentNode.getValue().equals(nextNode.getValue())) {
					// Remove the adjacent row and column with the same values
					nextNode.getUp().setDown(nextNode.getDown());
					if (nextNode.getDown() != null) {
						nextNode.getDown().setUp(nextNode.getUp());
					}

					nextNode.getLeft().setRight(nextNode.getRight());
					if (nextNode.getRight() != null) {
						nextNode.getRight().setLeft(nextNode.getLeft());
					}

					nodesRemoved++;
				}

				currentNode = currentNode.getRight();
				nextNode = nextNode.getRight();
			}

			currentRow = currentRow.getDown();
			nextRow = nextRow.getDown();
		}

		return nodesRemoved;
	}

	/**
	 * Method to add borders to the image.
	 */
	public void addBorder() {
		int newWidth = width + 2;
		int newHeight = height + 2;

		// Create a new Image with the increased dimensions
		Image<T> newImage = new Image<>(newWidth, newHeight);

		// Initialize the border with the same pixel values as adjacent pixels
		Node<T> newRow = newImage.getHead();
		Node<T> currentRow = head;

		for (int row = 0; row < newHeight; row++) {
			Node<T> newCurrentNode = newRow;
			Node<T> current = currentRow;

			for (int col = 0; col < newWidth; col++) {
				if (row > 0 && row < newHeight - 1 && col > 0 && col < newWidth - 1) {
					// Copy the pixel value from the original image
					newCurrentNode.setValue(current.getValue());
				}

				if (col < newWidth - 1) {
					// Move to the next column
					newCurrentNode = newCurrentNode.getRight();
				}

				if (current != null) {
					// Move to the next column in the original image
					current = current.getRight();
				}
			}

			if (row < newHeight - 1) {
				// Move to the next row in the new image
				newRow = newRow.getDown();
			}

			if (currentRow != null) {
				// Move to the next row in the original image
				currentRow = currentRow.getDown();
			}
		}

		// Update the current image with the new one
		this.head = newImage.getHead();
		this.width = newWidth;
		this.height = newHeight;
	}

	/**
	 * Method to remove the border of the image.
	 */
	public void removeBorder() {
		if (width < 2 || height < 2) {
			throw new RuntimeException("Image dimensions too small to remove the border");
		}

		// Decrease width and height by 2 to remove the border
		int newWidth = width - 2;
		int newHeight = height - 2;

		// Create a new Image with the decreased dimensions
		Image<T> newImage = new Image<>(newWidth, newHeight);

		// Copy the inner part of the original image to the new image
		Node<T> newRow = newImage.getHead();
		Node<T> currentRow = head.getDown(); // Skip the top border row

		for (int row = 0; row < newHeight; row++) {
			Node<T> newCurrentNode = newRow;
			Node<T> current = currentRow.getRight(); // Skip the left border node

			for (int col = 0; col < newWidth; col++) {
				// Copy the pixel value from the original image
				newCurrentNode.setValue(current.getValue());

				// Move to the next column in both images
				newCurrentNode = newCurrentNode.getRight();
				current = current.getRight();
			}

			// Move to the next row in both images
			newRow = newRow.getDown();
			currentRow = currentRow.getDown();
		}

		// Update the current image with the new one
		this.head = newImage.getHead();
		this.width = newWidth;
		this.height = newHeight;
	}

	/**
	 * Method to set the value of pixel to the maximum value around it.
	 * 
	 * @return the new image.
	 */
	public Image<T> maxFilter() {
		Image<T> newImage = new Image<>(width, height);

		// Create an ImageIterator for row-by-row traversal in both images
		ImageIterator<T> iterator = new ImageIterator<>(this, Direction.HORIZONTAL);
		ImageIterator<T> newIterator = new ImageIterator<>(newImage, Direction.HORIZONTAL);

		while (iterator.hasNext() && newIterator.hasNext()) {
			Node<T> currentNode = iterator.next();
			Node<T> newCurrentNode = newIterator.next();

			// Initialize max value with the current pixel's value
			T max = currentNode.getValue();

			// Iterate through the 3x3 neighborhood centered at the current pixel
			for (int i = -1; i <= 1; i++) {
				for (int j = -1; j <= 1; j++) {
					Node<T> neighbor = currentNode;

					// Move to the neighbor node in the neighborhood
					for (int k = 0; k < Math.abs(i); k++) {
						if (i < 0) {
							neighbor = neighbor.getUp();
						} else {
							neighbor = neighbor.getDown();

						}
					}
					for (int k = 0; k < Math.abs(j); k++) {
						if (j < 0) {
							neighbor = neighbor.getLeft();
						} else {
							neighbor = neighbor.getRight();

						}
					}

					// Update max value if the neighbor's value is greater
					if (neighbor != null && neighbor.getValue().compareTo(max) > 0) {
						max = neighbor.getValue();
					}
				}
			}

			// Set the max value in the new image
			newCurrentNode.setValue(max);
		}

		return newImage;
	}

	/**
	 * Method that creates an image iterator with the default direction.
	 * 
	 * @return iterator node.
	 */
	public Iterator<Node<T>> iterator() {
		return new ImageIterator<>(this, Direction.HORIZONTAL);
	}

	/**
	 * Method that creates an image iterator with specific direction.
	 * 
	 * @param dir the dirction of the iterator.
	 * @return the image iterator.
	 */
	public Iterator<Node<T>> iterator(Direction dir) {
		return new ImageIterator<>(this, dir);
	}

	/**
	 * To string method.
	 * 
	 * @return the string of the image.
	 */
	public String toString() {
		String string = "Image{" + "width=" + width + ", height=" + height + '}';
		// while this.

		return string;
	}

}
