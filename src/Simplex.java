import java.util.Iterator;
import java.util.Scanner;
import java.util.TreeSet;



class Simplex implements Comparable{
	float val;
	int dim;
	TreeSet<Integer> vert;

	Simplex(Scanner sc){
		val = sc.nextFloat();
		dim = sc.nextInt();
		vert = new TreeSet<Integer>();
		for (int i=0; i<=dim; i++)
			vert.add(sc.nextInt());
	}

	public String toString(){
		return "{val="+val+"; dim="+dim+"; "+vert+"}\n";
	}
	
	@Override
	public int compareTo(Object o) {
		Simplex s = (Simplex) o;
		return Float.compare(val, s.val);
    }
}

