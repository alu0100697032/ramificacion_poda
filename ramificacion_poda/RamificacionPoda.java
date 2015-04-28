package ramificacion_poda;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class RamificacionPoda {
	
	private int numeroElementos;
	private int dimensionElemento;
	ArrayList<String[]> listaCoordenadas;
	
	public RamificacionPoda(String nombreFichero) throws NumberFormatException, IOException{
		
		FileReader f = new FileReader(nombreFichero);
	    BufferedReader b = new BufferedReader(f);
	    numeroElementos = Integer.parseInt(b.readLine());
	    dimensionElemento = Integer.parseInt(b.readLine());
	    String [] coordenadasElemento;
	    listaCoordenadas = new ArrayList<String[]>();
	    for(int i = 0; i < numeroElementos; i++){
	    	coordenadasElemento = b.readLine().split("\t", dimensionElemento); 
		    listaCoordenadas.add(coordenadasElemento);
	    }
	    b.close();
	}

	public void mostrarDatos(){
		System.out.println("Número de elementos: " + numeroElementos);
		System.out.println("Dimensión del elemento: " + dimensionElemento);
		for(int i = 0; i < listaCoordenadas.size(); i++){
			System.out.println(java.util.Arrays.toString(listaCoordenadas.get(i)));
		}
	}
	
	/*
	 * GETTER Y SETTER
	 */
	public int getNumeroElementos() {
		return numeroElementos;
	}

	public void setNumeroElementos(int numeroElementos) {
		this.numeroElementos = numeroElementos;
	}


	public int getDimensionElemento() {
		return dimensionElemento;
	}


	public void setDimensionElemento(int dimensionElemento) {
		this.dimensionElemento = dimensionElemento;
	}
}
