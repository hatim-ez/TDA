import com.oracle.tools.packager.Log;

import java.io.*;
import java.util.*;




public class ReadFiltration {

    static Vector<Simplex> readFiltration(String filename) throws FileNotFoundException {
        Vector<Simplex> F = new Vector<Simplex>();
        Scanner sc = new Scanner(new File(filename));
        sc.useLocale(Locale.US);
        while (sc.hasNext())
            F.add(new Simplex(sc));
        sc.close();
        return F;
    }

    public static void main(String[] args) throws FileNotFoundException, java.io.IOException {

        long start = System.currentTimeMillis();
        Vector<Simplex> F = readFiltration("filtration_B.txt");
        long end = System.currentTimeMillis();
        System.out.println("read filration ends in : " + (end - start));
        
        
        start = end;
        Collections.sort(F);
        ComputeReduction p = new ComputeReduction(F);
        // Compute the boudary matrix
        p.computeBoundaryMatrix();
        end = System.currentTimeMillis();
        System.out.println("Boundary matrix computed in : " + (end - start));

        start = end;
        p.reduce();
        end = System.currentTimeMillis();
        System.out.println("Boundary Matrix reducted in: " + (end - start));
        
        start = end;
        // Compute the barcode
        p.barcode("barcodeB.out");
        end = System.currentTimeMillis();
        System.out.println("end time : " + (end - start));
    }
}

