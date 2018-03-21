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
        return String.format("[%s, f:%2.0f, g:%2.0f, h:%2.0f]", state.toString(), f(), cost, ((PuzzleState)state).h);        
        
    }

    @Override
    public boolean equals(Object node) {
        PuzzleState pz1 = (PuzzleState) ((PuzzleNode) node).state;
        PuzzleState currentState = (PuzzleState) state;
        int count = 0;
        for (int i = 0; i < currentState.puzzle.length; i++) {
            for (int j = 0; j < currentState.puzzle[0].length; j++) {
                if (pz1.puzzle[i][j] == currentState.puzzle[i][j]) {
                    count++;
                }
            }
        }
        return (count == (currentState.puzzle.length * currentState.puzzle[0].length)
                && ((PuzzleNode)node).f() == f());
    }
}
