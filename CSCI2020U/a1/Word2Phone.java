package csci2020u.a1;
import java.util.*;

class Word2Phone {
	public static void main(String[] args) {
		Word2PhoneMapper mapper = new Word2PhoneMapper();
		for(String word: args){
			System.out.println(mapper.map(word));
		}
	}
}