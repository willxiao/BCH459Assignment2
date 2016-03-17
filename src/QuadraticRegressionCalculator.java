import org.apache.commons.math.analysis.polynomials.PolynomialFunction;
import org.apache.commons.math.optimization.fitting.*;

import java.util.List;

import org.apache.commons.math.optimization.general.LevenbergMarquardtOptimizer;

public class QuadraticRegressionCalculator implements CostFunction<Double> {

	PolynomialFitter fitter =
        new PolynomialFitter(2, new LevenbergMarquardtOptimizer());

	@Override
	public Double getCost(List<Double> data) {
        for (int i = 0; i < data.size(); i++) {
            fitter.addObservedPoint(1.0, i, data.get(i));
        }
        
        PolynomialFunction fitted = null;
        try{
        	fitted = fitter.fit();
        }catch(Exception e){
        	System.out.println(e.getMessage());
        	return 0.0;
        }
        
        Double cost = 0.0;
        for(int i = 0; i < data.size(); i++){
        	Double difference = data.get(i) - fitted.value(i);
        	Double squared = Math.pow(difference, 2);
        	cost += squared;
        }
		fitter.clearObservations();
		return cost;
	}
}
