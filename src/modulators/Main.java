package modulators;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main {
	public static void main(String[] args) {
		// Audio
		Oscillator wave1 = new Oscillator();
		wave1.setWaveform(Waveform.TRI);
		wave1.getDepth().setValue(1);
		wave1.getPitch().setValue(1000);
		
		Oscillator wave2 = new Oscillator();
		wave2.setWaveform(Waveform.TRI);
		wave2.getDepth().setValue(2);
		wave2.getPitch().setValue(2);
		
		Envelope env1 = new Envelope();
		Modulator.assign(wave2, env1.getGate());
		Modulator.assign(env1, wave1.getDepth());		
		
		SampleReader master = new SampleReader();
		master.setInput(wave1);

		
		

		// wave1.getDepth().assign(env1);
		
		KeyTracker key1 = new KeyTracker();
		key1.setGate(env1);
		key1.setPitch(wave1.getPitch());
		key1.setVelocity(env1.getVelocity());
		
		Sequencer seq = new Sequencer(key1);
		seq.start();
		
		// MIDI
//		MidiDevice.Info[] infoList = MidiSystem.getMidiDeviceInfo();
//		for(MidiDevice.Info device: infoList) {
//			System.out.println(device.getName());
//		}
//		
//		KeyTracker track1 = new KeyTracker();
//		try {
//			MidiDevice minilogue = MidiSystem.getMidiDevice(infoList[5]);
//			track1.setMaster(minilogue);
//			track1.setGate(env1);
//			track1.setPitch(wave1.getRate());
//			minilogue.getTransmitter().setReceiver(track1);
//			minilogue.open();
//		} catch (MidiUnavailableException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		// env1.turnOn();
		
		// UI
		JFrame frame = new JFrame();
		JPanel main = new JPanel();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Envilloscope escope = new Envilloscope(env1);
		
		frame.getContentPane().add(main, BorderLayout.CENTER);
		main.add(escope);
		
	
		frame.setSize(new Dimension(300, 400));
		frame.pack();
		frame.setVisible(true);
		
		master.run();
		
	}
}
