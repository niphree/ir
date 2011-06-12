package ir.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Properties {

	
	public static final String INDEX_DIR;
	
	
	// init all values 
	static {
		java.util.Properties prop = new java.util.Properties();
		try {
			//File f = new File("BBBBB.txt");
			//File f = new File(".");
			String curDir = System.getProperty("user.dir");
			System.out.println("dir:");
			System.out.println(curDir);
			prop.load(new FileInputStream("ir.properties"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		INDEX_DIR = prop.getProperty("INDEX_DIR");
		
	}
	
	
}
