package puzzle.solver;

import java.util.logging.Level;
import java.util.logging.Logger;
import puzzle.solver.QueueInterface;


//**********************************************************************************
//**********************************************************************************
public class LinkedQueue implements QueueInterface, java.io.Serializable, Cloneable {
	private Node firstNode; // references node for front of queue
	private Node lastNode;  // references node for back of queue
	//*******************************************************************************
	public LinkedQueue() {
		firstNode = null;
		lastNode = null;
	}
	//*******************************************************************************
	public void enqueue(Object newEntry) {
		Node newNode = new Node(newEntry, null);
		if (isEmpty())
			firstNode = newNode;
		else
			lastNode.setNextNode(newNode);
		lastNode = newNode;
	}
	//*******************************************************************************
	public Object dequeue() {
		Object front = null;
		if (!isEmpty()) {
			front = firstNode.getData();
			firstNode = firstNode.getNextNode();

			if (firstNode == null)
				lastNode = null;
		}
		return front;
	}
	//*******************************************************************************
	public Object getFront() {
		Object front = null;
		if (!isEmpty())
			front = firstNode.getData();
		return front;
	}
	//*******************************************************************************
	public boolean isEmpty() {
		return firstNode == null;
	}
	//*******************************************************************************
	public void clear() {
		firstNode = null;
		lastNode = null;
	}
	//*******************************************************************************
	//*******************************************************************************
	private class Node {
		private Object data;
		private Node next;
		//***************************************************************************
		private Node(Object dataPortion) {
			data = dataPortion;
			next = null;	
		}
		//***************************************************************************
		private Node(Object dataPortion, Node nextNode)	{
			data = dataPortion;
			next = nextNode;	
		}
		//***************************************************************************
		private Object getData() {
			return data;
		}
		//***************************************************************************
		private void setData(Object newData) {
			data = newData;
		}
		//***************************************************************************
		private Node getNextNode() {
			return next;
		}
		//***************************************************************************
		private void setNextNode(Node nextNode) {
			next = nextNode;
		}
		//***************************************************************************
	}
        
        public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
        
        public int count() {
            LinkedQueue temp;
            try {
                temp = (LinkedQueue) this.clone();
                int count = 0;
                while(temp.dequeue() != null){
                    count++;
                }
                return count;
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(LinkedQueue.class.getName()).log(Level.SEVERE, null, ex);
                return 0;
            }
        }
	//*******************************************************************************
	//*******************************************************************************
}
//**********************************************************************************
//**********************************************************************************

