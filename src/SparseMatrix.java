import java.util.Collections;
import java.util.HashSet;
import java.util.Vector;

public class SparseMatrix {
	Vector <HashSet<Integer>> matrix;
	int length;
	
	SparseMatrix(int n){
		matrix = new Vector<HashSet<Integer>>();
		length = n;
		for (int i=0; i<n; i++){
			HashSet<Integer> line = new HashSet<Integer>();
			matrix.addElement(line);
		}
	}
	
	public void setValue(Integer i, Integer j, int val){
		//  val must be 0 or 1
		assert(val==1 || val ==0 );
		HashSet<Integer> line = matrix.get(i);
		if (!line.contains(j) && val == 1){
			line.add(j);
		}
		if (line.contains(j) && val == 0){
			line.remove(j);
		}
	}
	
	public int valueAt(int i, int j){
		if (matrix.elementAt(i).contains(j)){
			return 1;
		}
		return 0;
	}
	
	public void print(){
		for (int i=0; i<length; i++){
			HashSet<Integer> line = matrix.elementAt(i);
			for (int j=0; j<length; j++){
				if (line.contains(j))
					System.out.print("1 ");
				else
					System.out.print("0 ");
			}
			System.out.println();
		}
	}
}
