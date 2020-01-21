package FILE;

public class Node
{
	public char ch;
	public int fq;
	public int asciiCode;
	public String str;
	public String Code;
	public Node leftNode;
	public Node rightNode;
	public Node parent;
	
	public Node()
	{
		
	}
	public Node(char ch, int fq)
	{
		this.ch=ch;
		this.fq=fq;
		this.leftNode=null;
		this.rightNode=null;
		this.parent=null;
		this.asciiCode=(int)this.ch;
	}
	
	public Node(String str, int fq)
	{
		this.str=str;
		this.fq=fq;
	}
	
	public Node(String str, String Code)
	{
		this.str=str;
		this.Code=Code;
	}
}
