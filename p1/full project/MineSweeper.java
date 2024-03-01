// TO DO: add your implementation and JavaDocs.

import java.util.Random;
/**
 * This class creating a Minesweeper game it's functionality.
 * @author Yazeed Barham
 */
public class MineSweeper {

	// ******************************************************
	// ******* BELOW THIS LINE IS PROVIDED code *******
	// ******* Do NOT edit code! *******
	// ******* Remember to add JavaDoc *******
	// ******************************************************

	/**
	 * supported game levels.
	 *
	 */
	public enum Level {
		/**
		 * Possible levels.
		 */
		TINY, EASY, MEDIUM, HARD, CUSTOM    // levels 
	}

	// each level has a different board size (number of rows/columns)
	// and a different number of mines

	/**
	 * easy game row size.
	 */
	private static int ROWS_EASY = 9;
	
	/**
	 * easy game col size.
	 */
	
	private static int COLS_EASY = 9;
	/**
	 * easy game total mine.
	 */
	
	private static int MINES_EASY = 10;

	/**
	 * tiny game row size.
	 */
	private static int ROWS_TINY = 5;
	
	/**
	 * tiny game col size.
	 */
	private static int COLS_TINY = 5;
	
	/**
	 * tiny game total mines.
	 */
	private static int MINES_TINY = 3;
	
	/**
	 * medium game row size.
	 */
	private static int ROWS_MEDIUM = 16;
	
	/**
	 * medium size col size.
	 */
	private static int COLS_MEDIUM = 16;
	
	/**
	 * medium size total mines.
	 */
	private static int MINES_MEDIUM = 40;

	/**
	 * hard game row size.
	 */
	private static int ROWS_HARD = 16;
	
	/**
	 * hard game col size.
	 */
	private static int COLS_HARD = 30;
	
	/**
	 * hard game total mine. 
	 */
	private static int MINES_HARD = 99;

	/**
	 * 2D board of cell.
	 */
	private DynGrid310<Cell> board;

	/**
	 * number of rows of the board.
	 */
	private int rowCount;

	/**
	 * number of columns of the board.
	 */
	private int colCount;

	/**
	 * number of mines in the board.
	 */
	private int mineTotalCount;

	/**
	 * number of cells clicked / exposed.
	 */
	private int clickedCount;

	/**
	 * number of cells flagged as a mine.
	 */
	private int flaggedCount;

	/**
	 * game possible status.
	 *
	 */
	public enum Status {
		/**
		 * possible status.
		 */
		INIT, INGAME, EXPLODED, SOLVED  // game status 
	}

	/**
	 * status of the game.
	 */
	private Status status;

	/**
	 * string names of status.
	 */
	public final static String[] Status_STRINGS = { "INIT", "IN_GAME", "EXPLODED", "SOLVED" };

	
	/** 
	 * constructor to initialize game based on a provided seed for random numbers
	 * and the specified level.
	 * 
	 * @param seed int 
	 * @param level Level
	 */
	public MineSweeper(int seed, Level level) {

		// if level is customized, need more details (number of rows/columns/mines)
		if (level == Level.CUSTOM)
			throw new IllegalArgumentException("Customized games need more parameters!");

		// set number of rows, columns, mines based on the pre-defined levels
		switch (level) {
			case TINY:
				rowCount = ROWS_TINY;
				colCount = COLS_TINY;
				mineTotalCount = MINES_TINY;
				break;
			case EASY:
				rowCount = ROWS_EASY;
				colCount = COLS_EASY;
				mineTotalCount = MINES_EASY;
				break;
			case MEDIUM:
				rowCount = ROWS_MEDIUM;
				colCount = COLS_MEDIUM;
				mineTotalCount = MINES_MEDIUM;
				break;
			case HARD:
				rowCount = ROWS_HARD;
				colCount = COLS_HARD;
				mineTotalCount = MINES_HARD;
				break;
			default:
				// should not be able to reach here!
				rowCount = ROWS_TINY;
				colCount = COLS_TINY;
				mineTotalCount = MINES_TINY;
		}

		// create an empty board of the needed size
		// TODO: you implement this method
		board = genEmptyBoard(rowCount, colCount);

		// place mines, and initialize cells
		// TODO: you implement part of this method
		initBoard(seed);
	}

