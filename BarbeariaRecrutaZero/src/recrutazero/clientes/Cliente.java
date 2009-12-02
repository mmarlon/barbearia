package recrutazero.clientes;

public class Cliente {
	private int tempoServico;
	private EnumPatente patente;

	public Cliente(EnumPatente patente) {
		this.patente = patente;
	}

	public int getTempoServico() {
		return tempoServico;
	}

	public void setTempoServico(int tempoServico) {
		this.tempoServico = tempoServico;
	}

	public void setPatente(EnumPatente patente) {
		this.patente = patente;
	}

	public EnumPatente getPatente() {
		return patente;
	}

	@Override
	public String toString() {
		return patente.getPatente() + "\t" + tempoServico;
	}

}
