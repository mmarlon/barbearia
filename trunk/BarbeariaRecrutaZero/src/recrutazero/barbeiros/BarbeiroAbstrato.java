package recrutazero.barbeiros;

import java.util.concurrent.Semaphore;

import recrutazero.Simulador;
import recrutazero.clientes.Cliente;
import recrutazero.clientes.EnumPatente;
import recrutazero.forte.Barbearia;


public abstract class BarbeiroAbstrato implements Runnable{


	protected Barbearia barbearia;
	private Semaphore semaforoClientes;
	private Semaphore semaforoLugares;
	private static final Semaphore semaforoEscolheCliente = new Semaphore(1, true);
	
	public BarbeiroAbstrato(Barbearia barbearia, Semaphore semaforoLugares,	Semaphore semaforoClientes) {
		this.semaforoLugares = semaforoLugares;
		this.semaforoClientes = semaforoClientes;

		this.barbearia = barbearia;
		
	}

	/**
	 * Metodo que atende um cliente.
	 */
	private void atendeCliente() {
		Cliente cliente = escolhaCliente();
		//System.out.println(this.getClass().getSimpleName() + " atender� " + cliente.getPatente().getPatente() + " por " + cliente.getTempoServico() + " seg");
		sleep(cliente.getTempoServico() * Simulador.VELOCIDADE_SIMULACAO);
	}

	/**
	 * Metodo que escolhe dentre as filas qual cliente sera atendido!
	 * 
	 * @return Cliente a ser atendido
	 */
	protected Cliente escolhaCliente() {
		Cliente cliente = null;
		try {
			// Verifico se há algum produto produzido.
			semaforoClientes.acquire();

			semaforoEscolheCliente.acquire();
			cliente = barbearia.pegaProximoCliente(escolhePatente());
			semaforoEscolheCliente.release();

			// Indico que há espaço para a produção de pelo menos mais um
			// produto.
			semaforoLugares.release();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return cliente;

	}

	/**
	 * Metodo que escolhe a patente a ser atendida.
	 * 
	 * @return Patente a ser atendida
	 */
	protected abstract EnumPatente escolhePatente();

	/**
	 * Metodo que escolhe a patente de acordo com sua prioridades.
	 * 
	 * @return Patente a ser atendida
	 */
	protected abstract EnumPatente aplicaPrioridade();

	@Override
	public void run() {
		while (true) {
			atendeCliente();
		}
	}

	/**
	 * Método que fazer com que o funcionário termine seu expediente.
	 */
	public void fimDoDia() {
		try {
			//System.out.println("Terminando o "+this.getClass().getSimpleName());
			Thread.currentThread().interrupt();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método que define o tempo que o processo deve ficar parado até que possa
	 * gerar um outro evento.
	 * 
	 * @param tempoEspera
	 *            Tempo que a thread deve ficar dormindo!
	 */
	protected void sleep(long tempoEspera) {
		try {
			Thread.sleep(tempoEspera * Simulador.VELOCIDADE_SIMULACAO);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
