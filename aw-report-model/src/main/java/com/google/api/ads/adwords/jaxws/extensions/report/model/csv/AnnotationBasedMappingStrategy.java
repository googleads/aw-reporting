package com.google.api.ads.adwords.jaxws.extensions.report.model.csv;

import com.google.api.ads.adwords.jaxws.extensions.report.model.csv.annotation.CsvField;
import com.google.api.ads.adwords.jaxws.extensions.report.model.entities.Report;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.bean.CsvToBean;
import au.com.bytecode.opencsv.bean.MappingStrategy;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * This class describes the mapping strategy to convert CSV files into Java beans using annotations.
 *
 * {@code AnnotationBasedMappingStrategy} is an implementation of {@link MappingStrategy}. In order
 * to use this class, you need to instantiate a {@code AnnotationBasedMappingStrategy} with the
 * proper {@link CSVReader}, and use both classes to construct a {@link CsvToBean}.
 *
 * The {@code AnnotationBasedMappingStrategy} is expecting to map only Java beans that extend from
 * {@link Report}. A code example on how to use this mapping strategy:
 *
 * <pre>    {@code
 * CSVReader csvReader = new AwReportCsvReader(new InputStreamReader(
 *    new FileInputStream("file-name"), "UTF-8"), ',', '\"', 1);
 *
 * AnnotationBasedMappingStrategy<ReportAd> mappingStrategy =
 *    new AnnotationBasedMappingStrategy<ReportAd>(ReportAd.class);
 *
 * CsvToBean<ReportAd> csvToBean = new CsvToBean<ReportAd>();
 * List<ReportAd> parsedBeans = csvToBean.parse(mappingStrategy, csvReader);
 *}</pre>
 *
 * The example uses a {@link AwReportCsvReader} in order to parse the CSV files from the AdWords
 * report API.
 *
 * @author gustavomoreira@google.com (Gustavo Moreira)
 *
 * @param <T> type of sub Report.
 */
public class AnnotationBasedMappingStrategy<T extends Report> implements MappingStrategy<T>, Serializable {

  private static final long serialVersionUID = 1L;

  private Class<T> reportEntityClass;

  private final Map<Integer, String> csvIndexToReportNames = new HashMap<Integer, String>();

  /**
   * C'tor
   *
   * @param reportEntityClass the {@code class} of the report entity POJO. This parameter is
   *        obligatory.
   */
  public AnnotationBasedMappingStrategy(Class<T> reportEntityClass) {

    if (reportEntityClass == null) {
      throw new NullPointerException("The report entity class must be especified.");
    }
    this.reportEntityClass = reportEntityClass;
  }

  /**
   * Captures the header of the CSV file.
   *
   *  This method scans the class of the bean for the proper annotations, and associate the correct
   * column index to the correct field.
   *
   * @param csvReader the {@code CSVReader}
   */
  @Override
  public void captureHeader(CSVReader csvReader) throws IOException {

    String[] header = csvReader.readNext();

    Map<String, String> nameMapping = this.createNameMapping();

    for (int i = 0; i < header.length; i++) {
      this.csvIndexToReportNames.put(i, nameMapping.get(header[i]));
    }
  }

  /**
   * Creates the {@code Map} where the key is the name of the property in the CSV file, and the
   * value is the name of the field in the Java bean.
   *
   * @return the {@code Map} of the properties for the bean class.
   */
  private Map<String, String> createNameMapping() {

    Map<String, String> nameMapping = new HashMap<String, String>();

    Class<?> currentClass = this.reportEntityClass;
    while (currentClass != Object.class) {

      this.addNameMappingForDeclaredFields(nameMapping, currentClass);
      currentClass = currentClass.getSuperclass();
    }
    return nameMapping;
  }

  /**
   * Scans the current class' fields for the mapped properties, and associate then to the CSV file
   * columns.
   *
   * @param nameMapping the map to be filled.
   * @param currentClass the class to be scanned.
   */
  private void addNameMappingForDeclaredFields(
      Map<String, String> nameMapping, Class<?> currentClass) {
    Field[] declaredFields = currentClass.getDeclaredFields();

    if (declaredFields.length > 0) {
      for (Field field : declaredFields) {
        this.addNameMappingIfAnnotationPresent(nameMapping, field);
      }
    }
  }

  /**
   * Checks for the annotation, and if the annotation is present, creates the association between
   * the CSV property and the field.
   *
   * @param nameMapping the map that is being filled.
   * @param field the current field.
   */
  private void addNameMappingIfAnnotationPresent(Map<String, String> nameMapping, Field field) {

    if (field.isAnnotationPresent(CsvField.class)) {

      CsvField reportFieldAnnotation = field.getAnnotation(CsvField.class);
      String csvFieldName = reportFieldAnnotation.value();
      nameMapping.put(csvFieldName, field.getName());
    }
  }

  /**
   * Creates a new instance of the Java bean.
   *
   *  The Java bean must have a public constructor that receives no arguments in order to be
   * instantiated.
   *
   */
  @Override
  public T createBean() throws InstantiationException, IllegalAccessException {

    try {
      return this.reportEntityClass.getConstructor().newInstance();

    } catch (NoSuchMethodException e) {
      throw new InstantiationException("Could not instantiate "
          + this.reportEntityClass.getCanonicalName() + ". "
          + "The class must have a public constructor with no arguments.");
    } catch (SecurityException e) {
      throw new IllegalAccessException("Could not instantiate "
          + this.reportEntityClass.getCanonicalName() + ". "
          + "The class must have a public constructor with no arguments.");
    } catch (IllegalArgumentException e) {
      throw new InstantiationException("Could not instantiate "
          + this.reportEntityClass.getCanonicalName() + ". "
          + "The class must have a public constructor with no arguments.");
    } catch (InvocationTargetException e) {
      throw new InstantiationException("Could not instantiate "
          + this.reportEntityClass.getCanonicalName() + ". "
          + "The class must have a public constructor with no arguments.");
    }
  }

  /**
   * Find the property descriptor that is referenced by the given column index.
   *
   * The mapping between the indexes and the field were created when the CSV header was captured.
   */
  @Override
  public PropertyDescriptor findDescriptor(int columnNumber) throws IntrospectionException {

    String propertyName = this.csvIndexToReportNames.get(columnNumber);
    if (propertyName != null) {
      return new PropertyDescriptor(propertyName, this.reportEntityClass);
    }
    return null;
  }

}
