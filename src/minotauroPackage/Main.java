package minotauroPackage;

import java.io.IOException;

public class Main {

	public static void main(String[] args) {
		try {
			Minotauro minotauro = new Minotauro("minotauro.in");
			//minotauro.resolverKruskal();
			minotauro.resolverPrim();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
