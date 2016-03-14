import java.util.*;
import java.io.*;
class Top{
	public static void main(String arg[]){
		String[] attribute = new String[4];
		attribute[0] = "sectors";
		attribute[1] = "employers";
		attribute[2] = "positions";
		attribute[3] = "names";
		List<Record> list = new ArrayList<Record>();
		RecordLoader file = new ParseFile();
		list = file.load(arg[0]);
		IGroupBy group = new GroupBy();
		group.setData(list);	
		for(int x=0;x<4;x++){
			if(arg[2].compareTo(attribute[x]) == 0){
				group.groupby(x);
				break;
			}
		}
		group.printTopK(Integer.parseInt(arg[1]));
	}
}