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
import com.google.api.ads.adwords.jaxws.extensions.report.model.csv.annotation.CsvReport;
import com.google.api.ads.adwords.jaxws.extensions.report.model.util.BigDecimalUtil;
import com.google.api.ads.adwords.lib.jaxb.v201309.ReportDefinitionReportType;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * Specific report class for ReportKeyword
 *
 * @author jtoledo@google.com (Julian Toledo)
 * @author gustavomoreira
 */
@Entity
@com.googlecode.objectify.annotation.Entity
@Table(name = "AW_ReportKeyword")
@CsvReport(value = ReportDefinitionReportType.KEYWORDS_PERFORMANCE_REPORT)
public class ReportKeyword extends ReportBase {

  @Column(name = "KEYWORD_ID")
  @CsvField(value = "Keyword ID", reportField = "Id")
  private Long keywordId;

  @Column(name = "QUALITY_SCORE")
  @CsvField(value = "Quality score", reportField = "QualityScore")
  private BigDecimal qualityScore;

  @Column(name = "KEYWORD_MATCH_TYPE", length = 32)
  @CsvField(value = "Match type", reportField = "KeywordMatchType")
  private String keywordMatchType;

  @Column(name = "KEYWORD_TEXT", length = 255)
  @CsvField(value = "Keyword", reportField = "KeywordText")
  private String keywordText;

  @Lob
  @Column(name = "DESTINATION_URL", length = 2048)
  @CsvField(value = "Destination URL", reportField = "DestinationUrl")
  private String destinationUrl;

  @Column(name = "ADGROUP_ID")
  @CsvField(value = "Ad group ID", reportField = "AdGroupId")
  private Long adGroupId;

  @Column(name = "CAMPAIGN_ID")
  @CsvField(value = "Campaign ID", reportField = "CampaignId")
  private Long campaignId;

  @Column(name = "STATUS", length = 32)
  @CsvField(value = "Keyword state", reportField = "Status")
  private String status;

  @Column(name = "IS_NEGATIVE")
  @CsvField(value = "Is negative", reportField = "IsNegative")
  private boolean negative;

  /**
   * Hibernate needs an empty constructor
   */
  public ReportKeyword() {
  }

  public ReportKeyword(Long topAccountId, Long accountId) {
    this.topAccountId = topAccountId;
    this.accountId = accountId;
  }

  @Override
  public void setId() {
    // Generating unique id after having accountId, campaignId, adGroupId and date
    if (this.getAccountId() != null && this.getCampaignId() != null && this.getAdGroupId() != null
        && this.getKeywordId() != null) {
      this.id = this.getAccountId() + "-" + this.getCampaignId() + "-" + this.getAdGroupId() + "-"
          + this.getKeywordId();
    }

    this.id += setIdDates();

    // Adding extra fields for unique ID
    if (this.getAdNetwork() != null && this.getAdNetwork().length() > 0) {
      this.id += "-" + this.getAdNetwork();
    }
    if (this.getAdNetworkPartners() != null && this.getAdNetworkPartners().length() > 0) {
      this.id += "-" + this.getAdNetworkPartners();
    }
    if (this.getDevice() != null && this.getDevice().length() > 0) {
      this.id += "-" + this.getDevice();
    }
    if (this.getClickType() != null && this.getClickType().length() > 0) {
      this.id += "-" + this.getClickType();
    }
  }

  // adGroupId
  public Long getKeywordId() {
    return keywordId;
  }

  public void setKeywordId(Long keywordId) {
    this.keywordId = keywordId;
  }

  // qualityScore
  public BigDecimal getQualityScoreAsBigDecimal() {
    return qualityScore;
  }

  public String getQualityScore() {
    return BigDecimalUtil.formatAsReadable(qualityScore);
  }

  public void setQualityScore(BigDecimal qualityScore) {
    this.qualityScore = qualityScore;
  }

  public void setQualityScore(String qualityScore) {
    this.qualityScore = BigDecimalUtil.parseFromNumberString(qualityScore);
  }

  // keywordMatchType
  public String getKeywordMatchType() {
    return keywordMatchType;
  }

  public void setKeywordMatchType(String keywordMatchType) {
    this.keywordMatchType = keywordMatchType;
  }

  // keywordText
  public String getKeywordText() {
    return keywordText;
  }

  public void setKeywordText(String keywordText) {
    this.keywordText = keywordText;
  }

  // destinationUrl
  public String getDestinationUrl() {
    return destinationUrl;
  }

  public void setDestinationUrl(String destinationUrl) {
    this.destinationUrl = destinationUrl;
  }

  // adGroupId
  public Long getAdGroupId() {
    return adGroupId;
  }

  public void setAdGroupId(Long adGroupId) {
    this.adGroupId = adGroupId;
  }

  // campaignId
  public Long getCampaignId() {
    return campaignId;
  }

  public void setCampaignId(Long campaignId) {
    this.campaignId = campaignId;
  }

  // status
  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  // negative
  public boolean isNegative() {
    return negative;
  }

  public void setNegative(String negative) {
    this.negative = Boolean.parseBoolean(negative);
  }

  public void setNegative(boolean negative) {
    this.negative = negative;
  }
}
