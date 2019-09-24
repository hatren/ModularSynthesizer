package modulators;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;

// TODO: Panel 1: Measures the voltage (0 to 1)
// TODO: Panel 2: Show graph of ADSR

public class Envilloscope extends JPanel{
	private Envelope master;
	private JProgressBar volume;
	
	public Envilloscope(Envelope master) {
		this.master = master;
		this.volume = new JProgressBar();
		volume.setMinimum(0);
		volume.setMaximum(100);
		volume.setOrientation(JProgressBar.VERTICAL);
		this.add(volume);
		
		Timer fps = new Timer(1, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				volume.setValue((int) (master.getValue()*100));
				repaint();
			}
		});
		fps.start();
		
		this.setPreferredSize(new Dimension(300,400));
		this.setVisible(true);
	}
	
	
}
