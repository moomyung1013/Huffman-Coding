package FILE;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;


public class FileRead 
{
	public String filename; //�����̸�
	public ArrayList<Node> node=new ArrayList<Node>(); //���ڵ��� ���� ������ �ҷ��ö�, ���Ͽ��� ���ڳ�带 ������ ����Ʈ
	public String str=""; //���� �ؽ�Ʈ ������ ������ ������ ����
	public int count_c=0; //���� �ؽ�Ʈ ������ ���ڼ� ī��Ʈ
	public long startTime; //���ڵ� ���۽ð� ����
	public String decode="";
	int [] fq=new int[65536]; //������ �󵵼��� �����ϴ� �迭
	int ch,i;
	int count; //����� ���� ī��Ʈ
	int bitCount=7; //��Ʈ�� �̵��� Ƚ��
	int buffer=0, bit=0; //���ڵ��� ���Ͽ��� �о�� ���� ����, �̸� ��Ʈ�� ǥ���� ����
	BufferedReader bufRead;
	String ReadLine=null;
	String bitSize=null;
	String codeBit="";
	String originData="";
	File file;
	ArrayList <Node> codeTable=new ArrayList<Node>(); //���ڵ��� �� ��, ó�� �о�� ������ ���̺� ������ ������ ����Ʈ
	
	public FileRead(String filename) throws IOException
	{
		i=0;
		this.filename=filename;
		file=new File(filename);
		count=0; //������ ���� ī��Ʈ
		
	}
	
	@SuppressWarnings("resource")
	public ArrayList<Node> FileEncode()
	{
		for(int j=0;j<fq.length;j++)
		{//�󵵼��� �����ϴ� �迭 �ʱⰪ 0���� ����
			fq[j]=0;
		}
		startTime=System.currentTimeMillis();
		try
		{
			BufferedReader fread=new BufferedReader(new InputStreamReader(new FileInputStream(filename), "UTF-8"));
			ch=fread.read();
			while(ch!=-1)
			{//"\r\n" ó���� ���� ���ǹ�
				str=str+(char)ch; //�������� ���ڿ� ����
				fq[ch]++; //�󵵼� ���
				if(ch==10)
				{
					//count_c--;
					fq[ch]=0;
				}
				ch=fread.read(); //���� ���� �б�
				//count_c++; //���� ���� ����
			}			
			fread.close();
			
			for(int j=0;j<fq.length;j++)
			{
				if(fq[j]>0) //�󵵼��� 0�̻��̸�, �ش� ���ڰ� �����ϴ� ��
				{
					node.add(new Node((char)j,fq[j]));
					//count++; //��� �� ����
				}
			}
		}
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return node;
	}
	
	public void TableDecode(String filename)
	{
		String []token;
		try 
		{
			bufRead =new BufferedReader(new FileReader(filename));
			ReadLine=bufRead.readLine();
			originData+=(ReadLine+"\r\n");
			token=ReadLine.split(":");
			if(token.length==1) //ù���� ������ �ڵ带 �о��
			{
				ReadLine=bufRead.readLine();
				originData+=(ReadLine+"\r\n");
				token=ReadLine.split(":");
				codeTable.add(new Node("\r\n",token[1]));
			}
			else if(token.length==3)
			{
				codeTable.add(new Node(":",token[2]));
			}
			else
			{
				codeTable.add(new Node(token[0],token[1]));
				//ArrTable.add(codeTable);
			}
			for(;!token[0].contains("123");) //������ ���̺��� ���� "123"���� ������.
			{// ������ ���̺��� �� �о�� ������ �ݺ�
				ReadLine=bufRead.readLine();
				originData+=(ReadLine+"\r\n");
				token=ReadLine.split(":"); //���ڿ� �ڵ带 ':'������ �������Ƿ� :������ ���� ��Ʈ�� ����
				if(token[0].contains("123"))
					break;
				if(token.length==1) //���ڰ� '\n\r'�� ���� ��� ó��
				{
					ReadLine=bufRead.readLine();
					originData+=(ReadLine+"\r\n");
					token=ReadLine.split(":");
					codeTable.add(new Node("\r\n",token[1]));
				}
				else if(token.length==3) //���ڰ� ':'�ΰ��  ��ū�� 2����Ÿ���� �ɷ� ������ String���̰� 3�̵�
				{ //�̸� ó���ϴ� ���ǹ�
					codeTable.add(new Node(":",token[2]));
				}
				else //�׿� �Ϲ����� ������ ��� ó���ϴ� ���ǹ�
				{
					codeTable.add(new Node(token[0],token[1]));
				}
			}
			bitSize=bufRead.readLine();
			originData+=(bitSize+"\r\n");
		}
		
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public int readBit()
	{
		if(bitCount==7)
		{
			try
			{
				buffer=bufRead.read();
				originData+=(char)buffer;
				if(buffer==-1)
					return -1;
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
			bitCount=0;
		}
		if( (buffer & (64>>>bitCount)) == 0)
		{
			bitCount++;
			return 0;
		}
		else
		{
			bitCount++;
			return 1;
		}
	}
	public String BitDecode()
	{
		int realDecode=0; //���ڵ��� ���ڿ��� ���̸� ī��Ʈ
		bit=readBit();
		for(;buffer!=-1;)
		{
			Iterator<Node> it = codeTable.iterator();
			if(buffer==-1)
				break;
			if(realDecode-Integer.parseInt(bitSize)==0)
				break;//�������Ϸ� ���޹��� ���� ������ ���ڿ� ���̿� ���� ���ڵ� ���� ���ڿ��� ���̰� ���ٸ� break
			codeBit+=bit;
			realDecode++;
			while(it.hasNext())
			{
				Node n=it.next();
				if(n.Code.equals(codeBit))
				{
					decode+=n.str;
					codeBit="";
					break;
				}
			}
			bit=readBit();
		}
		try 
		{
			bufRead.close();
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return originData;
	}
}
