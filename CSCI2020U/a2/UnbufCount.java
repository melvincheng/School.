import java.util.*;
import java.io.*;

class UnbufCount{
	public static void main(String[] arg){
		try {
			InputStream inStream = new FileInputStream(arg[0]);
			Reader reader = new InputStreamReader(inStream,"UTF8");
			HashMap <Character,Integer> mapper = new HashMap<Character,Integer>();
			final long startTime = System.currentTimeMillis();
			int currChar=reader.read();
			char prevChar;
			Integer mapVal;
			while(currChar != -1) {
				prevChar=(char) currChar;
				currChar=reader.read();
				if(currChar == 10 || currChar == -1){
					prevChar = Character.toLowerCase(prevChar);
					mapVal = mapper.get(prevChar);
					mapVal = (mapVal==null)? 1:++mapVal;
					mapper.put(prevChar,mapVal);				
				}
			}
			long totalTime = System.currentTimeMillis() - startTime;
			System.out.println("Took: " + totalTime + " ms");
			for(int x = 'a';x<='z';x++){
				System.out.println((char)x + ": " + mapper.get((char)x));
			}
			reader.close();
			inStream.close();
		} catch (FileNotFoundException e){
			System.err.println(e.getMessage());
		} catch (UnsupportedEncodingException e){
			System.err.println(e.getMessage());
		} catch (IOException e){
			System.err.println(e.getMessage());
		}
	}
}
	