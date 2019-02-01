// Copyright 2016 Google Inc. All Rights Reserved.
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

package com.google.api.ads.adwords.awreporting.model.entities;

import com.google.api.ads.adwords.awreporting.model.csv.annotation.CsvField;
import com.google.api.ads.adwords.awreporting.model.persistence.mongodb.MongoEntity;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.joda.time.DateTime;

/**
 * The base abstract class for all Reports. For a full list of reports and their fields see
 * https://developers.google.com/adwords/api/docs/appendix/reports
 */
@MappedSuperclass
public abstract class Report implements MongoEntity {
  @Id
  @Column(name = "ROW_ID")
  protected String rowId;

  @Column(name = "TOP_CUSTOMER_ID")
  protected Long topCustomerId;

  @Column(name = "TIMESTAMP")
  protected Date timestamp;

  @Column(name = "START_DATE", length = 8)
  protected String startDate;

  @Column(name = "END_DATE", length = 8)
  protected String endDate;

  @Column(name = "DATE_RANGE_TYPE", length = 20)
  protected String dateRangeType;

  @Column(name = "ExternalCustomerId")
  @CsvField(value = "Customer ID", reportField = "ExternalCustomerId")
  protected Long customerId;

  /**
   * Constructor to satisfy Hibernate.
   */
  public Report() {
    timestamp = new DateTime().toDate();
  }

  /**
   * Constructor for Report base class.
   * 
   * @param topCustomerId
   * @param customerId
   */
  public Report(Long topCustomerId, Long customerId) {
    this.topCustomerId = topCustomerId;
    this.customerId = customerId;
    timestamp = new DateTime().toDate();
  }

  /**
   * Sets the primary key for each Report record.
   */
  public abstract void setRowId();
  
  @Override
  public String getRowId() {
    return rowId;
  }
  
  public Long getTopCustomerId() {
    return topCustomerId;
  }

  public void setTopCustomerId(Long topCustomerId) {
    this.topCustomerId = topCustomerId;
  }

  public Date getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Date timestamp) {
    this.timestamp = timestamp;
  }

  public String getStartDate() {
    return startDate;
  }

  public void setStartDate(String startDate) {
    this.startDate = startDate;
  }

  public String getEndDate() {
    return endDate;
  }

  public void setEndDate(String endDate) {
    this.endDate = endDate;
  }

  public String getDateRangeType() {
    return dateRangeType;
  }

  public void setDateRangeType(String dateRangeType) {
    this.dateRangeType = dateRangeType;
  }

  public Long getCustomerId() {
    return customerId;
  }

  public void setCustomerId(Long customerId) {
    this.customerId = customerId;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }

    if (this == obj) {
      return true;
    }

    if (!(obj instanceof Report)) {
      return false;
    }
    
    Report other = (Report) obj;
    return new EqualsBuilder()
        .append(rowId, other.rowId)
        .append(topCustomerId, other.topCustomerId)
        .append(timestamp, other.timestamp)
        .append(startDate, other.startDate)
        .append(endDate, other.endDate)
        .append(dateRangeType, other.dateRangeType)
        .append(customerId, other.customerId)
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder()
        .append(rowId)
        .append(topCustomerId)
        .append(timestamp)
        .append(startDate)
        .append(endDate)
        .append(dateRangeType)
        .append(customerId)
        .hashCode();
  }
}
