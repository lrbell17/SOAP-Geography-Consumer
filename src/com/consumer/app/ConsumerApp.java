package com.consumer.app;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.Scanner;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.consumer.model.State;
import com.consumer.service.ConsumerService;
import com.consumer.service.ConsumerServiceImpl;

public class ConsumerApp {
	 
	private static final String WSURL = "http://localhost:8888/soapWS";
	private static ConsumerService consumerService = new ConsumerServiceImpl();
	
	
	// MAIN METHOD
	public static void main(String[] args) {
		Scanner input;
		boolean cont = true;
		
		while (cont) {
			State state = getUserInput();
			System.out.println("\n" +state.toString());
			
			input = new Scanner(System.in);
			System.out.println("\nEnter another state? (y/n): ");
			String anotherState = input.nextLine();
			
			if (!anotherState.equals("y")) {
				cont = false;
				System.out.println("\nGoodbye!");
			}
		}
	}
	
	// Makes SOAP Request to "http://localhost:8888/soapWS", gets back a response, converts to State object
	public static State getState(String stateName) throws IOException, ParserConfigurationException, SAXException {

		String xmlInput = consumerService.getXMLInput(stateName);

		HttpURLConnection httpConn = consumerService.getConnection(WSURL);

		byte[] buffer = new byte[xmlInput.length()];
		buffer = xmlInput.getBytes();

		httpConn = consumerService.setProperties(httpConn, buffer);

		OutputStream out = httpConn.getOutputStream();
		out.write(buffer);
		out.close();

		String outputString = consumerService.readResponse(httpConn);

		State state = consumerService.convertToState(outputString);

		return state;

	}
	
	public static State getUserInput() {
		Scanner input;
		boolean valid = false;
		State state = null;
		
		while(!valid) {
			
			try {
				input = new Scanner(System.in);
				System.out.println("Enter a U.S. State: ");
				String stateName = input.nextLine();
				
				state = getState(stateName); // converts user input to State object
				if (state != null) {
					valid = true;
				} else {
					System.out.println("Invalid Entry!");
				}
			} catch (Exception e) {
				System.out.println("Invalid Entry!");
				e.printStackTrace();
			}

		}
		
		return state;
	}
}
