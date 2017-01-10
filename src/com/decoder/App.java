package com.decoder;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
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
		/*/
		System.out.println("Zeros main: " + dictionaryReader.getZeros());
		Interpreter intepreter = new Interpreter(dictionaryReader.getMap(), dictionaryReader.getZeros());
		System.out.println("Zdekodowano:");
		
		PrintWriter out = null;
		try {
			FileOutputStream fos = new FileOutputStream("/home/koda/decoded");
			out = new PrintWriter(new OutputStreamWriter(fos, StandardCharsets.UTF_8), true);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		out.println(intepreter.decode(codeReader.getBytes()));
		/*/
		Code keyy = new Code();
		keyy.code = 194;
		keyy.length = 8;
		
		String ala = "0";
		if(dictionaryReader.getMap().get(keyy) != null)
			ala = (String) dictionaryReader.getMap().get(keyy);
		System.out.println(ala);
		/*/
	}
}