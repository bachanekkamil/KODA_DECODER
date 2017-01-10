package com.decoder;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class DictionaryReader {
	
	private enum STATE {NOTHING, CODE, CHAR, LENGTH, ZERO_NAME, ZEROS};
	private Map m;
	private int zeros;
	private int minBin;
	private int maxBin;
	
	DictionaryReader(String filePath){
		Reader buffer = createBuffer(filePath);
		createMap(buffer);
	}
	
	public Map getMap(){
		return m;
	}
	
	public int getMinBin(){
		return minBin;
	}
	
	public int getMaxBin(){
		return maxBin;
	}
	
	public int getZeros(){
		return zeros;
	}
	
	public void printDictionary(){
		System.out.println("Zeros: " + zeros);
		Iterator it = m.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry pair = (Map.Entry) it.next();
			Code x = (Code) pair.getKey();
			System.out.println("Kod: " + x.code + " Length: " + x.length + " Symbol: " + pair.getValue());
		}
	}
	
	private Reader createBuffer(String filePath) {
		Reader buffer = null;
		try {
			InputStream in = new FileInputStream(filePath);
			Reader reader = new InputStreamReader(in, Charset.defaultCharset());
			buffer = new BufferedReader(reader);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return buffer;
	}
	
	private void createMap(Reader buffer){
		m = new HashMap<Code, String>();
		int r;
		STATE state = STATE.NOTHING;
		StringBuilder builder = new StringBuilder();
		String value = null;
		Code key = null;
		try {
			while((r = buffer.read()) != -1)
			{
				char ch = (char) r;
				switch(state)
				{
				case NOTHING:
					if(ch=='{') state = STATE.CHAR;
					if(ch=='[') state = STATE.ZERO_NAME;
					break;
				case CODE:
					if(ch!='=') builder.append(ch);
					else {
						key = new Code();
						key.code = Integer.parseInt(builder.toString());
						builder.setLength(0);
						state = STATE.LENGTH;
					}
					break;
				case CHAR:
					if(ch!='=') builder.append(ch);
					else {
						value = builder.toString();
						builder.setLength(0);
						state = STATE.CODE;
					}
					break;
				case LENGTH:
					if(ch!='}') builder.append(ch);
					else {
						key.length = Integer.parseInt(builder.toString());
						builder.setLength(0);
						state = STATE.NOTHING;
						m.put(key, value);
					}
					break;
				case ZERO_NAME:
					if(ch == '=') state = STATE.ZEROS;
					break;
				case ZEROS:
					if(ch!=']') builder.append(ch);
					else {
						zeros = Integer.parseInt(builder.toString());
						builder.setLength(0);
						state = STATE.NOTHING;
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
