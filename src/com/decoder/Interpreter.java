package com.decoder;

import java.util.Map;

public class Interpreter {
	
	private Map<Code, String> dictionary;
	private int zeros;
	private int minBinLength;
	private StringBuilder sb = new StringBuilder();
	private DictionaryReader.METHOD method;
	
	Interpreter(Map<Code, String> dic, int zeros, int min, DictionaryReader.METHOD method){
		this.dictionary = dic;
		this.zeros = zeros;
		this.minBinLength = min;
		this.method = method;
	}
	
	public String decode(byte[] bytes)
	{
		System.out.println("Bytes size: " + bytes.length);
		System.out.println("Zeros: " + zeros);
		System.out.println("MinBin: " + minBinLength);
		System.out.println("Method: " + DictionaryReader.getNameOfMethod(method));
		System.out.println("Decoding...");
		int actual = 0;
		int length = 0;

		int ommited = 0;
		for(int z = bytes.length - 1 ; z >= 0 ; z--)
		{	 
				for(int i=7 ; i>=0 ; i--){
					if(zeros <= ommited) {
						length++;
						if(getBit(bytes[z], i))
						{
							actual += pow2(length-1);
						}
						if(length >= minBinLength)
						if(isCode(actual, length)){
							actual = 0;
							length = 0;
						}
					}
					ommited++;
				}
				//System.out.println((bytes.length - z) + " / " + bytes.length);
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
			if(method != DictionaryReader.METHOD.CONTEXT_CHARACTERS)
				sb.insert(0, (String) dictionary.get(keyy));
			else{
				sb.insert(0, dictionary.get(keyy).charAt(0));
			}
			return true;
		}
		
		return false;
	}
	
	private int pow2(int x)
	{
		switch(x){
			case 0:
				return 1;
			case 1:
				return 2;
			case 2:
				return 4;
			case 3:
				return 8;
			case 4:
				return 16;
			case 5:
				return 32;
			case 6:
				return 64;
			case 7:
				return 128;
			case 8:
				return 256;
			case 9:
				return 512;
			case 10:
				return 1024;
			case 11:
				return 2048;
			case 12:
				return 4096;
			case 13:
				return 8192;
			case 14:
				return 16384;
			case 15:
				return 32768;
			case 16:
				return 65536;
			case 17:
				return 131072;
			case 18:
				return 262144;
			case 19:
				return 524288;
			case 20:
				return 1048576;
			case 21:
				return 2097152;
			case 22:
				return 4194304;
			case 23:
				return 8388608;
			case 24:
				return 16777216;
			case 25:
				return 33554432;
			case 26:
				return 67108864;
			case 27:
				return 134217728;
			case 28:
				return 268435456;
			case 29:
				return 536870912;
			case 30:
				return 1073741824;
			default:
				return 0;
		}
	}
}
