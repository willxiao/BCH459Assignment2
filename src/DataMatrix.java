import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataMatrix<T extends Number> {
	private ArrayList<ArrayList<T>> original_matrix;
	private int[] col_map;
	private Double mean;
	private Double variance;
	private Double standardDeviation;
	
	DataMatrix(ArrayList<ArrayList<T>> m){
		original_matrix = new ArrayList<ArrayList<T>>(m.size());
		for(List<T> expression_data : m){
			original_matrix.add(new ArrayList<T>(expression_data));
		}
		
		col_map = new int[m.get(0).size()];
		for(int i = 0; i < col_map.length; i++){
			col_map[i] = i;
		}
	}
	
	public void changeColumnOrder(int[] colOrder){
		for(int i = 0; i < colOrder.length; i++){
			col_map[i] = colOrder[i];
		}
	}
	
	public T getValue(int row, int column){
		return original_matrix.get(row).get(col_map[column]);
	}
	
	public List<T> getRow(int row){
		List<T> originalRow = original_matrix.get(row);
		List<T> toReturn = new ArrayList<T>();
		for(int i = 0; i < originalRow.size(); i++){
			toReturn.add(originalRow.get(col_map[i]));
		}
		return toReturn;
	}
	
	public Integer getRowCount(){
		return original_matrix.size();
	}
	
	public Integer getColCount(){
		return original_matrix.get(0).size();
	}
	
	public Double getMean(){
		if(mean == null){
			Double sum = 0.0;
			int count = original_matrix.size()*original_matrix.get(0).size();
			for(int i = 0; i < original_matrix.size(); i++){
				for(int j = 0; j < original_matrix.get(0).size(); j++){
					sum += original_matrix.get(i).get(j).doubleValue();
				}
			}
			mean = sum / count;
		}
		return mean;
	}
	
	public Double getVariance(){
		if(variance == null){
			Double squaresum = 0.0;
			int count = original_matrix.size()*original_matrix.get(0).size();
			Double m = getMean();
			
			for(int i = 0; i < original_matrix.size(); i++){
				for(int j = 0; j < original_matrix.get(0).size(); j++){
					squaresum += Math.pow(m - original_matrix.get(i).get(j).doubleValue(), 2.0);
				}
			}
			
			variance = squaresum / count;
		}
		return variance;
	}
	
	public Double getStandardDeviation(){
		if(standardDeviation == null){
			standardDeviation = Math.sqrt(getVariance());
		}
		return standardDeviation;
	}
}
