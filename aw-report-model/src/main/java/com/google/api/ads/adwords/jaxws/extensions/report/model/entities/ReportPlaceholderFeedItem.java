/**
 * 
 */
package com.google.api.ads.adwords.jaxws.extensions.report.model.entities;

import com.google.api.ads.adwords.jaxws.extensions.report.model.csv.annotation.CsvField;
import com.google.api.ads.adwords.jaxws.extensions.report.model.csv.annotation.CsvReport;
import com.google.api.ads.adwords.jaxws.extensions.report.model.util.BigDecimalUtil;
import com.google.api.ads.adwords.lib.jaxb.v201309.ReportDefinitionReportType;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Specific Report class for PlaceholderFeedItem
 *
 * @author markbowyer@google.com (Mark R. Bowyer)
 */
@Entity
@com.googlecode.objectify.annotation.Entity
@Table(name = "AW_ReportPlaceholderFeedItem")
@CsvReport(value = ReportDefinitionReportType.PLACEHOLDER_FEED_ITEM_REPORT)
public class ReportPlaceholderFeedItem extends ReportBase {

  @Column(name = "CAMPAIGN_ID")
  @CsvField(value = "Campaign ID", reportField = "CampaignId")
  private Long campaignId;

  @Column(name = "CAMPAIGN_NAME", length = 255)
  @CsvField(value = "Campaign", reportField = "CampaignName")
  private String campaignName;

  @Column(name = "STATUS", length = 32)
  @CsvField(value = "Feed item status", reportField = "Status")
  private String status;

  @Column(name = "ACCOUNT_TIMEZONE_ID", length = 255)
  @CsvField(value = "Time zone", reportField = "AccountTimeZoneId")
  private String timeZone;

  @Column(name = "ADGROUP_ID")
  @CsvField(value = "Ad group ID", reportField = "AdGroupId")
  private Long adGroupId;

  @Column(name = "ADGROUP_NAME", length = 255)
  @CsvField(value = "Ad group", reportField = "AdGroupName")
  private String adGroupName;

  @Column(name = "AD_ID")
  @CsvField(value = "Ad ID", reportField = "AdId")
  private Long adId;

  @Column(name = "CONVERSION_VALUE")
  @CsvField(value = "Total conv. value", reportField = "ConversionValue")
  private Long conversionValue;

  @Column(name = "CONVERSION_RATE_1")
  @CsvField(value = "Conv. rate (1-per-click)", reportField = "ConversionRate")
  private BigDecimal conversionRate1;

  @Column(name = "CONVERSION_RATE_MANY")
  @CsvField(value = "Conv. rate (many-per-click)", reportField = "ConversionRateManyPerClick")
  private BigDecimal conversionRateMany;

  @Column(name = "COST_PER_CONVERSION_1")
  @CsvField(value = "Cost / conv. (1-per-click)", reportField = "CostPerConversion")
  private BigDecimal costPerConversion1;

  @Column(name = "COST_PER_CONVERSION_MANY")
  @CsvField(value = "Cost / conv. (many-per-click)", reportField = "CostPerConversionManyPerClick")
  private BigDecimal costPerConversionMany;

  @Column(name = "CUSTOMER_DESCRIPTIVE_NAME", length = 255)
  @CsvField(value = "Client name", reportField = "CustomerDescriptiveName")
  private String customerDescriptiveName;

  @Column(name = "FEED_ID")
  @CsvField(value = "Feed ID", reportField = "FeedId")
  private Long feedId;

  @Column(name = "FEED_ITEM_ID")
  @CsvField(value = "Feed item ID", reportField = "FeedItemId")
  private Long feedItemId;

  @Column(name = "FEED_PLACEHOLDER_TYPE")
  @CsvField(value = "Feed placeholder type", reportField = "PlaceholderType")
  private int feedPlaceholderType;

  @Column(name = "PRIMARY_COMPANY_NAME", length = 255)
  @CsvField(value = "Company name", reportField = "PrimaryCompanyName")
  private String primaryCompanyName;

  @Column(name = "TOTAL_CONV_VALUE")
  @CsvField(value = "Total conv. value", reportField = "TotalConvValue")
  private Long totalConvValue;

  @Column(name = "VALUE_PER_CONVERSION_1")
  @CsvField(value = "Value / conv. (1-per-click)", reportField = "ValuePerConv")
  private BigDecimal valuePerConversion1;

  @Column(name = "VALUE_PER_CONVERSION_MANY")
  @CsvField(value = "Value / conv. (many-per-click)", reportField = "ValuePerConvManyPerClick")
  private BigDecimal valuePerConversionMany;

  /**
   * Hibernate needs an empty constructor
   */
  public ReportPlaceholderFeedItem() {}

  public ReportPlaceholderFeedItem(Long topAccountId, Long accountId) {
    this.topAccountId = topAccountId;
    this.accountId = accountId;
  }

