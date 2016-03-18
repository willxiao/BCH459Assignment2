import java.util.Iterator;
import java.io.IOException;
import java.io.PrintWriter;

public class Runner {
	public static final int TEN_FACTORIAL = 3628800;
	public static final int STEP = TEN_FACTORIAL / 1000;
	
	public static void main(String[] args){
		PrintWriter pw = null;
		try{
			pw = new PrintWriter("Results.txt");
			DataMatrix<Double> original = DataImporter.importFromFile("scrambled.csv");
			pw.println("Running Tests on original data");
			runTests(original,pw);
			
			pw.println("\nRunning Tests on normalized data");
			DataMatrix<Double> normalized = DataImporter.normalizeDataByRow(original);
			runTests(normalized,pw);
		}catch(IOException e){
			
		}finally{
			if(pw != null){
				pw.close();
			}
		}
	}
	
	public static void runTests(DataMatrix<Double> original,PrintWriter pw){
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
		
		pw.println("Best Regression Cost: "+bestRegressionCost);
		pw.print("Best Order based on regression cost: ");
		print(bestRegressionOrder,pw);
		
		pw.println("Best Quadratic Regression Cost: "+bestQRegressionCost);
		pw.print("Best Order based on Quadratic Regression cost: ");
		print(bestQRegressionOrder,pw);
		
		pw.println("Best Smooth Cost: "+bestSmoothCost);
		pw.print("Best Order based on smoothness cost: ");
		print(bestSmoothOrder,pw);
	}
	
	public static void print(int[] a,PrintWriter pw){
		if(a==null){
			return;
		}
    	for(int i = 0; i < a.length; i++){
    		pw.print(a[i]);
    	}
    	pw.println();
    }
}
