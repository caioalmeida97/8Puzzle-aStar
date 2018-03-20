package aStar;

import java.util.List;

public abstract class Node {

    Node parent;
    State state;
    double cost;

    public Node(Node parent, State state, double unitaryCost) {
        this.parent = parent;
        this.state = state;
        if (parent != null) {
            this.cost = parent.cost + unitaryCost;
        }
    }

    public abstract List<Node> successors();

    public void printPath() {
        System.out.println(this);
        if (parent != null) {
            parent.printPath();
        }
    }

    public double getCost(){
        return this.cost;
    }
}
