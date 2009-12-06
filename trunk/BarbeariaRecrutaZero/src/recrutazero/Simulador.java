package recrutazero;

import java.util.concurrent.Semaphore;

import recrutazero.barbeiros.BarbeiroAbstrato;
import recrutazero.barbeiros.BarbeiroOficial;
import recrutazero.barbeiros.BarbeiroPraca;
import recrutazero.barbeiros.BarbeiroSargento;
import recrutazero.forte.Barbearia;
import recrutazero.forte.ControleAcesso;
import recrutazero.forte.Forte;
import recrutazero.forte.GeradorEventos;
import recrutazero.forte.Observador;


public class Simulador {
	public static final int VELOCIDADE_SIMULACAO = 1; // Quanto maior, mais lento. Somente para DEBUG

	public static final int NUM_LUGARES = 20;
	private static int NUM_CLIENTES = 0;
	private static int DISPONIBILIDADE = 1;

	private static int OBSERVADOR = 1;
	
	public static void main(String[] args) {

		Semaphore semaforoClientes = new Semaphore(NUM_CLIENTES, true);
		Semaphore semaforoLugares = new Semaphore(NUM_LUGARES, true);
		Semaphore semaforoOficial = new Semaphore(DISPONIBILIDADE, true);
		Semaphore semaforoSargento = new Semaphore(DISPONIBILIDADE, true);
		Semaphore semaforoPraca = new Semaphore(DISPONIBILIDADE, true);
		
		Semaphore semaforoRelatorio = new Semaphore(OBSERVADOR, true);
				
		Barbearia barbearia = new Barbearia(semaforoOficial, semaforoSargento, semaforoPraca, semaforoRelatorio);
		new Thread(barbearia).start();
		
		BarbeiroAbstrato barbeiroOficial = new BarbeiroOficial(barbearia, semaforoLugares, semaforoClientes);
		BarbeiroAbstrato barbeiroPraca = new BarbeiroPraca(barbearia, semaforoLugares, semaforoClientes);
		BarbeiroAbstrato barbeiroSargento = new BarbeiroSargento(barbearia, semaforoLugares, semaforoClientes);
		
		Forte forte = new Forte(new GeradorEventos());
		
		ControleAcesso controleAcesso = new ControleAcesso(forte, barbearia, semaforoLugares, semaforoClientes, semaforoRelatorio);
		
		Observador observador = new Observador(barbearia, semaforoRelatorio);
		
		barbearia.addFuncionario(barbeiroOficial, barbeiroPraca, barbeiroSargento);
		barbearia.addControles(controleAcesso, observador);
		
		new Thread(barbeiroOficial).start();
		new Thread(barbeiroPraca).start();
		new Thread(barbeiroSargento).start();
		new Thread(controleAcesso).start();
		new Thread(observador).start();
		
	}

}
