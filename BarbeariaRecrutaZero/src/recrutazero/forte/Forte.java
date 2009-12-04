package recrutazero.forte;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import recrutazero.clientes.Cliente;
import recrutazero.clientes.EnumPatente;

public class Forte {

	protected GeradorEventos geradorEventos;
	protected Iterator<Cliente> iterator;
	private static final int MAX_CLIENTES = 1000;
	
	public Forte(GeradorEventos geradorEventos) {
		this.geradorEventos = geradorEventos;

		iterator = geraListaClientes().iterator();
	}

	protected List<Cliente> geraListaClientes() {
		
		System.out.println("Gerando uma lista aleatoria de clientes: 10% oficiais, 20% sargentos, 70% praças");

		// Cria um número aleatório de pausas. No máximo 10% dos clientes pode ser pausa
		int numeroPausas = (int)(MAX_CLIENTES * 0.1);
		
		int numClientes = MAX_CLIENTES - numeroPausas;

		HashSet<Cliente> hashCliente = geraHashClientes(numeroPausas, numClientes);
		
		Cliente [] clientes = new Cliente[hashCliente.size()];
		clientes = hashCliente.toArray(clientes);
		
		List<Cliente> listaClientes = new ArrayList<Cliente>();
		
		for (Cliente cliente : clientes) {
			listaClientes.add(cliente);
		}
		
		return listaClientes;
	}

	private HashSet<Cliente> geraHashClientes(int numeroPausas, int numClientes) {
		HashSet<Cliente> hashCliente = new HashSet<Cliente>();
		
		for (int i = 0; i < numeroPausas; ) {
			if (hashCliente.add(new Cliente(EnumPatente.PAUSA))) {
				i++;
			}
		}
		
		for (int i = 0; i < Math.round(numClientes * 0.1);) {
			EnumPatente patente = EnumPatente.OFICIAL;
			if (hashCliente.add(new Cliente(patente, geraTempoServico(patente)))) {
				i++;
			}
		}
		
		for (int i = 0; i < Math.round(numClientes * 0.2);) {
			EnumPatente patente = EnumPatente.SARGENTO;
			if (hashCliente.add(new Cliente(patente, geraTempoServico(patente)))) {
				i++;
			}
		}
		
		for (int i = 0; i < Math.round(numClientes * 0.7);) {
			EnumPatente patente = EnumPatente.PRACA;
			if (hashCliente.add(new Cliente(patente, geraTempoServico(patente)))) {
				i++;
			}
		}
		return hashCliente;
	}
	
	private int geraTempoServico(EnumPatente patente) {
		
		int tempoMinimo;
		int tempoAdicional;
		int tempoTotalServico = 0;
		
		switch (patente) {
		case OFICIAL:
			tempoMinimo = 4;
			tempoAdicional = geraEvento() % 3;
			tempoTotalServico = tempoMinimo + tempoAdicional;
			break;
		case SARGENTO:
			tempoMinimo = 2;
			tempoAdicional = geraEvento() % 3;
			tempoTotalServico = tempoMinimo + tempoAdicional;
			break;
		case PRACA:
			tempoMinimo = 1;
			tempoAdicional = geraEvento() % 3;
			tempoTotalServico = tempoMinimo + tempoAdicional;
			break;
		}
		
		return tempoTotalServico;
	}

	/**
	 * @see Forte#geraEvento()
	 */
	public int geraEvento() {
		return geradorEventos.gera();
	}

	/**
	 * @see Forte#getCliente() HÃ¡ 50% de chances de chegar alguÃ©m no forte. Se
	 *      chegou, verifique o tipo! Se nÃ£o, dÃ¡ uma pausa. Passando do
	 *      nÃºmero mÃ¡ximo de clientes gerados, ele passa a entregar PAUSAs.
	 *      Essas pausas farÃ£o o sistema parar.
	 */
	public Cliente getCliente() {
		Cliente cliente = new Cliente(EnumPatente.PAUSA);
		if (iterator.hasNext()) {
			cliente = iterator.next();
		}
		return cliente;
	}

}
