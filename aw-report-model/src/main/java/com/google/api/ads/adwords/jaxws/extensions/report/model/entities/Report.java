// Copyright 2011 Google Inc. All Rights Reserved.
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

package com.google.api.ads.adwords.jaxws.extensions.report.model.entities;

import com.google.api.ads.adwords.jaxws.extensions.report.model.csv.annotation.CsvField;
import com.google.api.ads.adwords.jaxws.extensions.report.model.persistence.mongodb.MongoEntity;

import org.joda.time.DateTime;

import com.googlecode.objectify.annotation.Index;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * The base abstract class for all Reports
 *
 *  Fields from http://code.google.com/apis/adwords/docs/appendix/reports.html Fields from
 * http://code.google.com/apis/adwords/docs/reportguide.html
 *
 * @author jtoledo@google.com (Julian Toledo)
 * @author gustavomoreira@google.com (Gustavo Moreira)
 */
@MappedSuperclass
public abstract class Report implements MongoEntity {

  // Variables for Persistence queries
  public static final String ID = "id";
  public static final String PARTNER_ID = "partnerId";
  public static final String TOP_ACCOUNT_ID = "topAccountId";
  public static final String ACCOUNT_ID = "accountId";
  public static final String CAMPAIGN_ID = "campaignId";
  public static final String ADGROUP_ID = "adGroupId";
  public static final String KEYWORD_ID = "keywordId";
  public static final String AD_ID = "adId";
  public static final String ADEXTENSION_ID = "adExtensionId";
  public static final String DAY = "day";
  public static final String DATE_START = "dateStart";
  public static final String DATE_END = "dateEnd";

  @Id
  @com.googlecode.objectify.annotation.Id
  @Column(name = "ROW_ID")
  protected String id;

  @Index
  @Column(name = "PARTNER_ID")
  protected Long partnerId;

  @Index
  @Column(name = "TOP_ACCOUNT_ID")
  protected Long topAccountId;

  @Column(name = "TIMESTAMP")
  protected Date timestamp;

  @Index
  @Column(name = "DATE_START")
  protected String dateStart;

  @Index
  @Column(name = "DATE_END")
  protected String dateEnd;

  @Column(name = "DATE_RANGE_TYPE")
  protected String dateRangeType;

  @Index
  @Column(name = "ACCOUNT_ID")
  @CsvField(value = "Customer ID", reportField = "ExternalCustomerId")
  protected Long accountId;

  public Report() {
    timestamp = new DateTime().toDate();
  }

  public Report(Long topAccountId, Long accountId) {
    this.topAccountId = topAccountId;
    this.accountId = accountId;
    timestamp = new DateTime().toDate();
  }

  public abstract void setId();

  public String setIdDates() {
    if (this.getDateStart() != null && this.getDateEnd() != null) {
      return "-" + this.getDateStart() + "-" + this.getDateEnd();
    }
    return "";
  }

  public String getId() {
    return id;
  }

  public Long getPartnerId() {
    return partnerId;
  }

  public void setPartnerId(Long partnerId) {
    this.partnerId = partnerId;
  }

  public Long getTopAccountId() {
    return topAccountId;
  }

  public void setTopAccountId(Long topAccountId) {
    this.topAccountId = topAccountId;
  }

  public Long getAccountId() {
    return accountId;
  }

  public void setAccountId(Long accountId) {
    this.accountId = accountId;
  }

  public String getDateRangeType() {
    return dateRangeType;
  }

  public void setDateRangeType(String dateRangeType) {
    this.dateRangeType = dateRangeType;
  }

  public Date getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Date timestamp) {
    this.timestamp = timestamp;
  }

  public String getDateStart() {
    return dateStart;
  }

  public void setDateStart(String dateStart) {
    this.dateStart = dateStart;
  }

  public String getDateEnd() {
    return dateEnd;
  }

  public void setDateEnd(String dateEnd) {
    this.dateEnd = dateEnd;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((accountId == null) ? 0 : accountId.hashCode());
    result = prime * result + ((dateEnd == null) ? 0 : dateEnd.hashCode());
    result = prime * result + ((dateRangeType == null) ? 0 : dateRangeType.hashCode());
    result = prime * result + ((dateStart == null) ? 0 : dateStart.hashCode());
    result = prime * result + ((partnerId == null) ? 0 : partnerId.hashCode());
    result = prime * result + ((timestamp == null) ? 0 : timestamp.hashCode());
    result = prime * result + ((topAccountId == null) ? 0 : topAccountId.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    Report other = (Report) obj;
    if (id == null) {
      if (other.id != null) {
        return false;
      }
    } else if (!id.equals(other.id)) {
      return false;
    }
    if (accountId == null) {
      if (other.accountId != null) {
        return false;
      }
    } else if (!accountId.equals(other.accountId)) {
      return false;
    }
    if (dateEnd == null) {
      if (other.dateEnd != null) {
        return false;
      }
    } else if (!dateEnd.equals(other.dateEnd)) {
      return false;
    }
    if (dateRangeType == null) {
      if (other.dateRangeType != null) {
        return false;
      }
    } else if (!dateRangeType.equals(other.dateRangeType)) {
      return false;
    }
    if (dateStart == null) {
      if (other.dateStart != null) {
        return false;
      }
    } else if (!dateStart.equals(other.dateStart)) {
      return false;
    }
    if (partnerId == null) {
      if (other.partnerId != null) {
        return false;
      }
    } else if (!partnerId.equals(other.partnerId)) {
      return false;
    }
    if (timestamp == null) {
      if (other.timestamp != null) {
        return false;
      }
    } else if (!timestamp.equals(other.timestamp)) {
      return false;
    }
    if (topAccountId == null) {
      if (other.topAccountId != null) {
        return false;
      }
    } else if (!topAccountId.equals(other.topAccountId)) {
      return false;
    }
    return true;
  }
}
