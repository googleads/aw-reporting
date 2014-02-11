package com.google.api.ads.adwords.jaxws.extensions.appengine;

import com.google.api.ads.adwords.jaxws.extensions.processors.ReportProcessor;
import com.google.api.ads.adwords.jaxws.extensions.processors.ReportProcessorOnMemory;
import com.google.api.ads.adwords.jaxws.extensions.util.DataBaseType;
import com.google.api.ads.adwords.jaxws.extensions.util.DynamicPropertyPlaceholderConfigurer;
import com.google.api.ads.adwords.lib.jaxb.v201309.ReportDefinitionDateRangeType;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class Aw_reporting_appengineServlet extends HttpServlet {
  
  private static final Logger LOGGER = Logger.getLogger(Aw_reporting_appengineServlet.class);
  
  /**
   * The DB type key specified in the properties file.
   */
  private static final String AW_REPORT_MODEL_DB_TYPE = "aw.report.model.db.type";
  
  /**
   * The Spring application context used to get all the beans.
   */
  private static ApplicationContext appCtx;
  
  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    resp.setContentType("text/plain");
    resp.getWriter().println("Hello, world");

    String dateStart = "20131201";
    String dateEnd = "20131231";    
    Properties properties = initApplicationContextAndProperties("aw-report-sample-appengine.properties");

    ReportProcessorOnMemory processor = createReportProcessor();
    try {
      processor.generateReportsForMCC(ReportDefinitionDateRangeType.CUSTOM_DATE, dateStart,
          dateEnd, null, properties);
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  /**
   * Creates the {@link ReportProcessor} autowiring all the dependencies.
   *
   * @return the {@code ReportProcessor} with all the dependencies properly injected.
   */
  private static ReportProcessorOnMemory createReportProcessor() {

    return appCtx.getBean(ReportProcessorOnMemory.class);
  }
  
  /**
   * Initialize the application context, adding the properties configuration file depending on the
   * specified path.
   *
   * @param propertiesPath the path to the file.
   * @return the resource loaded from the properties file.
   * @throws IOException error opening the properties file.
   */
  private static Properties initApplicationContextAndProperties(String propertiesPath)
      throws IOException {

    Resource resource = new ClassPathResource(propertiesPath);
    if (!resource.exists()) {
      resource = new FileSystemResource(propertiesPath);
    }
    LOGGER.trace("Innitializing Spring application context.");
    DynamicPropertyPlaceholderConfigurer.setDynamicResource(resource);

    Properties properties = PropertiesLoaderUtils.loadProperties(resource);
    String dbType = (String) properties.get(AW_REPORT_MODEL_DB_TYPE);
    if (dbType != null && dbType.equals(DataBaseType.MONGODB.name())) {

      LOGGER.debug("Using MONGO DB configuration properties.");
      appCtx = new ClassPathXmlApplicationContext("classpath:aw-reporting-mongodb-beans.xml");
    } else {

      LOGGER.debug("Using SQL DB configuration properties.");
      appCtx = new ClassPathXmlApplicationContext("classpath:aw-reporting-sql-beans.xml");
    }

    return properties;
  }
}
