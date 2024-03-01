// TO DO: add your implementation and JavaDoc
/**
 * this class creating a dynamic grid(2D array) with and it's functionality.
 * @author Yazeed Barham
 *
 * @param <T> generic variable
 */
public class DynGrid310<T> {

	// underlying 2-d array for storage -- you MUST use this for credit!
	// Do NOT change the name or type
	/**
	 * 2-d array for storage.
	 */
	private DynArr310<DynArr310<T>> storage;

	// ADD MORE PRIVATE MEMBERS HERE IF NEEDED!

	/**
	 * constructor to create an empty grid.
	 */
	public DynGrid310() {
		// constructor
		// create an empty grid (no content)
		// only use the parameterless constructor of DynArr310 to initialize
		storage = new DynArr310<DynArr310<T>>();

	}

	/**
	 * method to get the number of rows in the grid.
	 * @return number of rows
	 */
	public int getNumRow() {
		// report number of rows with contents in the grid
		// Note: this might be different from the max number of rows
		// of the underlying storage.
		// O(1)
		if (getNumCol() == 0) {
			return 0;
		} else {

			return storage.size(); // default return, remove or change as needed
		}
	}

	/**
	 * method to get the number of columns in the grid.
	 * @return number of columns
	 */
	public int getNumCol() {
		// report number of columns with contents in the grid
		// Note: similarly, this might be different from the max number of columns
		// of the underlying storage.
		// O(1)
		// DynArr310<T> temp = (T[])storage[0];
		if (storage.size() == 0) {
			return 0; // default return, remove or change as needed
		}
		return storage.get(0).size();
	}

	/**
	 * method to check if a cell is visible or not.
	 * @param row int index
	 * @param col int index
	 * @return true if the cell is viable
	 */
	public boolean isValidCell(int row, int col) {
		// check whether (row,col) corresponds to a cell with content
		// return true if yes, false otherwise
		// O(1)
		if (row < 0 || col < 0 || row >= getNumRow() || col >= getNumCol()) {
			return false;
		}
		return true; // default return, remove or change as needed
	}

	/**
	 * Method to return the value of the cell.
	 * @param row int index
	 * @param col int index
	 * @return the value of the cell
	 */
	public T get(int row, int col) {
		// report cell value at (row, col)

		// - Throw IndexOutOfBoundsException if any index is not valid
		// - Use this code to produce the correct error message for
		// the exception (do not use a different message):
		// "Index("+row+","+col+") out of bounds!"

		// O(1)
		if (isValidCell(row, col)) {
			return storage.get(row).get(col);
		} else {
			throw new IndexOutOfBoundsException("Index(" + row + "," + col + ") out of bounds!");
		}

		// default return, remove or change as needed
	}

	/**
	 * method to set the value of a cell.
	 * @param row int index
	 * @param col int index
	 * @param value of genaric value
	 * @return the old cell value
	 */
	public T set(int row, int col, T value) {
		// change cell value at (row, col) to be value, and return the old cell value

		// Use the exception (and error message) described in set()
		// for invalid indicies.

		// For valid indicies, if value is null, throw IllegalArgumentException.
		// - Use this _exact_ error message for the exception
		// (quotes are not part of the message):
		// "Null values not accepted!"

		// O(1)
		if (isValidCell(row, col)) {
			if (value==null) {
				throw new IllegalArgumentException("Null values not accepted!");
			}
		}
		return storage.get(row).set(col, value); // default return, remove or change as needed

	}

	/**
	 * Method to add a row to the grid.
	 * @param index int
	 * @param newRow of type DynArr310 array
	 * @return true if the new row was successfully added
	 */
	public boolean addRow(int index, DynArr310<T> newRow) {
		// insert newRow into the grid at index, shifting rows if needed
		// a new row can be appended

		// return false if newRow can not be added correctly, e.g.
		// - invalid index
		// - newRow is null or empty
		// - the number of items in newRow does not match existing rows
		//
		// return true otherwise
		//

		// O(R)
		if (storage == null || storage.size() == 0) {
			try {
				storage.insert(index, newRow);
				return true;
			} catch (Exception e) {
				return false;
			}
		}
		if (newRow.size() == storage.get(0).size()) {
			try {
				storage.insert(index, newRow);
				return true;
			} catch (Exception e) {
				return false;
			}
		}
		return false;

	}
	