  @Override
  public void setId() {

    // Generating unique id after having date and accountId
    if (this.getAccountId() != null) {
      this.id = this.getAccountId().toString();
    } else {
      this.id = "null";
    }
    if (this.getCampaignId() != null) {
      this.id += "-" + this.getCampaignId().toString();
    } else {
      this.id += "null";
    }
    if (this.getAdGroupId() != null) {
      this.id += "-" + this.getAdGroupId().toString();
    } else {
      this.id += "null";
    }
    if (this.getAdId() != null) {
      this.id += "-" + this.getAdId().toString();
    } else {
      this.id += "null";
    }
    if (this.getFeedItemId() != null) {
      this.id += "-" + this.getFeedItemId().toString();
    } else {
      this.id += "null";
    }
    if (this.getFeedId() != null) {
      this.id += "-" + this.getFeedId().toString();
    } else {
      this.id += "null";
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

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getTimeZone() {
    return timeZone;
  }

  public void setTimeZone(String timeZone) {
    this.timeZone = timeZone;
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

  public Long getAdId() {
    return adId;
  }

  public void setAdId(Long adId) {
    this.adId = adId;
  }

  public Long getConversionValue() {
    return conversionValue;
  }

  public void setConversionValue(Long conversionValue) {
    this.conversionValue = conversionValue;
  }

  public String getCostPerConversion1() {
    return costPerConversion1.toString();
  }

  public void setCostPerConversion1(BigDecimal costPerConversion1) {
    this.costPerConversion1 = costPerConversion1;
  }

  public void setCostPerConversion1(String costPerConversion1) {
    this.costPerConversion1 = BigDecimalUtil.parseFromNumberString(costPerConversion1);
  }

  public String getCostPerConversionMany() {
    return costPerConversionMany.toString();
  }

  public void setCostPerConversionMany(BigDecimal costPerConversionMany) {
    this.costPerConversionMany = costPerConversionMany;
  }

  public void setCostPerConversionMany(String costPerConversionMany) {
    this.costPerConversionMany = BigDecimalUtil.parseFromNumberString(costPerConversionMany);
  }

  public String getCustomerDescriptiveName() {
    return customerDescriptiveName;
  }

  public void setCustomerDescriptiveName(String customerDescriptiveName) {
    this.customerDescriptiveName = customerDescriptiveName;
  }

  public Long getFeedId() {
    return feedId;
  }

  public void setFeedId(Long feedId) {
    this.feedId = feedId;
  }

  public Long getFeedItemId() {
    return feedItemId;
  }

  public void setFeedItemId(Long feedItemId) {
    this.feedItemId = feedItemId;
  }

  public int getFeedPlaceholderType() {
    return feedPlaceholderType;
  }

  public void setFeedPlaceholderType(int feedPlaceholderType) {
    this.feedPlaceholderType = feedPlaceholderType;
  }

  public String getPrimaryCompanyName() {
    return primaryCompanyName;
  }

  public void setPrimaryCompanyName(String primaryCompanyName) {
    this.primaryCompanyName = primaryCompanyName;
  }

  public Long getTotalConvValue() {
    return totalConvValue;
  }

  public void setTotalConvValue(Long totalConvValue) {
    this.totalConvValue = totalConvValue;
  }

  public String getValuePerConversion1() {
    return BigDecimalUtil.formatAsReadable(valuePerConversion1);
  }

  public void setValuePerConversion1(BigDecimal valuePerConversion1) {
    this.valuePerConversion1 = valuePerConversion1;
  }

  public void setValuePerConversion1(String valuePerConversion1) {
    this.valuePerConversion1 = BigDecimalUtil.parseFromNumberString(valuePerConversion1);
  }

  public String getValuePerConversionMany() {
    return BigDecimalUtil.formatAsReadable(valuePerConversionMany);
  }

  public void setValuePerConversionMany(BigDecimal valuePerConversionMany) {
    this.valuePerConversionMany = valuePerConversionMany;
  }

  public void setValuePerConversionMany(String valuePerConversionMany) {
    this.valuePerConversionMany = BigDecimalUtil.parseFromNumberString(valuePerConversionMany);
  }

  public String getConversionRate1() {
    return BigDecimalUtil.formatAsReadable(conversionRate1);
  }

  public String getConversionRateMany() {
    return BigDecimalUtil.formatAsReadable(conversionRateMany);
  }

  public void setConversionRateMany(BigDecimal conversionRateMany) {
    this.conversionRateMany = conversionRateMany;
  }

  public void setConversionRateMany(String conversionRateMany) {
    this.conversionRateMany = BigDecimalUtil.parseFromNumberString(conversionRateMany);
  }

  public void setConversionRate1(BigDecimal conversionRate1) {
    this.conversionRate1 = conversionRate1;
  }

  public void setConversionRate1(String conversionRate1) {
    this.conversionRate1 = BigDecimalUtil.parseFromNumberString(conversionRate1);
  }
}
