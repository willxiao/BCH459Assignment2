import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DataImporter {
	public static DataMatrix<Double> importFromFile(String filename){
		Scanner s = null;
		ArrayList<ArrayList<Double>> matrix = new ArrayList<ArrayList<Double>>();
		
		try{
			s = new Scanner(new BufferedReader(new FileReader(filename)));
			
			while(s.hasNext()){
				String line = s.nextLine();
				String[] vals = line.split(",");
				
				ArrayList<Double> row = new ArrayList<Double>();
				for(int i = 1; i < vals.length; i++){
					row.add(Double.valueOf(vals[i]));
				}
				matrix.add(row);
			}
			
			return new DataMatrix<Double>(matrix);
		}
		catch(IOException e){
			System.out.println(e.getMessage());
		}
		finally{
			if(s!=null){
				s.close();
			}
		}
		
		return null;
	}
	
	public static DataMatrix<Integer> convertToDiscreteData(DataMatrix<Double> d){
		Double mean = d.getMean();
		Double standardDeviation = d.getStandardDeviation();
		
		ArrayList<ArrayList<Integer>> matrix = new ArrayList<ArrayList<Integer>>();
		for(int i = 0; i < d.getRowCount(); i++){
			for(int j = 0; j < d.getColCount(); j++){
				Double val = d.getValue(i, j);
				ArrayList<Integer> row = new ArrayList<Integer>();
				if(val >= mean + standardDeviation){
					row.add(2);
				}else if(val <= mean - standardDeviation){
					row.add(0);
				}else{
					row.add(1);
				}
				matrix.add(row);
			}
		}
		return new DataMatrix<Integer>(matrix);
	}
	
	public static void main(String[] args){
		DataMatrix<Double> d = importFromFile("scrambled.csv");
		System.out.println(d.getValue(10, 5));
		System.out.println(d);
	}
}
