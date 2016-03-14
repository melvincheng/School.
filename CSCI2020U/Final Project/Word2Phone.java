import java.util.*;

public class Word2Phone{
	Word2PhoneMapper mapper;
	String result = "";
	public Word2Phone(Word2PhoneMapper mapper){
		this.mapper = mapper;
	}

	synchronized public String change(String word){
		word = word.toLowerCase();
		char[] letters = word.toCharArray();
		for(int x=0;x<letters.length;x++){
			try{
				letters[x] = mapper.get(letters[x]);
			}catch(Exception e){
				return "";
			}
		}
		return (new String(letters));
	}
}