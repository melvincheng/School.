import java.util.*;
import java.io.*;
import java.util.regex.*;
class main{
	public static void main(String arg[]){
		List<Record> list = new ArrayList<Record>();
		RecordLoader file = new ParseFile();
		list = file.load("output.html");
		IGroupBy group = new GroupBy();
		group.setData(list);
		group.groupby(0);
	}
}