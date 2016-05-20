package mypackage;
import java.io.FileReader;
import java.util.List;
import java.util.*;

import com.opencsv.CSVReader;

public class CsvReader {
	
	public static List read(String address){
		try{
		CSVReader reader = new CSVReader(new FileReader(address), ',') ;
		
		List <String[]> list = reader.readAll();
		
		List <List <Float>> data = new ArrayList();
		
		for(int i = 0; i < list.size() ; i++){
			String [] a = list.get(i);
			List<Float> row = new ArrayList();
			for(int j = 1; j < a.length; j++){
				row.add( Float.valueOf(a[j]));
			}
			data.add(row);			
		}
		
		reader.close();
		/*for(int i = 0; i < list.size(); i++){
			System.out.println(Arrays.toString(list.toArray()));
		}*/
		
		return data;
		
		}catch(Exception e){
			e.printStackTrace();
		}
		List list2 = new ArrayList();
		return list2;
	}
	
}

