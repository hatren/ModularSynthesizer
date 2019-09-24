package modulators;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.sound.midi.*;
import javax.swing.Timer;


public class Sequencer implements Transmitter{
	
	private Receiver slave;
	private Parameter clock;
	private Parameter subdivision;
	private Parameter length;
	
	private Timer trigger;
	private ArrayList<MidiMessage> sequence;
	
	private int counter;
	
	public Sequencer(Receiver slave) {
		this.clock = new Parameter("Clock", 120);
		this.subdivision = new Parameter("Subdivision", 4);
		this.length = new Parameter("Length", 16);
		this.slave = slave;
		this.sequence = new ArrayList<MidiMessage>();
		this.counter = 0;
		
		read();
		trigger = new Timer((int) (100*clock.getValue()/(60*subdivision.getValue())), new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						// TODO Auto-generated method stub
						slave.send(next(), -1);
					}
		});
	}
	
	private void read() {
		for(int i = 0; i<this.length.getValue(); i++) {
			ShortMessage sm;
			try {
				if(i%2 == 0) {
					sm = new ShortMessage(ShortMessage.NOTE_ON, 40 + 2*i, 127);
				}else {
					sm = new ShortMessage(ShortMessage.NOTE_OFF,40 + 2*i, 127);
				}
				sequence.add(i, sm);;
			} catch (InvalidMidiDataException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	public MidiMessage next() {
		return sequence.get((int) ((counter++) % length.getValue()));
	}
	
	public void start() {
		trigger.start();
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		slave.close();
	}

	@Override
	public Receiver getReceiver() {
		// TODO Auto-generated method stub
		return slave;
	}

	@Override
	public void setReceiver(Receiver arg0) {
		// TODO Auto-generated method stub
		this.slave = arg0;
	}	
}
