import java.util.*;
import java.io.*;

class BufCount{
	public static void main(String[] arg){
		try {
			InputStream inStream = new FileInputStream(arg[0]);
			Reader reader = new InputStreamReader(inStream,"UTF8");
			BufferedReader bufReader = new BufferedReader(reader,Integer.parseInt(arg[1]));
			HashMap <Character,Integer> mapper = new HashMap<Character,Integer>();
			String line = bufReader.readLine();
			Integer mapVal;			
			char currChar;
			final long startTime = System.currentTimeMillis();
			while(line != null) {
				currChar = line.charAt(line.length()-1);
				currChar = Character.toLowerCase(currChar);
				mapVal = mapper.get(currChar);
				mapVal = (mapVal==null)? 1:++mapVal;
				mapper.put(currChar,mapVal);
				line = bufReader.readLine();
			}
			long totalTime = System.currentTimeMillis() - startTime;
			System.out.println("Took: " + totalTime + " ms");
			for(int x = 'a';x<='z';x++){
				System.out.println((char)x + ": " + mapper.get((char)x));
			}
			bufReader.close();
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
	