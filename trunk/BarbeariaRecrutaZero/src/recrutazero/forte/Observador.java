package recrutazero.forte;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import recrutazero.Simulador;


public class Observador implements Runnable{

	private static final int INTERVALO = 3;
	private Barbearia barbearia;
	private List<EstadoBarbearia> estadosBarbearia;
	private boolean fimDoDia;
	
	private Semaphore semaforoFazendoRelatorio;
	
	
	public Observador(Barbearia barbearia, Semaphore semaforoRelatorio) {
		this.barbearia = barbearia;
		estadosBarbearia = new ArrayList<EstadoBarbearia>();
		
		this.semaforoFazendoRelatorio = semaforoRelatorio;
		
	}
	@Override
	public void run() {
		while (!fimDoDia) {
			coletaDadosRelatorio();
			sleep(INTERVALO * Simulador.MULT_TEMPO);
		}
	}
	
	private void coletaDadosRelatorio() {
		if(barbearia.isMudouEstado()){
			try {
				semaforoFazendoRelatorio.acquire();
				EstadoBarbearia estado = barbearia.getEstado();
				estadosBarbearia.add(estado);
				semaforoFazendoRelatorio.release();
				
			} catch (InterruptedException e) {
				System.out.println("Não foi possível pegar os dados para o relatório.");
				e.printStackTrace();
			}
		
		}
	}

	public void fimDoDia() {
		fimDoDia = true;
		mostraRelatorio();
		
		try {
			//System.out.println("Terminando o "+this.getClass().getSimpleName());
			Thread.currentThread().interrupt();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	private void mostraRelatorio() {
		
		System.out.println("\nRelatório:");
		double numOficiais = 0;
		double numSargentos = 0;
		double numPracas = 0;
		double numCadeirasLivres = 0;
		
		double tempoEsperaOficiais = 0;
		double tempoEsperaSargentos = 0;
		double tempoEsperaPracas = 0;
		
		for (EstadoBarbearia estadoBarbearia : estadosBarbearia) {
			numOficiais+=estadoBarbearia.getNumOficiaisFilaEspera();
			numPracas+=estadoBarbearia.getNumPracasFilaEspera();
			numSargentos+=estadoBarbearia.getNumSargentosFilaEspera();
			numCadeirasLivres+=20-(estadoBarbearia.getNumOficiaisFilaEspera() + estadoBarbearia.getNumPracasFilaEspera() + estadoBarbearia.getNumSargentosFilaEspera());
			
			for (Integer tempo : estadoBarbearia.getTempoServicoSargento()) {
				tempoEsperaSargentos+=tempo;	
			}
			
			for (Integer tempo : estadoBarbearia.getTempoServicoOficiais()) {
				tempoEsperaOficiais+=tempo;	
			}
			
			for (Integer tempo : estadoBarbearia.getTempoServicoPracas()) {
				tempoEsperaPracas+=tempo;	
			}
		}
		
		double totalClientes = numOficiais + numPracas + numSargentos;
		System.out.println("Estado de ocupação das cadeiras:");
		System.out.println("Oficiais \t"+(numOficiais/(totalClientes))*100);
		System.out.println("Sargentos\t"+(numSargentos/(totalClientes))*100);
		System.out.println("Praças   \t"+(numPracas/(totalClientes))*100);
		System.out.println("Livres   \t"+(numCadeirasLivres/(totalClientes+numCadeirasLivres))*100);
		
		System.out.println("Comprimento médio das filas:");
		System.out.println("Oficiais \t"+(numOficiais/estadosBarbearia.size())*100);
		System.out.println("Sargentos\t"+(numSargentos/estadosBarbearia.size())*100);
		System.out.println("Praças   \t"+(numPracas/estadosBarbearia.size())*100);
		
		System.out.println("Tempo médio de atendimento:");
		
		System.out.println("Oficiais \t"+(tempoEsperaOficiais/numOficiais));
		System.out.println("Sargentos\t"+(tempoEsperaSargentos/numSargentos));
		System.out.println("Praças   \t"+(tempoEsperaPracas/numPracas));
		
		System.out.println("Número de atendimentos:");
		System.out.println("Oficiais \t"+(numOficiais));
		System.out.println("Sargentos\t"+(numSargentos));
		System.out.println("Praças   \t"+(numPracas));
		
		System.out.println("Número de atendimentos v2:");
		System.out.println("Oficiais \t"+(barbearia).getNumOficiais());
		System.out.println("Sargentos\t"+(barbearia).getNumSagentos());
		System.out.println("Praças   \t"+(barbearia).getNumPracas());
		
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
			Thread.sleep(tempoEspera * Simulador.MULT_TEMPO);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
