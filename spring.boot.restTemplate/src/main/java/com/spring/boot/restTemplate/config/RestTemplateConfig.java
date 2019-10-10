package com.spring.boot.restTemplate.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration

public class RestTemplateConfig {

	@Autowired
	RestTemplateResponseErrorHandler restTemplateResponseErrorHandler;
	
    @Bean
    @Qualifier("customRestTemplateCustomizer")
    public CustomRestTemplateCustomizer customRestTemplateCustomizer() {
        return new CustomRestTemplateCustomizer();
    }
    
    
    // Configuration by Creating Our Own RestTemplateBuilder.
    // This is the most extreme approach to customizing a RestTemplate. It disables the default auto-configuration of RestTemplateBuilder.
    @Bean
    @DependsOn(value = {"customRestTemplateCustomizer"})
    public RestTemplateBuilder restTemplateBuilder() { 
        return new RestTemplateBuilder(customRestTemplateCustomizer());
    }

    
    @Bean
    public RestTemplate restTemplate() {
    	RestTemplate restTemplate = restTemplateBuilder().build();
    	restTemplate.setRequestFactory(getClientHttpRequestFactory());	
    	restTemplate.setErrorHandler(restTemplateResponseErrorHandler);
    	return restTemplate;
    }
    
    
    // Timeout Configuration - Approach 1
    @Bean
    public ClientHttpRequestFactory getClientHttpRequestFactory() {
        int connectTimeout = 5000;
        int readTimeout = 50000;
        
      HttpComponentsClientHttpRequestFactory clientHttpRequestFactory
        = new HttpComponentsClientHttpRequestFactory();
      clientHttpRequestFactory.setConnectTimeout(connectTimeout);
      clientHttpRequestFactory.setReadTimeout(readTimeout);
      return clientHttpRequestFactory;
    }
   
    
    // Timeout Configuration - Approach 2
    // we can use HttpClient for further configuration options – as follows:
/*  @Bean
    public ClientHttpRequestFactory getClientHttpRequestFactory() {
        
    	int timeout = 15000;
        
        RequestConfig config = RequestConfig.custom()
          .setConnectTimeout(timeout)
          .setConnectionRequestTimeout(timeout)
          .setSocketTimeout(timeout)
          .build();
        
        CloseableHttpClient client = HttpClientBuilder
          .create()
          .setDefaultRequestConfig(config)
          .build();
        
        return new HttpComponentsClientHttpRequestFactory(client);
    } */
}
