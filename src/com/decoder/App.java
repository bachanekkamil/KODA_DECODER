package com.decoder;

import java.util.HashMap;
import java.util.Map;


public class App {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String codeFileName = "/home/koda/gen-bin2";		
		String dicFileName = "/home/koda/gen-bin2-dic";	
		/*/
		CodeReader codeReader = new CodeReader(codeFileName);
		DictionaryReader dictionaryReader = new DictionaryReader(dicFileName);
		dictionaryReader.printDictionary();	
		/*/
		byte[] bytes = new byte[1];
		bytes[0] = (byte) 250;
		Map m = new HashMap<Integer, String>();
		m.put(7, "chuj w");
		m.put(6, "dupe");
		m.put(2, "lodzi");
		
		Interpreter intepreter = new Interpreter(m);
		System.out.println("Zdekodowano:");		
		intepreter.decode(bytes);
	}
}