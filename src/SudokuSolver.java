/**
 * SudokuSolver - This class takes a 2d 9x9 array of ints as the unsolved board, where 0 represents a blank spot. It
 * prints this in a more presentable way by calling formatBoard(). It then verifies if the board is solvable or not by
 * traversing it row-by-row, checking if the number it is trying (ranging from 1 to 9) matches any in its current row,
 * column, or 3 by 3 grid. If the three methods for checking this return false, this means there are no matching numbers
 * in the column, row or grid, therefore it is a valid placement. Equally, if any of these methods return true, there
 * must be a matching number in 1 of the 3 board-attributes.
 *
 * If the algorithm faces scenario where there are no possible valid placements in a spot, it backtracks to the previous
 * spot and sets its value back to 0. This is because although its placement may have been valid, there could be other
 * potential valid placements in that spot, which would fix the impossible-scenario. This entire process is done
 * recursively until all empty spots are solved, in which case, the solved board is printed below the original, along
 * with a message: "Solved successfully!". If it cannot place a number ranging from 1 to 9 in an empty spot, the board
 * is determined unsolvable, and a sad print-statement follows.
 */
public class SudokuSolver {

    public final int BOARD_SIZE = 9;
    private final int[][] board = { {7, 0, 2, 0, 5, 0, 6, 0, 0},
                                    {0, 0, 0, 0, 0, 3, 0, 0, 0},
                                    {1, 0, 0, 0, 0, 9, 5, 0, 0},
                                    {8, 0, 0, 0, 0, 0, 0, 9, 0},
                                    {0, 4, 3, 0, 0, 0, 7, 5, 0},
                                    {0, 9, 0, 0, 0, 0, 0, 0, 8},
                                    {0, 0, 9, 7, 0, 0, 0, 0, 5},
                                    {0, 0, 0, 2, 0, 0, 0, 0, 0},
                                    {0, 0, 7, 0, 4, 0, 2, 0, 3} };

    /**
     * Main method that prints the original board, and either the solved or unsolved board
     * @param args - Contains the array of Strings passed in from command line arguments (if there are any)
     */
    public static void main(String[] args) {

        SudokuSolver solver = new SudokuSolver();
        System.out.println("\nOriginal board:\n");
        solver.formatBoard();

        if (solver.isSolvable(solver.getBoard())) {
            System.out.println("\nSolved successfully! :D\n");
            solver.formatBoard();
            return;
        }
        System.out.println("\nThis board is unsolvable, sadly :(\n");
    }
    
    /**
     * Format the board in a more presentable fashion using various forms of punctuation at specified indexes
     */
    private void formatBoard() {

        for (int row = 0; row < this.BOARD_SIZE; row++) {
            if (row % 3 == 0 && row != 0) {
                System.out.println(" |-----+-----+-----| ");
            } else if (row % 3 == 0) {
                System.out.println(" o-----------------o ");
            }

            for (int column = 0; column < this.BOARD_SIZE; column++) {
                if (column % 3 == 0) {
                    System.out.print(" | ");
                }
                System.out.print(this.board[row][column]);
            }
            System.out.println(" | ");
        }
        System.out.println(" o-----------------o ");
    }

    /**
     * Check if the number being tried is equal to any in its current row
     * @param board - the 2d array representing the board
     * @param n - the number being checked in the said row
     * @param row - the current row
     * @return - true if the number is equal to the one being checked in the current row, false otherwise
     */
    private boolean isNumberInRow(int[][] board, int n, int row) {

        for (int i = 0; i < this.BOARD_SIZE; i++) {
            if (board[row][i] == n) { return true; }
        }

        return false;
    }

    /**
     * Check if the number being tried is equal to any in its current column
     * @param board - the 2d array representing the board
     * @param n - the number being checked in the said column
     * @param column - the current column
     * @return - true if the number is equal to the one being checked in the current column, false otherwise
     */
    private boolean isNumberInColumn(int[][] board, int n, int column) {

        for (int i = 0; i < this.BOARD_SIZE; i++) {
            if (board[i][column] == n) { return true; }
        }

        return false;
    }

    /**
     * Check if the number being tried is equal to any in its current 3 by 3 grid/box. This method requires a nested for
     * loop, as it needs to factor in the current rows AND columns in its 3 by 3 box. Since it needs to check all spots
     * in its box, it uses the formula: 'row/column - (row/column modulus 3)', which will always get the first position
     * of any 3 by 3 box on the board. Once it has that position, the nested for loops provide a range of the local
     * box's row column, plus 3. This allows it to loop through the entire box to try different combinations of numbers
     * for ALL empty positions.
     * @param board - the 2d array representing the board
     * @param n - the number being checked in the said row & column
     * @param row - the current row
     * @param column - the current column
     * @return - true if the number is equal to the one being checked in the current 3 by 3 grid/box
     */
    private boolean isNumberIn3x3(int[][] board, int n, int row, int column) {
        int localBoxRow = row - (row % 3);
        int localBoxColumn = column - (column % 3);

        for (int i = localBoxRow; i < localBoxRow + 3; i++) {

            for (int j = localBoxColumn; j < localBoxColumn + 3; j++) {
                if (board[i][j] == n) { return true; }
            }
        }

        return false;
    }

    /**
     * Calls all three of the above checking-methods to see if they all return false (the goal).
     * @param board - the 2d array representing the board
     * @param n - the number being checked in the said row & column
     * @param row - the current row
     * @param column - the current column
     * @return - true if all three methods return false. It returns false if any of the methods return true
     */
    private boolean isValidPlacement(int[][] board, int n, int row, int column) {
        return !isNumberInRow(board, n, row) && !isNumberInColumn(board, n, column) &&
                !isNumberIn3x3(board, n, row, column);
    }

    /**
     * This method recursively tries many combinations of numbers in each spot to check if the board is solvable. If it
     * faces a scenario where it cannot place any valid numbers in the current spot, it backtracks to the previous
     * placement in order to try other potential valid numbers so that it can keep solving the rest of the board. If it
     * goes through all of this and still cannot place valid numbers in every spot, the board is considered unsolvable.
     * @param board - the 2d array representing the board
     * @return - true if the provided 2d array is deemed a solvable sudoku board, false otherwise
     */
    private boolean isSolvable(int[][] board) {

        for (int row = 0; row < this.BOARD_SIZE; row++) {

            for (int column = 0; column < this.BOARD_SIZE; column++) {
                if (board[row][column] == 0) {

                    for (int numberToTry = 1; numberToTry <= this.BOARD_SIZE; numberToTry++) {
                        if (isValidPlacement(board, numberToTry, row, column)) {
                            board[row][column] = numberToTry;

                            if (isSolvable(board)) { return true; }
                            board[row][column] = 0;
                        }
                    }

                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Getter for the board size
     * @return - the size of the board
     */
    public int getBoardSize() {
        return this.BOARD_SIZE;
    }

    /**
     * Getter for the given Sudoku board
     * @return - the numbers in the given board
     */
    public int[][] getBoard() {
        return board;
    }
}
