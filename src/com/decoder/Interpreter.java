package com.decoder;

import java.util.Map;

public class Interpreter {
	private String last;
	Map dictionary;
	
	Interpreter(Map dic){
		this.dictionary = dic;
	}
	
	public void decode(byte[] bytes)
	{
		int actual = 0;
		for(byte bajt : bytes){
			for(int i=0 ; i<8 ; i++){
				if(getBit(bajt, i))
					actual = (actual << 1) + 1;
				else
					actual = actual << 1;
				if(isCode(actual)){
					System.out.println(last);
					actual = 0;
				}
			}	
		}
	}
	
	private boolean getBit(byte byt, int number)
	{
		byte bit;
		byte mask = 0x0;
		
		switch(number)
		{
		case 0:
			mask = (byte) 0x80;
			break;
		case 1:
			mask = (byte) 0x40;
			break;
		case 2:
			mask = (byte) 0x20;
			break;
		case 3:
			mask = (byte) 0x10;
			break;
		case 4:
			mask = (byte) 0x8;
			break;
		case 5:
			mask = (byte) 0x4;
			break;
		case 6:
			mask = (byte) 0x2;
			break;
		case 7:
			mask = (byte) 0x1;
			break;
		}
		
		
		bit = (byte) (byt & mask);
				
		if((int)bit != 0) return true;
		return false;
	}
	
	private boolean isCode(int key){
		if(dictionary.get(key) != null){
			last = (String) dictionary.get(key);
			return true;
		}
		
		return false;
	}
}
