package edu.ics211.h08;

import java.util.ArrayList;

/**
 * Class for recursively finding a solution to a Hexadecimal Sudoku problem.
 *
 * @author Biagioni, Edoardo, Cam Moore date August 5, 2016
 * @author Kathleen Dy
 * 
 * missing solveSudoku, to be implemented by the students in ICS 211
 * 
 * sudoku.length = num of rows; sudoku[i].length = num of columns in row i
 */
public class HexadecimalSudoku {

  /**
   * Find an assignment of values to sudoku cells that makes the sudoku valid.
   *
   * @param sudoku the sudoku to be solved.
   * @return whether a solution was found if a solution was found, the sudoku is filled in with the solution if no
   * solution was found, restores the sudoku to its original value.
   */
  public static boolean solveSudoku(int[][] sudoku) {
    // throw new UnsupportedOperationException("solveSudoku not implemented.");
    // System.out.println(toString(sudoku, false));
    if (checkSudoku(sudoku, false)) { // sudoku is valid
      // if (isFilled(sudoku)) {
      // return true;
      // } else {
      return nextEmpty(sudoku, 0, 0); // solve the next empty cell starting from row 0 and column 0
      // }

    }
    return false;

  }


  /**
   * solveCell() uses recursion to solve the sudoku grid
   * 
   * @param sudoku, type int[][], the sudoku grid to solve
   * @param row, type int, the row to check, the first int in sudoku --> sudoku[row][column]
   * @param column, type int, the column to check, the second int in sudoku --> sudoku[row][column]
   * @return type boolean, true if sudoku has a solution, false otherwise
   */
  private static boolean solveCell(int[][] sudoku, int row, int column) {

    // System.out.println(toString(sudoku, true));
    // System.out.printf("(%d, %d, %d)%n", row, column, index);

    // if (sudoku[row][column] == -1) { // if cell is empty
    ArrayList<Integer> legalVals = legalValues(sudoku, row, column);
    // System.out.println(toString(legalVals));

    if (legalVals == null) { // no legal values
      System.out.println("null");
      return false;
    } else { // there is at least 1 legal value
      boolean val = false;
      // loop through all legalVals until a solution is found
      for (int i = 0; i < legalVals.size(); i++) {
        sudoku[row][column] = legalVals.get(i);
        // System.out.println(legalVals.get(i));
        // if (isFilled(sudoku)) { // don't need to checkSudoku() because using a legalVal will always be valid
        // System.out.println(toString(sudoku, false));
        // // System.out.println("YAY!!!");
        // return true;
        // } else { // sudoku is valid, but not filled --> continue filling the cells
        val = nextEmpty(sudoku, row, column);
        // if (column < 15) {
        // val = solveCell(sudoku, row, column + 1);
        // } else { // if (row < 15) {
        // // System.out.println("next row");
        // val = solveCell(sudoku, row + 1, 0);
        // }
        if (val) {
          return true;
        } else {
          // this legalVal is not a solution; Reset cell to be empty and try next legalVal
          sudoku[row][column] = -1;
        }
        // }

      }
      // no legalVals were a solution
      sudoku[row][column] = -1;
      return false;

    }
    // } else { // current cell is already filled; move on to next cell
    // return nextEmpty(sudoku, row, column);
    // }

  }


  /**
   * isFilled() checks if the whole sudoku grid is filled. Returns true if all values are not -1. Values > 16 && < 0 are
   * considered filled.
   * 
   * @param sudoku, type int[][], the sudoku grid to check
   * @return type boolean, false if there is at least one -1 value in sudoku, true otherwise
   */
  private static boolean isFilled(int[][] sudoku) {
    for (int i = 0; i < 16; i++) {
      for (int j = 0; j < 16; j++) {
        if (sudoku[i][j] == -1) {
          return false;
        }
      }
    }
    return true;
  }


  /**
   * nextEmpty() solves the next empty cell in the sudoku grid starting from the row and column parameters. Returns true
   * if there is no empty cells or if sudoku has a solution.
   * 
   * @param sudoku, type int[][], the sudoku grid to solve
   * @param row, type int, the index of the row to start at
   * @param column, type int, the index of the column to start at
   * @return type boolean, true if sudoku has a solution, false otherwise
   */
  private static boolean nextEmpty(int[][] sudoku, int row, int column) {
    for (int i = row; i < 16; i++) {
      // only loop through column parameter --> 16 when at the row parameter, else start at first column
      int x = 0;
      if (i == row) {
        x = column;
      }
      for (int j = x; j < 16; j++) {
        if (sudoku[i][j] == -1) {
          // System.out.printf("(%d, %d), (%d, %d)%n", row, column, i, j);
          return solveCell(sudoku, i, j);
        }
      }
    }
    return true;
  }


