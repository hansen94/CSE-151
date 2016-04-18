package mypackage;
import java.io.FileReader;
import java.util.List;
import java.util.*;

import com.opencsv.CSVReader;

public class CsvReader {
	
	public static List read(String address){
		try{
		CSVReader reader = new CSVReader(new FileReader(address)) ;
		
		List list = reader.readAll();
		
		reader.close();
		
		return list;
		
		}catch(Exception e){
			e.printStackTrace();
		}
		List list2 = new ArrayList();
		return list2;
	}
	
}

