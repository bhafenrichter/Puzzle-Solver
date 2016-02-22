/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package puzzle.solver;

public class Node {
    public int x;
    public int y;
    public int value;
    
    public Node(int x, int y, int value){
        this.x = x;
        this.y = y; 
        this.value = value;
    }
}
