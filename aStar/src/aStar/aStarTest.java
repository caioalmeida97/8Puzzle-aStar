package aStar;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author caio
 */
public class aStarTest {

    public static void main(String[] args) {
        //int[][] m = {{1,2,3},{4,5,6},{7,8,0}};
        PuzzleNode root = new PuzzleNode(null, new PuzzleState(), 0);
        aStarTest a = new aStarTest();
        Node n = a.aStar(root);
        System.out.println("FINISHED!!");
        if (n != null) {
            n.printPath();
        }
    }

    public Node aStar(Node root) {
        LinkedList<Node> fringe = new LinkedList<>();
        fringe.add(root);

        do {
            Node node = fringe.remove(0);
            if (node.state.isGoal()) {
                System.out.println("The solution is: " + node.toString());
                return node;
            } else {
                List<Node> successors = node.successors();
                for (Node suc : successors) {
                    int fsize = fringe.size(); //This avoids endless for
                    for (int i = 0; i < fsize; i++) {
                        PuzzleNode indexNode = (PuzzleNode) fringe.get(i);
                        if (((PuzzleNode) suc).f() <= ((PuzzleNode) indexNode).f()) {
                            if (!fringe.contains(suc)) {
                                fringe.add(i, suc);
                            }
                        }
                    }
                    if (!fringe.contains(suc)) {
                        fringe.add(suc);
                    }
                }
            }

            System.out.println("\nI'm about to print the fringe...");
            int i = 0;
            for (Node debug : fringe) {
                System.out.print(i + ": " + debug.toString() + " | ");
                i++;
            }
            System.out.println("\nPrinted fringe!!");

        } while (true);

    }

}
