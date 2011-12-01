package com.shafeentejani.luhnybin.state;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.shafeentejani.luhnybin.evaluator.LuhnSequenceEvaluator;
import com.shafeentejani.luhnybin.processor.LuhnProcessor;


public class SequenceStateTest {

	LuhnProcessor mockProcessor;
	LuhnSequenceEvaluator mockChecker;
	ByteArrayOutputStream byteStream;
	PrintStream printStream;
	SequenceState state;
	Character mask;
	
	@Before
	public void setUp(){
		mask = 'X';
		mockProcessor =  createStrictMock(LuhnProcessor.class);
		mockChecker = createStrictMock(LuhnSequenceEvaluator.class);
		byteStream = new ByteArrayOutputStream();
		printStream = new PrintStream(byteStream);
		state = new SequenceState(mask,mockChecker);
	}
	
	
	@Test
	public void testLetter(){
		
		mockProcessor.setNoSequenceState();
		expectLastCall();
		replay(mockProcessor);
		
		state.applyCharacter(mockProcessor, 'T', printStream);
		
		assertEquals("T",byteStream.toString());
		
		verify(mockProcessor);
		
	}
	
	@Test
	public void testDelimiter(){
		
		state.applyCharacter(mockProcessor, '-', printStream);
		assertEquals("",byteStream.toString());

	}
	
	@Test
	public void testDigit(){
		
		state.applyCharacter(mockProcessor, '0', printStream);
		
		assertEquals("", byteStream.toString());
	}
	
	@Test
	public void testLuhnSequence(){

		List<Character> sequence = new ArrayList<Character>(Arrays.asList(new Character[]{'4','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1'}));
		List<Integer> luhnSequence = new ArrayList<Integer>(Arrays.asList(new Integer[]{4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,1,1}));
		
		//when there are 14 digits, check if its a sequence
		expect(mockChecker.isLuhnSequence(luhnSequence.subList(0, 14))).andReturn(false);
		
		//when there are 15 digits, check last 14, then last 15
		expect(mockChecker.isLuhnSequence(luhnSequence.subList(1, 15))).andReturn(false); 	
		expect(mockChecker.isLuhnSequence(luhnSequence.subList(0, 15))).andReturn(false);
		
		//when there are 16 digits, check last 14, then last 15, then last 16
		expect(mockChecker.isLuhnSequence(luhnSequence.subList(2, 16))).andReturn(false);
		expect(mockChecker.isLuhnSequence(luhnSequence.subList(1, 16))).andReturn(false);
		expect(mockChecker.isLuhnSequence(luhnSequence.subList(0, 16))).andReturn(true);

		replay(mockChecker);
		
		for(Character c : sequence){
			state.applyCharacter(mockProcessor, c, printStream);
		}
		
		state.cleanUp(mockProcessor, printStream);
		
		assertEquals("XXXXXXXXXXXXXXXX", byteStream.toString());
		
		verify(mockChecker);
	}

	
	@Test
	public void testLuhnSequenceInMiddleOfNumberStream(){

		List<Character> sequence = new ArrayList<Character>(Arrays.asList(new Character[]{'9','5','6','6','1','3','9','5','9','9','3','2','5','3','7','5'}));
		List<Integer> luhnSequence = new ArrayList<Integer>(Arrays.asList(new Integer[]{9,5,6,6,1,3,9,5,9,9,3,2,5,3,7,5}));
		
		//when there are 14 digits, check if its a sequence
		expect(mockChecker.isLuhnSequence(luhnSequence.subList(0, 14))).andReturn(false);
		
		//when there are 15 digits, check last 14, then last 15
		expect(mockChecker.isLuhnSequence(luhnSequence.subList(1, 15))).andReturn(true); 	
		expect(mockChecker.isLuhnSequence(luhnSequence.subList(0, 15))).andReturn(false);
		
		//when there are 16 digits, check last 14, then last 15, then last 16
		expect(mockChecker.isLuhnSequence(luhnSequence.subList(2, 16))).andReturn(false);
		expect(mockChecker.isLuhnSequence(luhnSequence.subList(1, 16))).andReturn(false);
		expect(mockChecker.isLuhnSequence(luhnSequence.subList(0, 16))).andReturn(false);

		replay(mockChecker);
		
		for(Character c : sequence){
			state.applyCharacter(mockProcessor, c, printStream);
		}
		
		state.cleanUp(mockProcessor, printStream);
		
		assertEquals("9XXXXXXXXXXXXXX5", byteStream.toString());
		
		verify(mockChecker);
	}
	
