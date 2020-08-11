package com.consumer.service;

import java.io.IOException;
import java.net.HttpURLConnection;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.consumer.model.State;

public interface ConsumerService {

	public HttpURLConnection getConnection(String urlString) throws IOException;
	
	public String getXMLInput(String stateName);
	
	public HttpURLConnection setProperties(HttpURLConnection httpConn, byte[] buffer) throws IOException;
	
	public String readResponse(HttpURLConnection httpConn) throws IOException;
	
	public Document parseXmlFile(String input) throws ParserConfigurationException, SAXException, IOException;
	
	public State convertToState(String outputString);
}
