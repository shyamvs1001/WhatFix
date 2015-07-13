import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.StringJoiner;

/**
 * Expects a file of the same format given in the problem statement.
 * args[0] - filename with path. eg:  c:/users/shyamvs/desktop/knapsackInputs.txt
 * Output is printed in console.
 * @author ShyamVS
 *
 */
public class KnapsackSolver {

	public static void main(String[] args) throws IOException {
		String fileName = args[0];//"c:/users/shyamvs/desktop/knapsackInputs.txt";
		List<String> wordsList = Files.readAllLines(FileSystems.getDefault().getPath(fileName));
		wordsList.forEach(input -> findBestPackage(input));
	}

	private static void findBestPackage(String input) {
		String[] inputParts = input.split(":");
		int allowedPackageWeight = Integer.parseInt(inputParts[0].trim());
		String[] inputEntries = inputParts[1].trim().split(" ");
		int[] value = new int[inputEntries.length];
		float[] weight = new float[inputEntries.length];
		int i=0;
		for(String inputEntry : inputEntries){
			inputEntry = inputEntry.substring(1);
			inputEntry = inputEntry.substring(0, inputEntry.length()-1);
			String[] inputEntryParts = inputEntry.split(",");
			
			weight[i] = Float.parseFloat(inputEntryParts[1]);
			value[i] = Integer.parseInt(inputEntryParts[2].substring(1));
			i++;
		}
		solveKnapsack(weight,value,allowedPackageWeight);
	}
	
	private static void solveKnapsack(float[] weights, int[] values, int maxWeight)
    {
		List<Integer> indexes = new ArrayList<>(values.length);
		int index = 0;
		while(index<values.length){
			indexes.add(index);
			index++;
		}
		List<List<Integer>> combinations = differentCombinations(indexes);
		int maxValue = -1;
		List<Integer> maxValueCombination = new ArrayList<>();
		float maxValuesWeight = -1;
		
		for(List<Integer> combination : combinations){
			int combinationValue = getCombinationValue(values,combination);
			float combinationWeight = getWeight(weights,combination);
			if(maxValue<=combinationValue && combinationWeight<maxWeight){
				if(maxValue==combinationValue && maxValuesWeight<combinationWeight)
					continue;
				
				maxValue = combinationValue;
				maxValuesWeight = combinationWeight;
				maxValueCombination = combination;
			}
		}
		StringJoiner joiner = new StringJoiner(",");
		joiner.setEmptyValue("-");
		maxValueCombination.forEach(i -> joiner.add((i+1)+""));
		System.out.println(joiner.toString());
    }
	
	private static float getWeight(float[] weight, List<Integer> combination) {
		float sum = 0;
		sum = combination.stream().map(i -> weight[i]).reduce(0.0f, (a,b) -> a+b);
		return sum;
	}

	private static int getCombinationValue(int[] values,
			List<Integer> combination) {
		int sum = 0;
		sum = combination.stream().map(i -> values[i]).reduce(0, (a,b) -> a+b);
		return sum;
	}

	private static List<List<Integer>> differentCombinations(List<Integer> items) {
		List<List<Integer>> allCombinations = new LinkedList<>();
       if (items.isEmpty()) {
    	  allCombinations.add(new LinkedList<>());
          return allCombinations;
       }
      
       Integer first = items.get(0);
       List<List<Integer>> combinations = differentCombinations(items.subList(1, items.size()));
       for (List<Integer> subset : combinations) {
          allCombinations.add(subset);
          List<Integer> augmented = new LinkedList<>(subset);
          augmented.add(0, first);
          allCombinations.add(augmented);
       }
       return allCombinations;
   }
	
}
