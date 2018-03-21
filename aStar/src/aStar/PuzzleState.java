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

    int[][] puzzle = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}};
    String description;
    double h;

    //For the 1st state instance
    public PuzzleState() {
        this.description = "Initial State";
        System.out.println("Sorting puzzle...");
        puzzle = generate();
        System.out.println("Solvable puzzle sorted!");
        printPuzzle(puzzle);
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
        int[][] puzzle = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}};
        int[] zeroPos = new int[2];

        int scrambles = 100;
        for (int i = 0; i < scrambles; i++) {

            //Looking for zero position...
            for (int j = 0; j < puzzle.length; j++) {
                for (int k = 0; k < puzzle[0].length; k++) {
                    if (puzzle[j][k] == 0) {
                        zeroPos[0] = j;
                        zeroPos[1] = k;
                    }
                }
            }
            Direction dir = null;
            do {
                int random = (int) (Math.random() * (4));
                switch (random) {
                    case 0:
                        dir = Direction.RIGHT;
                        break;
                    case 1:
                        dir = Direction.LEFT;
                        break;
                    case 2:
                        dir = Direction.UP;
                        break;
                    case 3:
                        dir = Direction.DOWN;
                        break;
                }
            } while (!canIGo(dir, zeroPos));
            puzzle = move(zeroPos, puzzle, dir);

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

        //Getting the zero position in order to get the possible moves
        for (int i = 0; i < puzzle.length; i++) {
            for (int j = 0; j < puzzle[0].length; j++) {
                if (puzzle[i][j] == 0) {
                    zeroPos[0] = i;
                    zeroPos[1] = j;
                }
            }
        }

        List<Direction> moves = whereToGo(zeroPos);

        int newPuzzle[][] = new int[puzzle.length][puzzle[0].length]; //Has the same size as the original puzzle
        //Move Right
        if (moves.contains(Direction.RIGHT)) {
            newPuzzle = move(zeroPos, puzzle, Direction.RIGHT);
            nextStates.add(new PuzzleState(newPuzzle, "RIGHT"));
        }

        //Move Left
        if (moves.contains(Direction.LEFT)) {
            newPuzzle = move(zeroPos, puzzle, Direction.LEFT);
            nextStates.add(new PuzzleState(newPuzzle, "LEFT"));
        }

        //Move Up
        if (moves.contains(Direction.UP)) {
            newPuzzle = move(zeroPos, puzzle, Direction.UP);
            nextStates.add(new PuzzleState(newPuzzle, "UP"));
        }

        //Move Down
        if (moves.contains(Direction.DOWN)) {
            newPuzzle = move(zeroPos, puzzle, Direction.DOWN);
            nextStates.add(new PuzzleState(newPuzzle, "DOWN"));
        }
        return nextStates;
    }

    public int[][] move(int[] zeroPos, int[][] puzzle, Direction direction) {

        //Creates a copy so that the original puzzle does not change
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

    //Possible moves obtained by the position of the zero
    public List<Direction> whereToGo(int[] zeroPos) {
        List<Direction> directions = new ArrayList();
        if (zeroPos[1] >= 0 && zeroPos[1] < this.puzzle.length - 1) {
            directions.add(Direction.RIGHT);
        }
        if (zeroPos[1] > 0 && zeroPos[1] <= this.puzzle.length - 1) {
            directions.add(Direction.LEFT);
        }
        if (zeroPos[0] > 0 && zeroPos[0] <= this.puzzle.length - 1) {
            directions.add(Direction.UP);
        }
        if (zeroPos[0] >= 0 && zeroPos[0] < this.puzzle.length - 1) {
            directions.add(Direction.DOWN);
        }

        return directions;
    }

    //Returns if the move is possible or not
    public boolean canIGo(Direction dir, int[] zeroPos) {
        switch (dir) {
            case RIGHT:
                return (zeroPos[1] >= 0 && zeroPos[1] < this.puzzle.length - 1);
            case LEFT:
                return (zeroPos[1] > 0 && zeroPos[1] <= this.puzzle.length - 1);
            case UP:
                return (zeroPos[0] > 0 && zeroPos[0] <= this.puzzle.length - 1);
            case DOWN:
                return (zeroPos[0] >= 0 && zeroPos[0] < this.puzzle.length - 1);
        }
        return false;
    }

    //Prints any puzzle
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

    //Prints the original puzzle
    public String printPuzzle() {
        String ret = "";
        ret += "+-----------------+\n";
        for (int i = 0; i < puzzle.length; i++) {
            for (int j = 0; j < puzzle.length; j++) {
                if (j == 0) {
                    ret += "| " + puzzle[i][j] + "\t ";
                } else if (j > 0 && j < puzzle.length - 1) {
                    ret += puzzle[i][j] + "\t";
                } else if (j == puzzle.length - 1) {
                    ret += puzzle[i][j] + " |";
                }
            }
            ret += "\n";
        }
        ret += "+-----------------+";
        return ret;
//        System.out.println(ret);
    }

    @Override
    public String toString() {
        return printPuzzle();
    }

    //Prints the puzzle as a vector
    public String printSequence() {
        String ret = "";
        for (int i = 0; i < puzzle.length; i++) {
            for (int j = 0; j < puzzle[0].length; j++) {
                ret += puzzle[i][j] + " ";
            }
        }
        return ret;
    }

}
