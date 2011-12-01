package com.shafeentejani.luhnybin.state;

import java.io.PrintStream;

import com.shafeentejani.luhnybin.processor.LuhnProcessor;

public class NoSequenceState implements State {
	
	public void applyCharacter(LuhnProcessor processor, Character c, PrintStream output) {
		
		if(Character.isDigit(c)){
			processor.setSequenceState();
			processor.applyCharacter(c, output);
		}
		else{
			output.write(c);
		}
	}

	public void cleanUp(LuhnProcessor processor, PrintStream output) {
	}	
	
}