	/** 
	 * constructor: should only be used for customized games.
	 * 
	 * @param seed int 
	 * @param level int 
	 * @param rowCount int 
	 * @param colCount int 
	 * @param mineCount int 
	 */
	public MineSweeper(int seed, Level level, int rowCount, int colCount, int mineCount) {

		if (level != Level.CUSTOM)
			throw new IllegalArgumentException("Only customized games need more parameters!");

		// set number of rows/columns/mines
		// assume all numbers are valid (check MineGUI for additional checking code)
		this.rowCount = rowCount;
		this.colCount = colCount;
		this.mineTotalCount = mineCount;

		// create an empty board of the needed size: you implement this method
		board = genEmptyBoard(rowCount, colCount);

		// place mines, and initialize cells: you implement part of this method
		initBoard(seed);

	}

	/** 
	 * Method to initialize the game, including placing mines.
	 * assume it is invoked only after an empty board (rowCount x colCount)
	 * has been created and set. check code above for examples.
	 * 
	 * @param seed int
	 */
	// TODO: you implement some important steps of this method
	public void initBoard(int seed) {

		// use seed to initialize a random number sequence
		Random random = new Random(seed);

		// randomly place mines on board
		int mineNum = 0;
		for (; mineNum < mineTotalCount;) {

			// generate next (row, col)
			int row = random.nextInt(rowCount);
			int col = random.nextInt(colCount);

			// cell already has a mine: try again
			if (hasMine(row, col)) {
				continue;
			}

			// place mine
			board.get(row, col).setMine();
			mineNum++;
		}
		// System.out.println(board);

		// calculate nbr counts for each cell
		for (int row = 0; row < rowCount; row++) {
			for (int col = 0; col < colCount; col++) {

				// TODO: you implement countNbrMines()
				int count = countNbrMines(row, col);
				board.get(row, col).setCount(count);
			}
		}

		// initialize other game settings
		status = Status.INIT;

		flaggedCount = 0;
		clickedCount = 0;

	}

	/**
	 * Method to report number of rows.
	 * @return rowcount int
	 */
	public int rowCount() {
		return rowCount;
	}

	/**
	 * Method to report number of columns.
	 * @return colcount int
	 */
	public int colCount() {
		return colCount;
	}

	/**
	 * Method to report whether board is solved.
	 * @return true if solved
	 */
	public boolean isSolved() {
		return status == Status.SOLVED;
	}

	/**
	 * Method to report whether a mine has exploded.
	 * @return true if is exploded
	 */
	public boolean isExploded() {
		return status == Status.EXPLODED;
	}

	/**
	 * Method to display board.
	 * @return sb string 
	 */
	// use this for debugging
	public String boardToString() {
		StringBuilder sb = new StringBuilder();

		// header of column indexes
		sb.append("- |");
		for (int j = 0; j < board.getNumCol(); j++) {
			sb.append(j + "|");
		}
		sb.append("\n");

		for (int i = 0; i < board.getNumRow(); i++) {
			sb.append(i + " |");
			for (int j = 0; j < board.getNumCol(); j++) {
				sb.append(board.get(i, j).toString());
				sb.append("|");
			}
			sb.append("\n");
		}
		return sb.toString().trim();

	}

	/**
	 * Overridden to string method.
	 * @return a concatenated string game status
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Board Size: " + rowCount() + " x " + colCount() + "\n");
		sb.append("Total mines: " + mineTotalCount + "\n");
		sb.append("Remaining mines: " + mineLeft() + "\n");
		sb.append("Game status: " + getStatus() + "\n");

		sb.append(boardToString());
		return sb.toString().trim();
	}

	// ******************************************************
	// ******* Methods to report cell details *******
	// ******* These are used by GUI for display *******
	// ******* Check Cell class for helpful operations*******
	// ******************************************************

	/**
	 * Method to check if cell at (row,col) is flagged.
	 * 
	 * @param row int 
	 * @param col int 
	 * @return flagged cell
	 */
	public boolean isFlagged(int row, int col) {
		// return true if cell at (row,col) is flagged
		// false otherwise
		// return false for invalid cell indexes

		if (!board.isValidCell(row, col)) {
			return false;
		}

		Cell cell = board.get(row, col);
		return (cell.isFlagged());
	}

