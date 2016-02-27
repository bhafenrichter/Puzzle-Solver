package puzzle.solver;

public class Node implements Cloneable{
    public int x;
    public int y;
    public int value;
    
    public Node(int x, int y, int value){
        this.x = x;
        this.y = y; 
        this.value = value;
    }
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}