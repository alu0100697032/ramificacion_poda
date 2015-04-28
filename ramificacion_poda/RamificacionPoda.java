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
		
		//lectura del fichero 
		FileReader f = new FileReader(nombreFichero);
	    BufferedReader b = new BufferedReader(f);
	    //inicialización de los atributos
	    numeroElementos = Integer.parseInt(b.readLine());
	    dimensionElemento = Integer.parseInt(b.readLine());
	    listaCoordenadas = new ArrayList<String[]>();
	    //recorremos las lineas que representan cada elemento
	    for(int i = 0; i < numeroElementos; i++){
	    	//separamos las coordenadas y las metemos en un array
		    listaCoordenadas.add(b.readLine().split("\t", dimensionElemento));
	    }
	    b.close();//cerramos es buffer de lectura
	}
	/*
	 * funcion que muestra los datos cargados
	 */
	public void mostrarDatos(){
		System.out.println("Número de elementos: " + numeroElementos);
		System.out.println("Dimensión del elemento: " + dimensionElemento);
		for(int i = 0; i < listaCoordenadas.size(); i++){
			System.out.println(java.util.Arrays.toString(listaCoordenadas.get(i)));
		}
	}
	/*
	 * algoritmo greedy 
	 */
	public void greedy(){
		
	}
	
	/*
	 * metodos de acceso a los atributos
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
