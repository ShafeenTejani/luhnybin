package com.shafeentejani.luhnybin.processor;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

import com.shafeentejani.luhnybin.state.NoSequenceState;
import com.shafeentejani.luhnybin.state.SequenceState;

/**
 * An interface for state-based processing of character feeds to mask Luhn
 * sequences within them.
 * 
 * If characters are being processed using the
 * {@link #applyCharacter(Character, PrintStream)} method then the
 * {@link #cleanUp(PrintStream)} method should be called at the end to ensure
 * all processed characters are sent to the output stream the. This is not
 * required when calling {@link #processStream(InputStream, PrintStream)} which
 * processes the complete stream
 * 
 * @author Shaf
 * 
 */

public interface LuhnProcessor {

	/**
	 * Processes a single character
	 * 
	 * @param c the character to be processed 
	 * @param output the output stream for processed characters
	 */
	public void applyCharacter(Character c, PrintStream output);
	
	/**
	 * Sets the processor state to a {@link SequenceState}
	 */
	public void setSequenceState();
	
	/**
	 * Sets the processor state to a {@link NoSequenceState}
	 */
	public void setNoSequenceState();
	
	/**
	 * To be called once all characters have been individually processed. This ensures
	 * all processed characters have been sent to the output stream.
	 * 
	 * @param output the output stream for processed characters
	 */
	public void cleanUp(PrintStream output);
	
	
	/**
	 * Processes a complete stream of characters to mask Luhn sequences.
	 * 
	 * @param input an input stream of characters
	 * @param output the output stream for processed characters
	 * @throws IOException if an exception occurs while reading input data
	 */
	public void processStream(InputStream input, PrintStream output) throws IOException;
	
	
}
