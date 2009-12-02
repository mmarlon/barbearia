package recrutazero.forte;

import java.util.ArrayList;
import java.util.List;

public class EstadoBarbearia {
	private int numOficiaisFilaEspera;
	private int numSargentosFilaEspera;
	private int numPracasFilaEspera;
	
	private int numDescartados;
	
	private int totalClientes;

	private List<Integer> tempoServicoOficiais;
	private List<Integer> tempoServicoSargento;
	private List<Integer> tempoServicoPracas;
	
	public EstadoBarbearia() {
		tempoServicoOficiais = new ArrayList<Integer>();
		tempoServicoSargento = new ArrayList<Integer>();
		tempoServicoPracas = new ArrayList<Integer>();
	}

	public int getNumOficiaisFilaEspera() {
		return numOficiaisFilaEspera;
	}

	public void setNumOficiaisFilaEspera(int numOficiaisFilaEspera) {
		this.numOficiaisFilaEspera = numOficiaisFilaEspera;
	}

	public int getNumSargentosFilaEspera() {
		return numSargentosFilaEspera;
	}

	public void setNumSargentosFilaEspera(int numSargentosFilaEspera) {
		this.numSargentosFilaEspera = numSargentosFilaEspera;
	}

	public int getNumPracasFilaEspera() {
		return numPracasFilaEspera;
	}

	public void setNumPracasFilaEspera(int numPracasFilaEspera) {
		this.numPracasFilaEspera = numPracasFilaEspera;
	}
	
	public int getNumDescartados() {
		return numDescartados;
	}

	public void setNumDescartados(int numDescartados) {
		this.numDescartados = numDescartados;
	}

	public int getTotalClientes() {
		return totalClientes;
	}

	public void setTotalClientes(int totalClientes) {
		this.totalClientes = totalClientes;
	}

	public List<Integer> getTempoServicoOficiais() {
		return tempoServicoOficiais;
	}

	public void setTempoServicoOficiais(List<Integer> tempoServicoOficiais) {
		this.tempoServicoOficiais = tempoServicoOficiais;
	}

	public List<Integer> getTempoServicoSargento() {
		return tempoServicoSargento;
	}

	public void setTempoServicoSargento(List<Integer> tempoServicoSargento) {
		this.tempoServicoSargento = tempoServicoSargento;
	}

	public List<Integer> getTempoServicoPracas() {
		return tempoServicoPracas;
	}

	public void setTempoServicoPracas(List<Integer> tempoServicoPracas) {
		this.tempoServicoPracas = tempoServicoPracas;
	}

}
