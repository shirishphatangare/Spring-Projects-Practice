package com.in28minutes.soap.webservices.soapcoursemanagement.util;

import java.io.ByteArrayOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.WebServiceMessage;


public class Util {

	private Util() {}
	private static final Logger log = LoggerFactory.getLogger(Util.class);

	
	public static void logMessage(String id, WebServiceMessage webServiceMessage) {
		try {
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			webServiceMessage.writeTo(byteArrayOutputStream);
			String httpMessage = new String(byteArrayOutputStream.toByteArray());
			log.info(id + "--" + httpMessage);
		} catch (Exception e) {
			log.error("Unable to log HTTP message.", e);
		}
	}
}
