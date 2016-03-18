import java.util.List;

public class SmoothCost implements CostFunction<Integer> {

	@Override
	public Double getCost(List<Integer> data) {
		
		Integer prev = data.get(0);
		int numChanges = 0;
		for(Integer i : data){
			if(i != prev){
				numChanges++;
			}
			prev = i;
		}
		return 1.0*numChanges;
	}

}
