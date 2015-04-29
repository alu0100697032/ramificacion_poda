package ramificacion_poda;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Scanner;

public class RamificacionPoda {
	/*
	 * atributos
	 */
	private int numeroElementos;
	private int dimensionElemento;
	ArrayList<ArrayList<Float>> listaCoordenadas;
	/*
	 * constructor
	 */
	public RamificacionPoda(String nombreFichero) {
		
		//lectura del fichero
	    Scanner sc2 = null;
	    try {
	        sc2 = new Scanner(new File(nombreFichero));
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();  
	    }
	    //inicialización de los atributos
	    numeroElementos = Integer.parseInt(sc2.next());
	    dimensionElemento = Integer.parseInt(sc2.next());
	    listaCoordenadas = new ArrayList<>();
	    //lectura del fichero linea por linea
	    while (sc2.hasNextLine()) {
	            Scanner s2 = new Scanner(sc2.nextLine());
	            ArrayList<Float> dummy = new ArrayList<>();
	        while (s2.hasNext()) {//manipulamos la linea leida
	        	String a = s2.next().replace(",", ".");//remplazamos las comas por puntos porque las comas dan error... 
	            float s = Float.parseFloat(a);
	            dummy.add(s);
	        }
	        if(!dummy.isEmpty())
	        	listaCoordenadas.add(dummy);//añadimos las coordenadas a la lista
	    }
	}
	/*
	 * funcion que muestra los datos cargados
	 */
	public void mostrarDatos(){
		System.out.println("Número de elementos: " + numeroElementos);
		System.out.println("Dimensión del elemento: " + dimensionElemento);
		mostrarLista(listaCoordenadas);
	}
	public void mostrarLista(ArrayList<ArrayList<Float>> lista){
		for(int i = 0; i < lista.size(); i++){
			System.out.println((lista.get(i).toString()));
		}
	}
	/********************************************************************************************************
	 *****************************************ALGORITMO GREEDY***********************************************
	 ********************************************************************************************************/
	
	public void greedy(int m){
		ArrayList<ArrayList<Float>> S = new ArrayList<>(); //guarda el conjunto de elementos del resultado final
		//creamos una copia de coordenadas para ir borrando los elementos
		ArrayList<ArrayList<Float>> copiaCoordenadas = (ArrayList<ArrayList<Float>>) listaCoordenadas.clone();
		ArrayList<Float> centroGravedad = obtenerCentroGravedad(copiaCoordenadas);
		while(S.size() < m){
			S.add(obtenerElementoMasAlejado(centroGravedad, copiaCoordenadas));
			centroGravedad = obtenerCentroGravedad(S);
		}
		mostrarLista(S);
	}
	
	//devuelve el elemento mas alejado del centro de gravedad
	public ArrayList<Float> obtenerElementoMasAlejado(ArrayList<Float> centroGravedad, ArrayList<ArrayList<Float>> lista){
		ArrayList<Float> elemento =  new ArrayList<>();
		int posicionElemento = 0;
		float distanciaEuclidea = 0; 
		
		for(int i = 0; i < lista.size(); i++){//recorremos todos los puntos
			float auxiliar = 0;
			//inicio de la formula de la distancia euclidea
			for(int j = 0; j < dimensionElemento; j++){
				auxiliar += Math.pow((lista.get(i).get(j) - centroGravedad.get(j)), 2);//resta + cuadrado
			}
			auxiliar = (float) Math.sqrt(auxiliar);//raiz
			//fin de la formula de la distancia euclidea
			if(auxiliar > distanciaEuclidea){//comprobamos el maximo
				distanciaEuclidea = auxiliar;
				posicionElemento = i;
			}
		}
		elemento = lista.get(posicionElemento);	//guardamos las coordenadas en el elemento que devolvemos
		lista.remove(posicionElemento);			//borramos el elemento, queda en la solución final 
		return elemento;
	}
		
	//devuelve el centro de gravedad de los elementos
	public ArrayList<Float> obtenerCentroGravedad(ArrayList<ArrayList<Float>> elementos){
		ArrayList<Float> centroGravedad =  new ArrayList<>();
		for (int i = 0; i < dimensionElemento; i++){//según la dimensión de los elementos
			float aux = 0;
			for(int j = 0; j < elementos.size(); j++){//recorremos los elementos que nos pasa la funcion
				aux += elementos.get(j).get(i); //sumamos las x, las y ( y las z en caso de que haya)
			}									
			aux = aux/elementos.size();			//y dividimos entre el total de elementos
			centroGravedad.add(aux);			//añadimos la coordenada correspondiente al centro de gravedad
		}
		return centroGravedad;
	}
	
	/********************************************************************************************************
	 **************************************ALGORITMO GREEDY PROPUESTO****************************************
	 ********************************************************************************************************/
	
	public void greedyOwn(int m){
		//guarda el conjunto de elementos del resultado final, inicialmente son todos los puntos 
		//(copia de la lista de coordenadas)
		ArrayList<ArrayList<Float>> S = (ArrayList<ArrayList<Float>>) listaCoordenadas.clone(); 
		ArrayList<Float> centroGravedad = obtenerCentroGravedad(S);
		while(S.size() > m){
			obtenerElementoMasCercano(centroGravedad, S);
			centroGravedad = obtenerCentroGravedad(S);
		}
		mostrarLista(S);
	}
	
	//devuelve el elemento mas alejado del centro de gravedad
	public void obtenerElementoMasCercano(ArrayList<Float> centroGravedad, ArrayList<ArrayList<Float>> lista){
		int posicionElemento = 0;
		float distanciaEuclidea = 99999999; 
		
		for(int i = 0; i < lista.size(); i++){//recorremos todos los puntos
			float auxiliar = 0;
			//inicio de la formula de la distancia euclidea
			for(int j = 0; j < dimensionElemento; j++){
				auxiliar += Math.pow((lista.get(i).get(j) - centroGravedad.get(j)), 2);//resta + cuadrado
			}
			auxiliar = (float) Math.sqrt(auxiliar);//raiz
			//fin de la formula de la distancia euclidea
			if(auxiliar < distanciaEuclidea){//comprobamos el minimo
				distanciaEuclidea = auxiliar;
				posicionElemento = i;
			}
		}
		lista.remove(posicionElemento);			//borramos el elemento
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
