  // Copyright 2014 Google Inc. All Rights Reserved.
  //
  // Licensed under the Apache License, Version 2.0 (the "License");
  // you may not use this file except in compliance with the License.
  // You may obtain a copy of the License at
  //
  // http://www.apache.org/licenses/LICENSE-2.0
  //
  // Unless required by applicable law or agreed to in writing, software
  // distributed under the License is distributed on an "AS IS" BASIS,
  // WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  // See the License for the specific language governing permissions and
  // limitations under the License.

  package com.google.api.ads.adwords.awreporting.exporter;

  import com.google.api.ads.adwords.awreporting.exporter.reportwriter.FileSystemReportWriter;
  import com.google.api.ads.adwords.awreporting.exporter.reportwriter.GoogleDriveReportWriter;
  import com.google.api.ads.adwords.awreporting.exporter.reportwriter.MemoryReportWriter;
  import com.google.api.ads.adwords.awreporting.exporter.reportwriter.ReportWriterType;
  import com.google.api.ads.adwords.awreporting.exporter.reportwriter.ReportWriter.ReportFileType;
  import com.google.api.ads.adwords.awreporting.model.csv.CsvReportEntitiesMapping;
  import com.google.api.ads.adwords.awreporting.model.entities.NameImprClicks;
  import com.google.api.ads.adwords.awreporting.model.entities.Report;
  import com.google.api.ads.adwords.awreporting.model.entities.ReportPlaceholderFeedItem;
  import com.google.api.ads.adwords.awreporting.model.persistence.EntityPersister;
  import com.google.api.ads.adwords.awreporting.model.util.DateUtil;
  import com.google.api.ads.adwords.awreporting.util.TemplateStringsUtil;
  import com.google.api.ads.adwords.lib.jaxb.v201502.ReportDefinitionReportType;
  import com.google.api.ads.common.lib.exception.OAuthException;
  import com.google.api.client.auth.oauth2.Credential;
  import com.google.api.client.util.Maps;
  import com.google.common.collect.Lists;
  import com.lowagie.text.DocumentException;

  import org.apache.log4j.Logger;
  import org.joda.time.DateTime;
  import org.springframework.beans.factory.annotation.Autowired;
  import org.springframework.stereotype.Component;

  import java.io.File;
  import java.io.FileReader;
  import java.io.IOException;
  import java.io.InputStream;
  import java.io.InputStreamReader;
  import java.util.ArrayList;
  import java.util.Arrays;
  import java.util.HashMap;
  import java.util.List;
  import java.util.Map;
  import java.util.Map.Entry;
  import java.util.Properties;
  import java.util.Set;

  /**
   * Exports reports PDF/HTML reports to either the FileSystem or Google Drive.
   *
   * @author joeltoby@google.com (Joel Toby)
   * @author jtoledo@google.com (Julian Toledo)
   */
  @Component
  public abstract class ReportExporter {

    private static final Logger LOGGER = Logger.getLogger(ReportExporter.class);

    protected CsvReportEntitiesMapping csvReportEntitiesMapping;

    protected EntityPersister persister;

    
    /**
     * Export reports to PDF/HTML for one account.
     *
     * @param dateStart the start date for the reports
     * @param dateEnd the end date for the reports
     * @param accountId the account CID to generate PDF for
     * @param properties the properties file containing all the configuration
     * @param templateFile the template file to generate the report
     * @param outputDirectory where to output the files
     * @param sumAdExtensions to add up all the extensions
     * @throws IOException 
     * @throws OAuthException 
     * @throws DocumentException 
     * @throws Exception error creating PDF
     */
    public void exportReport(Credential credential, String mccAccountId, String dateStart, String dateEnd, Long accountId, Properties properties,
        File templateFile, String tempateName, File outputDirectory, Boolean sumAdExtensions)
            throws IOException, OAuthException, DocumentException {
      
      FileReader template = new FileReader(templateFile);

      exportReport(credential, mccAccountId, dateStart, dateEnd, accountId, properties,
          template, tempateName, outputDirectory, sumAdExtensions);
    }
    
    /**
     * Export reports to PDF/HTML for one account.
     *
     * @param dateStart the start date for the reports
     * @param dateEnd the end date for the reports
     * @param accountId the account CID to generate PDF for
     * @param properties the properties file containing all the configuration
     * @param templateInputStream the template file to generate the report
     * @param outputDirectory where to output the files
     * @param sumAdExtensions to add up all the extensions
     * @throws IOException 
     * @throws OAuthException 
     * @throws DocumentException 
     * @throws Exception error creating PDF
     */
    public void exportReport(Credential credential, String mccAccountId, String dateStart, String dateEnd, Long accountId, Properties properties,
        InputStream templateInputStream, String tempateName, File outputDirectory, Boolean sumAdExtensions)
            throws IOException, OAuthException, DocumentException {

      InputStreamReader template = new InputStreamReader(templateInputStream);

      exportReport(credential, mccAccountId, dateStart, dateEnd, accountId, properties,
          template, tempateName, outputDirectory, sumAdExtensions);
    }

    /**
     * Export reports to PDF/HTML for one account.
     *
     * @param dateStart the start date for the reports
     * @param dateEnd the end date for the reports
     * @param accountId the account CID to generate PDF for
     * @param properties the properties file containing all the configuration
     * @param template the template file to generete the report
     * @param outputDirectory where to output the files
     * @param sumAdExtensions to add up all the extensions
     * @throws IOException 
     * @throws OAuthException 
     * @throws DocumentException 
     * @throws Exception error creating PDF
     */
    public void exportReport(Credential credential, String mccAccountId, String dateStart, String dateEnd, Long accountId, Properties properties,
        InputStreamReader template, String templateName, File outputDirectory, Boolean sumAdExtensions)
            throws IOException, OAuthException, DocumentException {

      LOGGER.info("Starting Report Export for account " + accountId);

      Map<String, Object> reportDataMap = createReportDataMap(dateStart, dateEnd, accountId, properties, sumAdExtensions);

      if (reportDataMap.size() > 0) {

        String propertyReportWriterType = properties.getProperty("aw.report.exporter.reportwritertype");

        Boolean writeHtml = false;
     
        Boolean writePdf = false;
       
        Boolean writeDriveDoc = false;


      } else {
      	LOGGER.info("No data found for account " + accountId);
      }
    }

    /**
     * Generates the HashMap with all the data from reports
     *
     * @param dateStart the start date for the reports
     * @param dateEnd the end date for the reports
     * @param accountId the account CID to generate PDF for
     * @param properties the properties file containing all the configuration
     * @param sumAdExtensions to add up all the extensions
     */
    public Map<String, Object> createReportDataMap(String dateStart, String dateEnd,
        Long accountId, Properties properties, Boolean sumAdExtensions) {

      Map<String, Object> reportDataMap = Maps.newHashMap();
      Set<ReportDefinitionReportType> reports = csvReportEntitiesMapping.getDefinedReports();

      for (ReportDefinitionReportType reportType : reports) {
        if (properties.containsKey(reportType.name())) {
          // Adding each report type rows from DB to the account's monthly reports list.

          List<Report> monthlyReports = Lists.newArrayList(persister.listMonthReports(
              csvReportEntitiesMapping.getReportBeanClass(reportType), accountId,
              DateUtil.parseDateTime(dateStart), DateUtil.parseDateTime(dateEnd)));


          if (monthlyReports != null && monthlyReports.size() > 0) {
            reportDataMap.put(reportType.name(), monthlyReports);
            
            // Add formatted string values to reportDateMap for use in templates
            DateTime startDate = DateUtil.parseDateTime(dateStart);
            DateTime endDate = DateUtil.parseDateTime(dateEnd);
            String monthStartText = TemplateStringsUtil.formatDateFullMonthYear(startDate);
            String monthEndText = TemplateStringsUtil.formatDateFullMonthYear(endDate);
            String monthRangeText = TemplateStringsUtil.formatFullMonthDateRange(startDate, endDate);
            
            Map<String, String> dateStrings = new HashMap<String, String>();
            dateStrings.put("monthStart", monthStartText);
            dateStrings.put("monthEnd", monthEndText);
            dateStrings.put("monthRange", monthRangeText);
            
            reportDataMap.put("FORMATTED_DATE_STRINGS", dateStrings);
          }
        }
      }
      return reportDataMap;
    }

    /**
     * @param csvReportEntitiesMapping
     *            the csvReportEntitiesMapping to set
     */
    @Autowired
    public void setCsvReportEntitiesMapping(
        CsvReportEntitiesMapping csvReportEntitiesMapping) {
      this.csvReportEntitiesMapping = csvReportEntitiesMapping;
    }

    /**
     * @param persister the persister to set
     */
    @Autowired
    public void setPersister(EntityPersister persister) {
      this.persister = persister;
    }
  }
