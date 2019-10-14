package com.spring.soap.consumer.util;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.WebServiceMessage;


public class Util {

	private Util() {}
	private static final Logger log = LoggerFactory.getLogger(Util.class);

	
	/* Get exception stack trace as a String that can be logged as an error */
	public static String getStackTrace(Exception e) {
    	StringWriter stringWriter = new StringWriter();
    	PrintWriter printWriter = new PrintWriter(stringWriter);
    	e.printStackTrace(printWriter);
    	return stringWriter.toString();
    }
	
	
	public static String getCurrentDateTime(){
		ZonedDateTime ldt = ZonedDateTime.now();
		DateTimeFormatter dateFormatter= DateTimeFormatter.ofPattern(CourseConstants.COMMON_DATE_TIME_FORMAT);
		return ldt.format(dateFormatter);
	}
	
	
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
