package com.shafeentejani.luhnybin.state;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import com.shafeentejani.luhnybin.evaluator.LuhnSequenceEvaluator;
import com.shafeentejani.luhnybin.processor.LuhnProcessor;

public class SequenceState implements State {

	private final int[] allowedSequenceLengths = new int[] { 14, 15, 16 };

	private final LuhnSequenceEvaluator luhnChecker;
	private List<MarkableEntry> numbers;
	private List<MarkableEntry> verbose;
	private Character mask;

	public SequenceState(Character mask, LuhnSequenceEvaluator checker) {
		this.mask = mask;
		luhnChecker = checker;
		numbers = new ArrayList<MarkableEntry>();
		verbose = new ArrayList<MarkableEntry>();
	}

	public void applyCharacter(LuhnProcessor processor, Character c,
			PrintStream output) {

		if (Character.isDigit(c)) {

			addDigit(c);

			for (int length : allowedSequenceLengths) {
				if (numbers.size() < length)
					break;

				if (luhnChecker.isLuhnSequence(sublist(length))) {
					markSublist(length);
				}
			}
		} else if (isDelimiter(c)) {
			addDelimiter(c);
		} else {
			flushVerbose(output);
			output.write(c);
			processor.setNoSequenceState();
		}

	}

	public void cleanUp(LuhnProcessor processor, PrintStream output) {
		flushVerbose(output);
		processor.setNoSequenceState();
	}

	private boolean isDelimiter(Character c) {
		return c.equals(' ') || c.equals('-');
	}

	private void markSublist(int length) {
		for (int j = numbers.size() - length; j < numbers.size(); j++)
			numbers.get(j).mark();
	}

	private List<Integer> sublist(int length) {
		List<Integer> checklist = new ArrayList<Integer>();

		for (int j = numbers.size() - length; j < numbers.size(); j++) {
			checklist.add(Character.digit(numbers.get(j).getValue(), 10));
		}
		return checklist;
	}

	private boolean addDigit(Character c) {
		if (Character.isDigit(c)) {
			MarkableEntry entry = new MarkableEntry(c);
			numbers.add(entry);
			verbose.add(entry);
			return true;
		}
		return false;
	}

	private void addDelimiter(Character c) {
		MarkableEntry entry = new MarkableEntry(c);
		verbose.add(entry);
	}

	private void flushVerbose(PrintStream output) {

		for (MarkableEntry entry : verbose) {
			if (entry.isMarked())
				output.write(mask);
			else
				output.write(entry.getValue());
		}
		verbose.clear();
		numbers.clear();
	}

	protected static class MarkableEntry {

		private Character value;
		private boolean marked = false;

		public MarkableEntry(Character value) {
			this.value = value;
		}

		public void mark() {
			marked = true;
		}

		public boolean isMarked() {
			return marked;
		}

		public Character getValue() {
			return value;
		}
	}

}
