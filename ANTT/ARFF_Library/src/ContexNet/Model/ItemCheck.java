package ContexNet.Model;

import java.io.Serializable;

public class ItemCheck implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String Name;
	private boolean problem;
	private Severity severity;
	
	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public boolean isProblem() {
		return problem;
	}

	public void setProblem(boolean problem) {
		this.problem = problem;
	}

	public Severity getSeverity() {
		return severity;
	}

	public void setSeverity(Severity severity) {
		this.severity = severity;
	}

	public enum Severity {None, High, Medium, Low }

}
