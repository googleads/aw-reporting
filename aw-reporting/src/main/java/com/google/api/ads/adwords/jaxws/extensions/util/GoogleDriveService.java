package com.google.api.ads.adwords.jaxws.extensions.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;


public class GoogleDriveService {
  private static String CLIENT_ID = "";
  private static String CLIENT_SECRET = "";
  private static String REDIRECT_URI = "urn:ietf:wg:oauth:2.0:oob";
  private GoogleAuthorizationCodeFlow flow;
  private Drive service;
  
  private static GoogleDriveService instance = null;
  protected GoogleDriveService() throws IOException {
    
      HttpTransport httpTransport = new NetHttpTransport();
      JsonFactory jsonFactory = new JacksonFactory();
     
      flow = new GoogleAuthorizationCodeFlow.Builder(
          httpTransport, jsonFactory, CLIENT_ID, CLIENT_SECRET, Arrays.asList(DriveScopes.DRIVE))
          .setAccessType("online")
          .setApprovalPrompt("auto").build();
      
      String url = flow.newAuthorizationUrl().setRedirectUri(REDIRECT_URI).build();
      System.out.println("Please open the following URL in your browser then type the authorization code:");
      System.out.println("  " + url);
      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
      String code = br.readLine();
      
      GoogleTokenResponse response = flow.newTokenRequest(code).setRedirectUri(REDIRECT_URI).execute();
      GoogleCredential credential = new GoogleCredential().setFromTokenResponse(response);
      
      //Create a new authorized API client
      service = new Drive.Builder(httpTransport, jsonFactory, credential)
        .setApplicationName("AW Reports to DB").build();
  }
  
  public static GoogleDriveService getInstance() {
    if(instance == null)
      try {
        instance = new GoogleDriveService();
      } catch (IOException e) {
        e.printStackTrace();
        System.exit(1);
      }
    
    return instance;
  }
  
  public Drive getDriveService() {
    return service;
  }
}