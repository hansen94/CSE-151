package mypackage;
import java.io.FileReader;
import java.util.List;

import com.opencsv.CSVReader;

public class CsvReader {
	
	public static int readAndCount(){
		try{
		CSVReader reader = new CSVReader(new FileReader("src/mypackage/abalone.data.csv")) ;
		
		List list = reader.readAll();
		
		reader.close();
		
		return list.size();
		
		}catch(Exception e){
			e.printStackTrace();
		}
		return 0;
	}
	
}

