package recrutazero.clientes;

public enum EnumPatente {
	OFICIAL (1, "Oficial"),
	SARGENTO(2, "Sargento"),
	PRACA   (3, "Praça "),
	PAUSA   (0, "Pausa ");

	private int codigo;
	private String patente;
	
	private EnumPatente(int codigo, String patente) {
		this.codigo = codigo;
		this.patente = patente;
	}
	
	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getPatente() {
		return patente;
	}

	public void setPatente(String patente) {
		this.patente = patente;
	}
	
	@Override
	public String toString() {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(this.codigo);
		stringBuffer.append(" ");
		stringBuffer.append(this.patente);
		return stringBuffer.toString();
	}
		
}

