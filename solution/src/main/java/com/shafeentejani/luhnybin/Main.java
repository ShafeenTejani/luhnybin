package com.shafeentejani.luhnybin;

import java.io.IOException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.shafeentejani.luhnybin.processor.LuhnProcessor;

public class Main {

	public static void main(String[] args) {
		
		ApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");
		LuhnProcessor processor = (LuhnProcessor)context.getBean("processor");
		
		try {
			processor.processStream(System.in, System.out);
		} catch (IOException e) {

			e.printStackTrace();
		}
		
		System.out.flush();
	}
}
