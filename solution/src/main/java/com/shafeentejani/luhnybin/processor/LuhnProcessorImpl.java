package com.shafeentejani.luhnybin.processor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

import com.shafeentejani.luhnybin.evaluator.LuhnSequenceEvaluator;
import com.shafeentejani.luhnybin.state.NoSequenceState;
import com.shafeentejani.luhnybin.state.SequenceState;
import com.shafeentejani.luhnybin.state.State;

public class LuhnProcessorImpl implements LuhnProcessor {

	private final State NO_SEQUENCE_STATE;
	private final State SEQUENCE_STATE;
	
	private State currentState;
	
	public LuhnProcessorImpl(Character mask, LuhnSequenceEvaluator checker){
		NO_SEQUENCE_STATE = new NoSequenceState();
		SEQUENCE_STATE = new SequenceState(mask,checker);
		currentState = NO_SEQUENCE_STATE;
	}
	
	public void applyCharacter(Character c, PrintStream output){
		currentState.applyCharacter(this,c, output);
	}
	
	public void setSequenceState(){
		currentState = SEQUENCE_STATE;
	}
	
	public void setNoSequenceState(){
		currentState = NO_SEQUENCE_STATE;
	}

	public void cleanUp(PrintStream output) {
		currentState.cleanUp(this, output);
		
	}
	

	public void processStream(InputStream input, PrintStream output) throws IOException {
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));
		
		int i;
		
		while( (i = reader.read()) != -1){
			
			Character c = (char) i;
			applyCharacter(c, output);
		}
		
		cleanUp(output);
		
	}
	
	
}
