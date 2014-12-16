# AwReporting (Beta)

## Special Note
<<<<<<< HEAD
=======

If you are using this project, please follow the API anouncements and API version Sunsets:
https://developers.google.com/adwords/api/community/aw-announcements
https://developers.google.com/adwords/api/docs/sunset-dates

The AdWords API changes version more or less every 4 months, so you would need to upgrade your project around that timeframe.

>>>>>>> 2f2a7486c98ac121ad93d5d5bb44c6f875fcbc1a
Please let us know if you run into issues in the project's issue tracker (https://github.com/googleads/aw-reporting/issues), this Beta release may not fit your needs if you work with very large accounts but we are working to make the project better, your feedback is very important.

## Overview
AwReporting is an open-source Java framework for large scale AdWords API reporting.

* You can use this project to generate reports for all the AdWords accounts managed by an MCC. 

* 15 common reports are included in the reference implementation. You can easily follow the code examples to implement more. 

* Reports are stored in your **relational database**, so you can integrate them with your existing systems.

## Quick Start 

### Prerequisites

You will need Java, Maven and MySQL installed before configuring the project.

### Build the project using Maven

<code>$ git clone https://github.com/googleads/aw-reporting</code>

<code>$ mvn clean install eclipse:eclipse</code>

<code>$ mvn compile dependency:copy-dependencies package</code>

### Configure your MySQL database

<code>CREATE DATABASE AWReports DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;</code>

<code>CREATE USER 'reportuser'@'localhost' IDENTIFIED BY 'SOME_PASSWORD';</code>

<code>GRANT ALL PRIVILEGES ON AWReports.\* TO 'reportuser'@'localhost' WITH GRANT OPTION;</code>

### Configure AwReporting 

Now we'll create a properties file to specify your MCC, developer token, OAuth, and database credentials.

<code>$ vi aw-reporting/src/main/resources/aw-report-sample.properties</code>

Fill in the following fields with your MCC acount ID, and developer token.

>mccAccountId=

>developerToken=

Fill in your OAuth credentials. If you need to create them, visit: <a href>https://code.google.com/apis/console#access</a>

Note that you don't have enter RefreshToken as AwReporting takes care of getting new one when it when run for the first time.

>clientId=

>clientSecret=

Fill in the following with the number of rows that will be parsed from the CSV file before persisting to the DB.
The bigger the number, the bigger the memory usage, but also might give an improvement in performance.

>aw.report.processor.rows.size=1000

Fill in the following number to set the number of threads for the CSV processing and DB insertion.

>aw.report.processor.threads=4

Fill in the following with your database connection.

>aw.report.model.db.sql.url=jdbc:mysql://localhost:3306/AWReports?rewriteBatchedStatements=true

>aw.report.model.db.sql.username=reportuser

>aw.report.model.db.sql.password=SOME_PASSWORD

### Run the project and verify it's working 
<<<<<<< HEAD

Now, you are ready to run AwReporting with following command.

```
$ java -Xmx1G -jar aw-reporting/target/aw-reporting.jar -startDate YYYYMMDD -endDate YYYYMMDD \
-file aw-reporting/src/main/resources/aw-report-sample.properties -verbose
```

Be sure to specify the properties file you edited above on the command line. 

It's possible to run the project using either Eclipse or the command line. If using Eclipse, open and run:

> aw-reporting/src/main/java/com/google/api/ads/adwords/jaxws/extensions/AwReporting.java

As it's running, the project will provide status messages about the reports it's downloading on the command line. 

Check your database when the run finishes to be sure it's been populated with the reporting data, e.g.:

> SELECT * FROM AWReports.AW_ReportAd limit 1;

### Command line options 

=======

Now, you are ready to run AwReporting with following command.

```
$ java -Xmx1G -jar aw-reporting/target/aw-reporting.jar -startDate YYYYMMDD -endDate YYYYMMDD \
-file aw-reporting/src/main/resources/aw-report-sample.properties -verbose
```

Be sure to specify the properties file you edited above on the command line. 

It's possible to run the project using either Eclipse or the command line. If using Eclipse, open and run:

> aw-reporting/src/main/java/com/google/api/ads/adwords/jaxws/extensions/AwReporting.java

