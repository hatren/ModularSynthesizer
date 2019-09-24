package modulators;

import java.util.List;

import javax.sound.midi.*;

public class KeyTracker implements Receiver{
	// ***Vars***
	private static final int REFERENCE_NOTE_NUMBER = 69;
	private static final int REFERENCE_NOTE_FREQ = 440;
	private static final int NOTES_PER_OCTAVE = 12;
	
	private MidiDevice master;
	private MidiMessage message;
	
	private Parameter pitch;
	private Parameter velocity;
	private Envelope gate;
	
	// ***Methods***
	public MidiDevice getMaster() {
		return this.master;
	}
	
	public void setMaster(MidiDevice master) {
		this.master = master;
	}
	
	public Parameter getPitch() {
		return this.pitch;
	}
	
	public void setPitch(Parameter pitch) {
		this.pitch = pitch;
	}
	
	public Parameter getVelocity() {
		return this.velocity;
	}
	
	public void setVelocity(Parameter velocity) {
		this.velocity = velocity;
	}
	
	public void setGate(Envelope gate) {
		this.gate = gate;
	}

	public float midiNoteNumberToFrequencyConverter(int mnn) {
		float soundOffset = (mnn - REFERENCE_NOTE_NUMBER) / (float) NOTES_PER_OCTAVE;
		return (float) (REFERENCE_NOTE_FREQ * Math.pow(2.0, soundOffset));
	}

	
	@Override
	public void close() {
		// TODO Auto-generated method stub
		master.close();
	}

	@Override
	public void send(MidiMessage arg0, long arg1) {
		if(message != arg0) {
			// TODO Auto-generated method stub
			ShortMessage sm = (ShortMessage) arg0;
			// Pitch
			// pitch.setValue(REFERENCE_NOTE_FREQ * Math.pow(2.0, (message.getData1() - REFERENCE_NOTE_NUMBER) / (float) NOTES_PER_OCTAVE));
			
			
			// Gate
			if(sm.getCommand() == ShortMessage.NOTE_ON) {
				System.out.println("SM: " + sm.toString());
				pitch.setValue(this.midiNoteNumberToFrequencyConverter(sm.getData1()));
				velocity.setValue(sm.getData2()/127);
			}else if(sm.getCommand() == ShortMessage.NOTE_OFF) {
			}
			this.message = arg0;
		}
	}

}
