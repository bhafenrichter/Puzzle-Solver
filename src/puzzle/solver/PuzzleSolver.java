package puzzle.solver;

import java.util.ArrayList;
import java.util.Random;

public class PuzzleSolver {

    public static void main(String[] args) {
        PuzzleSolverModel model = getModelParameters();
        printPuzzle(model.puzzle);
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
                    model.puzzle[i][j] = new Node(i,j,input.getInteger(false, 0, 0, 0, "[" + i + ", " + j + "] = "));
                }
            }
        }else{
            //shuffle yourself
            shuffle(model.puzzle, model.numberOfShuffles);
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
    
    private static Node[][] shuffle(Node[][] puzzle, int shuffleCount) {  
        int curX = puzzle.length - 1;
        int curY = puzzle[0].length - 1;
        int prevValue = -1;
        for(int i = 0; i < shuffleCount; i++){
            //check possible positions and see if value is there to be shuffled
            ArrayList<Node> possibleSwaps = new ArrayList<Node>();
            
            if(curX + 1 < puzzle.length){
               possibleSwaps.add(puzzle[curX+1][curY]);
            }if(curX - 1 >= 0){
               possibleSwaps.add(puzzle[curX-1][curY]);
            }if(curY + 1 < puzzle.length){
               possibleSwaps.add(puzzle[curX][curY+1]);
            }if(curY - 1 >= 0){
               possibleSwaps.add(puzzle[curX][curY-1]);
            }
            
            //make a random selection that wasn't the same as the last one
            Random rand = new Random();
            int num = -1;
            
            //select the node and adjust the values in the Node[][]
            Node selectedNode = new Node(-1,-1,-1);
            
            //generate number until it isn't the previous or isn't the initialized value
            while(num == -1 || selectedNode.value == prevValue){
                num = rand.nextInt(possibleSwaps.size());
                selectedNode = possibleSwaps.get(num);
            }
            
            for (int j = 0; j < puzzle.length; j++) {
                for (int k = 0; k < puzzle[0].length; k++) {
                    if(puzzle[j][k].value == selectedNode.value){
                        //switch the values, values should always be distinct
                        int prev = puzzle[curX][curY].value;
                        puzzle[curX][curY].value = selectedNode.value;
                        puzzle[selectedNode.x][selectedNode.y].value = prev;
                        
                        //save value so we don't backtrack in randomness
                        prevValue = puzzle[curX][curY].value;
                        
                        curX = selectedNode.x;
                        curY = selectedNode.y;
                    }
                }
            }
            
            //printPuzzle(puzzle);
        }
        return puzzle;
    }
    
    private static Node[][] generatePuzzle(int puzzleSize) {
        Node[][] puzzle = new Node[0][0];
        switch(puzzleSize){
            case 8:
                puzzle = new Node[3][3];
                break;
            case 15:
                puzzle = new Node[4][4];
                break;
            case 24:
                puzzle = new Node[5][5];
                break;
            case 35:
                puzzle = new Node[6][6];
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
                    puzzle[i][j] = new Node(i,j,0);
                } else {
                    //populate the array
                    puzzle[i][j] = new Node(i,j,counter);
                    counter++;
                }

            }
        }
        return puzzle;
    }
    
    private static void printPuzzle(Node[][] puzzle){
        System.out.println("");
        for(int i = 0; i < puzzle.length; i++){
            for (int j = 0; j < puzzle[0].length; j++) {
                if(puzzle[i][j].value != 0){
                    System.out.print(puzzle[i][j].value + "\t");
                }else{
                    System.out.print("[] \t");
                }
                
            }
            System.out.println("");
        }
    }
}
