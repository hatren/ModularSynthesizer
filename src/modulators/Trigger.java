package modulators;

public class Trigger {
	// ***Vars***
	private boolean gate;
	private Parameter sensitivity;
	private Modulator master;
	
	public boolean getGate() {
		return this.gate;
	}
	
	public void setGate(boolean gate) {
		this.gate = gate;
	}
	
	public Parameter getSensitivity() {
		return this.sensitivity;
	}
	
	public Modulator getMaster() {
		return this.master;
	}
	
	public void setMaster(Modulator master) {
		this.master = master;
	}
	
}
