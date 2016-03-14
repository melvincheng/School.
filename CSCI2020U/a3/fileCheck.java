import java.util.*;
import java.io.*;
import java.util.regex.*;
class fileCheck{
	public static void main(String arg[]){
		try{
			InputStream inStream = new FileInputStream("current.txt");
			Reader reader = new InputStreamReader(inStream,"UTF8");
			BufferedReader bufReader = new BufferedReader(reader);
			String line = bufReader.readLine();
			String temp = "";
			int counter = 0;
			while(line!=null){
				counter++;
				temp = "\t" + Integer.toString(counter);
				if(!temp.equals(line)){
					System.out.println(counter);
					System.out.println(temp);
					System.out.println(line);
					break;
				}
				line = bufReader.readLine();
				line = bufReader.readLine();
				line = bufReader.readLine();
				line = bufReader.readLine();
				line = bufReader.readLine();
				line = bufReader.readLine();
			}

		} catch (FileNotFoundException e){
			System.err.println(e.getMessage());
		} catch (UnsupportedEncodingException e){
			System.err.println(e.getMessage());
		} catch (IOException e){
			System.err.println(e.getMessage());
		}
	}
}