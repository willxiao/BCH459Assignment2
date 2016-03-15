import java.util.List;

public interface CostFunction<T> {
	public Double getCost(List<T> data);
}