	/**
	 * Method to check if cell at (row,col) is not hidden.
	 * 
	 * @param row int 
	 * @param col int
	 * @return visible cell
	 */
	public boolean isVisible(int row, int col) {
		// return true if cell at (row,col) is not hidden
		// false otherwise
		// return false for invalid cell indexes

		if (!board.isValidCell(row, col)) {
			return false;
		}

		Cell cell = board.get(row, col);
		return (cell.visible());
	}

	/**
	 * Method to check if cell at (row,col) has a mine regardless whether it has been flagged or not.
	 * 
	 * @param row int 
	 * @param col int 
	 * @return cell that has mine 
	 */
	public boolean hasMine(int row, int col) {
		// return true if cell at (row,col) has a mine,
		// regardless whether it has been flagged or not;
		// false otherwise
		// return false for invalid cell indexes

		if (!board.isValidCell(row, col)) {
			return false;
		}

		Cell cell = board.get(row, col);
		return (cell.hasMine());
	}

	/**
	 * Method to returns the count associated with cell at (row,col) has a mine.
	 * 
	 * @param row int 
	 * @param col int
	 * @return count associated with cell
	 */
	public int getCount(int row, int col) {
		// return the count associated with cell at (row,col) has a mine
		// return -2 for invalid cell indexes

		if (!board.isValidCell(row, col)) {
			return -2;
		}

		Cell cell = board.get(row, col);
		return (cell.getCount());
	}

	// ******************************************************
	// ******* Methods to report game status *******
	// ******* These are used by GUI for display *******
	// ******************************************************

	/**
	 * Method to report how many mines have not be flagged.
	 * @return mines that haven't been flagged 
	 */
	public int mineLeft() {
		// report how many mines have not be flagged
		return mineTotalCount - flaggedCount;

	}

	/**
	 * Method to report current game status.
	 * @return game status 
	 */
	public String getStatus() {
		// report current game status
		return Status_STRINGS[status.ordinal()];

	}

	// ******************************************************
	// ******* Methods reserved for testing/grading *******
	// ******************************************************

	// return the game board
	/**
	 * Method to return the board that was created.
	 * @return the board that was created
	 */
	public DynGrid310<Cell> getBoard() {
		return board;
	}

	// set game board
	/**
	 * Method to set game board.
	 * @param newBoard to set to board
	 * @param mineCount mine numbers
	 */
	public void setBoard(DynGrid310<Cell> newBoard, int mineCount) {
		// set board
		this.board = newBoard;

		// set size
		rowCount = board.getNumRow();
		colCount = board.getNumCol();

		// set other features
		status = Status.INIT;

		flaggedCount = 0;
		clickedCount = 0;
		mineTotalCount = mineCount;
	}

	// ******************************************************
	// ******* END of PROVIDED code *******
	// ******************************************************

	// ******************************************************
	// ******* Code you need to implement *******
	// ******* Remember to add JavaDoc *******
	// ******************************************************

	// ADD MORE PRIVATE MEMBERS HERE IF NEEDED!

	// *******************************************************
	// ******* Methods to support board initialization *******
	// *******************************************************

