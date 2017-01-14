package com.decoder;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;


public class App {

	public static void main(String[] args) {
		
		String codeFileName = "/home/koda/gen-bin2";		
		String dicFileName = "/home/koda/gen-bin2-dic";	
		String outputFileName = "/home/koda/decoded";
		String outputPhotoFileName = "/home/koda/decoded.png";
		
		CodeReader codeReader = new CodeReader(codeFileName);
		
		DictionaryReader dictionaryReader = new DictionaryReader(dicFileName);
		dictionaryReader.printDictionary();	
		
		Interpreter intepreter = new Interpreter(dictionaryReader.getMap(), dictionaryReader.getZeros(), dictionaryReader.getMinBin(), dictionaryReader.getMethod(), dictionaryReader.getIsPhoto());
		
		if(!dictionaryReader.getIsPhoto()) {
			PrintWriter out = null;
			try {
				FileOutputStream fos = new FileOutputStream(outputFileName);
				out = new PrintWriter(new OutputStreamWriter(fos, StandardCharsets.UTF_8), true);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
			out.println(intepreter.decode(codeReader.getBytes()));
		}
		else {
			FileOutputStream fos;
			try {
				fos = new FileOutputStream(outputPhotoFileName);
				fos.write(intepreter.decodePhoto(codeReader.getBytes()));
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
		}

	}
}