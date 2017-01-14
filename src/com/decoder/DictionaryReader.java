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
import java.util.Map.Entry;


public class DictionaryReader {
	
	private static final String METHOD_NAME_SINGLE_CHARACTER = "SingleCharacter";
	private static final String METHOD_NAME_TWO_CHARACTERS = "TwoCharacters";
	private static final String METHOD_NAME_CONTEXT_CHARACTERS = "ContextCharacter";
	private static final String TYPE_NAME_TEXT = "text";
	private static final String TYPE_NAME_PHOTO = "image";
	private enum STATE {NOTHING, CODE, CHAR, LENGTH, ZERO_NAME, ZEROS, METHOD_NAME, TYPE, TYPE_NAME};
	public enum METHOD {UNKNOWN, SINGLE_CHARACTER, TWO_CHARACTERS, CONTEXT_CHARACTERS};
	private METHOD method = METHOD.UNKNOWN;
	private Map<Code, String> m;
	private int zeros;
	private int minBin = Integer.MAX_VALUE;
	private boolean isPhoto = false;
	
	public boolean getIsPhoto() {
		return isPhoto;
	}
	
	public static final String getNameOfMethod(METHOD method) {
		switch(method){
			case SINGLE_CHARACTER : return METHOD_NAME_SINGLE_CHARACTER;
			case TWO_CHARACTERS : return METHOD_NAME_TWO_CHARACTERS;
			case CONTEXT_CHARACTERS : return METHOD_NAME_CONTEXT_CHARACTERS;
			default : return "UNKNOWN_METHOD";
		}
		
	}
	
	DictionaryReader(String filePath){
		InputStream in = null;
		Reader buffer = null;
		try {
			in = new FileInputStream(filePath);
			Reader reader = new InputStreamReader(in, Charset.defaultCharset());
			buffer = new BufferedReader(reader);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		createMap(buffer);
		try {
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Map<Code, String> getMap(){
		return m;
	}
	
	public int getMinBin(){
		return minBin;
	}
	
	public int getZeros(){
		return zeros;
	}
	
	public void printDictionary(){
		System.out.println("Zeros: " + zeros);
		Iterator<Entry<Code, String>> it = m.entrySet().iterator();
		while(it.hasNext()){
			@SuppressWarnings("rawtypes")
			Map.Entry pair = (Map.Entry) it.next();
			Code x = (Code) pair.getKey();
			System.out.println("Kod: " + x.code + " Length: " + x.length + " Symbol: " + pair.getValue());
		}
	}
	
	private void checkMin()
	{
		Iterator<Entry<Code, String>> it = m.entrySet().iterator();
		while(it.hasNext()){
			@SuppressWarnings("rawtypes")
			Map.Entry pair = (Map.Entry) it.next();
			Code x = (Code) pair.getKey();
			if(x.length < minBin)
				minBin = x.length;
		}
	}
	
	private void setMethodName(String methodName) {
		if(methodName.equals(METHOD_NAME_TWO_CHARACTERS))
			method = METHOD.TWO_CHARACTERS;
		else if(methodName.equals(METHOD_NAME_CONTEXT_CHARACTERS))
			method = METHOD.CONTEXT_CHARACTERS;
			else if(methodName.equals(METHOD_NAME_SINGLE_CHARACTER))
				method = METHOD.SINGLE_CHARACTER;
	}
	
	private void setType(String typeName) {
		if(typeName.equals(TYPE_NAME_TEXT))
			isPhoto = false;
		else if(typeName.equals(TYPE_NAME_PHOTO))
			isPhoto = true;
	}
	
	public METHOD getMethod()
	{
		return method;
	}
	
	private void createMap(Reader buffer){
		m = new HashMap<Code, String>();
		int r;
		STATE state = STATE.NOTHING;
		StringBuilder builder = new StringBuilder();
		String value = null;
		Code key = null;
		int should_be_symbols = 1;
		int symbols = 0;
		try {
			while((r = buffer.read()) != -1)
			{
				char ch = (char) r;
				switch(state)
				{
				case NOTHING:
					if(ch=='{') state = STATE.CHAR;
					if(ch=='[') state = STATE.METHOD_NAME;
					break;
				case CODE:
					if(ch!='=') {
						builder.append(ch);
						
					}
					else {
						key = new Code();
						key.code = Integer.parseInt(builder.toString());
						builder.setLength(0);
						state = STATE.LENGTH;
						
					}
					break;
				case CHAR:
					if(ch!='=' || symbols < should_be_symbols) {
						builder.append(ch);
						symbols++;;
					}
					else {
						value = builder.toString();
						builder.setLength(0);
						state = STATE.CODE;
						symbols=0;
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
					break;
				case METHOD_NAME:
					if(ch != ']') builder.append(ch);
					else {
						String method = builder.toString();
						setMethodName(method);
						if(this.method != METHOD.SINGLE_CHARACTER)
							should_be_symbols = 2;
						builder.setLength(0);
						state = STATE.TYPE_NAME;
					}
					break;
				case TYPE_NAME:
					if(ch == '=') state = STATE.TYPE;
					break;
				case TYPE:
					if(ch!=']') builder.append(ch);
					else {
						String method = builder.toString();
						setType(method);
						builder.setLength(0);
						state = STATE.ZERO_NAME;
					}
				
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		checkMin();
	}
	
}
