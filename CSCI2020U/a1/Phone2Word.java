package csci2020u.a1;
import java.util.*;
import csci2020u.a1.*;

class Phone2Word {
	public static void main(String[] args) {
		Word2PhoneMapper mapper = new Word2PhoneMapper();
		csci2020u.a1.helper.Dictionary dic = new csci2020u.a1.helper.Dictionary();
		List<String> word = new ArrayList<String>();
		int counter=0;

		for(String input: args){
			word.add(input);
		}
		for(String tempWord:dic){
			if(word.equals(mapper.map(tempWord))){
				System.out.println(tempWord);
				counter++;
			}
		}
		System.out.println(counter + " word(s) found.");
	}
}