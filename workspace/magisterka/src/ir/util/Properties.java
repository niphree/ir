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
			prop.load(new FileInputStream("src/ir.properties"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		INDEX_DIR = prop.getProperty("INDEX_DIR");
		
	}
	
	
}
