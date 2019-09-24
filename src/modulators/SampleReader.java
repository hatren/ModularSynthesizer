package modulators;

import javax.sound.sampled.*;


public class SampleReader extends Thread{
	//***Vars***
	public static final int BUFFER_SIZE = 256;
	public static final int SAMPLES_PER_BUFFER = BUFFER_SIZE / 2;
	public static final int SAMPLE_RATE = 44100;
	public static final int SAMPLE_SIZE = 16;
	
	private static final int CHANNELS = 1;
	private static final boolean SIGNED = true;
	private static final boolean BIG_ENDIAN = true;

	private SourceDataLine auline;
	private DataLine.Info info;
	private AudioFormat format;
	private Modulator input;
	
	// ***Ctors***
	public SampleReader() {
		format = new AudioFormat(SAMPLE_RATE, SAMPLE_SIZE, CHANNELS, SIGNED, BIG_ENDIAN);
		info = new DataLine.Info(SourceDataLine.class, format);

		try {
			auline = (SourceDataLine) AudioSystem.getLine(info);
			auline.open(format);
			auline.start();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}
	
	// ***Methods***
	public Modulator getInput() {
		return this.input;
	}
	
	public void setInput(Modulator input) {
		this.input = input;
	}
	
	public void run() {	
		int samplesRead = 0;
		int runner = 0;
		byte[] buffer = new byte[BUFFER_SIZE];
		
		while(true) {
			short ss = (short) (input.compute(runner)*Short.MAX_VALUE);
			buffer[samplesRead++] = (byte) (ss >> 8);
			buffer[samplesRead++] = (byte) (ss & 0xFF);
			
			if (samplesRead >= BUFFER_SIZE) {
				auline.write(buffer, 0, BUFFER_SIZE);
				samplesRead = 0;
			}
		}
	}
}
