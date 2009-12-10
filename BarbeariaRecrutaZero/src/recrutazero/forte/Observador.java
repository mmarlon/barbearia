package recrutazero.forte;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import recrutazero.Simulador;


public class Observador implements Runnable{

	private static final int INTERVALO = 5;
	private Barbearia barbearia;
	private List<EstadoBarbearia> estadosBarbearia;
	private boolean fimDoDia;
	
	private Semaphore semaforoRelatorio;
	
	
	public Observador(Barbearia barbearia, Semaphore semaforoRelatorio) {
		this.barbearia = barbearia;
		estadosBarbearia = new ArrayList<EstadoBarbearia>();
		
		this.semaforoRelatorio = semaforoRelatorio;
		
	}
	@Override
	public void run() {
		while (!fimDoDia) {
			coletaDadosRelatorio();
			sleep(INTERVALO * Simulador.VELOCIDADE_SIMULACAO);
		}
	}
	
	private void coletaDadosRelatorio() {
		if(barbearia.isMudouEstado()){
			try {
				semaforoRelatorio.acquire();
				EstadoBarbearia estado = barbearia.getEstado();
				estadosBarbearia.add(estado);
				semaforoRelatorio.release();
				
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
		
		int numEstados = estadosBarbearia.size();
		
		double ocupacaoOficiais = 0.0;
		double ocupacaoSargentos = 0.0;
		double ocupacaoPracas = 0.0;
		double ocupacaoLivre = 0.0;
		
		double tamFilaOficiais = 0.0;
		double tamFilaSargentos = 0.0;
		double tamFilaPracas = 0.0;
		
		int clientesDescartados = 0;
		
		double tempoAtendimentoOficiais = 0.0;
		double tempoAtendimentoSargentos = 0.0;
		double tempoAtendimentoPracas = 0.0;
		
		double tempoEsperaOficiais = 0.0;
		double tempoEsperaSargentos = 0.0;
		double tempoEsperaPracas = 0.0;
		
		double atendimentoOficiais = 0.0;
		double atendimentoSargentos = 0.0;
		double atendimentoPracas = 0.0;
		
		int totalAtendimentoOficiais = Forte.numOficiais;
		int totalAtendimentoSargentos = Forte.numSargentos;
		int totalAtendimentoPracas = Forte.numPracas;
		
		int numEstAtendimentoOficiais = 0;
		int numEstAtendimentoSargentos = 0;
		int numEstAtendimentoPracas = 0;
		
		for (EstadoBarbearia estado : estadosBarbearia) {
			ocupacaoOficiais += estado.getEstadoOcupacaoOficiais();
			ocupacaoSargentos += estado.getEstadoOcupacaoSargentos();
			ocupacaoPracas += estado.getEstadoOcupacaoPracas();
			ocupacaoLivre += estado.getEstadoOcupacaoVazio();
			
			tamFilaOficiais += estado.getNumOficiaisFilaEspera();
			tamFilaSargentos += estado.getNumSargentosFilaEspera();
			tamFilaPracas += estado.getNumPracasFilaEspera();
			
			clientesDescartados += estado.getNumDescartados();
			
			if (estado.getTempoAtendimentoOficiais() > 0) {
				tempoAtendimentoOficiais += estado.getTempoAtendimentoOficiais();
				tempoEsperaOficiais += estado.getTempoEsperaOficiais();
				atendimentoOficiais += estado.getNumOficiaisAtendidos();
				numEstAtendimentoOficiais++;
			}
			
			if (estado.getTempoAtendimentoSargentos() > 0) {
				tempoAtendimentoSargentos += estado.getTempoAtendimentoSargentos();
				tempoEsperaSargentos += estado.getTempoEsperaSargentos();
				atendimentoSargentos += estado.getNumSargentosAtendidos();
				numEstAtendimentoSargentos++;
			}
			
			if (estado.getTempoAtendimentoPracas() > 0) {
				tempoAtendimentoPracas += estado.getTempoAtendimentoPracas();
				tempoEsperaPracas += estado.getTempoEsperaPracas();
				atendimentoPracas += estado.getNumPracasAtendidos();
				numEstAtendimentoPracas++;
			}
		}
		
		
		StringBuffer relatorio = new StringBuffer();

		relatorio.append("** RELATORIO **\n\n");
		
		relatorio.append("* Estado de Ocupacao das cadeiras:\n");
		relatorio.append("Oficiais: " + String.format("%.2f", (ocupacaoOficiais / numEstados)) + "%\n");
		relatorio.append("Sargentos: " + String.format("%.2f", (ocupacaoSargentos / numEstados)) + "%\n");
		relatorio.append("Pracas: " + String.format("%.2f", (ocupacaoPracas / numEstados)) + "%\n");
		relatorio.append("Livres: " + String.format("%.2f", (ocupacaoLivre / numEstados)) + "%\n\n");
		
		relatorio.append("* Comprimento medio das filas por categoria:\n");
		relatorio.append("Oficiais: " + String.format("%.2f", (tamFilaOficiais / numEstados)) + "\n");
		relatorio.append("Sargentos: " + String.format("%.2f", (tamFilaSargentos / numEstados)) + "\n");
		relatorio.append("Pracas: " + String.format("%.2f", (tamFilaPracas / numEstados)) + "\n\n");
		
		relatorio.append("* Numero de clientes descartados: " + clientesDescartados + "\n\n");
		
		relatorio.append("* Tempo medio de atendimento por categoria:\n");
		relatorio.append("Oficiais: " + String.format("%.2f", (tempoAtendimentoOficiais / numEstAtendimentoOficiais)) + " seg \n");
		relatorio.append("Sargentos: " + String.format("%.2f", (tempoAtendimentoSargentos / numEstAtendimentoSargentos)) + " seg \n");
		relatorio.append("Pracas: " + String.format("%.2f", (tempoAtendimentoPracas/ numEstAtendimentoPracas)) + " seg \n\n");
		
		relatorio.append("* Tempo medio de espera por categoria:\n");
		relatorio.append("Oficiais: " + String.format("%.2f", (tempoEsperaOficiais / numEstAtendimentoOficiais)) + " seg \n");
		relatorio.append("Sargentos: " + String.format("%.2f", (tempoEsperaSargentos / numEstAtendimentoSargentos)) + " seg \n");
		relatorio.append("Pracas: " + String.format("%.2f", (tempoEsperaPracas/ numEstAtendimentoPracas)) + " seg \n\n");
		
		relatorio.append("* Numero de atendimentos por categoria:\n");
		relatorio.append("Oficiais: " + String.format("%.2f", (atendimentoOficiais / numEstados)) + "\n");
		relatorio.append("Sargentos: " + String.format("%.2f", (atendimentoSargentos / numEstados)) + "\n");
		relatorio.append("Pracas: " + String.format("%.2f", (atendimentoPracas/ numEstados)) + "\n\n");
		
		relatorio.append("* Numero total de clientes por categoria:\n");
		relatorio.append("Oficiais: " + totalAtendimentoOficiais + "\n");
		relatorio.append("Sargentos: " + totalAtendimentoSargentos + "\n");
		relatorio.append("Pracas: " + totalAtendimentoPracas + "\n");
		relatorio.append("Pausas: " + ControleAcesso.getTotalPausa() + "\n\n");
		
		
		
		System.out.println(relatorio.toString());
		
		
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
