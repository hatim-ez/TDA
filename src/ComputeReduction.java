import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.TreeSet;
import java.util.Vector;

class ComputeReduction {
    Vector<Simplex> F; // List of the simplexs
    int[] low;  // represents the function low
    SparseMatrix matrix; // the boudary matrix

    public ComputeReduction(Vector<Simplex> F) {
        this.F = F;
        low = new int[F.size()];
        matrix = new SparseMatrix(F.size());
    }

	public void computeBoundaryMatrix() { // Compute the boudary matrix
        HashMap <TreeSet<Integer>, Integer> indices = new HashMap<>();  // The index of a Treeset in the list of simplexs
        for (int l = 0; l < F.size(); l++) {
            Simplex s = F.get(l);
            indices.put(s.vert, l);
        }
        int count = 0;
        for (int l = 0; l < F.size(); l++) {
            Simplex s = F.get(l);
            if (s.dim == 0) {
                count += 1;
                continue;
            }
            else {
                for (int j : s.vert) {
                    TreeSet<Integer> temp = (TreeSet<Integer>) s.vert.clone();
                    temp.remove(j);
                    Integer indice = indices.get(temp);
                    if (indice != null) {
                        matrix.setValue(indice, count, 1);
                    }
                }
                count += 1;
            }

        }
    }

    public void computeLow() {
        // Calculate low(i) for each column
        int size = matrix.matrix.size();
        for (int j = 0; j < size; j++) {
            int indiceLow = -1;
            for (int i = 0; i < size; i++) {
                if (matrix.valueAt(i, j)==1) {
                    indiceLow = i;
                }
            }
            low[j] = indiceLow;
        }
    }

    public static void updateLow(int j, SparseMatrix matrix, int[] low) {
        // Update the value of low(i)
        int indiceLow = -1;
        for (int i = 0; i < matrix.length; i++) {
            if (matrix.valueAt(i, j) == 1) {
                indiceLow = i;
            }
        }
        low[j] = indiceLow;
    }

    public void reduce() { // time complexity O(m³)
        // Reduce the matrix
        int size = matrix.length;
        computeLow();
        for (int j = 0; j < size; j++) {
            boolean hasBeenModified = true;
            while (hasBeenModified) {
                hasBeenModified = false;
                for (int l = 0; l < j; l++) {
                    if (low[l] == low[j] && low[l] != -1) {
                        for (int i = 0; i < size; i++) {
                            int val = matrix.valueAt(i,j);
                            val -= (matrix.valueAt(low[j],j) / matrix.valueAt(low[l],l)) * matrix.valueAt(i,l);
                            matrix.setValue(i,j,val%2);
                        }
                        assert matrix.valueAt(low[l],j) == 0;

                        updateLow(j, matrix, low);
                        hasBeenModified = true;
                        break;
                    }
                }
            }
        }
    }



    public void barcode(String output) throws FileNotFoundException, UnsupportedEncodingException{
        // compute the barcode and write the result in the file output.
        try {
            PrintWriter writer = new PrintWriter(output , "UTF-8");

            HashMap<Integer, Integer> reverseLow = new HashMap<Integer, Integer>();
            for (int i=0; i<low.length; i++){
                reverseLow.put(low[i], i);
            }

            for (int i = 0; i < matrix.length; i++){
                if (low[i] == -1) {
                    Integer reverse = reverseLow.get(i);
                    if (reverse != null) {
                        writer.println(F.get(i).dim+" "+F.get(i).val+" "+F.get(reverse).val);
                    }
                    else{
                        writer.println(F.get(i).dim+" "+F.get(i).val+" inf");
                    }
                }
        }
        writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    
    public void printArray(){
    	matrix.print();
    }
}
