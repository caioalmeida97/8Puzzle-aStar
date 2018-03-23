package aStar;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author caio
 */
public class AStarTest {

    public static void main(String[] args) {
        //int[][] m = {{1,2,3},{4,5,6},{7,8,0}};
        PuzzleNode root = new PuzzleNode(null, new PuzzleState(), 0);
        AStarTest a = new AStarTest();
        Node n = a.aStar(root);
        if (n != null) {
            System.out.println("\nThe path is listed below :)");
            n.printPath();
        }
        System.out.println("FINISHED!!");

    }

    public Node aStar(Node root) {
        LinkedList<Node> fringe = new LinkedList<>();
        fringe.add(root);

        do {
            Node node = fringe.remove(0);
            if (node.state.isGoal()) {
                System.out.println("The solution is: \n" + node.toString());
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
        } while (true);

    }

}
