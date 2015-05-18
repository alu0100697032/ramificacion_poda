package ramificacion_poda;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
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
	
	public ArrayList<ArrayList<Float>> greedy(int m){
		ArrayList<ArrayList<Float>> S = new ArrayList<>(); //guarda el conjunto de elementos del resultado final
		//creamos una copia de coordenadas para ir borrando los elementos
		ArrayList<ArrayList<Float>> copiaCoordenadas = (ArrayList<ArrayList<Float>>) listaCoordenadas.clone();
		ArrayList<Float> centroGravedad = obtenerCentroGravedad(copiaCoordenadas);
		while(S.size() < m){
			S.add(obtenerElementoMasAlejado(centroGravedad, copiaCoordenadas));
			centroGravedad = obtenerCentroGravedad(S);
		}
		//mostrarLista(S);
		return S;
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
	
	public ArrayList<ArrayList<Float>> greedyOwn(int m){
		//guarda el conjunto de elementos del resultado final, inicialmente son todos los puntos 
		//(copia de la lista de coordenadas)
		ArrayList<ArrayList<Float>> S = (ArrayList<ArrayList<Float>>) listaCoordenadas.clone(); 
		ArrayList<Float> centroGravedad = obtenerCentroGravedad(S);
		while(S.size() > m){
			obtenerElementoMasCercano(centroGravedad, S);
			centroGravedad = obtenerCentroGravedad(S);
		}
		//mostrarLista(S);
		return S;
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
	
	/********************************************************************************************************
	 ******************************************ALGORITMO GRASP***********************************************
	 ********************************************************************************************************/
	
	public ArrayList<ArrayList<Float>> grasp(int m, int lrc){
		ArrayList<ArrayList<Float>> S = new ArrayList<>(); //guarda el conjunto de elementos del resultado final
		//creamos una copia de coordenadas para ir borrando los elementos
		ArrayList<ArrayList<Float>> copiaCoordenadas = (ArrayList<ArrayList<Float>>) listaCoordenadas.clone();
		ArrayList<Float> centroGravedad = obtenerCentroGravedad(copiaCoordenadas);
		while(S.size() < m){
			S.add(obtenerElementoMasAlejadoLRC(centroGravedad, copiaCoordenadas, lrc));
			centroGravedad = obtenerCentroGravedad(S);
		}
		busquedaLocal(centroGravedad, S, copiaCoordenadas);
		//mostrarLista(S);
		return S;
	}
	
	//devuelve el elemento mas alejado del centro de gravedad segun la LRC
	public ArrayList<Float> obtenerElementoMasAlejadoLRC(ArrayList<Float> centroGravedad, ArrayList<ArrayList<Float>> lista, int l){
		//guarda las posiciones de los elementos de la lrc para poder borrarlo de la lista mas tarde
		ArrayList<Integer> posicionesElementosLRC =  new ArrayList<>(); 
		//guarda los elementos de la lrc
		ArrayList<ArrayList<Float>> lrc =  new ArrayList<>();
		//hacemos una copia para ir borrando y poder buscar los l mejores elementos
		ArrayList<ArrayList<Float>> copiaLista = (ArrayList<ArrayList<Float>>) lista.clone();
		
		int posicionElemento = 0;
		//creamos la lrc
		for(int h = 0; h < l; h++){//tamaño de la lrc
			float distanciaEuclideaMinima = 0;
			for(int i = 0; i < copiaLista.size(); i++){//recorremos todos los puntos
				float auxiliar = 0;
				//inicio de la formula de la distancia euclidea
				for(int j = 0; j < dimensionElemento; j++){
					auxiliar += Math.pow((copiaLista.get(i).get(j) - centroGravedad.get(j)), 2);//resta + cuadrado
				}
				auxiliar = (float) Math.sqrt(auxiliar);//raiz
				//fin de la formula de la distancia euclidea
				if(auxiliar > distanciaEuclideaMinima){//comprobamos el maximo
					distanciaEuclideaMinima = auxiliar;
					posicionElemento = i;
				}
			}
			//añadimos a la lrc el elemento mas alejado, guardamos la posicion 
			//y lo borramos de la lista copiada para buscar el siguiente mayor
			lrc.add(copiaLista.get(posicionElemento));
			posicionesElementosLRC.add(posicionElemento);
			copiaLista.remove(posicionElemento);
		}
		//de la lrc seleccionamos uno aleatorio, lo borramos de la lista de puntos 
		//y lo devolvemos para guardarlo en la solucion final 
		ArrayList<Float> elemento =  new ArrayList<>();
		Random random = new Random();
		int elementoSeleccionadoLRC = random.nextInt(lrc.size());
		elemento = lrc.get(elementoSeleccionadoLRC);
		lista.remove(posicionesElementosLRC.get(elementoSeleccionadoLRC));//borramos el elemento, queda en la solución final 
		return elemento;
	}
	/********************************************************************************************************
	 ******************************************BUSQUEDA LOCAL************************************************
	 ********************************************************************************************************/
	 
	public void busquedaLocal(ArrayList<Float> centroGravedad, ArrayList<ArrayList<Float>> lista, ArrayList<ArrayList<Float>> copia){
		
		boolean mejora = false;
		//repetir hasta que no mejore
		do{
			int posicionElemento = 0;
			float distanciaEuclidea = 0; 
			float distanciaEuclideaMejorable = 9999999;
			int posicionElementoMejorable = 0;
			mejora = false;
			//recorremos los puntos que no estan en la solucion para ver cual es el mas alejado del centro de gravedad
			for(int i = 0; i < copia.size(); i++){
				float auxiliar = 0;
				//inicio de la formula de la distancia euclidea
				for(int j = 0; j < dimensionElemento; j++){
					auxiliar += Math.pow((copia.get(i).get(j) - centroGravedad.get(j)), 2);//resta + cuadrado
				}
				auxiliar = (float) Math.sqrt(auxiliar);//raiz
				//fin de la formula de la distancia euclidea
				if(auxiliar > distanciaEuclidea){//comprobamos el maximo
					distanciaEuclidea = auxiliar;
					posicionElemento = i;
				}//endif
			}//endfor
			//recorremos los puntos que estan en la solucion para ver cual es el mas cercano al centro de gravedad
			for(int i = 0; i < lista.size(); i++){
				float auxiliar = 0;
				//inicio de la formula de la distancia euclidea
				for(int j = 0; j < dimensionElemento; j++){
					auxiliar += Math.pow((lista.get(i).get(j) - centroGravedad.get(j)), 2);//resta + cuadrado
				}
				auxiliar = (float) Math.sqrt(auxiliar);//raiz
				//fin de la formula de la distancia euclidea
				if(distanciaEuclideaMejorable > auxiliar){//mas cercano
					distanciaEuclideaMejorable = auxiliar;
					posicionElementoMejorable = i;
				}//endif
			}//endfor
			//si mejora la solucion se cambia
			if(distanciaEuclidea > distanciaEuclideaMejorable){
				lista.add(copia.get(posicionElemento));
				copia.remove(posicionElemento);
				//copia.add(lista.get(posicionElementoMejorable));
				lista.remove(posicionElementoMejorable);
				mejora = true;
			}
			//se recalcula en centro de gravedad
			centroGravedad = obtenerCentroGravedad(lista);
		}while(mejora == true);
	}
	
	/********************************************************************************************************
	 ***************************************RAMIFICACIÓN Y PODA**********************************************
	 ********************************************************************************************************/
	public void ramificacionPoda(int m, ArrayList<ArrayList<Float>> cotaInicial){
		float cota = dispersion(cotaInicial);
		System.out.println("Cota inicial: " + cota);
		ArrayList<ArrayList<ArrayList<Float>>> ramificacion = new ArrayList<>();
		//ArrayList<ArrayList<Float>> subconjunto = new ArrayList<>();
		ArrayList<ArrayList<Float>> copiaLista = new ArrayList<>();
		/**/
		for(int i = 0; i < listaCoordenadas.size(); i++){
			copiaLista = (ArrayList<ArrayList<Float>>) listaCoordenadas.clone();
			//subconjunto.clear();
			ArrayList<ArrayList<Float>> subconjunto = new ArrayList<>();
			subconjunto.add(copiaLista.get(i));
			copiaLista.remove(i);
			for(int j = 0; j < m - 1; j++){
				subconjunto.add(obtenerElementoMasAlejado(obtenerCentroGravedad(subconjunto), copiaLista));
			}
			if(dispersion(subconjunto) > cota){
				ramificacion.add(subconjunto);
			}
		}
		
		
		
		
		System.out.println("Mejoras al grasp:");
		for(int i = 0; i < ramificacion.size(); i++){
			mostrarLista(ramificacion.get(i));
			System.out.println(dispersion(ramificacion.get(i)));
			System.out.println("");
		}
	}
	
	public float dispersion(ArrayList<ArrayList<Float>> lista){
		ArrayList<Float> centroGravedad = obtenerCentroGravedad(lista);
		float dispersion = 0; 
		for(int i = 0; i < lista.size(); i++){
			float auxiliar = 0;
			//inicio de la formula de la distancia euclidea
			for(int j = 0; j < dimensionElemento; j++){
				auxiliar += Math.pow((lista.get(i).get(j) - centroGravedad.get(j)), 2);//resta + cuadrado
			}
			auxiliar = (float) Math.sqrt(auxiliar);//raiz
			//fin de la formula de la distancia euclidea
			dispersion += auxiliar;
		}//endfor
		return dispersion;
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
