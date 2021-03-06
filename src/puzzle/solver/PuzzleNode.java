package puzzle.solver;

import java.util.ArrayList;

public class PuzzleNode implements Cloneable{
    int heuristicScore;
    Node[][] puzzle;
    ArrayList<PuzzleNode> children;
    PuzzleNode parent;
    public PuzzleNode(){
        children = new ArrayList<PuzzleNode>();
        puzzle = new Node[3][3];
    }
    
    public PuzzleNode(Node[][] puzzle) throws CloneNotSupportedException{
        this.puzzle = new Node[puzzle.length][puzzle[0].length];
        for (int i = 0; i < puzzle.length; i++) {
            for (int j = 0; j < puzzle.length; j++) {
                this.puzzle[i][j] = (Node) puzzle[i][j].clone();
            }
        }
    }

    public int getDepth(){
        int depth = 0;
        PuzzleNode cur = this;
        while(cur.parent != null){
            depth++;
            cur = cur.parent;
        }
        return depth;
    }
    
    public int getRawScore(Node[][] goalState) {
        int rawScore = 0;
        for (int i = 0; i < goalState.length; i++) {
            for (int j = 0; j < goalState.length; j++) {
                if (puzzle[i][j].value != goalState[i][j].value) {
                    rawScore++;
                }
            }
        }
        return rawScore;
    }
    
    public int getBreadthTotalScore(Node[][] goalState){
        //return getRawScore(goalState) + getDepth();
        return 0;
    }
    
    public int getBestTotalScore(){
        return 0;
    }
    
    public int computeRawScore() {
        return 0;
    }

    public int computeTotalScore(int depth) {
        return 0;
    }
    
    
}