package HUFF;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import FILE.*;
import GUI.EncodeFrame;

public class Encode 
{
	FileWrite fw;
	HuffmanTree HufTree;
	String filename;
	ArrayList <Node> node=new ArrayList <Node>();
	public int bitSize=0;
	public String CodeStr="";
	public String NameAndCode="";
	public Encode(FileRead file, String filePath)
	{
		this.filename=filePath+"(HuffZip)";
		this.node=file.FileEncode();
		HufTree=new HuffmanTree(this.node);
		
		for(int i=0; i<node.size(); i++)
		{
			NameAndCode+=HufTree.HufCode(node.get(i));
			bitSize+=node.get(i).fq*node.get(i).Code.length();
		}
		
		int i=0;
		for(int j=0;j<file.str.length();)
		{
			if(i==node.size())
			{
				i=0;
			}
			if((int)file.str.charAt(j)==10)
				j++;
			else if(node.get(i).ch==file.str.charAt(j))
			{
				CodeStr=CodeStr+node.get(i).Code;
				i=0;
				j++;
			}
			else
				i++;
		}
		DoFileWrite();
		
		//압축 완료 시간 및 압축 시간 계산//
		double endTime=System.currentTimeMillis();
		double dataEncodeTime=((endTime-file.startTime)/1000.0);
		
		//압축 효율 계산//
		File originalFile=new File(filePath);
		File encodeFile=new File(filename);
		long originalFileSize = originalFile.length();
		long encodeFileSize = encodeFile.length();
		double dataEncodeRate = (1 - (double)encodeFileSize / originalFileSize) * 100; 	//압축률을 계산
		
		new EncodeFrame(node, dataEncodeTime, dataEncodeRate,file.str,NameAndCode);
	}
  public void DoFileWrite()
  {
	  fw=new FileWrite(filename);
		try 
		{
			fw.TableWrite(node, bitSize);
			fw.StringToChar(CodeStr);
			fw.close();
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  }
  
}
