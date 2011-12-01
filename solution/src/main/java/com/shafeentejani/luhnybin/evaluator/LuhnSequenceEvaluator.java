package com.shafeentejani.luhnybin.evaluator;

import java.util.List;

/**
 * 
 * A interface for the evaluation of Luhn Sequences
 * 
 * @author Shaf
 *
 */

public interface LuhnSequenceEvaluator {

	/**
	 * Determines if a sequence of integers is a Luhn sequence.
	 * This is only guaranteed to work correctly if the integers are single digits.
	 * 
	 * @param sequence
	 * @return <code>true</code> if the sequence is a Luhn sequence, <code>false</code> otherwise.
	 */
	public boolean isLuhnSequence(List<Integer> sequence);

}