	/**
	 * Method to create an empty board.
	 * @param rowNum row numbers 
	 * @param colNum columns numbers
	 * @return the empty board
	 */
	public static DynGrid310<Cell> genEmptyBoard(int rowNum, int colNum) {

		// create and return a grid with rowNum x colNum individual cells in it
		// - all cells are default cell objects (no mines)
		// - if rowNum or colNum is not positive, return null

		// amortized O(rowCount x colCount)
		if (rowNum < 1 || colNum < 1) {
			return null;
		}
		DynGrid310<Cell> newboard = new DynGrid310<Cell>();
		for (int row = 0; row < rowNum; row++) {
			DynArr310<Cell> newRow = new DynArr310<>();
			for (int col = 0; col < colNum; col++) {
				Cell cell = new Cell();
				// newboard.set(row, col, cell);
				newRow.add(cell);
			}
			newboard.addRow(row, newRow);
		}

		return newboard; // default return, remove or change as needed

	}

	// DynGrid310<Cell> newboard = new DynGrid310<Cell>();
	/**
	 * Method to return number of mines in the neighbor cells.
	 * @param row int
	 * @param col int
	 * @return number of mine neighbors
	 */
	public int countNbrMines(int row, int col) {
		// count the number of mines in the neighbor cells of cell (row, col)
		// return -2 for invalid row / col indexes
		// return -1 if cell at (row, col) has a mine underneath it
		if (row < 0 || row >= board.getNumRow() || col < 0 || col >= board.getNumCol()) {
			return -2;
		}
		if (board.get(row, col).hasMine()) {
			return -1;
		}
		int mineCount = 0;
		int[] dx = { -1, -1, -1, 0, 0, 1, 1, 1 };
		int[] dy = { -1, 0, 1, -1, 1, -1, 0, 1 };

		// Iterate through all neighboring cells
		for (int i = 0; i < 8; i++) {
			int newRow = row + dx[i];
			int newCol = col + dy[i];

			// Check if the neighboring cell is valid and contains a mine
			if (board.isValidCell(newRow, newCol) && board.get(newRow, newCol).hasMine()) {
				mineCount++;
			}
		}

		// O(1)
		return mineCount; // default return, remove or change as needed

	}

	// ******************************************************
	// ******* Methods to support game operations *******
	// ******************************************************

	/**
	 * Method to open a specific cell, check for a mine, update the game status, and set to visible.
	 * @param row int
	 * @param col int
	 * @return the number on the cell
	 */
	public int clickAt(int row, int col) {
		// open cell located at (row,col)
		// for a valid cell location:
		// - no change if cell is already flagged or exposed, return -2
		// - if cell has a mine, open it would explode the mine,
		// update game status accordingly and return -1
		// - otherwise, open this cell and return number of mines adjacent to it
		// - if the cell is not adjacent to any mines (i.e. a zero-count cell),
		// also open all zero-count cells that are connected to this cell,
		// as well as all cells that are orthogonally or diagonally adjacent
		// to those zero-count cells.
		// - HINT: recursion can really help! Consider define private helper methods.
		// - update game status as needed
		// - update other game features as needed
		//
		// for an invalid cell location:
		// - no change and return -2

		// return -2; //default return, remove or change as needed
		if (!board.isValidCell(row, col)) {
			return -2;
		}

		Cell clickedCell = board.get(row, col);
		status = Status.INGAME;

		// If the cell is already flagged or exposed, do nothing and return -2
		if (clickedCell.isFlagged() || clickedCell.visible()) {
			return -2;
		}

		// If the clicked cell contains a mine, it explodes, and the game is lost
		if (clickedCell.hasMine()) {
			status = Status.EXPLODED;
			clickedCell.setVisible();
			return -1;
		}

		// Otherwise, reveal the clicked cell
		clickedCell.setVisible();
		clickedCount++;

		// If the clicked cell has no neighboring mines, recursively reveal adjacent
		// cells
		if (clickedCell.getCount() == 0) {
			revealAdjacentCells(row, col);
		}

		// Check if the game is solved (all non-mine cells are exposed)
		if (clickedCount == rowCount * colCount - mineTotalCount) {
			status = Status.SOLVED;
		}

		// Return the number of mines adjacent to the clicked cell
		return clickedCell.getCount();

	}

