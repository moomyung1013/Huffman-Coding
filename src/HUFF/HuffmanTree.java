package HUFF;

import java.util.ArrayList;

import FILE.*;

public class HuffmanTree 
{
	ArrayList <Node> node=new ArrayList<Node>();
	PQueue pq;
	public Node NewNode;
	
	public HuffmanTree(ArrayList<Node> node)
	{
		this.node=node;
		
		pq=new PQueue(this.node);
		for(int i=0;i<this.node.size();i++)
		{
			pq.enqueue(this.node.get(i));
		}
		
		int index=pq.size();
		
		for(int i=0;i<(index*2)-1;i++)
		{
			Node a, b;
			String temp;
			a=pq.peek();
			pq.dequeue();
			b=pq.peek();
			pq.dequeue();
			
			temp=String.valueOf(a.ch+b.ch);
			NewNode=new Node(temp,(a.fq)+(b.fq));
			NewNode.leftNode=a;
			NewNode.rightNode=b;
			
			a.parent=NewNode;
			b.parent=NewNode;
			
			if(!pq.isEmpty()) 
			{
				pq.enqueue(NewNode);
			}
			else
				break;
		}
	}
	
	public String HufCode(Node node)
	{
		Node parentNode=node.parent;
		Node temp=node;
		String code="";
		while(parentNode!=null)
		{
			if(temp==parentNode.leftNode)
				code='0'+code; 
			else if(temp==parentNode.rightNode)
				code='1'+code;
			temp=temp.parent;
			parentNode=parentNode.parent;
		}
		node.Code=code;
		if(node.ch==13)
			return "\r\n : "+node.Code+"\n";
		return node.ch+" : "+node.Code+"\n";
	}
	
}
