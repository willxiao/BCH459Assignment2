import java.util.List;

import org.apache.commons.math.stat.regression.SimpleRegression;

public class RegressionCost implements CostFunction<Double> {
	private SimpleRegression r = new SimpleRegression();
	
	@Override
	public Double getCost(List<Double> data) {
		for(int i = 0; i < data.size(); i++){
			r.addData(i, data.get(i));
		}
		Double d = r.getRSquare();
		r.clear();
		return d;
	}

}
