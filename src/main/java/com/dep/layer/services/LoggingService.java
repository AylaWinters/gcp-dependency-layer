package com.dep.layer.services;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.dep.layer.entites.APILogEntity;
import com.dep.layer.gcp.StorageConfig;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class LoggingService {

	private final Storage storage;
	
	@Autowired
    public LoggingService() {
    	this.storage = StorageConfig.getInstance();
    }
    
    public void pushLogToBucket(APILogEntity logEntity, ResponseEntity<Object> response,
    		long transStartTime) throws JsonProcessingException {
    	final ObjectMapper mapper = new ObjectMapper();
    	final String fileName = response.getStatusCode().toString() + "-" 
    			+ LocalDate.now() + logEntity.getTransactionId();
    	logEntity.setTotalTransTime(System.currentTimeMillis() - transStartTime);
    	final String json = mapper.writeValueAsString(logEntity);
    	
    	if(response.getStatusCodeValue() != 200) {
    		logEntity.setExceptionDetails(response.getBody().toString());
    	}
    	
    	BlobId blobId = BlobId.of(System.getenv("BUCKET_NAME"), fileName);
    	BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("text/plain").build();
		Blob blob = storage.create(blobInfo, json.getBytes(StandardCharsets.UTF_8));

    }
}
