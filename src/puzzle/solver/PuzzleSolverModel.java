package puzzle.solver;

public class PuzzleSolverModel {
    int puzzleSize;
    int numberOfShuffles;
    boolean isShowIntermediateStates;
    int searchMode;
    PuzzleNode root;
    Node[][] goalState;
    public PuzzleSolverModel(){
        searchMode = 1;
        root = new PuzzleNode();
    }
}
