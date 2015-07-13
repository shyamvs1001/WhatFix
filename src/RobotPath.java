import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.StringJoiner;
import java.util.stream.IntStream;


public class RobotPath {

	public static void main(String[] args) {
		Graph graph = new Graph(4);
		System.out.println(graph.getUniquePaths());
	}

}

class Graph{
	private final Map<String,Node> indexToNode = new HashMap<>();
	private int size;
	public Graph(int n){
		size = n;
		//form nxn matrix of nodes.
		IntStream.range(0, n).forEach(i -> IntStream.range(0, n).forEach(j -> indexToNode.put(i+""+j, new Node(i,j))));
		//form the graph by connecting each node to adjacent nodes - maximum 4.
		indexToNode.values().forEach(node -> fillAdjacents(node));
	}

	public int getUniquePaths() {
		Node startingNode = indexToNode.get(0+""+0);
		Node destinationNode = indexToNode.get((size-1)+""+(size-1));
		int pathCount = 0;
		return dfs(startingNode,destinationNode,new HashSet<Node>(),pathCount);
	}
	
	//dfs finds all paths
	private int dfs(Node n, Node t, Set<Node> alreadyVisitedNodes, int uniquePathCount){
		if(alreadyVisitedNodes.contains(n))
			return uniquePathCount;
		Set<Node> visitedNodes = new LinkedHashSet<>();
		visitedNodes.addAll(alreadyVisitedNodes);
		Stack<Node> stack = new Stack<>();
		stack.push(n);
		visitedNodes.add(n);
		if(n==t){
			uniquePathCount++;
			StringJoiner s = new StringJoiner("->");
			visitedNodes.forEach(nn -> s.add(nn.getValue()));
//			System.out.println(s.toString());
			stack.pop();
			return uniquePathCount;
		}
			
		for(Node adjacent : n.getAdjacents()){
			uniquePathCount = dfs(adjacent, t, visitedNodes, uniquePathCount);
		}
		stack.pop();
		return uniquePathCount;
		
	}

	private void fillAdjacents(Node n) {
		char[] values = n.getValue().toCharArray();
		int i = Integer.parseInt(values[0]+"");
		int j = Integer.parseInt(values[1]+"");
		Set<Node> adjacents = n.getAdjacents();
		if(i>0){
			adjacents.add(indexToNode.get((i-1)+""+j));
		}
		if(i<size-1){
			adjacents.add(indexToNode.get((i+1)+""+j));
		}
		if(j>0){
			adjacents.add(indexToNode.get(i+""+(j-1)));
		}
		if(j<size-1){
			adjacents.add(indexToNode.get(i+""+(j+1)));
		}
	}
}

class Node{
	private String value;
	
	private final Set<Node> adjacents = new HashSet<>(); 
	
	public Node(int i, int j){
		this.value = i+""+j;
	}
	
	public String getValue(){
		return value;
	}
	
	public void addAdjacentNode(Node n){
		this.adjacents.add(n);
	}
	
	public Set<Node> getAdjacents(){
		return adjacents;
	}
}