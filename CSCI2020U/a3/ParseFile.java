import java.util.*;
import java.io.*;
import java.util.regex.*;
class ParseFile implements RecordLoader{
	public List<Record> load(String filename){
		try{
			List<Record> list = new ArrayList<Record>();
			InputStream inStream = new FileInputStream(filename);
			Reader reader = new InputStreamReader(inStream,"UTF8");
			BufferedReader bufReader = new BufferedReader(reader);
			String line = bufReader.readLine();

			String sector = "<h1> Public Sector Salary Disclosure for 2013:[\\s]?(?<sector>[^<]*)</h1>";
			Pattern patternSector = Pattern.compile(sector);
			Matcher matchSector = patternSector.matcher(line);

			String employer = "<td colspan=\"2\" align=\"left\" valign=\"top\"><span lang=\"en\">(<.*>)?(?<employer>[^<]*)(</span>)?";
			Pattern patternEmployer = Pattern.compile(employer);
			Matcher matchEmployer = patternEmployer.matcher(line);

			String lastName = "<td align=\"left\" valign=\"top\">(<a href='/en/publications/salarydisclosure/2014/addenda_14.html#[^>]*>)?(?<lastName>[^<]*)(</a>)?</td>";
			Pattern patternLastName = Pattern.compile(lastName);
			Matcher matchLast = patternLastName.matcher(line);

			String firstName = "<td colspan=\"2\" align=\"left\" valign=\"top\">(<[^>]*>)?(?<firstName>[^<]*)(</a>)?</td>";
			Pattern patternFirstName = Pattern.compile(firstName);
			Matcher matchFirst = patternFirstName.matcher(line);

			String position = "[\\s]*(<[^>]*>)?(?<position>[^>]*)(</span>&nbsp;/&nbsp;<span lang=\"fr_ca\">[^>]*)?</span></td>";
			Pattern patternPosition = Pattern.compile(position);
			Matcher matchPosition = patternPosition.matcher(line);

			String salary = "<td align=\"right\" valign=\"top\">(<[^>]*>)?[$](?<salary>[^<]*)(</a>)?</td>";
			Pattern patternSalary = Pattern.compile(salary);
			Matcher matchSalary = patternSalary.matcher(line);

			String skip = "<span lang=\"fr_ca\">&nbsp;/&nbsp;";
			Pattern patternSkip = Pattern.compile(skip);
			Matcher matchSkip = patternSkip.matcher(line);

			Record newRecord = new Record();
			String currentSector = "";
			while(line!=null){

				if(matchSkip.find()){
					line=bufReader.readLine();
				}

				else if(matchSector.find()){
					currentSector =  matchSector.group("sector");
				}

				else if(matchEmployer.find()){
					newRecord = new Record();
					newRecord.employer = matchEmployer.group("employer");
					newRecord.sector = currentSector;
				}

				else if(matchLast.find()){
					newRecord.name = matchLast.group("lastName");	
				}

				else if(matchFirst.find()){
					newRecord.name = newRecord.name + " " + matchFirst.group("firstName");					
				}

				else if(matchPosition.find()){
					newRecord.position = matchPosition.group("position");
				}

				else if(matchSalary.find()){
					newRecord.salary = Float.parseFloat(matchSalary.group("salary").replace(",",""));
					list.add(newRecord);
				}
				matchSector = patternSector.matcher(line);
				matchEmployer = patternEmployer.matcher(line);
				matchLast = patternLastName.matcher(line);
				matchFirst = patternFirstName.matcher(line);
				matchPosition = patternPosition.matcher(line);
				matchSalary = patternSalary.matcher(line);
				matchSkip = patternSkip.matcher(line);

				line=bufReader.readLine();
			}
			return list;
		} catch (FileNotFoundException e){
			System.err.println(e.getMessage());
		} catch (UnsupportedEncodingException e){
			System.err.println(e.getMessage());
		} catch (IOException e){
			System.err.println(e.getMessage());
		}
		return null;
	}
}