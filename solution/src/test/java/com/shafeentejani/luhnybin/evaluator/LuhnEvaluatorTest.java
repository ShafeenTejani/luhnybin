package com.shafeentejani.luhnybin.evaluator;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.shafeentejani.luhnybin.evaluator.LuhnSequenceEvaluator;
import com.shafeentejani.luhnybin.evaluator.LuhnSequenceEvaluatorImpl;


public class LuhnEvaluatorTest {

	LuhnSequenceEvaluator checker;
	
	@Before
	public void setUp(){
		checker = new LuhnSequenceEvaluatorImpl();
	}
	
	@Test
	public void testValidLuhnSequence(){
		
		List<Integer> validSequence = new ArrayList<Integer>(Arrays.asList(new Integer[]{5,6,6,1,3,9,5,9,9,3,2,5,3,7}));
		
		assertEquals(true,checker.isLuhnSequence(validSequence));
		
	}
	
	@Test
	public void testInvalidLuhnSequence(){
		
		List<Integer> invalidSequence = new ArrayList<Integer>(Arrays.asList(new Integer[]{5,6,6,1,3,9,5,9,9,3,2,5,3,5}));
		
		assertEquals(false,checker.isLuhnSequence(invalidSequence));
	}
	
	@Test
	public void testEmptyLuhnSequence(){
		
		List<Integer> emptySequence = new ArrayList<Integer>();
		
		assertEquals(true, checker.isLuhnSequence(emptySequence));
	}
	
}
