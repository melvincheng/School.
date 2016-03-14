package csci2020u.a1;
import java.util.*;

class Word2PhoneMapper implements IWord2Phone{
	public List<String> map(String word){
		List<String> List= new ArrayList<String>();
		HashMap <Character,Integer> mapper = new HashMap<Character,Integer>();
		word=word.toLowerCase();
		String tempWord = "";

		mapper.put('a',2);
		mapper.put('b',2);
		mapper.put('c',2);
		mapper.put('d',3);
		mapper.put('e',3);
		mapper.put('f',3);
		mapper.put('g',4);
		mapper.put('h',4);
		mapper.put('i',4);
		mapper.put('j',5);
		mapper.put('k',5);
		mapper.put('l',5);
		mapper.put('m',6);
		mapper.put('n',6);
		mapper.put('o',6);
		mapper.put('p',7);
		mapper.put('q',7);
		mapper.put('r',7);
		mapper.put('s',7);
		mapper.put('t',8);
		mapper.put('u',8);
		mapper.put('v',8);
		mapper.put('w',9);
		mapper.put('x',9);
		mapper.put('y',9);
		mapper.put('z',9);

		for(int x=0;x<word.length();x++){
			tempWord = tempWord + mapper.get(word.charAt(x));
		}
		List.add(tempWord);
		return List;
	}
}


