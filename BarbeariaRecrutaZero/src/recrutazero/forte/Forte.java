package recrutazero.forte;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import recrutazero.clientes.Cliente;
import recrutazero.clientes.EnumPatente;



public class Forte {

	protected GeradorEventos geradorEventos;
	protected Iterator<Cliente> iterator;
	private static final int MAX_CLIENTES = 1000;
	
	public Forte(GeradorEventos geradorEventos) {
		this.geradorEventos= geradorEventos;

		iterator = geraClistaClientes().iterator();
	}


	protected List<Cliente> geraClistaClientes() {
		List<Cliente> clientes = new ArrayList<Cliente>();
		
		for(int i = 0; i < MAX_CLIENTES; i++){
			Cliente cliente; 
			
			if(geradorEventos.gera()%11==0){
				cliente = new Cliente(EnumPatente.PAUSA);
			}else {
				cliente = escolheTipoCliente();
			}
			
			clientes.add(cliente);
		}
		
		return clientes;
	}

	/**
	 * @see Forte#geraEvento()
	 */
	public int geraEvento() {
		return geradorEventos.gera();
	}

	/**
	 * @see Forte#getCliente()
	 * Há 50% de chances de chegar alguém no forte. Se chegou, verifique o tipo! Se não, dá uma pausa.
	 * Passando do número máximo de clientes gerados, ele passa a entregar PAUSAs. Essas pausas farão o sistema parar. 
	 */
	public Cliente getCliente() {
		Cliente cliente = new Cliente(EnumPatente.PAUSA);
		if(iterator.hasNext()){
			cliente = iterator.next();
		}
		return cliente;
	}

	private Cliente escolheTipoCliente() {
		
		EnumPatente patente;
		int tipoCliente  = geraEvento()%100;
		int offSizeTempoServico = geraEvento()%3; // Variando entre 0 e 2
		int baseTempoServico = 0;
		
		if(tipoCliente <= 70){
			patente = EnumPatente.PRACA;
			baseTempoServico = 1; 				
		}
		else {
		
			if(tipoCliente <=90){
				patente = EnumPatente.SARGENTO;
				baseTempoServico = 2;
			}
			else {
				patente = EnumPatente.OFICIAL;
				baseTempoServico = 4;
			}
		}
		
		Cliente cliente = new Cliente(patente);
		cliente.setTempoServico(baseTempoServico+offSizeTempoServico);
		return cliente;
	}



		

}
