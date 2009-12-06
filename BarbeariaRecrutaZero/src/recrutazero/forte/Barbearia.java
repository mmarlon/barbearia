package recrutazero.forte;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import recrutazero.barbeiros.BarbeiroAbstrato;
import recrutazero.clientes.Cliente;
import recrutazero.clientes.EnumPatente;



public class Barbearia implements Runnable {

	private int numOficiais;
	private int numSagentos;
	private int numPracas;
	protected List<Cliente> oficiaisList;
	protected List<Cliente> sargentosList;
	protected List<Cliente> pracasList;
	protected Semaphore oficiaisSemaforo;
	protected Semaphore sargentosSemaforo;
	protected Semaphore pracasSemaforo;
	private Semaphore semaforoFim = new Semaphore(0, true);
	private Semaphore semaforoFazendoRelatorio;
	private boolean mudouEstado = false;
	private boolean fechar = false;
	private int numClientes = 0;
	private List<BarbeiroAbstrato> barbeiros = new ArrayList<BarbeiroAbstrato>();
	private ControleAcesso controleAcesso;
	private Observador observador;
	
	private List<Cliente> oficiaisAtendidos;
	private List<Cliente> sargentosAtendidos;
	private List<Cliente> pracasAtendidos;
	private List<Cliente> clientesAtendidos;	
	
	public Barbearia(Semaphore oficiaisSemaforo, Semaphore sargentosSemaforo, Semaphore pracasSemaforo, Semaphore semaforoRelatorio) {
		this.oficiaisSemaforo = oficiaisSemaforo;
		this.sargentosSemaforo = sargentosSemaforo;
		this.pracasSemaforo = pracasSemaforo;
		
		this.semaforoFazendoRelatorio = semaforoRelatorio;

		oficiaisList = new ArrayList<Cliente>();
		sargentosList = new ArrayList<Cliente>();
		pracasList = new ArrayList<Cliente>();
		
		oficiaisAtendidos = new ArrayList<Cliente>();
		sargentosAtendidos = new ArrayList<Cliente>();
		pracasAtendidos = new ArrayList<Cliente>();
		clientesAtendidos = new ArrayList<Cliente>();
		
	}

