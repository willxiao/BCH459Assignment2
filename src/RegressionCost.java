import java.util.List;

import org.apache.commons.math.stat.regression.SimpleRegression;

public class RegressionCost implements CostFunction<Double> {
	
	@Override
	public Double getCost(List<Double> data) {
		SimpleRegression r = new SimpleRegression();
		for(int i = 0; i < data.size(); i++){
			r.addData(i, data.get(i));
		}
		return r.getRSquare();
	}

}
