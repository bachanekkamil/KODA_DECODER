package com.decoder;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.io.Reader;


public class App {
	enum STATE {NOTHING, CODE, CHAR};



	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("KOOOODA");		
		String codeFileName = "/home/koda/gen-bin2";		
		String dicFileName = "/home/koda/gen-bin2-dic";		
		CodeReader codeReader = new CodeReader(codeFileName);
		DictionaryReader dictionaryReader = new DictionaryReader(dicFileName);
		dictionaryReader.printDictionary();	
	}
	

}