	/**
	 * A helper method to check the neighbors cells if they have mines or not.
	 * @param row int 
	 * @param col int
	 */
	private void revealAdjacentCells(int row, int col) {
		// Define the eight possible directions to check for neighbors
		int[] dx = { -1, -1, -1, 0, 0, 1, 1, 1 };
		int[] dy = { -1, 0, 1, -1, 1, -1, 0, 1 };

		// Iterate through all neighboring cells
		for (int i = 0; i < 8; i++) {
			int newRow = row + dx[i];
			int newCol = col + dy[i];

			// Check if the neighboring cell is valid and not exposed
			if (board.isValidCell(newRow, newCol) && !board.get(newRow, newCol).visible()) {
				// Recursively click on the neighboring cell
				clickAt(newRow, newCol);
			}
		}
	}

	/**
	 * Method to set a cell as flagged.
	 * @param row int
	 * @param col int
	 * @return true if the cell was successfully flagged
	 */
	public boolean flagAt(int row, int col) {
		// flag at cell located at (row,col),
		// return whether the cell is flagged or not
		//
		// - no change if cell is not hidden (already open), return false
		// - otherwise, flag the cell as needed and update relevant game features
		// - update game status as needed
		//
		// - return false for an invalid cell location
		// O(1)

		// return false; //default return, remove or change as needed
		if (!board.isValidCell(row, col)) {
			return false;
		}

		Cell flaggedCell = board.get(row, col);

		// If the cell is already exposed, do not flag it and return false
		if (flaggedCell.visible()) {
			return false;
		}

		// If the cell is already flagged, unflag it
		if (!flaggedCell.isFlagged()) {
			flaggedCell.setFlagged();
			flaggedCount++;
		}

		return true;

	}

