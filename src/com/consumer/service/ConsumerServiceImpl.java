package com.consumer.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.consumer.model.State;

public class ConsumerServiceImpl implements ConsumerService{

	// establish a connection
	public HttpURLConnection getConnection(String urlString) throws IOException {
		
		URL url = new URL(urlString);
		URLConnection connection = url.openConnection();
		HttpURLConnection httpConnection = (HttpURLConnection) connection;
		
		return httpConnection;
	}
	
	// What we want to pass into body of our post request
	public String getXMLInput(String stateName) {
		String xmlInput = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\""
				+ " xmlns:ss=\"http://soapwebapp.com/soap-web-app\">"
				+ " <soapenv:Header></soapenv:Header>"
				+ "<soapenv:Body>"
					+ "<ss:getStateRequest>"
						+ "<ss:name>" + stateName + "</ss:name>"
					+ "</ss:getStateRequest>"
				+ "</soapenv:Body>"
			+ "</soapenv:Envelope>";
		
		return xmlInput;
	}
	
	// setting properties of POST request, like we would do in Postman UI
	public HttpURLConnection setProperties(HttpURLConnection httpConn, byte[] buffer) throws IOException {
		String soapAction = "";
		
		httpConn.setRequestProperty("Content-length", String.valueOf(buffer.length));
		httpConn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
		httpConn.setRequestProperty("SOAPAction", soapAction);
		httpConn.setRequestMethod("POST");
		
		httpConn.setDoInput(true);
		httpConn.setDoOutput(true);
		
		return httpConn;
		
	}
	
	// Reads response, converts to String
	public String readResponse(HttpURLConnection httpConn) throws IOException {
		
		String responseString = null;
		String outputString = "";

		InputStreamReader inputStreamReader = new InputStreamReader(httpConn.getInputStream());
		BufferedReader in = new BufferedReader(inputStreamReader);
		
		while((responseString = in.readLine()) != null) {
			outputString += responseString;
		}
		
		return outputString;
	}
	
	// returns something more readable than an xml file
	public Document parseXmlFile(String input) throws ParserConfigurationException, SAXException, IOException {
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		InputSource inputSource = new InputSource(new StringReader(input));
		
		return db.parse(inputSource);
		
	}
	
	// Convert returned String into State object
	public State convertToState(String outputString) {
		
		try {
			
			Document document = parseXmlFile(outputString);
			
			NodeList name = document.getElementsByTagName("ns2:name");
			NodeList capital = document.getElementsByTagName("ns2:capital");
			NodeList population = document.getElementsByTagName("ns2:population");
			NodeList region = document.getElementsByTagName("ns2:region");
			
			State state = new State();
			state.setName(name.item(0).getTextContent());
			state.setCapital(capital.item(0).getTextContent());
			state.setPopulation(Integer.parseInt(population.item(0).getTextContent()));
			state.setRegion(region.item(0).getTextContent());
			
			return state;
		} catch (Exception e) {
			return null;
		}
	}
}