As it's running, the project will provide status messages about the reports it's downloading on the command line. 

Check your database when the run finishes to be sure it's been populated with the reporting data, e.g.:

> SELECT * FROM AWReports.AW_ReportAd limit 1;

### Command line options 

>>>>>>> 2f2a7486c98ac121ad93d5d5bb44c6f875fcbc1a
Set the following command line options before running the project:

<pre>

Note: aw-reporting.jar is in the aw-reporting/aw-reporting/target/ directory.

<code>java -Xmx1G -jar aw-reporting.jar -startDate YYYYMMDD -endDate YYYYMMDD -file &lt;file&gt;</code>

<code>java -Xmx1G -jar aw-reporting.jar -generatePdf &lt;htmlTemplateFile&gt; &lt;outputDirectory&gt; -startDate YYYYMMDD -endDate YYYYMMDD -file &lt;file&gt;</code>


<code>Arguments:

   -accountIdsFile &lt;file&gt;           Defines a file that contains all the account IDs, one per line, to be used
                                          instead of getting the accounts from the API. The list can contain all the accounts,
                                          or just a specific set of accounts

   -dateRange <DateRangeType>             ReportDefinitionDateRangeType.

   -debug     Will display all the debug information. If the option 'verbose' is
              activated, all the information will be displayed on the console as
              well

   -endDate &lt;YYYMMDD&gt;      End date for CUSTOM_DATE Reports (YYYYMMDD)

   -file &lt;file&gt;            aw-report-sample.properties file.

   -generatePdf &lt;htmlTemplateFile&gt; &lt;outputDirectory&gt;
                           
                           Generate Monthly Account Reports for all Accounts in PDF
   
                           NOTE: For PDF use aw-report-sample-for-pdf.properties instead, the fields need to be different.

   -help                   Print this message.

   -startDate &lt;YYYYMMDD&gt;   Start date for CUSTOM_DATE Reports (YYYYMMDD).

   -verbose                The application will print all the tracing on the console

</code>
</pre>

### Import the project into Eclipse (optional)

To import the project into Eclipse, first import the model:

> File -> Import -> General -> Existing projects into workspace.

> aw-reporting/aw-report-model

Next import the database code:

> File -> Import -> General -> Existing projects into workspace.

> aw-reporting/aw-reporting


## Details about the code
<<<<<<< HEAD

For better organization and encapsulation, the project groups the reporting workflow into two parts:
**Aw-Report-Model** for the logic (API services, downloader and processors) and **Aw-Reporting** for persistence, entities and the CSV mapping to AdWords information.


### Aw-Report-Model
Provides all the necessary classes to persist data and the entities’ mapping  to AdWords report data.

* **Entities:** these POJOs define all the available fields for each report kind as java fields, by using annotations. The Entities contain the information to link the java fields to the report fields definition, the csv display name header fields and the datastore fields.

* **CSV:** The CSV classes use the OpenCSV library to convert CSV files into Java beans using annotations. The package also contains two new annotations to define the Report Definition Type and the mapping between java field, report’s Column Name and Display Name headers. For example:

  + Annotation **@CsvReport** at the Report class level, for example for ReportAccount:
<code>@CsvReport(value=
  ReportDefinitionReportType.ACCOUNT_PERFORMANCE_REPORT)
public class ReportAccount extends Report {...</code>

