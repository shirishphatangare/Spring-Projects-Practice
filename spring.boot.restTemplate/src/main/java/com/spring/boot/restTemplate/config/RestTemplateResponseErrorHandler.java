package com.spring.boot.restTemplate.config;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResponseErrorHandler;

import com.spring.boot.restTemplate.exception.NotFoundException;

// A custom error handler for a RestTemplate that converts HTTP errors into meaningful exceptions.


@Component
public class RestTemplateResponseErrorHandler
    implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse httpResponse)
        throws IOException {

        return (httpResponse
          .getStatusCode()
          .series() == HttpStatus.Series.CLIENT_ERROR || httpResponse
          .getStatusCode()
          .series() == HttpStatus.Series.SERVER_ERROR);
    }

    @Override
    public void handleError(ClientHttpResponse httpResponse)
        throws IOException {
    	System.out.println("Entering Genneric Error Handling...");
        if (httpResponse
          .getStatusCode()
          .series() == HttpStatus.Series.SERVER_ERROR) {
            //Handle SERVER_ERROR - in case of HTTP status 5xx
        	System.out.println("This is Server Error i.e. case of HTTP status 5xx");
            throw new HttpServerErrorException(httpResponse.getStatusCode());
        } else if (httpResponse
          .getStatusCode()
          .series() == HttpStatus.Series.CLIENT_ERROR) {
            //Handle CLIENT_ERROR - in case of HTTP status 4xx
        	System.out.println("This is Client Error i.e. case of HTTP status 4xx");
            if (httpResponse.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new NotFoundException(httpResponse.getStatusCode() + " - Resource Not Found!");
            }else {
            	throw new HttpClientErrorException(httpResponse.getStatusCode());
            }
        }
    }
}
