package puzzle.solver;

public class PuzzleSolverModel {
    int puzzleSize;
    int numberOfShuffles;
    boolean isShowIntermediateStates;
    int searchMode;
    Node[][] puzzle;
    public PuzzleSolverModel(){
        searchMode = 1;
    }
}
