public class CostCalculator<T extends Number> {
	private CostFunction<T> costFunction;
	
	CostCalculator(CostFunction<T> c){
		costFunction = c;
	}
	
	public Double getCost(DataMatrix<T> d){
		Double c = 0.0;
		for(int i = 0; i < d.getRowCount(); i++){
			c += costFunction.getCost(d.getRow(i));
		}
		return c;
	}
}
