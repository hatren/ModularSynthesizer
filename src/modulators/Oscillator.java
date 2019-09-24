package modulators;

public class Oscillator implements Modulator{
	// ***Vars***
	private Parameter depth;
	private Parameter pitch;
	private Waveform waveform;
	
	// ***Ctors***
	public Oscillator() {
		this.depth = new Parameter("Depth", 1);
		this.pitch = new Parameter("Pitch", 440);
		this.waveform = Waveform.SIN; 
	}
	
	// ***Methods***
	public Parameter getDepth() {
		return this.depth;
	}
	
	public Parameter getPitch() {
		return this.pitch;
	}
	
	public Waveform getWaveform() {
		return this.waveform;
	}
	
	public void setWaveform(Waveform shape) {
		this.waveform = shape;
	}
	
	@Override
	public double compute(int sample) {
		switch (waveform) {
		default:
			return 0;
		case SIN:
			return depth.compute(sample)*Math.sin(pitch.compute(sample)*sample*2*Math.PI/SampleReader.SAMPLE_RATE);
		case SAW:
			return depth.compute(sample)*(SampleReader.SAMPLE_RATE/2 + pitch.compute(sample)*sample % SampleReader.SAMPLE_RATE);
		case TRI:
			return 2*depth.compute(sample)*Math.asin(Math.sin(pitch.compute(sample)*sample*2*Math.PI/SampleReader.SAMPLE_RATE))/Math.PI;
		case SQU:
			return depth.compute(sample)*Math.round(Math.sin(pitch.compute(sample)*sample*2*Math.PI/SampleReader.SAMPLE_RATE));
		}
	}
}
