package FILE;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;


public class FileRead 
{
	public String filename; //파일이름
	public ArrayList<Node> node=new ArrayList<Node>(); //인코딩을 위한 파일을 불러올때, 파일에서 문자노드를 저장할 리스트
	public String str=""; //원본 텍스트 파일의 내용을 저장할 변수
	public int count_c=0; //원본 텍스트 파일의 글자수 카운트
	public long startTime; //인코드 시작시간 측정
	public String decode="";
	int [] fq=new int[65536]; //문자의 빈도수를 저장하는 배열
	int ch,i;
	int count; //노드의 개수 카운트
	int bitCount=7; //비트가 이동한 횟수
	int buffer=0, bit=0; //디코딩할 파일에서 읽어온 정수 값과, 이를 비트로 표현할 변수
	BufferedReader bufRead;
	String ReadLine=null;
	String bitSize=null;
	String codeBit="";
	String originData="";
	File file;
	ArrayList <Node> codeTable=new ArrayList<Node>(); //디코딩을 할 때, 처음 읽어온 허프만 테이블 정보를 저장할 리스트
	
	public FileRead(String filename) throws IOException
	{
		i=0;
		this.filename=filename;
		file=new File(filename);
		count=0; //문자의 개수 카운트
		
	}
	
	@SuppressWarnings("resource")
	public ArrayList<Node> FileEncode()
	{
		for(int j=0;j<fq.length;j++)
		{//빈도수를 저장하는 배열 초기값 0으로 설정
			fq[j]=0;
		}
		startTime=System.currentTimeMillis();
		try
		{
			BufferedReader fread=new BufferedReader(new InputStreamReader(new FileInputStream(filename), "UTF-8"));
			ch=fread.read();
			while(ch!=-1)
			{//"\r\n" 처리를 위한 조건문
				str=str+(char)ch; //원본파일 문자열 저장
				fq[ch]++; //빈도수 계산
				if(ch==10)
				{
					//count_c--;
					fq[ch]=0;
				}
				ch=fread.read(); //다음 문자 읽기
				//count_c++; //문자 갯수 증가
			}			
			fread.close();
			
			for(int j=0;j<fq.length;j++)
			{
				if(fq[j]>0) //빈도수가 0이상이면, 해당 문자가 존재하는 뜻
				{
					node.add(new Node((char)j,fq[j]));
					//count++; //노드 수 증가
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
			if(token.length==1) //첫줄의 허프만 코드를 읽어옴
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
			for(;!token[0].contains("123");) //허프만 테이블의 끝을 "123"으로 지정함.
			{// 허프만 테이블을 다 읽어올 때까지 반복
				ReadLine=bufRead.readLine();
				originData+=(ReadLine+"\r\n");
				token=ReadLine.split(":"); //문자와 코드를 ':'단위로 나눴으므로 :단위로 끊어 스트링 저장
				if(token[0].contains("123"))
					break;
				if(token.length==1) //문자가 '\n\r'와 같은 경우 처리
				{
					ReadLine=bufRead.readLine();
					originData+=(ReadLine+"\r\n");
					token=ReadLine.split(":");
					codeTable.add(new Node("\r\n",token[1]));
				}
				else if(token.length==3) //문자가 ':'인경우  토큰이 2번나타나는 걸로 여겨져 String길이가 3이됨
				{ //이를 처리하는 조건문
					codeTable.add(new Node(":",token[2]));
				}
				else //그외 일반적인 문자의 경우 처리하는 조건문
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
		int realDecode=0; //디코딩된 문자열의 길이를 카운트
		bit=readBit();
		for(;buffer!=-1;)
		{
			Iterator<Node> it = codeTable.iterator();
			if(buffer==-1)
				break;
			if(realDecode-Integer.parseInt(bitSize)==0)
				break;//이진파일로 전달받은 원본 파일의 문자열 길이와 현재 디코딩 중인 문자열의 길이가 같다면 break
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
