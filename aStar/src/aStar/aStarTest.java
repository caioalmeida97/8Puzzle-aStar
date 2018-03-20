/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aStar;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author caio
 */
public class aStarTest {

    public static void main(String[] args) {
        PuzzleNode root = new PuzzleNode(null, new PuzzleState(), 0);
        aStarTest a = new aStarTest();
        Node n = a.aStar(root);
        if(n != null){
            n.printPath();
        }
//        System.out.println("Printing path...");
//        test.printPath();
////        puzzleTest.printPuzzle();
//        System.out.println(((PuzzleState) test.state).h);
//        System.out.println("These are the following nodes:");
//        List<Node> successors = test.successors();
//        for (Node node : successors) {
//            PuzzleNode currentNode = (PuzzleNode) node;
//            currentNode.toString();
//            System.out.println("The f value is : " + currentNode.f());
//        }

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
                        if (((PuzzleNode) suc).f() < ((PuzzleNode) indexNode).f()) {
                            fringe.add(i, suc);
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