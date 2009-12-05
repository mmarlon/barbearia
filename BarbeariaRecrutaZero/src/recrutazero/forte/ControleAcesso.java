package recrutazero.forte;

import java.util.concurrent.Semaphore;

import recrutazero.Simulador;
import recrutazero.clientes.Cliente;
import recrutazero.clientes.EnumPatente;


public class ControleAcesso implements Runnable{
	
	private Forte forte;
	private Barbearia barbearia;
	private Semaphore semaforoClientes;
	private Semaphore semaforoLugares;
	private Semaphore semaforoRelatorio;
	private int numPausa = 0;
	private boolean fimDia = false;
	int n = 0;
	
	private static int numDescartados;
	
	public ControleAcesso(Forte forte, Barbearia barbearia, Semaphore semaforoLugares, Semaphore semaforoClientes, Semaphore semaforoRelatorio) {
		this.barbearia = barbearia;
		this.forte = forte;		
		
		this.semaforoLugares = semaforoLugares; // contador
		this.semaforoClientes = semaforoClientes; // sincronizacao
		
		this.semaforoRelatorio = semaforoRelatorio;
		
	}

	@Override
	public void run() {
		while(!fimDia)
		{
			adicionaClienteBarbearia();
			aguardaProximaChegada();
		}
		
	}

	/**
	 * Método que faz o tenente ficar esperando apróxima chegada de algum cliente.
	 */
	private void aguardaProximaChegada() {
		sleep((forte.geraEvento()%5+1));
	}

	/**
	 * Método que verifica se há um cliente querendo entrar na barbearia.
	 * Se houver cliente querendo entrar e tiver espaço na barbearia, ele adiciona.
	 */
	private void adicionaClienteBarbearia() {
			Cliente cliente = forte.getCliente();			
			tentaAdicionarCliente(cliente);			
			verificaFechamento();
	}

	private void tentaAdicionarCliente(Cliente cliente) {
		try {
			if(!EnumPatente.PAUSA.equals(cliente.getPatente())){
				
				//Verifico se há espaço para a produção de mais um produto.
				semaforoRelatorio.acquire();
				if (barbearia.getNumClientes() < Simulador.NUM_LUGARES) {
					semaforoLugares.acquire();
					barbearia.addClienteEspera(cliente);
					//Indico que há pelo menos um produto já produzido.
					semaforoClientes.release();
					numPausa = 0;
				}
				else {
					numDescartados++;
					numPausa = 0;
					System.out.println("descartado");
				}
				semaforoRelatorio.release();
			}else {
				numPausa++;
				System.out.println("0 Pausa");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void verificaFechamento() {
		if(numPausa>=5){
			barbearia.fecha();
			fimDia = true;
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
			Thread.sleep(tempoEspera);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static int getNumeroDescartados() {
		return numDescartados;
	}
	
	public static void limparDescartados() {
		numDescartados = 0;
	}

}
