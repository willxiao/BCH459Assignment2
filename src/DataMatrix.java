import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataMatrix<T> {
	private ArrayList<ArrayList<T>> original_matrix;
	private Map<Integer, Integer> col_map;
	
	DataMatrix(List<List<T>> m){
		original_matrix = new ArrayList<ArrayList<T>>(m.size());
		for(List<T> expression_data : m){
			original_matrix.add(new ArrayList<T>(expression_data));
		}
		
		col_map = new HashMap<Integer,Integer>();
		for(int i = 0; i < m.get(0).size(); i++){
			col_map.put(i, i);
		}
	}
	
	public void changeColumnOrder(int[] colOrder){
		for(int i = 0; i < colOrder.length; i++){
			col_map.put(i, colOrder[i]);
		}
	}
	
	public T getValue(int row, int column){
		return original_matrix.get(row).get(col_map.get(column));
	}
	
	public List<T> getRow(int row){
		List<T> originalRow = original_matrix.get(row);
		List<T> toReturn = new ArrayList<T>();
		for(int i = 0; i < originalRow.size(); i++){
			toReturn.add(originalRow.get(col_map.get(row)));
		}
		return toReturn;
	}
	
	public Integer getRowCount(){
		return original_matrix.size();
	}
	
}
