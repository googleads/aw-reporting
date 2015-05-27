// Copyright 2013 Google Inc. All Rights Reserved.
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

package com.google.api.ads.adwords.awreporting.model.csv;

import com.google.api.ads.adwords.awreporting.model.csv.annotation.CsvField;
import com.google.api.ads.adwords.awreporting.model.csv.annotation.CsvReport;
import com.google.api.ads.adwords.awreporting.model.entities.Report;
import com.google.api.ads.adwords.lib.jaxb.v201502.ReportDefinitionReportType;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;
import org.springframework.util.SystemPropertyUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Class responsible to hold the mapping between report type and the class that represents the CSV
 * file data.
 *
 *  Note: only one bean per report type is allowed. If a second mapping is found, the first one will
 * be overwritten.
 *
 * @author gustavomoreira@google.com (Gustavo Moreira)
 */
public class CsvReportEntitiesMapping {

  private String packageToScan;

  private final Map<ReportDefinitionReportType, Class<? extends Report>> reportDefinitionMap =
      new HashMap<ReportDefinitionReportType, Class<? extends Report>>();

  private final Map<String, Class<? extends Report>> experimentalReportsDefinitionMap =
      new HashMap<String, Class<? extends Report>>();

  private final Map<ReportDefinitionReportType, List<String>> reportProperties =
      new HashMap<ReportDefinitionReportType, List<String>>();

  private static final Logger LOGGER =
      Logger.getLogger(CsvReportEntitiesMapping.class.getCanonicalName());

  /**
   * C'tor
   *
   * @param packageToScan the package to scan.
   */
  public CsvReportEntitiesMapping(String packageToScan) {
    this.packageToScan = packageToScan;
  }

  /**
   * Initializes the report type definition map.
   *
   * The base package is scanned in order to find the candidates to report beans, and the map of
   * {@code ReportDefinitionReportType} to the report bean class is created, based on the annotated
   * classes.
   *
   */
  public void initializeReportMap() {

    List<Class<? extends Report>> reportBeans;
    try {
      reportBeans = this.findReportBeans(this.packageToScan);

    } catch (ClassNotFoundException e) {
      LOGGER.severe("Class not found in classpath: " + e.getMessage());
      throw new IllegalStateException(e);
    } catch (IOException e) {
      LOGGER.severe("Could not read class file: " + e.getMessage());
      throw new IllegalStateException(e);
    }

    for (Class<? extends Report> reportBeanClass : reportBeans) {
      CsvReport csvReport = reportBeanClass.getAnnotation(CsvReport.class);

      if (csvReport.value().equals(ReportDefinitionReportType.UNKNOWN)) {
        this.experimentalReportsDefinitionMap.put(csvReport.fileOnlyReportType(), reportBeanClass);
      } else {
        this.reportDefinitionMap.put(csvReport.value(), reportBeanClass);

        Set<String> propertyExclusions = new HashSet<String>();
        String[] reportExclusionsArray = csvReport.reportExclusions();
        propertyExclusions.addAll(Arrays.asList(reportExclusionsArray));

        List<String> propertiesToSelect =
            this.findReportPropertiesToSelect(reportBeanClass, propertyExclusions);
        this.reportProperties.put(csvReport.value(), propertiesToSelect);
      }
    }
  }

  /**
   * Retrieves the report definitions defined by the report bean classes.
   *
   * @return the {@code Set} with all the definitions found in the report bean classes
   */
  public Set<ReportDefinitionReportType> getDefinedReports() {

    return this.reportProperties.keySet();
  }

  /**
   * Retrieves the bean class that maps the report data in the CSV file.
   *
   * @param reportType the type of the report.
   * @return the class of the bean that represents the report data.
   */
  public Class<? extends Report> getReportBeanClass(ReportDefinitionReportType reportType) {

    return this.reportDefinitionMap.get(reportType);
  }

  /**
   * Retrieves the bean class that maps the report data in the CSV file, and it's in the
   * experimental set.
   *
   * @param reportTypeName the name of the report that is in the experimental set.
   * @return the class of the bean that represents the report data.
   */
  public Class<? extends Report> getExperimentalReportBeanClass(String reportTypeName) {

    return this.experimentalReportsDefinitionMap.get(reportTypeName);
  }

  /**
   * Retrieves the properties that should be selected in the report.
   *
   * @param reportType the report type.
   * @return the list of properties that should be selected in the report.
   */
  public List<String> retrievePropertiesToSelect(ReportDefinitionReportType reportType) {

    return this.reportProperties.get(reportType);
  }

  /**
   * Finds the properties that will be selected to be part of the report.
   *
   * @param reportBeanClass the report class.
   * @param propertyExclusions the properties that must not be added to the report.
   * @return the list of properties to be part of the report
   */
  private List<String> findReportPropertiesToSelect(Class<? extends Report> reportBeanClass,
      Set<String> propertyExclusions) {

    List<String> propertiesToSelect = new ArrayList<String>();

    Class<?> currentClass = reportBeanClass;
    while (currentClass != Object.class) {

      this.addAllMappedSelectionProperties(propertiesToSelect, currentClass, propertyExclusions);

      currentClass = currentClass.getSuperclass();
    }
    return propertiesToSelect;
  }