	@Test
	public void testNonLuhnSequence(){
	
		List<Character> sequence = new ArrayList<Character>(Arrays.asList(new Character[]{'8','7','6','6','1','4','9','7','2','1','1','2','5','3','7'}));
		List<Integer> luhnSequence = new ArrayList<Integer>(Arrays.asList(new Integer[]{8,7,6,6,1,4,9,7,2,1,1,2,5,3,7}));
		
		//when there are 14 digits, check if its a sequence
		expect(mockChecker.isLuhnSequence(luhnSequence.subList(0, 14))).andReturn(false);
		
		//when there are 15 digits, check last 14, then last 15
		expect(mockChecker.isLuhnSequence(luhnSequence.subList(1, 15))).andReturn(false); 	
		expect(mockChecker.isLuhnSequence(luhnSequence.subList(0, 15))).andReturn(false);
		
		replay(mockChecker);
		
		for(Character c : sequence){
			state.applyCharacter(mockProcessor, c, printStream);
		}
		
		state.cleanUp(mockProcessor, printStream);
		
		assertEquals("876614972112537", byteStream.toString());
		
		verify(mockChecker);
		
	}
	
	@Test
	public void testLuhnSequenceWithDelimiters(){
		

		List<Character> sequence = new ArrayList<Character>(Arrays.asList(new Character[]{'4','1','1','1',' ','1','1','1','1',' ','1','1','1','1','-','1','1','1','1'}));
		List<Integer> luhnSequence = new ArrayList<Integer>(Arrays.asList(new Integer[]{4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,1,1}));
		
		//when there are 14 digits, check if its a sequence
		expect(mockChecker.isLuhnSequence(luhnSequence.subList(0, 14))).andReturn(false);
		
		//when there are 15 digits, check last 14, then last 15
		expect(mockChecker.isLuhnSequence(luhnSequence.subList(1, 15))).andReturn(false); 	
		expect(mockChecker.isLuhnSequence(luhnSequence.subList(0, 15))).andReturn(false);
		
		//when there are 16 digits, check last 14, then last 15, then last 16
		expect(mockChecker.isLuhnSequence(luhnSequence.subList(2, 16))).andReturn(false);
		expect(mockChecker.isLuhnSequence(luhnSequence.subList(1, 16))).andReturn(false);
		expect(mockChecker.isLuhnSequence(luhnSequence.subList(0, 16))).andReturn(true);

		replay(mockChecker);
		
		for(Character c : sequence){
			state.applyCharacter(mockProcessor, c, printStream);
		}
		
		state.cleanUp(mockProcessor, printStream);
		
		assertEquals("XXXX XXXX XXXX-XXXX", byteStream.toString());
		
		verify(mockChecker);
		
	}
	
	@Test
	public void testDigitsFollowedByLetter(){

		List<Integer> luhnSequence = new ArrayList<Integer>(Arrays.asList(new Integer[]{1,2,3,4,5,6,7,8,9,1,0,1,1,2}));
		
		//when there are 14 digits, check if its a sequence
		expect(mockChecker.isLuhnSequence(luhnSequence.subList(0, 14))).andReturn(false);
		
		mockProcessor.setNoSequenceState();
		expectLastCall();
		
		replay(mockChecker,mockProcessor);
		
		for(Character c : "12345678910112a".toCharArray()){
			state.applyCharacter(mockProcessor, c, printStream);
		}
		
		assertEquals("12345678910112a", byteStream.toString());
		
		verify(mockChecker,mockProcessor);
	}
}