	/**
	 * Method to add a column to the grid.
	 * @param index int
	 * @param newCol of type DynArr310 array
	 * @return true if the new column was successfully added
	 */
	public boolean addCol(int index, DynArr310<T> newCol) {
		// insert newCol as a new column into the grid at index, shifting cols if needed
		// a new column can be appended

		// return false if newCol can be added correctly, e.g.
		// - invalid index
		// - newCol is null or empty
		// - the number of items in newCol does not match existing columns
		//
		// return true otherwise

		// O(CR) where R is the number of rows and C is the number of columns of the
		// grid
		// default return, remove or change as needed
		try {

			if (newCol.capacity() != storage.capacity()) {
				return false;
			}
			for (int i = 0; i < this.getNumRow(); i++) {

				storage.get(i).insert(index, newCol.get(i));

			}
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	/**
	 * method to remove a specific row.
	 * @param index int
	 * @return true if it is removed successfully
	 */
	public DynArr310<T> removeRow(int index) {
		// remove and return a row at index, shift rows as needed to remove the gap
		// return null for an invalid index

		// O(R) where R is the number of rows of the grid
		try {

			return storage.remove(index);// default return, remove or change as needed
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * method to remove a specific column.
	 * @param index int
	 * @return true if it is removed successfully
	 */
	public DynArr310<T> removeCol(int index) {
		// remove and return a column at index, shift cols as needed to remove the gap
		// return null for an invalid index
		//
		// O(RC) where R is the number of rows and C is the number of columns
		if (index < 0 || index > getNumCol()) {
			return null;
		}
		DynArr310<T> temp = new DynArr310<T>();
		int x = getNumRow();
		for (int i = 0; i < x; i++) {
			// System.out.println(getNumRow());
			// System.out.println(storage);
			// System.out.println(storage.get(i));
			temp.add(storage.get(i).remove(index));

		}

		return temp;
		// default return, remove or change as needed

	}

	// ******************************************************
	// ******* BELOW THIS LINE IS PROVIDED code *******
	// ******* Do NOT edit code! *******
	// ******* Remember to add JavaDoc *******
	// ******************************************************

	/**
	 * Overridden to string method.
	 * @return a concatenated string of the grid items
	 */
	@Override
	public String toString() {
		if (getNumRow() == 0 || getNumCol() == 0) {
			return "empty board";
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < getNumRow(); i++) {
			sb.append("|");
			for (int j = 0; j < getNumCol(); j++) {
				sb.append(get(i, j).toString());
				sb.append("|");
			}
			sb.append("\n");
		}
		return sb.toString().trim();

	}

	// ******************************************************
	// ******* BELOW THIS LINE IS TESTING CODE *******
	// ******* Edit it as much as you'd like! *******
	// ******* Remember to add JavaDoc *******
	// ******************************************************

	/**
	 * This is main method to test the calss.
	 * @param args array of strings not used
	 */
	public static void main(String[] args) {
		// These are _sample_ tests. If you're seeing all the "yays" that's
		// an excellend first step! But it does NOT guarantee your code is 100%
		// working... You may edit this as much as you want, so you can add
		// own tests here, modify these tests, or whatever you need!

		// create a grid of strings
		DynGrid310<String> sgrid = new DynGrid310<>();

		// prepare one row to add
		DynArr310<String> srow = new DynArr310<>();
		srow.add("English");
		srow.add("Spanish");
		srow.add("German");

		// addRow and checking
		

		if (sgrid.getNumRow() == 0 && sgrid.getNumCol() == 0 && !sgrid.addRow(1, srow) && sgrid.addRow(0, srow)
				&& sgrid.getNumRow() == 1 && sgrid.getNumCol() == 3) {
			System.out.println("Yay 1");
		}

		// get, set, isValidCell
		if (sgrid.get(0, 0).equals("English") && sgrid.set(0, 1, "Espano").equals("Spanish")
				&& sgrid.get(0, 1).equals("Espano") && sgrid.isValidCell(0, 0) && !sgrid.isValidCell(-1, 0)
				&& !sgrid.isValidCell(3, 2)) {
			System.out.println("Yay 2");
		}

		// a grid of integers
		DynGrid310<Integer> igrid = new DynGrid310<Integer>();
		boolean ok = true;

		// add some rows (and implicitly some columns)
		for (int i = 0; i < 3; i++) {
			DynArr310<Integer> irow = new DynArr310<>();
			irow.add((i + 1) * 10);
			irow.add((i + 1) * 11);

			ok = ok && igrid.addRow(igrid.getNumRow(), irow);
		}

		// toString
		// System.out.println(igrid);
		if (ok && igrid.toString().equals("|10|11|\n|20|22|\n|30|33|")) {
			System.out.println("Yay 3");
		}

		// prepare a column
		DynArr310<Integer> icol = new DynArr310<>();

		// add two rows
		icol.add(-10);
		icol.add(-20);

		// attempt to add, should fail
		ok = igrid.addCol(1, icol);

		// expand column to three rows
		icol.add(-30);

		// addCol and checking

		if (!ok && !igrid.addCol(1, null) && igrid.addCol(1, icol) && igrid.getNumRow() == 3
				&& igrid.getNumCol() == 3) {
			System.out.println("Yay 4");
		}

		

		if (igrid.removeRow(5) == null && igrid.removeRow(1).toString().equals("[20, -20, 22]")
				&& igrid.getNumRow() == 2 && igrid.getNumCol() == 3) {
			System.out.println("Yay 5");
		}



		// System.out.println();
		if (igrid.removeCol(0).toString().equals("[10, 30]") && igrid.removeCol(1).toString().equals("[11, 33]")
				&& igrid.removeCol(0).toString().equals("[-10, -30]") && igrid.getNumRow() == 0
				&& igrid.getNumCol() == 0) {
			System.out.println("Yay 6");
		}

	}

}