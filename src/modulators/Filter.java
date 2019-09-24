package modulators;

public class Filter implements Modulator{
	private double previous;
	private double momentum;
	private Parameter cutoff;
	private Parameter resonance;
	
	private Modulator input;
	
	public Filter() {
		this.cutoff = new Parameter("Cutoff", .0250);
		this.resonance = new Parameter("Resonance", .015);
		this.momentum = 0;
		this.previous = 0;
	}
	
	public void setInput(Modulator input) {
		this.input = input;
	}
	
	@Override
	public double compute(int sample) {
		double difference = input.compute(sample) - previous;
		this.momentum += difference*cutoff.getValue();
		this.previous = momentum + difference*resonance.getValue();
		return previous;
	}
	
}
