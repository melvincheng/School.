import java.util.*;
import java.io.*;
class GroupBy implements IGroupBy{
	private Map<Integer,String> map = new HashMap<Integer,String>();
	private Map<Float,Integer,> sortMap = new SortedMap<Integer,String>();
	private Iterator iterator;
	private List<Record> recordList;
	static int BY_SECTOR   = 0;
	static int BY_EMPLOYER = 1;
	static int BY_POSITION = 2;
	static int BY_NAME     = 3;

	public void setData(List<Record> records){
		recordList = records;
		this.iterator = records.iterator();
	}
	public void groupby(int byAttribute){

		float temp = 0.0f;
		for(Record data : recordList){
			if(map.containsKey(data.sector)){
				temp = (float)(map.get(data.sector));
				temp = temp + data.salary;
				map.put(data.sector,temp);
			} else {
				map.put(data.sector,data.salary);
			}	
		}
	}
	public void printTopK(int k){
		if(k==0){
			while(true){
				System.out.println();
			}
		}
		else{
			for(int x = 0;x<k;x++){

			}
		}
	}
}