  + Annotation **@CsvField** at the java field level, for example for avgCpm:
<code>@CsvField (value = "Avg. CPM", reportField = "AverageCpm")
public BigDecimal avgCpm;</code>

+ **Persistence:** The persistence layer uses Spring for bean management, injection and in class annotations, this helps to clearly demarcate the application layers.
AuthTokenPersister: is the interface for the authorization token storage, we have implemented it for Mysql and a MongoDB.
ReportEntitiesPersister is the interface for the report entities storage, we have implemented it for Mysql and a MongoDB.


### Aw-Reporting
Provides the logic (API services, downloader and processors) 

* **Downloader:** Based on MultipleClientReportDownloader java example (it uses the Library ReportDownloader) the Downloader is on charge of downloading all the report files using multiple threads.

* **Processors:** The ReportProcessor is the class with the main logic, it is responsible for calling the downloader, use the CVS classes for the parsing and call the Persistence helpers for the storage. This class can be replace by a custom processor by changing the bean component in the projects xml configuration files.

* **API Services:** Beside the report Downloader calls to AdHoc Reports, the ManagedCustomerDelegate is the only class talking to the AdWords API, it is on charge of getting all the account ids in the MCC tree.

* **AwReporting main:** The AwReporting main class is on charge of printing the help information, the properties file example and of passing the command line parameters to the processor for execution.

## PDF Generation

PDF generation works monthly and also needs the use of a HTML template like ACCOUNT\_PERFORMANCE\_REPORT.tmpl

First run the the date range without the -generatePdf to download the data needed to generate them.

=======

For better organization and encapsulation, the project groups the reporting workflow into two parts:
**Aw-Report-Model** for the logic (API services, downloader and processors) and **Aw-Reporting** for persistence, entities and the CSV mapping to AdWords information.


### Aw-Report-Model
Provides all the necessary classes to persist data and the entities’ mapping  to AdWords report data.

* **Entities:** these POJOs define all the available fields for each report kind as java fields, by using annotations. The Entities contain the information to link the java fields to the report fields definition, the csv display name header fields and the datastore fields.

* **CSV:** The CSV classes use the OpenCSV library to convert CSV files into Java beans using annotations. The package also contains two new annotations to define the Report Definition Type and the mapping between java field, report’s Column Name and Display Name headers. For example:

  + Annotation **@CsvReport** at the Report class level, for example for ReportAccount:
<code>@CsvReport(value=
  ReportDefinitionReportType.ACCOUNT_PERFORMANCE_REPORT)
public class ReportAccount extends Report {...</code>

  + Annotation **@CsvField** at the java field level, for example for avgCpm:
<code>@CsvField (value = "Avg. CPM", reportField = "AverageCpm")
public BigDecimal avgCpm;</code>

+ **Persistence:** The persistence layer uses Spring for bean management, injection and in class annotations, this helps to clearly demarcate the application layers.
AuthTokenPersister: is the interface for the authorization token storage, we have implemented it for Mysql and a MongoDB.
ReportEntitiesPersister is the interface for the report entities storage, we have implemented it for Mysql and a MongoDB.


### Aw-Reporting
Provides the logic (API services, downloader and processors) 

* **Downloader:** Based on MultipleClientReportDownloader java example (it uses the Library ReportDownloader) the Downloader is on charge of downloading all the report files using multiple threads.

* **Processors:** The ReportProcessor is the class with the main logic, it is responsible for calling the downloader, use the CVS classes for the parsing and call the Persistence helpers for the storage. This class can be replace by a custom processor by changing the bean component in the projects xml configuration files.

* **API Services:** Beside the report Downloader calls to AdHoc Reports, the ManagedCustomerDelegate is the only class talking to the AdWords API, it is on charge of getting all the account ids in the MCC tree.

* **AwReporting main:** The AwReporting main class is on charge of printing the help information, the properties file example and of passing the command line parameters to the processor for execution.

## PDF Generation

PDF generation works monthly and also needs the use of a HTML template like ACCOUNT\_PERFORMANCE\_REPORT.tmpl

First run the the date range without the -generatePdf to download the data needed to generate them.

>>>>>>> 2f2a7486c98ac121ad93d5d5bb44c6f875fcbc1a
Here's an example properties file for PDF generation:

> aw-report-sample-for-pdf.properties

### Fine print
Pull requests are very much appreciated. Please sign the [Google Code contributor license agreement](http://code.google.com/legal/individual-cla-v1.0.html) (There is a convenient online form) before submitting.

<dl>
  <dt>Authors</dt><dd><a href="https://plus.google.com/+JulianCToledo/">Julian Toledo (Google Inc.)
<dd><a href="https://plus.google.com/+GustavoMenezes/">Gustavo Menezes (Google Inc.)</a></dd>
  <dt>Copyright</dt><dd>Copyright © 2013 Google, Inc.</dd>
  <dt>License</dt><dd>Apache 2.0</dd>
  <dt>Limitations</dt><dd>This is example software, use with caution under your own risk.</dd>
</dl>
