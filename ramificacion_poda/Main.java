package ramificacion_poda;

import java.io.IOException;

public class Main {

	public static RamificacionPoda rp; 
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		// TODO Auto-generated method stub
	    rp = new RamificacionPoda("src/instancias/max_div_20_2.txt");
	    rp.mostrarDatos();
	    System.out.println();
	    //GREEDY
	    System.out.println("GREEDY");
	    rp.greedy(3);
	    //GREEDY PROPUESTO
	    System.out.println("GREEDY PROPUESTO");
	    rp.greedyOwn(3);
	    //GRASP
	    System.out.println("GRASP");
	    rp.grasp(3, 3);
	}

}
