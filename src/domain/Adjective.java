package domain;

public class Adjective {

	private int codAdj;
	private String adjective;
	private int value;

	public Adjective(String adj) {
		this.adjective = adj;
	}

	public Adjective(String adj, int value) {
		this.adjective = adj;
		this.value = value;
	}

	public int getCodAdj() {
		return codAdj;
	}

	public void setCodAdj(int codAdj) {
		this.codAdj = codAdj;
	}

	public String getAdjective() {
		return adjective;
	}

	public void setAdjective(String adjective) {
		this.adjective = adjective;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	@Override
	public boolean equals(Object adj) {
		if (adj instanceof Adjective) {
			return ((Adjective) adj).getAdjective().equals(this.adjective);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return this.getAdjective().hashCode();
	}

}
