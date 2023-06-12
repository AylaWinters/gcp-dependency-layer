package com.dep.layer.gcp;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

@Configuration
public class StorageConfig {

	private static final Storage INSTANCE = initializeCloudStorage();
	
	private StorageConfig() {
		
	}
	
	@Bean
	public static Storage initializeCloudStorage() {
		StorageOptions storageOptions;
		try {
			storageOptions = StorageOptions.getDefaultInstance().toBuilder()
					.setProjectId(System.getenv("PROJECT_ID"))
                    .setCredentials(GoogleCredentials.getApplicationDefault())
                    .build();
			return storageOptions.getService();
		} catch (IOException e) {
            e.printStackTrace();
            return null;
        }
		
	}
	
	public static Storage getInstance() {
		return INSTANCE;
	}
}
