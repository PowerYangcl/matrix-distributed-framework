package com.matrix.mvc;

import java.io.IOException;

import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;
 
//import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

@SuppressWarnings("all")
public class NoCacheMappingJacksonHttpMessageConverter extends MappingJackson2HttpMessageConverter {
	@Override  
    protected void writeInternal(Object o, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {  
        outputMessage.getHeaders().add("Pragma","No-cache");
        outputMessage.getHeaders().add("Cache-Control","no-cache");
        outputMessage.getHeaders().add("Expires", "0");
        super.writeInternal(o, outputMessage);  
    }
}
