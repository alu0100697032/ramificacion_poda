package ramificacion_poda;

import java.io.IOException;

public class Main {

	public static RamificacionPoda rp; 
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		// TODO Auto-generated method stub
	    rp = new RamificacionPoda("src/instancias/prueba.txt");
	    rp.mostrarDatos();
	    System.out.println();
	    //GREEDY
	    System.out.println("GREEDY");
	    rp.greedy(2);
	    //GREEDY PROPUESTO
	    System.out.println("GREEDY PROPUESTO");
	    rp.greedyOwn(2);
	}

}
