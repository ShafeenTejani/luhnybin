package com.shafeentejani.luhnybin.state;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.easymock.EasyMock.*;
import org.junit.Before;
import org.junit.Test;

import com.shafeentejani.luhnybin.processor.LuhnProcessor;



public class NoSequenceStateTest {

	LuhnProcessor mockProcessor;
	ByteArrayOutputStream byteStream;
	PrintStream printStream;
	NoSequenceState state;
	
	@Before
	public void setUp(){
		
		mockProcessor =  createStrictMock(LuhnProcessor.class);
		byteStream = new ByteArrayOutputStream();
		printStream = new PrintStream(byteStream);
		state = new NoSequenceState();
	}
	
	@Test
	public void testLetterInput(){
		
		state.applyCharacter(mockProcessor, 'T', printStream);
		
		assertEquals("T",byteStream.toString());

	}
	
	@Test
	public void testDelimiterInput(){
		
		state.applyCharacter(mockProcessor, '-',printStream);
		state.applyCharacter(mockProcessor, ' ',printStream);
		
		assertEquals("- ",byteStream.toString());
	}
	
	@Test
	public void testDigitInput(){
		
		Character digit = '0';
		
		mockProcessor.setSequenceState();
		expectLastCall();
		mockProcessor.applyCharacter(digit, printStream);
		expectLastCall();
		replay(mockProcessor);
		
		state.applyCharacter(mockProcessor, digit, printStream);
		
		verify(mockProcessor);
	}
	
}
