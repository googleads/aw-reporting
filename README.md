# AdWords API Reporting Sync

:warning: **WARNING: This project has reached its end of life, and no new features will be added. We will sunset this project on 30 Apr 2022, and will continue to maintain the project and provide bug fixes until then. We recommend using the [BigQuery Data Transfer Service for Google Ads](https://cloud.google.com/bigquery-transfer/docs/adwords-transfer) which provides similar functionality.**

## Overview

AwReporting is an open-source Java framework for large scale AdWords API
reporting.

*   Support **full suite** of AdWords API
    [reports](https://developers.google.com/adwords/api/docs/appendix/reports)
    with full list of fields.
*   Report data are stored in your **local database**, so you can integrate it
    with your existing systems.

## Quick Start

### Prerequisites

You will need [Java](http://www.oracle.com/technetwork/java/index.html)
8 (9 is not supported), [Maven](https://maven.apache.org/) and [MySQL](https://www.mysql.com/)
installed before configuring this project.

### Build the project using Maven
```
    $ git clone https://github.com/googleads/aw-reporting
    $ mvn clean install
```
### Configure your MySQL database
```
    CREATE DATABASE AWAPIReports DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;
    CREATE USER 'reportuser'@'localhost' IDENTIFIED BY 'SOME_PASSWORD';
    GRANT ALL PRIVILEGES ON AWAPIReports.\* TO 'reportuser'@'localhost' WITH GRANT OPTION;
```
### Configure AwReporting

Now you should create a properties file to specify your manager account ID,
developer token, OAuth credentials and database login.
```
    $ vi aw-reporting/src/main/resources/aw-report-sample.properties
```

Fill in the following fields with your manager account ID and developer token.

> managerAccountId=

> developerToken=

Fill in your OAuth credentials. If you need to create them, visit
[Google API Console Credentials page](https://console.developers.google.com/apis/credentials).

Note that you don't have to enter RefreshToken, as AwReporting takes care of
getting a new one when it runs for the first time.

> clientId=

> clientSecret=

Fill in the following with the number of rows that will be parsed in batch from
report data before persisting to the database. The bigger the number, the bigger
the memory usage, but it might also results an improvement in performance.

> aw.report.processor.rows.size=1000

Fill in the following to set the number of threads for report data processing
and database insertion.

> aw.report.processor.threads=20

Fill in the following with your database connection.

> aw.report.model.db.sql.url=jdbc:mysql://localhost:3306/AWReports?rewriteBatchedStatements=true

> aw.report.model.db.sql.username=reportuser

> aw.report.model.db.sql.password=<SOME_PASSWORD>

Specify report types and fields you want to get data.

> ACCOUNT_PERFORMANCE_REPORT=ExternalCustomerId,AccountDescriptiveName,Date,Impressions,Clicks,Cost,...

### Run the project and verify it's working

Now, you are ready to run AwReporting with the following command.
```
$ java -Xmx1G -jar aw-reporting/target/aw-reporting.jar -startDate YYYYMMDD -endDate YYYYMMDD \
-file aw-reporting/src/main/resources/aw-report-sample.properties
```

Be sure to specify the properties file you edited above on the command line.

It's possible to run the project using either Eclipse or the command line. If
using Eclipse, open and run:

> aw-reporting/src/main/java/com/google/api/ads/adwords/awreporting/AwReporting.java

As it's running, the project will provide status messages about the reports it's
downloading on the command line.

Check your database when the run finishes to be sure it's been populated with
the reporting data, e.g.:

> SELECT * FROM AWReports.AW_AccountPerformanceReport limit 1;

### Command line options

Set the following command line options before running the project:

```

Note: aw-reporting.jar is in the aw-reporting/aw-reporting/target/ directory.

java -Xmx1G -jar aw-reporting.jar -startDate YYYYMMDD -endDate YYYYMMDD -file <file>

Arguments:

   -accountIdsFile <accountIdsFile>
                              Consider ONLY the account IDs specified on the file to run the report.

   -csvReportFile <csvReportFile>
                              This is the path to the CSV report file that will be used to import data directly
                              into AwReporting.

   -dateRange <dateRange>
                              ReportDefinitionDateRangeType.

   -endDate <endDate>
                              End date for CUSTOM_DATE Reports (YYYYMMDD)

   -file <file>
                              aw-report-sample.properties file.

   -help
                              Print this message.

   -onFileReport <onFileReport>
                              This is an experimental argument, where you can specify the report type, and the
                              processor will read the data directly from the CSV file passed on the
                              'csvReportFile' argument. It's mandatory to pass the 'csvReportFile' argument if you
                              pass this argument.

   -startDate <startDate>
                              Start date for CUSTOM_DATE Reports (YYYYMMDD).
```

### Import the project into Eclipse (optional)

> File -> Import -> General -> Existing projects into workspace.

Import both modules:

> aw-reporting/aw-reporting-model

> aw-reporting/aw-reporting

## Details about the code

For better organization and encapsulation, the project groups the reporting
workflow into two parts:

*    **aw-reporting-model** for database persistence, entities and the CSV
     mapping to AdWords report information.
*    **aw-reporting** for the logic (API services, downloader and processors).

### Module "aw-reporting-model"

Provides all the necessary classes to persist data and the entities’ mapping to
AdWords report data.

*   **entities:** these POJOs define all the available fields for each report
    type as java fields, by using annotations. The entities contain information
    to link the java fields to the report fields definition, the csv display
    name fields and the datastore fields.

*   **csv:** The CSV classes use the OpenCSV library to convert CSV files into
    Java beans using annotations. The package also contains three new
    annotations:

    +   Annotation **@CsvReport** at the report's Java class level, to define
        the report definition type. For example for AccountPerformanceReport:
        ```
        @CsvReport(value= ReportDefinitionReportType.ACCOUNT_PERFORMANCE_REPORT)
        public class AccountPerformanceReport extends Report {...
        ```

    +   Annotation **@CsvField** at the report's Java field level, to define the
        mapping between java field, report's field name and display name. For
        example for avgCpm:
        ```
        @CsvField (value = "Avg. CPM", reportField = "AverageCpm")
        public BigDecimal avgCpm;
        ```

    +   Annotation **@MoneyField** at the report's Java field level, to convert
        the field's values from micro amount to normal currency. For example for
        cost:
        ```
        @MoneyField
        public BigDecimal cost;
        ```

+   **persistence:** The persistence layer uses Spring for bean management,
    injection and in class annotations, this helps to clearly demarcate the
    application layers. "AuthTokenPersister" is the interface for the
    authorization token storage, where we provided implementation for MySQL and
    MongoDB. "EntityPersister" is the interface for the report entities storage,
    where we provided implementation for MySQL and a MongoDB.

### Module "aw-reporting"

Provides the logic (API services, downloader and processors)

*   **downloader:** Based on MultipleClientReportDownloader (which uses client
    library's ReportDownloader) for downloading all the report files with
    multiple threads.

*   **processors:** The ReportProcessor is the class with the main logic, it is
    responsible for calling the downloader, parsing the CSV reports and
    persisting report data into storage. It would be replaced by a custom
    processor by changing the bean component in the xml configuration file.

*   **API Services:** Besides calling AdWords API to download report data, the
    ManagedCustomerDelegate is the only class using AdWords API to get all the
    AdWords account IDs under the top manager account.

*   **AwReporting main:** The AwReporting main class is the entry point for
    passing command line arguments to downloader / processor for execution.

## Offline Data Import

In case some report types are not yet exposed in AdWords API, but are already
available in the AdWords User Interface, we introduced the feature of importing
data to the database directly from CSV files that were downloaded from the
interface.

The offline data import works just as the online mode (where the data is
downloaded from the API), but skips the download step. All the field mappings
and report types supported are still the same, but keep in mind that most of the
entity IDs are not available in the reports downloaded from the interface.

**IMPORTANT NOTE:** Before importing the CSV with AwReporting, you must edit the
file and make sure that it's in the same format as the CSV file downloaded from
the API:

*   First line must contain the name or description of the report;
*   Second line must contain the column names/headers;
*   Following lines must contain the data.

Usually when you download a report from the interface, the CSV file will contain
some additional lines in the beginning of the file. You have to remove those
lines before importing it into AwReporting.

To use the offline import data, you just need to specify in the command line the
report type that you will import, and the local file that you will use as an
addition to the other arguments:
```
$ java -Xmx1G -jar aw-reporting/target/aw-reporting.jar -startDate YYYYMMDD -endDate YYYYMMDD \
-file aw-reporting/src/main/resources/aw-report-sample.properties \
-onFileReport CAMPAIGN_PERFORMANCE_REPORT -csvReportFile <CSV FILE LOCATION>
```

**IMPORTANT NOTE:** The dates specified are very import, because they will be
used to populate the database following the same format as the data downloaded
from the API. Date periods *are not supported*.

## Migration Guide
We will keep migrating AwReporting to the latest AdWords API version. In order
to migrate your version of AwReporting, please follow these steps:

1. run <code>git pull</code> to get the latest code.
1. run <code>mvn clean install</code> to build it.
1. Check [change log](https://github.com/googleads/aw-reporting/blob/master/ChangeLog.md)
   to see what AdWords API version is being used and refer to the AdWords API's
   [release notes](https://developers.google.com/adwords/api/docs/reference/)
   for changes on reports.
1. Create a new database, update properties file to make AwReporting persist
   report data into the new database.
1. Create database scripts to import data from old database to new one, or just
   run AwReporting to pull all historical data you need.
   
## Troubleshooting

The MySql version supported by default is v5.6. If you encounter any database
issues, specifically on schema creation, and you are using a higher version of
MySql, the suggested fix is to change the `hibernate.dialect` property in
`src/main/resources/data-source-mysql.xml` so that it refers to a higher
version dialect, e.g. `org.hibernate.dialect.MySQL8Dialect`.
