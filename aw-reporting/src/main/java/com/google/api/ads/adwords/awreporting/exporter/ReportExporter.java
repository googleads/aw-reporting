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

      Boolean writeHtml = true;
      if (properties.getProperty("aw.report.exporter.writeHtml") != null) {
        writeHtml = Boolean.valueOf(properties.getProperty("aw.report.exporter.writeHtml"));
      }

      Boolean writePdf = true;
      if (properties.getProperty("aw.report.exporter.writePdf") != null) {
        writePdf = Boolean.valueOf(properties.getProperty("aw.report.exporter.writePdf"));
      }
      
      Boolean writeDriveDoc = false;
      if (properties.getProperty("aw.report.exporter.writeDriveDoc") != null) {
        writeDriveDoc = Boolean.valueOf(properties.getProperty("aw.report.exporter.writeDriveDoc"));
      }

      // Get the Fonts for the PDF from the properties file
      String propertyReportFonts = properties.getProperty("aw.report.exporter.reportfonts");
      List<String> fontPaths = Lists.newArrayList();
      if (propertyReportFonts != null) {
        fontPaths = Arrays.asList(propertyReportFonts.split(","));
      }

      LOGGER.debug("Generating in Memory HTML for account: " + accountId);
      // Writing HTML to Memory
      MemoryReportWriter mrwHtml = MemoryReportWriter.newMemoryReportWriter();
      HTMLExporter.exportHtml(reportDataMap, template, mrwHtml);

      if (propertyReportWriterType != null && 
          propertyReportWriterType.equals(ReportWriterType.GoogleDriveWriter.name())) {
        
        // One folder per account
        Boolean perAccountFolder = false;
        if (properties.getProperty("aw.report.exporter.reportwritertype.drive.peraccountfolder") != null) {
          perAccountFolder = Boolean.valueOf(properties.getProperty("aw.report.exporter.writeDriveDoc"));
        }

        // Writing HTML to GoogleDrive
        if (writeHtml) {
          LOGGER.debug("Writing (to GoogleDrive) HTML for account: " + accountId);
          GoogleDriveReportWriter gdrwHtml = new GoogleDriveReportWriter.GoogleDriveReportWriterBuilder(
              accountId, dateStart, dateEnd, mccAccountId, credential, ReportFileType.HTML,
              templateName)
            .withFolderPerAccount(perAccountFolder)
            .build();
          gdrwHtml.write(mrwHtml.getAsSource());
        }

        // Writing PDF to GoogleDrive
        if (writePdf) {
          LOGGER.debug("Writing (to GoogleDrive) PDF for account: " + accountId);
          GoogleDriveReportWriter gdrwPdf = new GoogleDriveReportWriter.GoogleDriveReportWriterBuilder(
              accountId, dateStart, dateEnd, mccAccountId, credential, ReportFileType.PDF,
              templateName)
            .withFolderPerAccount(perAccountFolder)
            .build();
          HTMLExporter.exportHtmlToPdf(mrwHtml.getAsSource(), gdrwPdf, fontPaths);
        }
        
        // Writing Drive Doc to GoogleDrive
        if (writeDriveDoc) {
          LOGGER.debug("Writing GoogleDrive Doc for account: " + accountId);
          GoogleDriveReportWriter gdrwDriveDoc = new GoogleDriveReportWriter.GoogleDriveReportWriterBuilder(
              accountId, dateStart, dateEnd, mccAccountId, credential, ReportFileType.DRIVE_DOC,
              templateName)
            .withFolderPerAccount(perAccountFolder)
            .build();
          gdrwDriveDoc.write(mrwHtml.getAsSource());
        }

      } else {

        // Writing HTML to Disk
        if (writeHtml) {
          LOGGER.debug("Writing (to FileSystem) HTML for account: " + accountId);
          FileSystemReportWriter fsrwHtml = FileSystemReportWriter.newFileSystemReportWriter(
              templateName, dateStart, dateEnd, accountId, outputDirectory, ReportFileType.HTML);
          fsrwHtml.write(mrwHtml.getAsSource());
        }

        // Writing PDF to Disk
        if (writePdf) {
          LOGGER.debug("Writing (to FileSystem) PDF for account: " + accountId);
          FileSystemReportWriter fsrwPdf = FileSystemReportWriter.newFileSystemReportWriter(
              templateName, dateStart, dateEnd, accountId, outputDirectory, ReportFileType.PDF);
          HTMLExporter.exportHtmlToPdf(mrwHtml.getAsSource(), fsrwPdf, fontPaths);
        }
      }
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

        if (reportType.name() == "PLACEHOLDER_FEED_ITEM_REPORT") {
          Map<String, NameImprClicks> adExtensionsMap = new HashMap<String, NameImprClicks>();
          int sitelinks = 0;
          long totalClicks = 0;
          long totalImpressions = 0;
          

          // Aggregate totals by clickType starting with '0' values for each type to avoid NPEs
          Map<String, NameImprClicks> totalsByClickType = new HashMap<String, NameImprClicks>();          
          totalsByClickType.put("Sitelink", new NameImprClicks("Sitelink"));
          totalsByClickType.put("Call", new NameImprClicks("Call"));
          totalsByClickType.put("App", new NameImprClicks("App"));
          totalsByClickType.put("Location", new NameImprClicks("Location"));
          totalsByClickType.put("Review", new NameImprClicks("Review"));

          for (Report report : monthlyReports) {
            int placeholderType = ((ReportPlaceholderFeedItem) report).getFeedPlaceholderType();
            String clickType = "Headline";
            switch (placeholderType) {
            case 1:
              clickType = "Sitelink";
              break;
            case 2:
              clickType = "Call";
              break;
            case 3:
              clickType = "App";
              break;
            case 7:
              clickType = "Location";
              break;
            case 8:
              clickType = "Review";
              break;
            }
            Long impressions = ((ReportPlaceholderFeedItem) report).getImpressions();
            Long clicks = ((ReportPlaceholderFeedItem) report).getClicks();
            boolean isSelfAction = ((ReportPlaceholderFeedItem) report).isSelfAction();
            if (!clickType.equals("Headline")) {
              if (clickType.equals("Sitelink")) {
                sitelinks++;
              }
              if(isSelfAction) {
              if (adExtensionsMap.containsKey(clickType)) {
                  NameImprClicks oldValues = adExtensionsMap.get(clickType);
                  oldValues.impressions += impressions;
                  oldValues.clicks += clicks;
                  totalClicks += clicks;
                  totalImpressions += impressions;
                  adExtensionsMap.put(clickType, oldValues);
              } else {
                NameImprClicks values = new NameImprClicks(); 
                values.impressions = impressions;
                values.clicks = clicks;
                totalClicks += clicks;
                totalImpressions += impressions;
                adExtensionsMap.put(clickType, values);
              }
              
              // Add total values for each clickType
              NameImprClicks clickTypeNic;
              if(totalsByClickType.containsKey(clickType)) {
                clickTypeNic = totalsByClickType.get(clickType);
                clickTypeNic.impressions += impressions;
                clickTypeNic.clicks += clicks;
              }
            }
          }
          }

          List<NameImprClicks> adExtensions = new ArrayList<NameImprClicks>();
          for (Map.Entry<String, NameImprClicks> entry : adExtensionsMap.entrySet()) { 
            NameImprClicks nic = new NameImprClicks();
            nic.clickType = entry.getKey();
            if (nic.clickType.equals("Sitelink")) {
              nic.clickType = "Sitelinks (x" + sitelinks + ")";
            }
            nic.clicks = entry.getValue().clicks;
            nic.impressions = entry.getValue().impressions;
            adExtensions.add(nic);
          }
          NameImprClicks nic = new NameImprClicks();
          nic.clickType = "Total";
          nic.clicks = totalClicks;
          nic.impressions = totalImpressions;
          adExtensions.add(nic);

          if (monthlyReports != null && monthlyReports.size() > 0) {

            reportDataMap.put("ADEXTENSIONS", adExtensions);

            // Add report data for totals for each clickType
            for(Entry<String, NameImprClicks> clickTypeTotal : totalsByClickType.entrySet()) {
              reportDataMap.put(
                  "ADEXTENSIONSTOTALS-" + clickTypeTotal.getKey(), clickTypeTotal.getValue());
            }
            reportDataMap.put("ADEXTENSIONSTOTALS", nic);
          }
        }

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
