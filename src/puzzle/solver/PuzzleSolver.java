package puzzle.solver;

public class PuzzleSolver {

    public static void main(String[] args) {
        PuzzleSolverModel model = getModelParameters();
        
        switch(model.searchMode){
            case 1:
                breadthFirst(model);
                break;
            case 2:
                bestFirst(model);
                break;
            default:
                System.out.println("Invalid input, please try again.");
                break;
        }
    }
    private static void breadthFirst(PuzzleSolverModel model) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static void bestFirst(PuzzleSolverModel model) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private static PuzzleSolverModel getModelParameters() {
        PuzzleSolverModel model = new PuzzleSolverModel();
        
        KeyboardInputClass input = new KeyboardInputClass();
        
        model.puzzleSize = input.getInteger(true, 0, 0, 35, "Specify the puzzle size (8, 15, 24, or 35; 0 to exit):");
        
        model.puzzle = generatePuzzle(model.puzzleSize);
        
        model.numberOfShuffles = input.getInteger(false, 0, 0, 0, "Number of shuffle moves desired? (press ENTER alone to specify starting board):");
        
        if(model.numberOfShuffles == 0){
            for (int i = 0; i < model.puzzle.length; i++) {
                for (int j = 0; j < model.puzzle[0].length; j++) {
                    model.puzzle[i][j] = input.getInteger(false, 0, 0, 0, "[" + i + ", " + j + "] = ");
                }
            }
        }else{
            //shuffle yourself
            for(int i = 0; i < model.numberOfShuffles; i++){
                shuffle(model.puzzle);
            }
        }
        
        String str = input.getKeyboardInput("Show intermediate board positions? (Y/N: Default=N)");
        if(str.toLowerCase().equals("Y") || str.toLowerCase().equals("Yes")){
            model.isShowIntermediateStates = true;
        }else{
            model.isShowIntermediateStates = false;
        }
        
        model.searchMode = input.getInteger(true, 0, 0, 2, "Search mode (1=breadth-first (default); 2=best-first):");
        return model;
    }
    
    private static void shuffle(int[][] puzzle) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private static int[][] generatePuzzle(int puzzleSize) {
        int[][] puzzle = new int[0][0];
        switch(puzzleSize){
            case 8:
                puzzle = new int[3][3];
                break;
            case 15:
                puzzle = new int[4][4];
                break;
            case 24:
                puzzle = new int[5][5];
                break;
            case 35:
                puzzle = new int[6][6];
                break;
            case 0:
          
                break;
            default:
                
                break;
        }
        
        //populate the puzzle
        int counter = 1;
        for (int i = 0; i < puzzle.length; i++) {
            for (int j = 0; j < puzzle[0].length; j++) {
                if (counter == puzzleSize + 1) {
                    //give it the empty space
                    puzzle[i][j] = 0;
                } else {
                    //populate the array
                    puzzle[i][j] = counter;
                    counter++;
                }

            }
        }
        
        return puzzle;
    }
}
