package ramificacion_poda;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {

	public static RamificacionPoda rp; 
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		// TODO Auto-generated method stub
	    rp = new RamificacionPoda("src/instancias/max_div_15_3.txt");
	    rp.mostrarDatos();
	}

}
