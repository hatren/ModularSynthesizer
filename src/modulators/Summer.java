package modulators;

import java.util.ArrayList;

public class Summer implements Modulator{
	//***Vars***
	private ArrayList<Modulator> waveList;
	private Modulator master;
	
	//***Ctors***
	public Summer() {
		this.waveList = new ArrayList<Modulator>();
		this.master = null;
	}
	
	//***Methods***
	public void add(Modulator wave) {
		waveList.add(wave);
	}
	
	@Override
	public double compute(int sample) {
		// TODO Auto-generated method stub
		double output = 0;
		for(Modulator wave : waveList) {
			output += wave.compute(sample);
		}
		return output/waveList.size();
	}
}
