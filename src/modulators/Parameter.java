package modulators;

public class Parameter implements Modulator{
	// ***Vars***
	private String name;
	private double value;
	private Modulator master;
	
	// ***Ctors***
	public Parameter(String name, double value) {
		setName(name);
		setValue(value);
		setMaster(null);
	}
	
	// ***Methods***
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Modulator getMaster() {
		return this.master;
	}
	
	public void setMaster(Modulator master) {
		this.master = master;
	}
	
	public double getValue() {
		return this.value;
	}
	
	public void setValue(double value) {
		this.value = value;
	}
	
	@Override
	public double compute(int sample) {
		if (master != null){
			return this.value*master.compute(sample);
		}
		else {
			return value;
		}
	}
}