  /**
   * Find the legal values for the given sudoku and cell.
   *
   * @param sudoku the sudoku being solved.
   * @param row the row of the cell to get values for.
   * @param col the column of the cell.
   * @return an ArrayList of the valid values.
   */
  public static ArrayList<Integer> legalValues(int[][] sudoku, int row, int column) {
    int[] possible = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15 };
    ArrayList<Integer> list = null;
    // if (checkSudoku(sudoku, true)) {
    if (sudoku[row][column] == -1) {
      list = new ArrayList<>();
      for (int i = 0; i < 4; i++) {
        int x = ((row / 4) * 4) + i;
        for (int j = 0; j < 4; j++) {
          int y = ((column / 4) * 4) + j;
          if (sudoku[x][y] > -1) {
            possible[sudoku[x][y]] = -1;
          }
        }
      }

      for (int i = 0; i < sudoku.length; i++) {
        if (sudoku[i][column] > -1) {
          possible[sudoku[i][column]] = -1;
        }
        if (sudoku[row][i] > -1) {
          possible[sudoku[row][i]] = -1;
        }
      }

      for (int i = 0; i < possible.length; i++) {
        if (possible[i] != -1) {
          list.add(i);
        }
      }
    }
    // }
    return list;
  }


  /**
   * checks that the sudoku rules hold in this sudoku puzzle. cells that contain -1 are not checked.
   *
   * @param sudoku the sudoku to be checked.
   * @param printErrors whether to print the error found, if any.
   * @return true if this sudoku obeys all of the sudoku rules, otherwise false.
   */
  public static boolean checkSudoku(int[][] sudoku, boolean printErrors) {
    if (sudoku.length != 16) {
      if (printErrors) {
        System.out.println("sudoku has " + sudoku.length + " rows, should have 16");
      }
      return false;
    }
    for (int i = 0; i < sudoku.length; i++) {
      if (sudoku[i].length != 16) {
        if (printErrors) {
          System.out.println("sudoku row " + i + " has " + sudoku[i].length + " cells, should have 16");
        }
        return false;
      }
    }
    /* check each cell for conflicts */
    for (int i = 0; i < sudoku.length; i++) {
      for (int j = 0; j < sudoku.length; j++) {
        int cell = sudoku[i][j];
        if (cell == -1) {
          continue; /* blanks are always OK */
        }
        if ((cell < 0) || (cell > 16)) {
          if (printErrors) {
            System.out
                .println("sudoku row " + i + " column " + j + " has illegal value " + String.format("%02X", cell));
          }
          return false;
        }
        /* does it match any other value in the same row? */
        for (int m = 0; m < sudoku.length; m++) {
          if ((j != m) && (cell == sudoku[i][m])) {
            if (printErrors) {
              System.out.println(
                  "sudoku row " + i + " has " + String.format("%X", cell) + " at both positions " + j + " and " + m);
            }
            return false;
          }
        }
        /* does it match any other value it in the same column? */
        for (int k = 0; k < sudoku.length; k++) {
          if ((i != k) && (cell == sudoku[k][j])) {
            if (printErrors) {
              System.out.println(
                  "sudoku column " + j + " has " + String.format("%X", cell) + " at both positions " + i + " and " + k);
            }
            return false;
          }
        }
        /* does it match any other value in the 4x4? */
        for (int k = 0; k < 4; k++) {
          for (int m = 0; m < 4; m++) {
            int testRow = (i / 4 * 4) + k; /* test this row */
            int testCol = (j / 4 * 4) + m; /* test this col */
            if ((i != testRow) && (j != testCol) && (cell == sudoku[testRow][testCol])) {
              if (printErrors) {
                System.out.println("sudoku character " + String.format("%X", cell) + " at row " + i + ", column " + j
                    + " matches character at row " + testRow + ", column " + testCol);
              }
              return false;
            }
          }
        }
      }
    }
    return true;
  }


  /**
   * Converts the sudoku to a printable string.
   *
   * @param sudoku the sudoku to be converted.
   * @param debug whether to check for errors.
   * @return the printable version of the sudoku.
   */
  public static String toString(int[][] sudoku, boolean debug) {
    if ((!debug) || (checkSudoku(sudoku, true))) {
      String result = "";
      for (int i = 0; i < sudoku.length; i++) {
        if (i % 4 == 0) {
          result = result + "+---------+---------+---------+---------+\n";
        }
        for (int j = 0; j < sudoku.length; j++) {
          if (j % 4 == 0) {
            result = result + "| ";
          }
          if (sudoku[i][j] == -1) {
            result = result + "  ";
          } else {
            result = result + String.format("%X", sudoku[i][j]) + " ";
          }
        }
        result = result + "|\n";
      }
      result = result + "+---------+---------+---------+---------+\n";
      return result;
    }
    return "illegal sudoku";
  }


  /**
   * toString() takes an ArrayList as the parameter and returns a string representation of it.
   * 
   * @param list, type ArrayList, the list to print
   * @return type String, the string representation of the list
   */
  public static String toString(ArrayList<?> list) {
    StringBuilder string = new StringBuilder();
    string.append("[");

    for (int i = 0; i < list.size(); i++) {
      string.append(list.get(i));
      string.append(", ");
    }

    if (string.length() > 2) {
      string.replace(string.length() - 2, string.length(), "");
    }
    string.append("]");

    string.append(String.format(" Size: %d", list.size()));
    return string.toString();
  }
}
