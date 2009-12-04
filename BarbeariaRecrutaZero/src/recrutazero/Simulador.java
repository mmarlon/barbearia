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

	// Indica quantos produtos podem ser produzidos antes que sejam consumidos.
	private static final int NUM_LUGARES = 1;
	
	// Indica quantos produtos estarao produzidos no inicio da execucao.
	private static int NUM_PRODUZIDOS = 0;
	
	// O primeiro a tentar fazer uso da exclusão mutua vai encontrar o caminho liberado
	private static int CAMINHO_LIVRE = 1;
	
	private static int OBSERVADOR = 1;
	
	public static void main(String[] args) {

		Semaphore semaforoClientes = new Semaphore(NUM_PRODUZIDOS, true);
		Semaphore semaforoLugares = new Semaphore(NUM_LUGARES, true);
		Semaphore semaforoOficial = new Semaphore(CAMINHO_LIVRE, true);
		Semaphore semaforoSargento = new Semaphore(CAMINHO_LIVRE, true);
		Semaphore semaforoPraca = new Semaphore(CAMINHO_LIVRE, true);
		
		Semaphore semaforoRelatorio = new Semaphore(OBSERVADOR, true);
				
		Barbearia barbearia = new Barbearia(semaforoOficial, semaforoSargento, semaforoPraca, semaforoRelatorio);
		new Thread(barbearia).start();
		
		Integer teste = new Integer(NUM_LUGARES);
		
		BarbeiroAbstrato barbeiroOficial = new BarbeiroOficial(barbearia, semaforoLugares, semaforoClientes, teste);
		BarbeiroAbstrato barbeiroPraca = new BarbeiroPraca(barbearia, semaforoLugares, semaforoClientes, teste);
		BarbeiroAbstrato barbeiroSargento = new BarbeiroSargento(barbearia, semaforoLugares, semaforoClientes, teste);
		
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
