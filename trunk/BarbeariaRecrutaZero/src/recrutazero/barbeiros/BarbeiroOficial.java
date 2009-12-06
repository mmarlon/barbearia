package recrutazero.barbeiros;

import java.util.concurrent.Semaphore;

import recrutazero.clientes.EnumPatente;
import recrutazero.forte.Barbearia;


public class BarbeiroOficial extends BarbeiroAbstrato{
	

	public BarbeiroOficial(Barbearia barbearia, Semaphore semaforoLugares,
			Semaphore semaforoClientes) {
		super(barbearia, semaforoLugares, semaforoClientes);
	}

	/**
	 * @see BarbeiroAbstrato#escolhePatente()
	 */
	@Override
	protected EnumPatente escolhePatente(){
	
		EnumPatente patente;
		if(barbearia.possuiPatenteEspera(EnumPatente.OFICIAL)){
			patente = EnumPatente.OFICIAL;
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
		if(barbearia.possuiPatenteEspera(EnumPatente.SARGENTO) ){
			patente = EnumPatente.SARGENTO;
		} else {
			patente = EnumPatente.PRACA;
		}
		
		return patente;
	}

}
