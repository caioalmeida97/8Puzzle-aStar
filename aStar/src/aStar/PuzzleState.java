package aStar;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author caio
 */
public class PuzzleState extends State {

    public enum Direction {
        RIGHT, LEFT, UP, DOWN
    }

    int[][] puzzle;
    String description;
    double h;

    //For the 1st state instance
    public PuzzleState() {
        this.description = "Initial State";
        do {
            System.out.println("Sorting puzzle...");
            puzzle = generate();
            //printPuzzle(puzzle);
        } while (!isSolvable());
        System.out.println("Solvable puzzle sorted!");
        printPuzzle();
        this.h = h();
    }

    public PuzzleState(int[][] puzzle, String description) {
        this.puzzle = puzzle;
        this.description = description;
        this.h = h();
    }

    //Checks if the puzzle is goal or not
    @Override
    public boolean isGoal() {
        int[][] goal = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}};
        int count = 0;
        for (int i = 0; i < puzzle.length; i++) {
            for (int j = 0; j < puzzle[0].length; j++) {
                if (puzzle[i][j] == goal[i][j]) {
                    count++;
                }
            }
        }
        return (count == puzzle.length * puzzle[0].length);
    }

    //Generates a random puzzle
    public int[][] generate() {
        int[][] puzzle = new int[3][3];
        List<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            numbers.add(i);
        }
        for (int i = 0; i < puzzle.length; i++) {
            for (int j = 0; j < puzzle[0].length; j++) {
                int random = (int) (Math.random() * (numbers.size()));
                puzzle[i][j] = numbers.remove(random);
            }
        }
        return puzzle;
    }

    public boolean isSolvable() {
        int n = puzzle.length;
        int[] arr = new int[(int) Math.pow(n, 2)];
        int s = 0;
        for (int j = 0; j < n; j++) {
            for (int k = 0; k < n; k++) {
                arr[s] = puzzle[j][k];
                s++;
            }

        }

        int inversion = 0;

        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = i + 1; j < arr.length; j++) {

                if (arr[i] > arr[j]) {
                    inversion++;
                }

            }

        }

        if (n % 2 != 0) { // GRID IS ODD
            if (inversion % 2 == 0) { // INVERSION IS EVEN
                return true;
            }

        } else if (n % 2 == 0) { //GRID IS EVEN
            int i = 0;
            int j = 0;
            for (i = 0; i < n; i++) {
                for (j = 0; j < n; j++) {
                    if (this.puzzle[i][j] == Math.pow(n, 2)) {
                        if (i % 2 == 0 && inversion % 2 != 0 || i % 2 != 0 && i % 2 != 0 && inversion % 2 == 0) {
                            return true;
                        }
                    }

                }

            }
        }
        return false;

    }

    public double h() {
        double h = 0;

        //Calculating by Manhattan distance
        for (int i = 0; i < puzzle.length; i++) {
            for (int j = 0; j < puzzle[0].length; j++) {

                int xi, yi, xf, yf;

                xi = j;
                yi = i;

                xf = (int) puzzle[i][j] % puzzle.length;
                yf = (int) puzzle[i][j] / puzzle.length;

                int md = Math.abs(xf - xi) + Math.abs(yf - yi);

                //0 cannot be counted
                if (puzzle[i][j] == 0) {
                    md = 0;
                }

                h += md;
            }
        }
        return h;
    }

    public List<State> expand() {

        List<State> nextStates = new ArrayList<>();
        int[] zeroPos = new int[2];
        for (int i = 0; i < puzzle.length; i++) {
            for (int j = 0; j < puzzle[0].length; j++) {
                if (puzzle[i][j] == 0) {
                    zeroPos[0] = i;
                    zeroPos[1] = j;
                }
            }
        }

        int newPuzzle[][] = new int[puzzle.length][puzzle[0].length];
        //Move Right
        if (zeroPos[1] >= 0 && zeroPos[1] < this.puzzle.length - 1) {
            newPuzzle = move(zeroPos, puzzle, Direction.RIGHT);
            nextStates.add(new PuzzleState(newPuzzle, "Moved RIGHT"));
        }

        //Move Left
        if (zeroPos[1] > 0 && zeroPos[1] <= this.puzzle.length - 1) {
            newPuzzle = move(zeroPos, puzzle, Direction.LEFT);
            nextStates.add(new PuzzleState(newPuzzle, "Moved LEFT"));
        }

        //Move Up
        if (zeroPos[0] > 0 && zeroPos[0] <= this.puzzle.length - 1) {
            newPuzzle = move(zeroPos, puzzle, Direction.UP);
            nextStates.add(new PuzzleState(newPuzzle, "Moved UP"));
        }

        //Move Down
        if (zeroPos[0] >= 0 && zeroPos[0] < this.puzzle.length - 1) {
            newPuzzle = move(zeroPos, puzzle, Direction.DOWN);
            nextStates.add(new PuzzleState(newPuzzle, "Moved DOWN"));
        }
        return nextStates;
    }

    public int[][] move(int[] zeroPos, int[][] puzzle, Direction direction) {
        int[][] copy = new int[puzzle.length][puzzle[0].length];
        for (int i = 0; i < puzzle.length; i++) {
            for (int j = 0; j < puzzle[0].length; j++) {
                copy[i][j] = puzzle[i][j];
            }
        }

        int aux = copy[zeroPos[0]][zeroPos[1]];
        switch (direction) {
            case RIGHT:
                copy[zeroPos[0]][zeroPos[1]] = copy[zeroPos[0]][zeroPos[1] + 1];
                copy[zeroPos[0]][zeroPos[1] + 1] = aux;
                break;
            case LEFT:
                copy[zeroPos[0]][zeroPos[1]] = copy[zeroPos[0]][zeroPos[1] - 1];
                copy[zeroPos[0]][zeroPos[1] - 1] = aux;
                break;
            case UP:
                copy[zeroPos[0]][zeroPos[1]] = copy[zeroPos[0] - 1][zeroPos[1]];
                copy[zeroPos[0] - 1][zeroPos[1]] = aux;
                break;
            case DOWN:
                copy[zeroPos[0]][zeroPos[1]] = copy[zeroPos[0] + 1][zeroPos[1]];
                copy[zeroPos[0] + 1][zeroPos[1]] = aux;
                break;
            default:
                break;
        }
        return copy;
    }

    //Prints the puzzle
    public void printPuzzle(int[][] puzzle) {
        System.out.println("+-----------------+");
        for (int i = 0; i < puzzle.length; i++) {
            for (int j = 0; j < puzzle.length; j++) {
                if (j == 0) {
                    System.out.print("| " + puzzle[i][j] + "\t ");
                } else if (j > 0 && j < puzzle.length - 1) {
                    System.out.print(puzzle[i][j] + "\t");
                } else if (j == puzzle.length - 1) {
                    System.out.print(puzzle[i][j] + " |");
                }
            }
            System.out.println();
        }
        System.out.println("+-----------------+");
    }

    public void printPuzzle() {
        System.out.println("+-----------------+");
        for (int i = 0; i < puzzle.length; i++) {
            for (int j = 0; j < puzzle.length; j++) {
                if (j == 0) {
                    System.out.print("| " + puzzle[i][j] + "\t ");
                } else if (j > 0 && j < puzzle.length - 1) {
                    System.out.print(puzzle[i][j] + "\t");
                } else if (j == puzzle.length - 1) {
                    System.out.print(puzzle[i][j] + " |");
                }
            }
            System.out.println();
        }
        System.out.println("+-----------------+");
    }

    @Override
    public String toString() {
        printPuzzle();
        return this.description;
    }

}
