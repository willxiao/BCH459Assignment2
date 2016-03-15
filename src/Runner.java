import java.util.Iterator;

public class Runner {
	public static final int TEN_FACTORIAL = 3628800;
	
	public static void main(String[] args){
		DataMatrix<Double> continuous = DataImporter.importFromFile("FILENAME");
		DataMatrix<Integer> discrete = DataImporter.convertToDiscreteData(continuous);
		
		CostCalculator<Double> regressionCalculator = new CostCalculator<Double>(new RegressionCost());
		CostCalculator<Integer> smoothCalculator = new CostCalculator<Integer>(new SmoothCost());
		
		Double bestRegressionCost = Double.POSITIVE_INFINITY;
		int[] bestRegressionOrder = null;
		
		Double bestSmoothCost = Double.POSITIVE_INFINITY;
		int[] bestSmoothOrder = null;
		
		int count = 0;
		for(Iterator<int[]> it = new PermIterator(continuous.getRow(0).size()); it.hasNext(); ) {
            int[] colOrder = it.next().clone();
			continuous.changeColumnOrder(colOrder);
			discrete.changeColumnOrder(colOrder);
			
			Double regCost = regressionCalculator.getCost(continuous);
			if(regCost < bestRegressionCost){
				bestRegressionCost = regCost;
				bestRegressionOrder = colOrder;
			}
			
			Double smoothCost = smoothCalculator.getCost(discrete);
			if(smoothCost < bestSmoothCost){
				bestSmoothCost = smoothCost;
				bestSmoothOrder = colOrder;
			}
			
			count++;
			if( count % 1000 == 0){
				Double percentageDone = (1.0*count)/TEN_FACTORIAL;
				System.out.println(percentageDone+"% done");
			}
        }
		
		System.out.println("Best Regression Cost: "+bestRegressionCost);
		System.out.print("Best Order based on regression cost: ");
		print(bestRegressionOrder);
		
		System.out.println("Best Smooth Cost: "+bestSmoothCost);
		System.out.print("Best Order based on smoothness cost: ");
		print(bestSmoothOrder);
	}
	
	public static void print(int[] a){
    	for(int i = 0; i < a.length; i++){
    		System.out.print(a[i]);
    	}
    	System.out.println();
    }
}
