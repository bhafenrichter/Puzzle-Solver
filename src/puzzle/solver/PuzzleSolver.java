package puzzle.solver;

import java.util.ArrayList;
import java.util.Random;

public class PuzzleSolver {

    public static void main(String[] args) {
        PuzzleSolverModel model = getModelParameters();
        
        System.out.println("Goal:");
        try {
            printPuzzle(new PuzzleNode(model.goalState), 0);
        } catch (CloneNotSupportedException ex) { }
        System.out.println("Start State (" + model.numberOfShuffles + " shuffle moves actually made)");
        System.out.println("Starting Board");
        printPuzzle(model.root,0);
        
        System.out.println("Working...");
        
        switch (model.searchMode) {
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
        LinkedQueue open = new LinkedQueue();
        LinkedQueue closed = new LinkedQueue();
        
        open.enqueue(model.root);
        int currentDepth = 0;
        while (!open.isEmpty()) {
            //get the next puzzle in queue
            PuzzleNode cur = (PuzzleNode) open.dequeue();
            
            //check to see if we've changed depths. Adds one to open count because it doesn't account for current one that was dequeued
            if(currentDepth != cur.getDepth()){
                System.out.println("Time at depth " + cur.getDepth() + " = " + 5 + "(Nodes in OPEN = " + (open.count() + 1) + "; CLOSED = " + closed.count());
                currentDepth = cur.getDepth();
            }
            
            //get the children
            cur.children = getPuzzleNodeChildren(cur);
            //we've found the goal state we've been looking for
            if (comparePuzzles(cur.puzzle, model.goalState)) {
                printPuzzle(cur,0);
                showFinalResults(cur);
                break;
            } else {
                //add the children to the queue
                for (int i = 0; i < cur.children.size(); i++) {
                    try {
                        //check to see if child is in the queue already
                        if (!isInQueues((LinkedQueue) open.clone(), (LinkedQueue) closed.clone(), cur.children.get(i))) {
                            open.enqueue(cur.children.get(i));
                        }
                    } catch (CloneNotSupportedException ex) { }
                }

                //add current puzzlenode to closed
                closed.enqueue(cur);
            }
        }
    }

    private static void bestFirst(PuzzleSolverModel model) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static PuzzleSolverModel getModelParameters() {
        PuzzleSolverModel model = new PuzzleSolverModel();

        KeyboardInputClass input = new KeyboardInputClass();

        model.puzzleSize = input.getInteger(true, 0, 0, 35, "Specify the puzzle size (8, 15, 24, or 35; 0 to exit):");

        model.goalState = generatePuzzle(model.puzzleSize);
        model.numberOfShuffles = input.getInteger(false, 0, 0, 0, "Number of shuffle moves desired? (press ENTER alone to specify starting board):");

        if (model.numberOfShuffles == 0) {
            for (int i = 0; i < model.root.puzzle.length; i++) {
                for (int j = 0; j < model.root.puzzle[0].length; j++) {
                    model.root.puzzle[i][j] = new Node(i, j, input.getInteger(false, 0, 0, 0, "[" + i + ", " + j + "] = "));
                }
            }
        } else {
            //shuffle yourself (generate puzzle again because pass by reference sucks in Java
            model.root.puzzle = shuffle(generatePuzzle(model.puzzleSize), model.numberOfShuffles);
        }
        String str = input.getKeyboardInput("Show intermediate board positions? (Y/N: Default=N)");
        if (str.toLowerCase().equals("Y") || str.toLowerCase().equals("Yes")) {
            model.isShowIntermediateStates = true;
        } else {
            model.isShowIntermediateStates = false;
        }

        model.searchMode = input.getInteger(true, 0, 0, 2, "Search mode (1=breadth-first (default); 2=best-first):");
        return model;
    }

    private static Node[][] shuffle(Node[][] puzzle, int shuffleCount) {
        int curX = puzzle.length - 1;
        int curY = puzzle[0].length - 1;
        int prevValue = -1;
        for (int i = 0; i < shuffleCount; i++) {
            //check possible positions and see if value is there to be shuffled
            ArrayList<Node> possibleSwaps = new ArrayList<Node>();

            if (curX + 1 < puzzle.length) {
                possibleSwaps.add(puzzle[curX + 1][curY]);
            }
            if (curX - 1 >= 0) {
                possibleSwaps.add(puzzle[curX - 1][curY]);
            }
            if (curY + 1 < puzzle.length) {
                possibleSwaps.add(puzzle[curX][curY + 1]);
            }
            if (curY - 1 >= 0) {
                possibleSwaps.add(puzzle[curX][curY - 1]);
            }

            //make a random selection that wasn't the same as the last one
            Random rand = new Random();
            int num = -1;

            //select the node and adjust the values in the Node[][]
            Node selectedNode = new Node(-1, -1, -1);

            //generate number until it isn't the previous or isn't the initialized value
            while (num == -1 || selectedNode.value == prevValue) {
                num = rand.nextInt(possibleSwaps.size());
                selectedNode = possibleSwaps.get(num);
            }

            for (int j = 0; j < puzzle.length; j++) {
                for (int k = 0; k < puzzle[0].length; k++) {
                    if (puzzle[j][k].value == selectedNode.value) {
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
        switch (puzzleSize) {
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
                    puzzle[i][j] = new Node(i, j, 0);
                } else {
                    //populate the array
                    puzzle[i][j] = new Node(i, j, counter);
                    counter++;
                }

            }
        }
        return puzzle;
    }

    private static boolean comparePuzzles(Node[][] p1, Node[][] p2) {
        for (int i = 0; i < p1.length; i++) {
            for (int j = 0; j < p1[0].length; j++) {
                if (p1[i][j].value != p2[i][j].value) {
                    return false;
                }
            }
        }
        return true;
    }

    private static void printPuzzle(PuzzleNode pn, int depth) {
        System.out.println("");
        for (int i = 0; i < pn.puzzle.length; i++) {
            for (int j = 0; j < pn.puzzle[0].length; j++) {
                if (pn.puzzle[i][j].value != 0) {
                    System.out.print(pn.puzzle[i][j].value + "\t");
                } else {
                    System.out.print("[] \t");
                }

            }
            System.out.println("");
        }
        System.out.println("(Raw Score: " + pn.computeRawScore() + " Depth = " +  depth + " Total Score = " + pn.computeTotalScore(depth));
    }

    private static boolean isInQueues(LinkedQueue open, LinkedQueue closed, PuzzleNode child) {
        while (!open.isEmpty()) {
            //if the two puzzles are equal, then don't add it to the list
            if (comparePuzzles(((PuzzleNode) open.dequeue()).puzzle, child.puzzle)) {
                return true;
            }
        }
        while (!closed.isEmpty()) {
            //if the two puzzles are equal, then don't add it to the list
            if (comparePuzzles(((PuzzleNode) closed.dequeue()).puzzle, child.puzzle)) {
                return true;
            }
        }
        return false;
    }

    private static ArrayList<PuzzleNode> getPuzzleNodeChildren(PuzzleNode cur) {
        ArrayList<PuzzleNode> puzzles = new ArrayList<PuzzleNode>();
        Node[][] copy = cur.puzzle;
        //get the zero node
        Node zeroNode = new Node(0, 0, 0);
        for (int i = 0; i < cur.puzzle.length; i++) {
            for (int j = 0; j < cur.puzzle[0].length; j++) {
                Node node = cur.puzzle[i][j];
                if (node.value == 0) {
                    zeroNode = node;
                    break;
                }
            }
        }

        //get the possible swaps
        ArrayList<Node> possibleSwaps = new ArrayList<Node>();
        if (zeroNode.x + 1 < cur.puzzle.length) {
            possibleSwaps.add(cur.puzzle[zeroNode.x + 1][zeroNode.y]);
        }
        if (zeroNode.x - 1 >= 0) {
            possibleSwaps.add(cur.puzzle[zeroNode.x - 1][zeroNode.y]);
        }
        if (zeroNode.y + 1 < cur.puzzle.length) {
            possibleSwaps.add(cur.puzzle[zeroNode.x][zeroNode.y + 1]);
        }
        if (zeroNode.y - 1 >= 0) {
            possibleSwaps.add(cur.puzzle[zeroNode.x][zeroNode.y - 1]);
        }

        //generate the puzzles and swap the tiles
        for (int i = 0; i < possibleSwaps.size(); i++) {
            //stores the 2d array of Nodes
            PuzzleNode temp;
            try {
                temp = new PuzzleNode(cur.puzzle);
                for (int j = 0; j < cur.puzzle.length; j++) {
                    for (int k = 0; k < cur.puzzle[0].length; k++) {
                        if (temp.puzzle[j][k].value == possibleSwaps.get(i).value) {
                            //switch the values, values should always be distinct
                            int prev = temp.puzzle[zeroNode.x][zeroNode.y].value;
                            temp.puzzle[zeroNode.x][zeroNode.y].value = possibleSwaps.get(i).value;
                            temp.puzzle[possibleSwaps.get(i).x][possibleSwaps.get(i).y].value = 0;
                        }
                    }
                }
                //prints out the 2d array that should be the same everytime.  But isn't
                temp.parent = cur;
                puzzles.add(temp);
            } catch (CloneNotSupportedException ex) {System.out.println(ex);}

        }
        return puzzles;
    }

    private static void showFinalResults(PuzzleNode cur) {
        KeyboardInputClass input = new KeyboardInputClass();
        
        ArrayList<PuzzleNode> puzzles = new ArrayList<PuzzleNode>();
        puzzles.add(cur);
        while(cur != null){
            puzzles.add(cur.parent);
            cur = cur.parent;
        }
        String str = input.getKeyboardInput("Success! Press ENTER to show all boards; Press S to step through:");
        
        

        for(int i = puzzles.size() - 2; i >= 0; i--){
            if(str.toLowerCase().equals("s")){
                input.getKeyboardInput("");
            }
            printPuzzle(puzzles.get(i),0);
        }
    }
}