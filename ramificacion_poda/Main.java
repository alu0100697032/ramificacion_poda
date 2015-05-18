package ramificacion_poda;

import java.io.IOException;

public class Main {

	public static RamificacionPoda rp; 
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		// TODO Auto-generated method stub
	    rp = new RamificacionPoda("src/instancias/max_div_30_3.txt");
	    rp.mostrarDatos();
	    System.out.println();
	    //GREEDY
	    System.out.println("GREEDY");
	    rp.mostrarLista(rp.greedy(3));
	    //GREEDY PROPUESTO
	    System.out.println("GREEDY PROPUESTO");
	    rp.mostrarLista(rp.greedyOwn(3));
	    //GRASP
	    System.out.println("GRASP");
	    rp.mostrarLista(rp.grasp(3, 3));
	    //RAMIFICACION Y PODA
	    System.out.println("Ramificacion y poda");
	    rp.ramificacionPoda(3, rp.greedy(3));
	}

}
