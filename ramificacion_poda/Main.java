/*
 * Víctor Hernández Pérez
 * alu0100697032@ull.edu.es
 * Grado en ingeniería informática
 * Tercero 
 * Itinerario: Computación
 */
package ramificacion_poda;

import java.io.IOException;

public class Main {

	public static RamificacionPoda rp; 
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		// TODO Auto-generated method stub
	    rp = new RamificacionPoda("src/instancias/max_div_15_2.txt");
	    rp.mostrarDatos();
	    System.out.println();
	    //GREEDY
	    System.out.println("GREEDY");
	    rp.greedy(5);
	    //GREEDY PROPUESTO
	    System.out.println("GREEDY PROPUESTO");
	    rp.greedyOwn(5);
	    //GRASP
	    System.out.println("GRASP");
	    rp.grasp(5, 3);
	    //RAMIFICACION Y PODA
	    System.out.println("Ramificacion y poda");
	    rp.ramificacionPoda(3, rp.greedy(3));
	}

}