	/**
	 * Method to set a cell as unflagged.
	 * @param row int
	 * @param col int
	 * @return true if the cell was successfully unflagged
	 */
	public boolean unFlagAt(int row, int col) {
		// Un-flag at cell located at (row,col),
		// return whether the cell is updated from flagged to unflagged
		//
		// - no change if cell is not flagged before, return false
		// - otherwise, unflag the cell and update relevant game features

		// - return false for an invalid cell location
		// O(1)

		// return false; //default return, remove or change as needed
		if (!board.isValidCell(row, col)) {
			return false;
		}

		Cell flaggedCell = board.get(row, col);

		// If the cell is already exposed, do not flag it and return false
		if (flaggedCell.visible()) {
			return false;
		}

		// If the cell is already flagged, unflag it
		if (flaggedCell.isFlagged()) {
			flaggedCell.unFlagged();
			flaggedCount--;

		} else {
			return false;
		}
	

		return true;

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
	public static void main(String args[]) {
		// basic: get an empty board with no mines
		DynGrid310<Cell> myBoard = MineSweeper.genEmptyBoard(3, 4);

		// board size, all 12 cells should be in the default state, no mines
		if (myBoard.getNumRow() == 3 && myBoard.getNumCol() == 4 && !myBoard.get(0, 0).hasMine()
				&& !myBoard.get(1, 3).visible() && !myBoard.get(2, 2).isFlagged()
				&& myBoard.get(2, 1).getCount() == -1) {
			System.out.println("Yay 0");
		}

		// init a game at TINY level
		// use the same random number sequence as GUI -
		// this will create the same board as Table 2 of p1 spec PDF.
		// you can change this for your own testing.

		Random random = new Random(10);
		MineSweeper game = new MineSweeper(random.nextInt(), Level.TINY);

		// print out the initial board and verify game setting
		// System.out.println(game);
		// expected board:
		// - |0|1|2|3|4|
		// 0 |?|?|?|?|?|
		// 1 |?|?|?|?|?|
		// 2 |?|?|?|?|?|
		// 3 |?|?|?|?|?|
		// 4 |?|?|?|?|?|

		// countNbrMines
		if (game.countNbrMines(0, 0) == 0 && game.countNbrMines(4, 2) == 1 && game.countNbrMines(3, 3) == 3
				&& game.countNbrMines(2, 3) == -1 && game.countNbrMines(5, 5) == -2) {
			System.out.println("Yay 1");
		}

		// first click at (3,3)
		/*
		 * System.out.println(game.clickAt(-1,0));
		 * System.out.println(game.clickAt(3,3));
		 * System.out.println(game.isVisible(3,3));
		 * System.out.println(!game.isVisible(0,0));
		 * System.out.println(game.getStatus()); System.out.println(game.mineLeft());
		 */

		if (game.clickAt(-1, 0) == -2 && game.clickAt(3, 3) == 3 && game.isVisible(3, 3) && !game.isVisible(0, 0)
				&& game.getStatus().equals("IN_GAME") && game.mineLeft() == 3) {
			System.out.println("Yay 2");
		}
		// System.out.println(game);
		// expected board:
		// - |0|1|2|3|4|
		// 0 |?|?|?|?|?|
		// 1 |?|?|?|?|?|
		// 2 |?|?|?|?|?|
		// 3 |?|?|?|3|?|
		// 4 |?|?|?|?|?|

		// click at a mine cell
		/*
		 * System.out.println(game.clickAt(2,3));
		 * System.out.println(game.isVisible(2,3));
		 * System.out.println(game.getStatus());
		 */
		if (game.clickAt(2, 3) == -1 && game.isVisible(2, 3) && game.getStatus().equals("EXPLODED")) {
			System.out.println("Yay 3");
		}
		// System.out.println(game);
		// expected board:
		// - |0|1|2|3|4|
		// 0 |?|?|?|?|?|
		// 1 |?|?|?|?|?|
		// 2 |?|?|?|X|?|
		// 3 |?|?|?|3|?|
		// 4 |?|?|?|?|?|

		// start over with the same board
		random = new Random(10);
		game = new MineSweeper(random.nextInt(), Level.TINY);
		game.clickAt(3, 3);

		// flag and unflag
		if (game.flagAt(2, 3) && !game.isVisible(2, 3) && game.isFlagged(2, 3) && game.flagAt(2, 4)
				&& game.mineLeft() == 1 && game.unFlagAt(2, 3) && !game.isFlagged(2, 3) && game.mineLeft() == 2) {
			System.out.println("Yay 4");
		}

		// cell state & operations
		// - a flagged cell can not be clicked
		// - flag a cell already flagged does not change anything but still returns true
		// - an opened cell cannot be flagged or unflagged
		// - a hidden cell not flagged cannot be unflagged

		/*
		 * System.out.println(game.clickAt(2,4)); System.out.println(game.flagAt(2,4));
		 * System.out.println(!game.flagAt(3,3));
		 * System.out.println(!game.unFlagAt(3,3));
		 * System.out.println(!game.unFlagAt(2,3));
		 */

		if (game.clickAt(2, 4) == -2 && game.flagAt(2, 4) && !game.flagAt(3, 3) && !game.unFlagAt(3, 3)
				&& !game.unFlagAt(2, 3)) {
			System.out.println("Yay 5");
		}

		// clicking on a zero-count cell
		if (game.clickAt(0, 0) == 0 && game.isVisible(0, 0) && game.isVisible(4, 0) && game.isVisible(0, 4)
				&& game.isVisible(3, 2) && !game.isVisible(3, 4) && !game.isVisible(4, 3)) {
			System.out.println("Yay 6");
		}
		// System.out.println(game);
		// expected board:
		// - |0|1|2|3|4|
		// 0 | | | | | |
		// 1 | | |1|2|2|
		// 2 | | |1|?|F|
		// 3 | | |2|3|?|
		// 4 | | |1|?|?|

		// open all none-mine cells without any explosion solve the game!
		if (game.clickAt(4, 4) == 1 && game.clickAt(3, 4) == 3 && game.getStatus().equals("SOLVED")) {
			System.out.println("Yay 7");
		}
		// System.out.println(game);
		// expected board:
		// - |0|1|2|3|4|
		// 0 | | | | | |
		// 1 | | |1|2|2|
		// 2 | | |1|?|F|
		// 3 | | |2|3|3|
		// 4 | | |1|?|1|
	}

}