package recrutazero.forte;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import recrutazero.clientes.Cliente;
import recrutazero.clientes.EnumPatente;

public class Forte {

	protected GeradorEventos geradorEventos;
	protected Iterator<Cliente> iterator;
	private static final int MAX_CLIENTES = 1000;
	
	private LinkedList<Cliente> listaClienteOficial;
	private LinkedList<Cliente> listaClienteSargento;
	private LinkedList<Cliente> listaClientePraca;
	

	public Forte(GeradorEventos geradorEventos) {
		this.geradorEventos = geradorEventos;

		iterator = geraListaClientes().iterator();
	}

	protected List<Cliente> geraListaClientes() {
		List<Cliente> clientes = new ArrayList<Cliente>();

		// Cria um número aleatório de pausas. No máximo 10% dos clientes pode ser pausa
		int numeroPausas = (int)(MAX_CLIENTES * 0.1);
		
		int numClientes = MAX_CLIENTES - numeroPausas;
		
		// Popula lista de Oficiais, Sargentos e Praças
		popularListaClientes(numClientes);
		

		// Cria uma lista com as posicoes que as pausas terão.
		List<Integer> listaPausas = criarListaPausas(numeroPausas);
		
		Iterator<Integer> iterator = listaPausas.iterator();
		
		// Pega a primeira pausa
		int proximaPausa = 0;
		if (iterator.hasNext()) {
			proximaPausa = iterator.next();
		}

		for (int i = 0; i < MAX_CLIENTES; i++) {
			Cliente cliente = null;
			
			if (i != proximaPausa) {
				cliente = escolheTipoCliente();
			}
			else {
				cliente = new Cliente(EnumPatente.PAUSA);
				
				// Vê quando vai ser a próxima pausa
				if (iterator.hasNext()) {
					proximaPausa = iterator.next();
				}
			}


			clientes.add(cliente);
		}

		return clientes;
	}

	private void popularListaClientes(int numClientes) {
		listaClienteOficial = new LinkedList<Cliente>();
		for (int i = 0; i < (int)(numClientes * 0.1); i++) {
			EnumPatente patente = EnumPatente.OFICIAL;
			int baseTempoServico = 4;
			int offSizeTempoServico = geraEvento() % 3; // Variando entre 0 e 2
			
			listaClienteOficial.add(new Cliente(patente, baseTempoServico + offSizeTempoServico));
		}
		
		listaClienteSargento = new LinkedList<Cliente>();
		for (int i = 0; i < (int)(numClientes * 0.2); i++) {
			EnumPatente patente = EnumPatente.SARGENTO;
			int baseTempoServico = 2;
			int offSizeTempoServico = geraEvento() % 3; // Variando entre 0 e 2
			
			listaClienteSargento.add(new Cliente(patente, baseTempoServico + offSizeTempoServico));
		}
		
		listaClientePraca = new LinkedList<Cliente>();
		for (int i = 0; i < (int)(numClientes * 0.7); i++) {
			EnumPatente patente = EnumPatente.PRACA;
			int baseTempoServico = 1;
			int offSizeTempoServico = geraEvento() % 3; // Variando entre 0 e 2
			
			listaClientePraca.add(new Cliente(patente, baseTempoServico + offSizeTempoServico));
		}
		
	}

	private List<Integer> criarListaPausas(int numeroPausas) {
		List<Integer> listaPausas = new ArrayList<Integer>();
		for (int i = 0; i < numeroPausas; ) {
			int proxima = geraEvento() % MAX_CLIENTES;
			if (!listaPausas.contains(proxima)) {
				listaPausas.add(proxima);
				i++;
			}
		}
		Collections.sort(listaPausas);
		
		return listaPausas;
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

	private Cliente escolheTipoCliente() {
		
		Cliente cliente = null;
		boolean escolhido = false;

		while (!escolhido) {
			int numeroLista = geraEvento() % 3;

			switch (numeroLista) {
			case 0:
				if (!listaClienteOficial.isEmpty()) {
					cliente = listaClienteOficial.remove();
					escolhido = true;
				}
				break;
				
			case 1:
				if (!listaClienteSargento.isEmpty()) {
					cliente = listaClienteSargento.remove();
					escolhido = true;
				}
				break;
			case 2:
				if (!listaClientePraca.isEmpty()) {
					cliente = listaClientePraca.remove();
					escolhido = true;
				}
				break;
			default:
				break;
			}
		}
		
		
		

//		EnumPatente patente;
//		int tipoCliente = geraEvento() % 100;
//		int offSizeTempoServico = geraEvento() % 3; // Variando entre 0 e 2
//		int baseTempoServico = 0;
//
//		if (tipoCliente <= 70) {
//			patente = EnumPatente.PRACA;
//			baseTempoServico = 1;
//		} else {
//
//			if (tipoCliente <= 90) {
//				patente = EnumPatente.SARGENTO;
//				baseTempoServico = 2;
//			} else {
//				patente = EnumPatente.OFICIAL;
//				baseTempoServico = 4;
//			}
//		}
//
//		Cliente cliente = new Cliente(patente);
//		cliente.setTempoServico(baseTempoServico + offSizeTempoServico);
		return cliente;
	}
	

}
