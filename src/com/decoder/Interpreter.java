package com.decoder;

import java.util.Map;

public class Interpreter {
	private String last;
	Map dictionary;
	int zeros;
	StringBuilder sb = new StringBuilder();
	
	Interpreter(Map dic, int zeros){
		this.dictionary = dic;
		this.zeros = zeros;
	}
	
	
	public String decode(byte[] bytes)
	{
		System.out.println("Bytes szie: " + bytes.length);
		System.out.println("Zeros: " + zeros);
		int actual = 0;
		int length = 0;
		/*/
		for(byte bajt : bytes){
			ii++;
			//System.out.println(ii);
			for(int i=0 ; i<8 ; i++){
				length++;
				if(getBit(bajt, i))
					actual = (actual << 1) + 1;
				else
					actual = actual << 1;
				if(isCode(actual, length)){
					System.out.println(last);
					actual = 0;
					length = 0;
				}
			}	
		}
		/*/
		int ommited = 0;
		for(int z = bytes.length - 1 ; z >= 0 ; z--)
		{	 
				for(int i=7 ; i>=0 ; i--){
					if(zeros <= ommited) {
						length++;
						if(getBit(bytes[z], i))
							actual += Math.pow(2, length - 1);
						if(isCode(actual, length)){
							//System.out.print(last);
							actual = 0;
							length = 0;
						}
					}
					ommited++;
				}
				System.out.println((bytes.length - z) + " / " + bytes.length);
		}
		return sb.toString();
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
	
	private boolean isCode(int key, int length){
		Code keyy = new Code();
		keyy.code = key;
		keyy.length = length;
		if(dictionary.get(keyy) != null){
			sb.insert(0, (String) dictionary.get(keyy));
			return true;
		}
		
		return false;
	}
}
