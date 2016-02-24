package puzzle.solver;

import java.util.ArrayList;

public class PuzzleNode {
    int heuristicScore;
    Node[][] puzzle;
    ArrayList<Node> children;
    
    public PuzzleNode(){
        children = new ArrayList<Node>();
    }
}
