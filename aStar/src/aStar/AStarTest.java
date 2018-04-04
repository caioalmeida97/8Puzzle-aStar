package aStar;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

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
            System.out.println("Size of the fringe: " + fringe.size());
            Node node = fringe.remove(0);
            if (node.state.isGoal()) {
                System.out.println("The solution is: \n" + node.toString());
                return node;
            } else {
                List<Node> successors = node.successors();
                for (Node suc : successors) {
                    int fsize = fringe.size(); //This avoids endless for
                    for (ListIterator<Node> iterator = fringe.listIterator(); iterator.hasNext();) {
                        if (((PuzzleNode) suc).f() <= ((PuzzleNode) iterator.next()).f()) {
                            if (!fringe.contains(suc)) {
                                iterator.previous();
                                iterator.add(suc);
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
