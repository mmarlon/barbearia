package recrutazero.clientes;

public class Cliente {
	private int tempoServico;
	private EnumPatente patente;
	protected int id;
	private long momentoEntrada;
	private long momentoAtendimento;
	
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
	
	public long getMomentoEntrada() {
		return momentoEntrada;
	}

	public void setMomentoEntrada(long momentoEntrada) {
		this.momentoEntrada = momentoEntrada;
	}

	public long getMomentoAtendimento() {
		return momentoAtendimento;
	}

	public void setMomentoAtendimento(long momentoAtendimento) {
		this.momentoAtendimento = momentoAtendimento;
	}
	
	public int getTempoEsperaFila() {
		if (this.momentoAtendimento > 0 && this.momentoEntrada > 0) {
			return (int) (this.momentoAtendimento - this.momentoEntrada);
		}
		
		return Integer.MAX_VALUE;
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
