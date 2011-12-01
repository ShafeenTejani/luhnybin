package com.shafeentejani.luhnybin.evaluator;

import java.util.List;

public final class LuhnSequenceEvaluatorImpl implements LuhnSequenceEvaluator {

	public boolean isLuhnSequence(List<Integer> sequence){
		
		boolean doubleValue = false;
		int sum = 0;
		
		for(int i=sequence.size() - 1; i >=0; i--){
			
			if(doubleValue){
				sum += doubleAndAddDigits(sequence.get(i));
			}
			else
				sum += sequence.get(i);
			
			doubleValue = toggle(doubleValue);
		}
		
		return (sum % 10) == 0;
	}
	
	
	private boolean toggle(boolean val){
		return val ^= true;
	}
	
	private int doubleAndAddDigits(int i){
		
		int val = 2*i;
		int sum = 0;
		
		while( val > 0){
			sum += val % 10;
			val /= 10;
		}
		
		return sum;
	}
	
}
