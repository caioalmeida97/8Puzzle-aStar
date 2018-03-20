/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aStar;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author caio
 */
public class PuzzleNode extends Node {

    public PuzzleNode(Node parent, State state, double unitaryCost) {
        super(parent, state, unitaryCost);
    }

    @Override
    public List<Node> successors() {
        List<State> nextStates = ((PuzzleState) state).expand();
        List<Node> ret = new ArrayList();
        for (State nextState : nextStates) {
            PuzzleState currentState = (PuzzleState) nextState;
            ret.add(new PuzzleNode(this, currentState, 1));
        }
        return ret;
    }

    public double f() {
        return this.cost + ((PuzzleState) state).h;
    }

    @Override
    public String toString() {
        return String.format("%s", state.toString());
    }

}
