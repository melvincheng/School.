import java.util.*;
import java.io.*;

public class Dictionary extends ArrayList<String>{
	public Dictionary(String file){
		try{
			InputStream inStream = new FileInputStream(file);
			Reader reader = new InputStreamReader(inStream,"UTF8");
			BufferedReader bufReader = new BufferedReader(reader);
			String word;
			word = bufReader.readLine();
			while(word!=null){
				this.add(word);
				word = bufReader.readLine();
			}
		}catch(Exception e){
			System.err.println("Error during file read");
		}
	}
}