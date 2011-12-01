package com.shafeentejani.luhnybin.state;

import java.io.PrintStream;

import com.shafeentejani.luhnybin.processor.LuhnProcessor;

/**
 * An interface for the various states of a {@link LuhnProcessor} To ensure all
 * processed characters have been sent to the output stream the
 * {@link #cleanUp(LuhnProcessor, PrintStream)} method should be called after
 * all characters are processed
 * 
 * @author Shaf
 * 
 */

public interface State {

	/**
	 * A method to be called when a new character is to be processed by a {@link LuhnProcessor}
	 * 
	 * @param processor the processor for which this state belongs to
	 * @param c	the character to be processed
	 * @param output the output stream for processed characters
	 */
	public void applyCharacter(LuhnProcessor processor,Character c, PrintStream output);
	
	
	/**
	 * To be called once all characters have been processed. This ensures
	 * all processed characters have been sent to the output stream.
	 * 
	 * @param processor the processor for which this state belongs to
	 * @param output the output stream for processed characters
	 */
	public void cleanUp(LuhnProcessor processor, PrintStream output);
	
}
