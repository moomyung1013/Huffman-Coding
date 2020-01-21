package FILE;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

public class FileWrite 
{
	FileWriter fis;
	BufferedWriter bou;
	public int bitCount;
	public int buffer;
	
	public FileWrite(String filename)
	{ //인코딩한 파일을 저장할 때 생성자
		bitCount=0;
		buffer=0;

		try 
		{
			fis=new FileWriter(filename);
			bou=new BufferedWriter(fis);
		} 
		catch (FileNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		      
	}
	public FileWrite(String filename, String str)
	{ //디코딩한 파일을 저장할 때 생성자
		try 
		{
			filename+="(unzip)";
			fis=new FileWriter(filename);
			bou=new BufferedWriter(fis);
			
			bou.write(str);
			bou.close();
			fis.close();
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void TableWrite(ArrayList<Node> node, int bitSize) throws IOException
	{
		/*Iterator <Node> it=node.iterator();
		while(it.hasNext())
		{
			Node n=it.next();
			try 
			{
				if(n.asciiCode==13)
					bou.write("\r\n");
				else
					bou.write(n.asciiCode);
				bou.write(':');
				bou.write(n.Code+"\r\n");
				bou.flush();
			} 
			catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}*/
		for(int i=0;i<node.size();i++)
		{
			try 
			{
				if(node.get(i).asciiCode==13)
					bou.write("\r\n");
				else
					bou.write(node.get(i).asciiCode);
				bou.write(':');
				bou.write(node.get(i).Code);
				bou.newLine();
				bou.flush();
			} 
			catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		bou.write("123\n");
		bou.write(Integer.toString(bitSize)+"\r\n");
		bou.flush();
	}

	public void StringToChar(String Code)
	{
		for(int i=0;i<Code.length();i++)
		{
			CharToBit(Code.charAt(i));
			if(i==Code.length()-1)
			{
				writeBit();
				break;
			}
		}
	}
	
	public void CharToBit(char code)
	{
		if(code=='1')
		{
			buffer=buffer|(64>>>bitCount);
		}
		bitCount++;
		if(bitCount==7) //1바이트가 되면
		{
			writeBit(); //buffer를 파일에 저장한다.
		}
		
	}
	public void writeBit()
	{
		if(bitCount==0)
			return;
		try 
		{
			bou.write(buffer);
			bou.flush();
			buffer=0;
			bitCount=0;
			return;
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void close()
	{
		try {
			fis.close();
			bou.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
