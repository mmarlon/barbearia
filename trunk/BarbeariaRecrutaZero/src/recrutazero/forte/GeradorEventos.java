package recrutazero.forte;

import java.util.Random;

public class GeradorEventos {

	protected Random random;
	
	public GeradorEventos() {
		random = new Random(System.currentTimeMillis());
	}

	public int gera() {
		return Math.abs(random.nextInt());
	}

	
}
