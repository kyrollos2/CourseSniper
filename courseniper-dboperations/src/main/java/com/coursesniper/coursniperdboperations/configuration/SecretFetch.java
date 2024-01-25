package com.coursesniper.coursniperdboperations.configuration;
import java.io.IOException;

import org.springframework.stereotype.Service;

import com.google.cloud.secretmanager.v1.SecretManagerServiceClient;
import com.google.cloud.secretmanager.v1.SecretVersionName;
@Service
public class SecretFetch {

    // Constructor for SecretFetch
    public SecretFetch() {
    }

    public String accessSecretVersion(String projectId, String secretId, String versionId) throws IOException {
        // Try-with-resources to ensure the client is closed after use
        try (SecretManagerServiceClient client = SecretManagerServiceClient.create()) {
            SecretVersionName secretVersionName = SecretVersionName.of(projectId, secretId, versionId);
            String payload = client.accessSecretVersion(secretVersionName).getPayload().getData().toStringUtf8();
            return payload;
        }
    }
}
