package modulators;

import javax.swing.*;

public class Oscilloscope extends JPanel {
	// Require multiple buffers in order to calculate the graph of the oscilloscope
	private int[] chunk;
	
	
	// Idea 1:
	// Simply process an entire chunk of buffers at a time
	// Scale chunk by x^2
	
	// Idea 2:
	// Process the chunk sequentially and replace a sub-chunk at a time
	
	private void addBuffer() {
		// Adds a buffer to the Stack
	}
	
}
