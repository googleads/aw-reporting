package com.google.api.ads.adwords.jaxws.extensions.report.model.util;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.bean.CsvToBean;
import au.com.bytecode.opencsv.bean.MappingStrategy;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Modified CSV to Bean converter to handle the different number formats from the reports.
 *
 * @param <T> the object type
 */
public class ModifiedCsvToBean<T> extends CsvToBean<T> implements Serializable{

  private static final long serialVersionUID = 1L;

  /**
   * Parses the CSV lazily, letting the client class decide when getting new elements.
   * 
   * @param mapper the mapping strategy for the file
   * @param csv the CSV file reader
   * @return the Iterator to go over the CSV elements
   */
  public CsvParserIterator<T> lazyParse(MappingStrategy<T> mapper, CSVReader csv) {

    try {
      mapper.captureHeader(csv);
      return new CsvParserIterator<T>(mapper, csv, this);
      
    } catch (Exception e) {
      throw new RuntimeException("Error parsing CSV!", e);
    }
  }

  /**
   * @see au.com.bytecode.opencsv.bean.CsvToBean
   *      #parse(au.com.bytecode.opencsv.bean.MappingStrategy, au.com.bytecode.opencsv.CSVReader)
   */
  @Override
  public List<T> parse(MappingStrategy<T> mapper, CSVReader csv) {
    try {
      mapper.captureHeader(csv);
      String[] line;
      List<T> list = new ArrayList<T>();
      while (null != (line = csv.readNext())) {
        try {
          T obj = processLine(mapper, line);
          if (obj != null) {
            list.add(obj);
          }
        } catch (Exception e) {
          System.err.println("Error Parsing Line: " + Arrays.deepToString(line));
          throw new RuntimeException(e);
        }
      }
      return list;
    } catch (Exception e) {
      throw new RuntimeException("Error parsing CSV!", e);
    }
  }

  /**
   * @see au.com.bytecode.opencsv.bean.CsvToBean
   *      #processLine(au.com.bytecode.opencsv.bean.MappingStrategy, java.lang.String[])
   */
  @Override
  protected T processLine(MappingStrategy<T> mapper, String[] line) throws IllegalAccessException,
      InvocationTargetException, InstantiationException, IntrospectionException {

    T bean = mapper.createBean();
    int col;
    for (col = 0; col < line.length; col++) {
      try {
        PropertyDescriptor prop = mapper.findDescriptor(col);
        if (null != prop) {
          String value = this.trimIfPossible(line[col], prop);
          Object obj = this.convertValue(value, prop);
          prop.getWriteMethod().invoke(bean, obj);
        }
      } catch (Exception e) {
        System.err.println("Error Parsing column # " + col + " with contents: " + line[col]);
        System.err.println("Error Parsing PropertyDescriptor: " + mapper.findDescriptor(col));
        throw new RuntimeException(e);
      }
    }
    return bean;
  }

  /**
   * Trims the property if it is of type String
   *
   * @param valueAsString the value of the property
   * @param propertyDescriptor the property descriptor
   * @return the property value trimmed
   */
  private String trimIfPossible(String valueAsString, PropertyDescriptor propertyDescriptor) {
    return trimmableProperty(propertyDescriptor) ? valueAsString.trim() : valueAsString;
  }

  /**
   * Verifies if the property is trimmable, by looking at the properties type
   *
   * @param propertyDescriptor the property descriptor
   * @return true if the property is of the type String, false otherwise
   */
  private boolean trimmableProperty(PropertyDescriptor propertyDescriptor) {
    return !String.class.isAssignableFrom(propertyDescriptor.getPropertyType());
  }
}
