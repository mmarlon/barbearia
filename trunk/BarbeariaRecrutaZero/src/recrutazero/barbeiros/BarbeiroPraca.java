package recrutazero.barbeiros;

import java.util.concurrent.Semaphore;

import recrutazero.clientes.EnumPatente;
import recrutazero.forte.Barbearia;


public class BarbeiroPraca extends BarbeiroAbstrato{
	

	public BarbeiroPraca(Barbearia barbearia, Semaphore semaforoLugares,
			Semaphore semaforoClientes, Integer teste) {
		super(barbearia, semaforoLugares, semaforoClientes);
	}

	/**
	 * @see BarbeiroAbstrato#escolhePatente()
	 */
	@Override
	protected EnumPatente escolhePatente(){

		EnumPatente patente;
		if(barbearia.possuiPatenteEspera(EnumPatente.PRACA)){
			patente = EnumPatente.PRACA;
		}else{
			patente = aplicaPrioridade();
		}
	
		return patente;
	}

	/**
	 * @see BarbeiroAbstrato#aplicaPrioridade()
	 */
	@Override
	protected EnumPatente aplicaPrioridade() {
		
		EnumPatente patente;
		if(barbearia.possuiPatenteEspera(EnumPatente.OFICIAL) ){
			patente = EnumPatente.OFICIAL;
		}else{
			patente = EnumPatente.SARGENTO;
		}
		
		return patente;
	}

}