  /**
   * Adds all the mapped report properties to the selection list.
   *
   * @param propertiesToSelect the selection list
   * @param currentClass the actual class
   * @param propertyExclusions the properties that must not be added to the report.
   */
  private void addAllMappedSelectionProperties(List<String> propertiesToSelect,
      Class<?> currentClass, Set<String> propertyExclusions) {

    Field[] declaredFields = currentClass.getDeclaredFields();
    for (int i = 0; i < declaredFields.length; i++) {

      Field field = declaredFields[i];
      this.addPropertyNameIfAnnotationPresent(propertiesToSelect, field, propertyExclusions);
    }
  }

  /**
   * Adds the report property to select if the CSV annotation is present
   *
   * @param propertiesToSelect the list of properties that will be selected for the report.
   * @param field the field
   * @param propertyExclusions the properties that must not be added to the report.
   */
  private void addPropertyNameIfAnnotationPresent(List<String> propertiesToSelect, Field field,
      Set<String> propertyExclusions) {

    if (field.isAnnotationPresent(CsvField.class)) {
      CsvField reportFieldAnnotation = field.getAnnotation(CsvField.class);
      String reportPropertyName = reportFieldAnnotation.reportField();

      if (!propertyExclusions.contains(reportPropertyName)) {
        propertiesToSelect.add(reportPropertyName);
      }
    }
  }

  /**
   * Finds the beans classes that are annotated with {@code CsvReport} and extends the
   * {@code Report} base class.
   *
   * @param basePackage the package to be scanned.
   * @return the list of classes that match the requirements to be a report bean.
   */
  private List<Class<? extends Report>> findReportBeans(String basePackage) throws IOException,
      ClassNotFoundException {

    ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
    MetadataReaderFactory metadataReaderFactory =
        new CachingMetadataReaderFactory(resourcePatternResolver);

    List<Class<? extends Report>> candidates = new ArrayList<Class<? extends Report>>();

    String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX
        + resolveBasePackage(basePackage) + "/" + "**/*.class";

    Resource[] resources = resourcePatternResolver.getResources(packageSearchPath);

    for (Resource resource : resources) {
      this.addCandidateIfApplicable(resource, metadataReaderFactory, candidates);
    }
    return candidates;
  }

  /**
   * Adds the resource as a candidate if the resource matches the rules.
   *
   * @param resource the current resource.
   * @param metadataReaderFactory the meta data factory for the bean.
   * @param candidates the list of candidates.
   * @throws IOException in case the meta data could not be created.
   * @throws ClassNotFoundException in case the class is not present in the classpath
   */
  @SuppressWarnings("unchecked")
  private void addCandidateIfApplicable(Resource resource,
      MetadataReaderFactory metadataReaderFactory, List<Class<? extends Report>> candidates)
      throws IOException, ClassNotFoundException {

    if (resource.isReadable()) {
      MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(resource);

      if (isAnnotationPresentAndReportSubclass(metadataReader)) {
        candidates.add((Class<? extends Report>) Class.forName(
            metadataReader.getClassMetadata().getClassName()));
      }
    }
  }

  /**
   * Resolve the package name to a canonical path, in case there any place holders.
   *
   * @param basePackage the base package to be scanned.
   * @return the canonical version of the package name.
   */
  private String resolveBasePackage(String basePackage) {

    return ClassUtils.convertClassNameToResourcePath(
        SystemPropertyUtils.resolvePlaceholders(basePackage));
  }

  /**
   * Checks for the annotation that maps the bean to a CSV file report.
   *
   * @param metadataReader the meta data reader for the bean class.
   * @return true if the {@code CsvReport} annotation is present and the bean class is a sub class
   *         of {@code Report}.
   */
  private boolean isAnnotationPresentAndReportSubclass(MetadataReader metadataReader) {

    String className = metadataReader.getClassMetadata().getClassName();
    try {
      Class<?> beanClass = Class.forName(className);
      if (beanClass.getAnnotation(CsvReport.class) != null && this.isReportSubclass(beanClass)) {
        return true;
      }
    } catch (ClassNotFoundException e) {
      LOGGER.warning("Class not found in classpath: " + className);
    }
    return false;
  }

  /**
   * Searches for the {@code Report} class in the superclass stack of the given class.
   *
   * @param beanClass the base class of the bean.
   * @return true if the given is an extension of the {@code Report} class.
   */
  private boolean isReportSubclass(Class<?> beanClass) {

    Class<?> currentClass = beanClass;
    while (currentClass != Object.class) {
      if (currentClass == Report.class) {
        return true;
      }
      currentClass = currentClass.getSuperclass();
    }
    return false;
  }
}
