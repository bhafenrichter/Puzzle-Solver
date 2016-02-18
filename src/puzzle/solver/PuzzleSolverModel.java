package puzzle.solver;

public class PuzzleSolverModel {
    int puzzleSize;
    int numberOfShuffles;
    boolean isShowIntermediateStates;
    int searchMode;
    int[][] puzzle;
    public PuzzleSolverModel(){
        searchMode = 1;
    }
}