	@Override
	public void run() {
		try {
			semaforoFim.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		alertaFim();
		
		System.exit(0);
		
	}

	private void alertaFim() {
		for (BarbeiroAbstrato barbeiro : barbeiros) {
			barbeiro.fimDoDia();
		}
		controleAcesso.fimDoDia();
		observador.fimDoDia();
	}

	/**
	 * @see Barbearia#addClienteEspera(Cliente)
	 */
	public void addClienteEspera(Cliente cliente) {
		addCliente(escolheLista(cliente.getPatente()), cliente, escolheSemaforo(cliente.getPatente()));
	}

	/**
	 * @see Barbearia#pegaProximoCliente(EnumPatente)
	 */
	public Cliente pegaProximoCliente(EnumPatente patente) {
		return getCliente(escolheLista(patente), escolheSemaforo(patente));
	}

	private Semaphore escolheSemaforo(EnumPatente patente) {
		Semaphore semaforo = null;
		if (EnumPatente.OFICIAL.equals(patente)) {
			semaforo = oficiaisSemaforo;
			numOficiais++;
		}
	
		if (EnumPatente.SARGENTO.equals(patente)) {
			semaforo = sargentosSemaforo;
			numSagentos++;
		}
	
		if (EnumPatente.PRACA.equals(patente)) {
			semaforo = pracasSemaforo;
			numPracas++;
		}
		return semaforo;
	
	}

	/**
	 * Método que faz a remoção de um cliente de uma dada lista.
	 * @param lista Lista de onde um cliente deve ser removido.
	 * @return O Cliente removido.
	 */
	private Cliente getCliente(List<Cliente> lista, Semaphore semaforo) {
		
		Cliente cliente = null;
		
		try {
			
			semaforoFazendoRelatorio.acquire();
			// Garanto que os dados da fila não serão modificados enquanto faço a adição do produto.
			semaforo.acquire();
			cliente = lista.remove(0);
			marcarAtendimento(cliente);
			numClientes--;
			mudouEstado = true;
			verificaTermino();
			semaforo.release();
			semaforoFazendoRelatorio.release();
						
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return cliente;
	}

	private void marcarAtendimento(Cliente cliente) {
		cliente.setMomentoAtendimento(System.currentTimeMillis());
		switch (cliente.getPatente()) {
		case OFICIAL:
			oficiaisAtendidos.add(cliente);
			break;
		case SARGENTO:
			sargentosAtendidos.add(cliente);
			break;
		case PRACA:
			pracasAtendidos.add(cliente);
			break;
		}
	}

	private void verificaTermino() {
		if(numClientes==0 && this.fechar){
			semaforoFim.release();
		}
	}

	/**
	 * Método que adiciona um cliente em uma dada lista.
	 * @param fila Lista onde um cliente de ser adicionado.
	 * @param cliente Cliente que deseja ser adicionado.
	 */
	private void addCliente(List<Cliente> fila, Cliente cliente, Semaphore semaforo) {
		
		try {
			//Se estiver fazendo relatório, os valores das listas não podem mudar!
			//Garanto que os dados da fila não serão modificados enquanto faço a adição do produto.
			semaforo.acquire();
			cliente.setMomentoEntrada(System.currentTimeMillis());
			fila.add(cliente);
			mudouEstado = true;
			System.out.println(cliente);
			numClientes++;
			semaforo.release();
		
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * @see Barbearia#possuiPatenteEspera(EnumPatente)
	 */
	public boolean possuiPatenteEspera(EnumPatente patente) {
		return (escolheLista(patente).size()>0);
	}

	/**
	 * Método que escolhe uma lista dada uma patente.
	 * @param patente uma patente 
	 * @return Lista referente a patente passada por param.
	 */
	private List<Cliente> escolheLista(EnumPatente patente) {
		List<Cliente> lista = null;
		if (EnumPatente.OFICIAL.equals(patente)) {
			lista = oficiaisList;
		}
	
		if (EnumPatente.SARGENTO.equals(patente)) {
			lista = sargentosList;
		}
	
		if (EnumPatente.PRACA.equals(patente)) {
			lista = pracasList;
		}
		return lista;
	}

	/**
	 * @see Barbearia#addFuncionario(Funcionario...)
	 */
	public void addFuncionario(BarbeiroAbstrato... barb) {
		for (BarbeiroAbstrato barbeiro : barb) {
			barbeiros.add(barbeiro);
		}
	}
	
	public void addControles(ControleAcesso controleAcesso, Observador observador) {
		this.controleAcesso = controleAcesso;
		this.observador = observador;
	}

	/**
	 * @see Barbearia#fecha() 
	 */
	public void fecha() {
		this.fechar = true;
		verificaTermino();
	}

	/**
	 * @see Barbearia#getEstado()
	 */
	public EstadoBarbearia getEstado() throws InterruptedException {
		
		EstadoBarbearia estadoBarbearia = new EstadoBarbearia();
		
		estadoBarbearia.analisarFilasEspera(oficiaisList, sargentosList, pracasList);
		
		estadoBarbearia.setNumDescartados(ControleAcesso.getNumeroDescartados());
		ControleAcesso.limparDescartados();
		
		estadoBarbearia.analisarAtendimentos(oficiaisAtendidos, sargentosAtendidos, pracasAtendidos);
		oficiaisAtendidos.clear();
		sargentosAtendidos.clear();
		pracasAtendidos.clear();
	
		mudouEstado = false;
		
		return estadoBarbearia;
	}


	public boolean isMudouEstado() {
		return mudouEstado;
	}

	public int getNumOficiais() {
		return numOficiais/2;
	}

	public int getNumSagentos() {
		return numSagentos/2;
	}

	public int getNumPracas() {
		return numPracas/2;
	}
	
	public int getNumClientes() {
		return numClientes;
	}
}
