# AwReporting Alerting Framework

## Overview

AwReporting Alerting is an alerting framework based on AdWords reports downloaded by AwReporting, with defined interfaces for alert rules and actions, where user can easily implement customized logic to plug in. This tool can be used by novice users (simple alerts through configuration) as well as more advanced users (custom alerts through interface plugging).

## Quick Start 

### Prerequisites

You will need Java, Maven and MySQL installed before configuring the project.

### Build the project using Maven

AwReporting Alerting can be compiled using Maven by executing the following on the command line: 

<code>$ mvn compile dependency:copy-dependencies package</code>

This project needs aw-report-model and aw-reporting jars.

### Configure your MySQL database

<code>CREATE DATABASE AWReportingAlerts DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;</code>

<code>CREATE USER 'reportuser'@'localhost' IDENTIFIED BY 'SOME_PASSWORD';</code>

<code>GRANT ALL PRIVILEGES ON AWReportingAlerts.* TO 'reportuser'@'localhost' WITH GRANT OPTION;</code>

### Configure AwReportingAlerting

<code> vi aw-reporting-alerting/src/main/resources/aw-report-alerting-sample.properties</code>
 
 - Fill in the MCC account ID, developer token, client ID and client secret, as well as database url and credentials.

<code> vi aw-reporting-alerting/src/main/resources/aw-report-alerting-alerts-sample.json</code>

 - This is the JSON configuration of the alerts that you want to run. It's referred from the .properties file in the same folder.

## Run the project

<pre>
java -Xmx4G -jar aw-reporting-alerting.jar -file &lt;file&gt; -verbose

Arguments:

 -accountIdsFile &lt;file&gt;  Consider ONLY the account IDs specified on the file to run the report

 -debug                  Display all the debug information. If the option 'verbose' is activated, all the information will be displayed on the console as well

 -file &lt;file&gt;            The properties file (refer to ./aw-report-alertingsample.properties as an example)

 -verbose                The application will print all the tracing on the console
</pre>

## Implement custom alert rules

Alert rules are responsible for:

 - Defining a list of field names to extend in the report
 - Determining a list of field values to extend for each report entry
 - Determining whether each report entry should be skipped from result alerts

Custom alert rules should derive from com.google.api.ads.adwords.awreporting.alerting.rule.AlertRule, and:

 - All AlertRule implementations MUST have a constructor with a JsonObject parameter, otherwise it will fail to load.
 - All AlertRule implementations MUST be stateless, since the same instance will be shared among multiple threads.

## Implement custom alert actions

Alert actions are responsible for processing each report entry, and:

 - Performing some action immediately, or
 - Recording some info and performing aggregated action at the end

Custom alert actions should derive from com.google.api.ads.adwords.awreporting.alerting.action.AlertAction, and:

 - All AlertAction implementations MUST NOT modify report entries (since an report entry may be processed by multiple alert actions). All the modifications should be done by AlertRules.

## Plug custom alert rules and alert actions

Just edit the JSON configuration file:

 - Under <code>"Rules"</code>, put class name of the custom alert rule in <code>"RuleClass"</code> field, along with other parameters that will be passed to the custom alert rule's constructor. For example
 <pre>
	"Rules": [
	  {
	    "RuleClass": "AddAccountManager"
	  },
	  {
	    "RuleClass": "AddAccountMonthlyBudget"
	  }
	]
 </pre>

 - Under <code>"Actions"</code>, put class name of the custom alert rule in <code>"ActionClass"</code> field, along with other parameters that will be passed to the custom alert rule's constructor. For example
 <pre>
	"Actions": [
	  {
	    "ActionClass": "SimpleConsoleWriter"
	  },
	  {
	    "ActionClass": "PerAccountManagerEmailSender",
	    "Subject": "Low impression accounts",
	    "CC": "abc@example.com,xyz@example.com"
	  }
	]
 </pre>

### Fine print
Pull requests are very much appreciated. Please sign the [Google Code contributor license agreement](http://code.google.com/legal/individual-cla-v1.0.html) (There is a convenient online form) before submitting.

<dl>
  <dt>Authors</dt><dd><a href="https://plus.google.com/+ZhuoChenGoogle/">Zhuo Chen (Google Inc.)</a></dd>
  <dt>Copyright</dt><dd>Copyright © 2015 Google, Inc.</dd>
  <dt>License</dt><dd>Apache 2.0</dd>
  <dt>Limitations</dt><dd>This is example software, use with caution under your own risk.</dd>
</dl>
