import java.util.*;
import java.util.regex.*;

public class Helper{
	String p2wString = ("[0-9]*");
	String w2pString = ("[a-zA-Z]");
	Pattern p2wPattern;
	Pattern w2pPattern;
	Matcher p2wMatcher;
	Matcher w2pMatcher;
	Word2Phone w2p;
	Phone2Word p2w;
	String phoneResult;
	ArrayList<String> wordResult;
	Boolean choice=null;

	public Helper(Dictionary dictionary, Word2PhoneMapper mapper){
		this.w2p = new Word2Phone(mapper);
		this.p2w = new Phone2Word(dictionary,mapper,w2p);
		this.p2wPattern = Pattern.compile(p2wString);
		this.w2pPattern = Pattern.compile(w2pString);
	}

	public void input(String line){
		p2wMatcher = p2wPattern.matcher(line);
		w2pMatcher = w2pPattern.matcher(line);
		if(w2pMatcher.find()){
			phoneResult = w2p.change(line);
			choice = true;
		}else if(p2wMatcher.find()){
			wordResult = p2w.find(line);
			choice = false;
		}
	}
}