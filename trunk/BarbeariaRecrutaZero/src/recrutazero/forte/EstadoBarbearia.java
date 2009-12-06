package recrutazero.forte;

import java.util.List;

import recrutazero.Simulador;
import recrutazero.clientes.Cliente;

public class EstadoBarbearia {
	
	private int numeroLugares;
	
	private int numOficiaisFilaEspera;
	private int numSargentosFilaEspera;
	private int numPracasFilaEspera;
	
	private int numVagaFilaEspera;
	private int numDescartados;
	
	private double tempoAtendimentoOficiais;
	private double tempoAtendimentoSargentos;
	private double tempoAtendimentoPracas;
	
	private double tempoEsperaOficiais;
	private double tempoEsperaSargentos;
	private double tempoEsperaPracas;
	
	private int numOficiaisAtendidos;
	private int numSargentosAtendidos;
	private int numPracasAtendidos;
	
	public EstadoBarbearia() {
		this.numeroLugares = Simulador.NUM_LUGARES;
	}
	
	public void analisarFilasEspera(List<Cliente> filaOficiais, List<Cliente> filaSargentos, List<Cliente> filaPracas) {
		this.numOficiaisFilaEspera = filaOficiais.size();
		this.numSargentosFilaEspera = filaSargentos.size();
		this.numPracasFilaEspera = filaPracas.size();
		
		this.numVagaFilaEspera = numeroLugares - numOficiaisFilaEspera - numSargentosFilaEspera - numPracasFilaEspera;
	}
	
	public void analisarAtendimentos(List<Cliente> oficiais, List<Cliente> sargentos, List<Cliente> pracas) {
		this.numOficiaisAtendidos = oficiais.size();
		for (Cliente oficial : oficiais) {
			this.tempoAtendimentoOficiais += oficial.getTempoServico();
			this.tempoEsperaOficiais += oficial.getTempoEsperaFila();
		}
		if (this.numOficiaisAtendidos > 0) {
			this.tempoAtendimentoOficiais /= numOficiaisAtendidos;
			this.tempoEsperaOficiais /= numOficiaisAtendidos;
		}
		
		
		this.numSargentosAtendidos = sargentos.size();
		for (Cliente sargento : sargentos) {
			this.tempoAtendimentoSargentos += sargento.getTempoServico();
			this.tempoEsperaSargentos += sargento.getTempoEsperaFila();
		}
		if (this.numSargentosAtendidos > 0) {
			this.tempoAtendimentoSargentos /= numSargentosAtendidos;
			this.tempoEsperaSargentos /= numSargentosAtendidos;
		}
		
		
		this.numPracasAtendidos = pracas.size();
		for (Cliente praca : pracas) {
			this.tempoAtendimentoPracas += praca.getTempoServico();
			this.tempoEsperaPracas += praca.getTempoEsperaFila();
		}
		if (this.numPracasAtendidos > 0) {
			this.tempoAtendimentoPracas /= numPracasAtendidos;
			this.tempoEsperaPracas /= numPracasAtendidos;
		}
		
	}
	
	public double getEstadoOcupacaoOficiais() {
		return (this.numOficiaisFilaEspera / (double)numeroLugares) * 100;
	}
	
	public double getEstadoOcupacaoSargentos() {
		return (this.numSargentosFilaEspera / (double)numeroLugares) * 100;
	}
	
	public double getEstadoOcupacaoPracas() {
		return (this.numPracasFilaEspera / (double)numeroLugares) * 100;
	}
	
	public double getEstadoOcupacaoVazio() {
		return (this.numVagaFilaEspera / (double)numeroLugares) * 100;
	}

	public int getNumOficiaisFilaEspera() {
		return numOficiaisFilaEspera;
	}

	public int getNumSargentosFilaEspera() {
		return numSargentosFilaEspera;
	}

	public int getNumPracasFilaEspera() {
		return numPracasFilaEspera;
	}

	public int getNumDescartados() {
		return numDescartados;
	}

	public void setNumDescartados(int numDescartados) {
		this.numDescartados = numDescartados;
	}

	public double getTempoAtendimentoOficiais() {
		return tempoAtendimentoOficiais;
	}

	public double getTempoAtendimentoSargentos() {
		return tempoAtendimentoSargentos;
	}

	public double getTempoAtendimentoPracas() {
		return tempoAtendimentoPracas;
	}

	public double getTempoEsperaOficiais() {
		return tempoEsperaOficiais;
	}

	public double getTempoEsperaSargentos() {
		return tempoEsperaSargentos;
	}

	public double getTempoEsperaPracas() {
		return tempoEsperaPracas;
	}

	public int getNumOficiaisAtendidos() {
		return numOficiaisAtendidos;
	}

	public int getNumSargentosAtendidos() {
		return numSargentosAtendidos;
	}

	public int getNumPracasAtendidos() {
		return numPracasAtendidos;
	}
	
}
