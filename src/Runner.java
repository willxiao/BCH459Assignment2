import java.util.Iterator;

public class Runner {
	public static final int TEN_FACTORIAL = 3628800;
	public static final int STEP = TEN_FACTORIAL / 1000;
	
	public static void main(String[] args){
		DataMatrix<Double> original = DataImporter.importFromFile("scrambled.csv");
		System.out.println("Running Tests on original data");
		runTests(original);
		
		System.out.println("\nRunning Tests on normalized data");
		DataMatrix<Double> normalized = DataImporter.normalizeDataByRow(original);
		runTests(normalized);
	}
	
	public static void runTests(DataMatrix<Double> original){
		DataMatrix<Double> continuous = original;
		DataMatrix<Integer> discrete = DataImporter.convertToDiscreteData(continuous);
		DataMatrix<Double> continuous_q = original;
		
		
		CostCalculator<Double> regressionCalculator = new CostCalculator<Double>(new RegressionCost());
		CostCalculator<Integer> smoothCalculator = new CostCalculator<Integer>(new SmoothCost());
		CostCalculator<Double> q_regressionCalculator = new CostCalculator<Double>(new QuadraticRegressionCalculator());
		
		Double bestRegressionCost = Double.POSITIVE_INFINITY;
		Double bestSmoothCost = Double.POSITIVE_INFINITY;
		Double bestQRegressionCost = Double.POSITIVE_INFINITY;
		int[] bestRegressionOrder = null;
		int[] bestSmoothOrder = null;
		int[] bestQRegressionOrder = null;
		
		int count = 0;
		for(Iterator<int[]> it = new PermIterator(continuous.getRow(0).size()); it.hasNext(); ) {
            int[] colOrder = it.next().clone();
			continuous.changeColumnOrder(colOrder);
			discrete.changeColumnOrder(colOrder);
			continuous_q.changeColumnOrder(colOrder);
			
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
			
			Double q_cost = q_regressionCalculator.getCost(continuous_q);
			if(q_cost < bestQRegressionCost){
				bestQRegressionCost = q_cost;
				bestQRegressionOrder = colOrder;
			}
			
			count++;
			if( count % STEP == 0){
				Double percentageDone = (100.0*count)/TEN_FACTORIAL;
				System.out.println(percentageDone+"% done");
			}
        }
		
		System.out.println("Best Regression Cost: "+bestRegressionCost);
		System.out.print("Best Order based on regression cost: ");
		print(bestRegressionOrder);
		
		System.out.println("Best Quadratic Regression Cost: "+bestQRegressionCost);
		System.out.print("Best Order based on Quadratic Regression cost: ");
		print(bestQRegressionOrder);
		
		System.out.println("Best Smooth Cost: "+bestSmoothCost);
		System.out.print("Best Order based on smoothness cost: ");
		print(bestSmoothOrder);
	}
	
	public static void print(int[] a){
		if(a==null){
			return;
		}
    	for(int i = 0; i < a.length; i++){
    		System.out.print(a[i]);
    	}
    	System.out.println();
    }
}
