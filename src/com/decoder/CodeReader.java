package com.decoder;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class CodeReader {
	private byte[] bytes = null;
	
	CodeReader(String filePath){
		Path pathCodeFile = null;
		try {
			pathCodeFile = Paths.get(new URI("file:///" + filePath));
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		try {
			bytes = Files.readAllBytes(pathCodeFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int ii = 0;
		for(byte baajt : bytes)	
		{
			ii++;
			if(ii < 100){
			System.out.printf("0x%02X", baajt);
			System.out.println("");
			}
		}
		
	}
	
	public byte[] getBytes(){
		return bytes;
	}
	

}
