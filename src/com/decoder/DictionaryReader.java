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

import com.decoder.App.STATE;

public class DictionaryReader {
	private Map m;
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
	
	public void printDictionary(){
		Iterator it = m.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry pair = (Map.Entry) it.next();
			System.out.println(pair.getKey() + " = " + pair.getValue());
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
		m = new HashMap<Integer, String>();
		int r;
		STATE state = STATE.NOTHING;
		StringBuilder builder = new StringBuilder();
		String value = null;
		Integer key = null;
		try {
			while((r = buffer.read()) != -1)
			{
				char ch = (char) r;
				switch(state)
				{
				case NOTHING:
					if(ch=='{') state = STATE.CHAR;
					break;
				case CODE:
					if(ch!='}') builder.append(ch);
					else {
						key = new Integer(builder.toString());
						builder.setLength(0);
						state = STATE.NOTHING;
						m.put(key, value);
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
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
