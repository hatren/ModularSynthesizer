package modulators;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class Envelope implements Modulator {
	// ***Vars***
	private Parameter attack;
	private Parameter decay;
	private Parameter sustain;
	private Parameter release;
	
	private Parameter velocity;
	private Parameter gate;
	private Stage stage;
	
	private int counter;
	
	private double value;
	
	// ***Ctors***
	public Envelope() {
		this.attack = new Parameter("Attack", 25);
		this.decay = new Parameter("Decay", 20);
		this.sustain = new Parameter("Sustain", .5);
		this.release = new Parameter("Release", 3000);
		
		this.velocity = new Parameter("Velocity", 1);
		this.gate = new Parameter("Gate", 1);
		this.value = 0;
		this.counter = 0;
	}
	
	// ***Methods***
	public Parameter getVelocity() {
		return this.velocity;
	}
	
	public Parameter getGate() {
		return this.gate;
	}
	
	public double getValue() {
		return this.value;
	}
	
	private void setStage(Stage stage) {
		this.stage = stage;
	}
	
	private void trigger(boolean trigger) {
		if(trigger) {
			counter = (int) (44.1*value*attack.getValue());
		}else {
			counter = (int) (44.1*(1 - value)*release.getValue());
		}
	}

	@Override
	public double compute(int sample) {	
		double ms;
		double previous = gate.getValue();
		double sensor = gate.compute(sample);
		
		boolean on = ((previous < .5) && (.5 < sensor)) || ((.5 < previous)&&(sensor < .5));
		
		// If the difference between the previous gate and new gate crosses .5, trigger the gate
		
		trigger(on);
		
		// Check Stage
		if(on) {
			ms = counter/44.1;
			
			if(ms < attack.getValue()) {
				setStage(Stage.ATTACK);
			}else if(ms < attack.getValue()+decay.getValue()) {
				setStage(Stage.DECAY);
			}else {
				setStage(Stage.SUSTAIN);
			}
		}else {
			ms = counter/44.1;
			
			if(ms<release.getValue()) {
				setStage(Stage.RELEASE);
			}else {
				setStage(Stage.OFF);
			}
		}
		
		// Compute Stage
		switch(this.stage) {
			case ATTACK:
				this.value = ms/attack.compute(sample);
				break;
			case DECAY:
				this.value = 1 + (sustain.compute(sample) - 1)*(ms - attack.compute(sample))/decay.compute(sample);
				break;
			case SUSTAIN:
				this.value = sustain.compute(sample);
				break;
			case RELEASE:
				this.value = 1 - (ms/release.compute(sample));
				break;
			case OFF:
				this.value = 0;
				break;
		}
		
		counter++;
		value *= velocity.compute(sample);
		return value;
	}
	
	public static void main(String[] args) {
		Envelope env = new Envelope();
		
		Oscillator osc1 = new Oscillator();
		Modulator.assign(osc1, env.getGate());
		
		Timer time = new Timer(100, new ActionListener() {
			int runner = 0;
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				runner++;
				System.out.println(runner);
				System.out.println(env.compute(runner));
			}
		});
		
		time.start(); 
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		time.stop();
	}
}



