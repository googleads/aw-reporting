//Copyright 2015 Google Inc. All Rights Reserved.
//
//Licensed under the Apache License, Version 2.0 (the "License");
//you may not use this file except in compliance with the License.
//You may obtain a copy of the License at
//
//http://www.apache.org/licenses/LICENSE-2.0
//
//Unless required by applicable law or agreed to in writing, software
//distributed under the License is distributed on an "AS IS" BASIS,
//WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//See the License for the specific language governing permissions and
//limitations under the License.

package com.google.api.ads.adwords.awreporting.alerting.util;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import au.com.bytecode.opencsv.CSVReader;

import com.google.api.ads.adwords.awreporting.alerting.report.ReportData;
import com.google.api.ads.adwords.awreporting.authentication.Authenticator;
import com.google.api.ads.adwords.awreporting.authentication.InstalledOAuth2Authenticator;
import com.google.api.ads.adwords.awreporting.exporter.reportwriter.ReportWriterType;
import com.google.api.ads.adwords.jaxws.v201502.cm.ReportDefinitionReportType;
import com.google.api.ads.adwords.lib.client.AdWordsSession;
import com.google.api.ads.common.lib.exception.ValidationException;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
* Generate test entities for other test cases
*/
public class TestEntitiesGenerator {

  public static Authenticator getTestAuthenticator() {
    return new InstalledOAuth2Authenticator("DevToken", "ClientId", "ClientSecret",
                                            ReportWriterType.FileSystemWriter);
  }
  
  public static AdWordsSession.Builder getTestAdWordsSessionBuilder() {
    return new AdWordsSession.Builder().withEndpoint("http://www.google.com")
               .withDeveloperToken("DeveloperToken")
               .withClientCustomerId("123")
               .withUserAgent("UserAgent")
               .withOAuth2Credential(new GoogleCredential.Builder().build());
  }
  
  public static AdWordsSession getTestAdWordsSession() throws ValidationException {
    return getTestAdWordsSessionBuilder().build();
  }

  public static JsonObject getTestReportQueryConfig() {
    String jsonConfigStr = "{" +
                           "  \"ReportType\": \"KEYWORDS_PERFORMANCE_REPORT\", " +
                           "  \"Fields\": \"ExternalCustomerId,AccountDescriptiveName,Id,KeywordText,Impressions,Ctr\", " +
                           "  \"Conditions\": \"Impressions > 100 AND Ctr < 0.05\", " +
                           "  \"TimeRange\": \"YESTERDAY\" " +
                           "}";
    return new JsonParser().parse(jsonConfigStr).getAsJsonObject();
  }
  
  public static Map<String, String> getTestFiledsMapping() {
    Map<String, String> fieldsMapping = new HashMap<String, String>();
    fieldsMapping.put("Account", "AccountDescriptiveName");
    fieldsMapping.put("Clicks", "Clicks");
    fieldsMapping.put("Cost", "Cost");
    fieldsMapping.put("Converted clicks", "ConvertedClicks");
    fieldsMapping.put("CTR", "Ctr");
    fieldsMapping.put("Day", "Date");
    fieldsMapping.put("Customer ID", "ExternalCustomerId");
    fieldsMapping.put("Impressions", "Impressions");
    
    return fieldsMapping;
  }
  
  public static String getTestReportFilePath() {
    return "src/test/resources/csv/reportDownload-ACCOUNT_PERFORMANCE_REPORT-2602198216-1370030134500.report";
  }
  
  /**
   * Test report headers: ExternalCustomerId, Date, AccountDescriptiveName, Cost, Clicks, Impressions, ConvertedClicks, Ctr
   * Test report entries: 7
   */
  public static ReportData getTestReportData() throws IOException {
    CSVReader csvReader = null;
    
    try {
      // Load test CSV file
      csvReader = new CSVReader(new FileReader(getTestReportFilePath()));
      
      // Set up fields mapping
      Map<String, String> fieldsMapping = TestEntitiesGenerator.getTestFiledsMapping();
    
      // Parse the CSV file into report
      return new ReportData(csvReader.readNext(), csvReader.readAll(), fieldsMapping,
                            "Test alert", ReportDefinitionReportType.ACCOUNT_PERFORMANCE_REPORT);
    }
    finally {
      if (null != csvReader) {
        csvReader.close();
      }
    }
  }
}
