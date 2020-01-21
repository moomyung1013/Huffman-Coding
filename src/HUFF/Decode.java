package HUFF;

import FILE.*;
import GUI.DecodeFrame;

public class Decode 
{
	FileRead fr;
	FileWrite fw;
	String decode, originData;
	public Decode(FileRead fr, String filePath)
	{
		double startTime=System.currentTimeMillis();
		
		this.fr=fr;
		originData="";
		fr.TableDecode(filePath);
		originData=fr.BitDecode();
		decode=fr.decode;

		fw=new FileWrite(filePath, new String(decode));
		double endTime=System.currentTimeMillis();
		double dataDecodeTime=((endTime-startTime)/1000.0);
		new DecodeFrame(dataDecodeTime,originData,decode);
		
	}

}
