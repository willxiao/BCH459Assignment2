import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataMatrix<T> {
	private ArrayList<ArrayList<T>> original_matrix;
	private int[] col_map;
	
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
			toReturn.add(originalRow.get(col_map[row]));
		}
		return toReturn;
	}
	
	public Integer getRowCount(){
		return original_matrix.size();
	}
	
}
