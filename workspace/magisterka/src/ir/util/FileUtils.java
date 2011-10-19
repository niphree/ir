package ir.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class FileUtils {

	public static boolean save_file(String filename, Object obiekt){
		boolean ok = true;
		FileOutputStream fos = null;
		ObjectOutputStream out = null;
		try {
			fos = new FileOutputStream(filename,false);
			out = new ObjectOutputStream(fos);
			out.writeObject(obiekt);
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ok = false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ok = false;
		}
		return ok;
	}
	
}
