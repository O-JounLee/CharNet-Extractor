package graphBuilder;

public class MyLink {

	public static int edgeCount=0;
	double capacity;
	 double weight; 
	 int id;

	 public MyLink(double weight, double capacity) {
	 this.id = edgeCount++; 
	 this.weight = weight;
	 this.capacity = capacity;
	 }
	 public String toString() { 
	 return "E"+id;
	 }
}
