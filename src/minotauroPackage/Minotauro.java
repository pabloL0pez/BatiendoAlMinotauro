package minotauroPackage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Minotauro {

	private int planilla[][]; // Matriz de adyacencia
	private int cantDescansos; // Cantidad de nodos
	private int cantCaminos;
	
	// Si resuelvo con Kruskal
	private ArrayList<Camino> caminos = new ArrayList<Camino>();
	private int[] padres;
	
	// Si resuelvo con Prim
	private ArrayList<Integer> conjuntoSol = new ArrayList<Integer>();
	private ArrayList<Integer> conjuntoNoSol = new ArrayList<Integer>();
	
	private ArrayList<Camino> mapa = new ArrayList<Camino>();
	
	public Minotauro(String path) throws FileNotFoundException {
		Scanner scan = new Scanner(new File(path));
		int distancia = 0;
		
		this.cantDescansos = scan.nextInt();
		this.planilla = new int[this.cantDescansos][this.cantDescansos];
		this.padres = new int[this.cantDescansos];
		
		for (int i = 0 ; i < this.cantDescansos ; i++) {
			this.padres[i] = i;
			this.conjuntoNoSol.add(i);
			for (int j = 0 ; j < this.cantDescansos ; j++) {
				if ((distancia = scan.nextInt()) != 0) {
					this.caminos.add(new Camino(i, j, distancia));
				}
				this.planilla[i][j] = distancia;
			}
		}
		
		Collections.sort(this.caminos);
		scan.close();
	}
	
	public void resolverKruskal() throws IOException {
		int descanso1;
		int descanso2;
		int distancia;
		int i = 1;
		
		descanso1 = this.caminos.get(0).getDescanso1();
		descanso2 = this.caminos.get(0).getDescanso2();
		distancia = this.caminos.get(0).getDistancia();
		
		union(descanso1, descanso2);
		this.mapa.add(new Camino(descanso1+1, descanso2+1, distancia));
		this.cantCaminos++;
		
		while(this.cantCaminos < (this.cantDescansos) && i < this.caminos.size()) {
			descanso1 = this.caminos.get(i).getDescanso1();
			descanso2 = this.caminos.get(i).getDescanso2();
			distancia = this.caminos.get(i).getDistancia();
			if (find(descanso1) != find(descanso2)) {
				union(descanso1, descanso2);
				this.mapa.add(new Camino(descanso1+1, descanso2+1, distancia));
				this.cantCaminos++;
			}
			i++;
		}
		
		escribirSolucion("Kruskal");
	}
	
	private int find(int descanso) {
		return this.padres[descanso] == descanso ? descanso : find(this.padres[descanso]);
	}
	
	private void union(int descanso1, int descanso2) {
		this.padres[find(descanso1)] = find(descanso2);
	}
	
	public void resolverPrim() throws IOException {
		int descanso;
		this.conjuntoSol.add(0);
		this.conjuntoNoSol.remove((Object)0);
		
		while (!this.conjuntoNoSol.isEmpty()) {
			descanso = buscarMenor();
			this.cantCaminos++;
			this.conjuntoNoSol.remove((Object)descanso);
			this.conjuntoSol.add(descanso);
		}
		
		escribirSolucion("Prim");
	}
	
	private int buscarMenor() {
		int descanso1 = 0;
		int descanso2 = 0;
		int distancia = 0;
		int distanciaMinima = Integer.MAX_VALUE;
		
		for (int i = 0 ; i < this.conjuntoSol.size() ; i++) {
			for (int j = 0 ; j < this.planilla.length ; j++) {
				distancia = this.planilla[this.conjuntoSol.get(i)][j];
				if (distancia != 0 && distancia < distanciaMinima && !this.conjuntoSol.contains((Object)j)) {
					descanso1 = this.conjuntoSol.get(i);
					descanso2 = j;
					distanciaMinima = distancia;
				}
			}
		}
		
		this.mapa.add(new Camino(descanso1+1, descanso2+1, distanciaMinima));
		return descanso2;
	}

	private void escribirSolucion(String algoritmo) throws IOException {
		BufferedWriter buffer = new BufferedWriter(new FileWriter("minotauro" + algoritmo + ".out"));
		
		buffer.write(String.valueOf(this.cantCaminos));
		buffer.newLine();
		for (int i = 0 ; i < this.mapa.size() ; i++) {
			buffer.write(this.mapa.get(i).getDescanso1() + " " + this.mapa.get(i).getDescanso2() + " " + this.mapa.get(i).getDistancia());
			buffer.newLine();
		}
		
		buffer.close();
	}
	
	public void mostrarPlanilla() {
		for (int i = 0 ; i < this.cantDescansos ; i++) {
			for (int j = 0 ; j < this.cantDescansos ; j++) {
				System.out.print(this.planilla[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	public void mostrarCaminos() {
		for (int i = 0 ; i < this.caminos.size(); i++) {
			System.out.println(this.caminos.get(i).getDescanso1() + " " + this.caminos.get(i).getDescanso2() + " " + this.caminos.get(i).getDistancia());
		}
	}
	
}
