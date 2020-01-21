package FILE;

import java.util.*;

public class PQueue
{
	Vector<Node> Queue;
	public PQueue(ArrayList<Node> node)
	{
		Queue=new Vector<Node>();
		//this.node=node;
	}
	
	public void enqueue(Node data) 
	{
		//Node b;
		Queue.add(data);
		for(int i=0;i<Queue.size()-1;i++)
		{
			if(data.fq<Queue.get(i).fq)
			{
				Queue.remove(Queue.size()-1);
				Queue.add(i,data);
				break;
			}
		}
	}
	
	public Node dequeue() 
	{
		return Queue.remove(0);
	}
	
	public Node peek() 
	{
		return Queue.elementAt(0);
	}
	
	public boolean isEmpty() 
	{
		return Queue.isEmpty();
	}
	public int size() 
	{
		return Queue.size();
	}
}
