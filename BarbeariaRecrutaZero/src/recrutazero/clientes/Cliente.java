package recrutazero.clientes;

public class Cliente {
	private int tempoServico;
	private EnumPatente patente;
	protected int id;
	
	public Cliente(EnumPatente patente) {
		this.patente = patente;
		this.id = gerarId();
	}
	
	public Cliente(EnumPatente patente, int tempoServico) {
		this.patente = patente;
		this.tempoServico = tempoServico;
		this.id = gerarId();
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

	public Integer getId() {
		return id;
	}
	
	private int gerarId() {
		Long tempoCorrente = System.currentTimeMillis();
		
		String stringTempo = Long.toString(tempoCorrente);
		
		this.id = Integer.parseInt(stringTempo.substring(4, 13));
		
		return this.id;
	}
	
	@Override
	public boolean equals(Object c) {
		if (c instanceof Cliente) {
			Cliente cliente = (Cliente) c;
			return this.getId().equals(cliente.getId());
		}
		else {
			return false;
		}
	}
	
	@Override
	public int hashCode() {
		return id;
	}
	
	@Override
	public String toString() {
		return this.patente + "\t" + this.tempoServico;
	}
	
}
