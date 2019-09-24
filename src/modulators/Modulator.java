package modulators;

public interface Modulator {
	double compute(int sample);
	
	static void assign(Modulator output, Parameter input) {
		input.setMaster(output);
	}
}
