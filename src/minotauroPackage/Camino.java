package minotauroPackage;

public class Camino implements Comparable<Camino>{

	private int descanso1;
	private int descanso2;
	private int distancia;
	
	public Camino(int descanso1, int descanso2, int cantPasos) {
		this.descanso1 = descanso1;
		this.descanso2 = descanso2;
		this.distancia = cantPasos;
	}

	public int getDescanso1() {
		return descanso1;
	}

	public int getDescanso2() {
		return descanso2;
	}

	public int getDistancia() {
		return distancia;
	}

	@Override
	public int compareTo(Camino camino) {
		return this.distancia - camino.getDistancia();
	}
	
}
