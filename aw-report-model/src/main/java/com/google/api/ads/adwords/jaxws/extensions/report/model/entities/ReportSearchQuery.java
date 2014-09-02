package com.google.api.ads.adwords.jaxws.extensions.report.model.entities;

import com.google.api.ads.adwords.jaxws.extensions.report.model.csv.annotation.CsvField;
import com.google.api.ads.adwords.jaxws.extensions.report.model.csv.annotation.CsvReport;
import com.google.api.ads.adwords.lib.jaxb.v201406.ReportDefinitionReportType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Report class for the Search Query Performance report.
 *
 * @author gustavomoreira@google.com (Gustavo Moreira)
 */
@Entity
@com.googlecode.objectify.annotation.Entity
@Table(name = "AW_ReportSearchQuery")
@CsvReport(value = ReportDefinitionReportType.SEARCH_QUERY_PERFORMANCE_REPORT)
public class ReportSearchQuery extends ReportBase {

  @Column(name = "AD_FORMAT")
  @CsvField(value = "Ad type", reportField = "AdFormat")
  private String adFormat;

  @Column(name = "ADGROUP_ID")
  @CsvField(value = "Ad group ID", reportField = "AdGroupId")
  private Long adGroupId;

  @Column(name = "ADGROUP_NAME")
  @CsvField(value = "Ad group", reportField = "AdGroupName")
  private String adGroupName;

  @Column(name = "ADGROUPT_STATUS")
  @CsvField(value = "Ad group state", reportField = "AdGroupStatus")
  private String adGroupStatus;

  @Column(name = "CAMPAIGN_ID")
  @CsvField(value = "Campaign ID", reportField = "CampaignId")
  private Long campaignId;

  @Column(name = "CAMPAIGN_NAME", length = 255)
  @CsvField(value = "Campaign", reportField = "CampaignName")
  private String campaignName;

  @Column(name = "CAMPAIGN_STATUS", length = 32)
  @CsvField(value = "Campaign state", reportField = "CampaignStatus")
  private String campaignStatus;

  @Column(name = "CREATIVE_ID")
  @CsvField(value = "Ad ID", reportField = "CreativeId")
  private Long creativeId;

  @Column(name = "DESTINATION_URL")
  @CsvField(value = "Destination URL", reportField = "DestinationUrl")
  private String DestinationUrl;

  @Column(name = "KEYWORD_ID")
  @CsvField(value = "Keyword ID", reportField = "KeywordId")
  private Long keywordId;

  @Column(name = "KEYWORDTEXTMATCHINGQUERY")
  @CsvField(value = "Keyword", reportField = "KeywordTextMatchingQuery")
  private String keywordTextMatchingQuery;

  @Column(name = "MATCH_TYPE")
  @CsvField(value = "Match type", reportField = "MatchType")
  private String matchType;

  @Column(name = "QUERY")
  @CsvField(value = "Search term", reportField = "Query")
  private String query;

  @Override
  public void setId() {

    // Generating unique id after having accountId, campaignId and date
    if (this.getAccountId() != null && this.getCampaignId() != null) {
      this.id = this.getAccountId() + "-" + this.getCampaignId();
    }

    this.id += setIdDates();

    // Adding extra fields for unique ID
    if (this.getAdGroupId() != null) {
      this.id += "-" + this.getAdGroupId();
    }
    if (this.getCreativeId() != null) {
      this.id += "-" + this.getCreativeId();
    }
    if (this.getKeywordId() != null) {
      this.id += "-" + this.getKeywordId();
    }
    if (this.getAdFormat() != null && this.getAdFormat().length() > 0) {
      this.id += "-" + this.getAdFormat();
    }
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
    if (this.getQuery() != null && this.getQuery().length() > 0) {
      this.id += "-" + this.getQuery();
    }

  }

  public String getAdFormat() {
    return adFormat;
  }

  public void setAdFormat(String adFormat) {
    this.adFormat = adFormat;
  }

  public Long getAdGroupId() {
    return adGroupId;
  }

  public void setAdGroupId(Long adGroupId) {
    this.adGroupId = adGroupId;
  }

  public String getAdGroupName() {
    return adGroupName;
  }

  public void setAdGroupName(String adGroupName) {
    this.adGroupName = adGroupName;
  }

  public String getAdGroupStatus() {
    return adGroupStatus;
  }

  public void setAdGroupStatus(String adGroupStatus) {
    this.adGroupStatus = adGroupStatus;
  }

  public Long getCampaignId() {
    return campaignId;
  }

  public void setCampaignId(Long campaignId) {
    this.campaignId = campaignId;
  }

  public String getCampaignName() {
    return campaignName;
  }

  public void setCampaignName(String campaignName) {
    this.campaignName = campaignName;
  }

  public String getCampaignStatus() {
    return campaignStatus;
  }

  public void setCampaignStatus(String campaignStatus) {
    this.campaignStatus = campaignStatus;
  }

  public Long getCreativeId() {
    return creativeId;
  }

  public void setCreativeId(Long creativeId) {
    this.creativeId = creativeId;
  }

  public String getDestinationUrl() {
    return DestinationUrl;
  }

  public void setDestinationUrl(String destinationUrl) {
    DestinationUrl = destinationUrl;
  }

  public Long getKeywordId() {
    return keywordId;
  }

  public void setKeywordId(Long keywordId) {
    this.keywordId = keywordId;
  }

  public String getKeywordTextMatchingQuery() {
    return keywordTextMatchingQuery;
  }

  public void setKeywordTextMatchingQuery(String keywordTextMatchingQuery) {
    this.keywordTextMatchingQuery = keywordTextMatchingQuery;
  }

  public String getMatchType() {
    return matchType;
  }

  public void setMatchType(String matchType) {
    this.matchType = matchType;
  }

  public String getQuery() {
    return query;
  }

  public void setQuery(String query) {
    this.query = query;
  }

}
