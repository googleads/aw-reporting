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
 * Specific report class for ReportKeyword
 *
 * @author jtoledo@google.com (Julian Toledo)
 * @author gustavomoreira@google.com (Gustavo Moreira)
 * @author nafis@google.com (Nafis Zebarjadi)
 */
@Entity
@com.googlecode.objectify.annotation.Entity
@Table(name = "AW_ReportURL")
@CsvReport(value = ReportDefinitionReportType.URL_PERFORMANCE_REPORT,
    reportExclusions = {"AveragePosition", "Device", "ClickType"})
public class ReportUrl extends ReportBase {

  @Column(name = "ACCOUNT_TIME_ZONE_ID")
  @CsvField(value = "Time zone", reportField = "AccountTimeZoneId")
  private String accountTimeZoneId;

  @Column(name = "AD_FORMAT")
  @CsvField(value = "Ad type", reportField = "AdFormat")
  private String adFormat;

  @Column(name = "AD_GROUP_CRITERION_STATUS")
  @CsvField(value = "Keyword/Placement state", reportField = "AdGroupCriterionStatus")
  private String adGroupCriterionStatus;

  @Column(name = "AD_GROUP_ID")
  @CsvField(value = "Ad group ID", reportField = "AdGroupId")
  private Long adGroupId;

  @Column(name = "AD_GROUP_NAME")
  @CsvField(value = "Ad group", reportField = "AdGroupName")
  private String adGroupName;

  @Column(name = "AD_GROUP_STATUS")
  @CsvField(value = "Ad group state", reportField = "AdGroupStatus")
  private String adGroupStatus;

  @Column(name = "CAMPAIGN_ID")
  @CsvField(value = "Campaign ID", reportField = "CampaignId")
  private Long campaignId;

  @Column(name = "CAMPAIGN_NAME")
  @CsvField(value = "Campaign", reportField = "CampaignName")
  private String campaignName;

  @Column(name = "CAMPAIGN_STATUS")
  @CsvField(value = "Campaign state", reportField = "CampaignStatus")
  private String campaignStatus;

  @Column(name = "CONVERSION_RATE")
  @CsvField(value = "Conv. rate (1-per-click)", reportField = "ConversionRate")
  private BigDecimal conversionRate;

  @Column(name = "CONVERSION_RATE_MANY_PER_CLICK")
  @CsvField(value = "Conv. rate (many-per-click)", reportField = "ConversionRateManyPerClick")
  private BigDecimal conversionRateManyPerClick;

  @Column(name = "CONVERSIONS_MANY_PER_CLICK")
  @CsvField(value = "Conv. (many-per-click)", reportField = "ConversionsManyPerClick")
  private String conversionsManyPerClick;

  @Column(name = "CONVERSION_VALUE")
  @CsvField(value = "Total conv. value", reportField = "ConversionValue")
  private String conversionValue;

  @Column(name = "COST_PER_CONVERSION")
  @CsvField(value = "Cost / conv. (1-per-click)", reportField = "CostPerConversion")
  private BigDecimal costPerConversion;

  @Column(name = "COST_PER_CONVERSION_MANY_PER_CLICK")
  @CsvField(value = "Cost / conv. (many-per-click)", reportField = "CostPerConversionManyPerClick")
  private BigDecimal costPerConversionManyPerClick;

  @Column(name = "CRITERIA_PARAMETERS")
  @CsvField(value = "Keyword / Placement", reportField = "CriteriaParameters")
  public String criteriaParameters;

  @Column(name = "CUSTOMER_DESCRIPTIVE_NAME")
  @CsvField(value = "Client name", reportField = "CustomerDescriptiveName")
  private String customerDescriptiveName;

  @Column(name = "DAY_OF_WEEK")
  @CsvField(value = "Day of week", reportField = "DayOfWeek")
  private String dayOfWeek;

  @Column(name = "DISPLAY_NAME")
  @CsvField(value = "Criteria Display Name", reportField = "DisplayName")
  private String displayName;

  @Column(name = "DOMAIN")
  @CsvField(value = "Domain", reportField = "Domain")
  private String domain;

  @Column(name = "IS_AUTO_OPTIMIZED")
  @CsvField(value = "Targeting Mode", reportField = "IsAutoOptimized")
  private String isAutoOptimized;

  @Column(name = "IS_BID_ON_PATH")
  @CsvField(value = "Added", reportField = "IsBidOnPath")
  private String isBidOnPath;

  @Column(name = "IS_PATH_EXCLUDED")
  @CsvField(value = "Excluded", reportField = "IsPathExcluded")
  private String isPathExcluded;

  @Column(name = "MONTH_OF_YEAR")
  @CsvField(value = "Month of Year", reportField = "MonthOfYear")
  private String monthOfYear;

  @Column(name = "PRIMARY_COMPANY_NAME")
  @CsvField(value = "Company name", reportField = "PrimaryCompanyName")
  private String primaryCompanyName;

  @Column(name = "PRIMARY_USER_LOGIN")
  @CsvField(value = "Login email", reportField = "PrimaryUserLogin")
  private String primaryUserLogin;

  @Column(name = "QUARTER")
  @CsvField(value = "Quarter", reportField = "Quarter")
  private String quarter;

  @Column(name = "TOTAL_CONV_VALUE")
  @CsvField(value = "Total conv. value", reportField = "TotalConvValue")
  private String totalConvValue;

  @Column(name = "URL")
  @CsvField(value = "URL", reportField = "Url")
  private String url;

  @Column(name = "VALUE_PER_CONV")
  @CsvField(value = "Value / conv. (1-per-click)", reportField = "ValuePerConv")
  private BigDecimal valuePerConv;

  @Column(name = "VALUE_PER_CONVERSION")
  @CsvField(value = "Value / conv. (1-per-click)", reportField = "ValuePerConversion")
  private BigDecimal valuePerConversion;

  @Column(name = "VALUE_PER_CONVERSION_MANY_PER_CLICK")
  @CsvField(
      value = "Value / conv. (many-per-click)", reportField = "ValuePerConversionManyPerClick")
  private BigDecimal valuePerConversionManyPerClick;

  @Column(name = "VALUE_PER_CONV_MANY_PER_CLICK")
  @CsvField(value = "Value / conv. (many-per-click)", reportField = "ValuePerConvManyPerClick")
  private BigDecimal valuePerConvManyPerClick;

  @Column(name = "VIEW_THROUGH_CONVERSIONS")
  @CsvField(value = "View-through conv.", reportField = "ViewThroughConversions")
  private String viewThroughConversions;

  /**
   * Hibernate needs an empty constructor
   */
  public ReportUrl() {}

  public ReportUrl(Long topAccountId, Long accountId) {
    this.topAccountId = topAccountId;
    this.accountId = accountId;
  }

  @Override
  public void setId() {
    // Generating unique id after having accountId, campaignId, adGroupId and
    // url
    if (this.getAccountId() != null && this.getCampaignId() != null && this.getAdGroupId() != null
        && this.getUrl() != null) {
      this.id = this.getAccountId() + "-" + this.getCampaignId() + "-" + this.getAdGroupId() + "-"
          + this.getUrl();
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

  public String getAccountTimeZoneId() {
    return accountTimeZoneId;
  }

  public void setAccountTimeZoneId(String accountTimeZoneId) {
    this.accountTimeZoneId = accountTimeZoneId;
  }

  public String getAdFormat() {
    return adFormat;
  }

  public void setAdFormat(String adFormat) {
    this.adFormat = adFormat;
  }

  public String getAdGroupCriterionStatus() {
    return adGroupCriterionStatus;
  }

  public void setAdGroupCriterionStatus(String adGroupCriterionStatus) {
    this.adGroupCriterionStatus = adGroupCriterionStatus;
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

  public String getConversionRate() {
    return BigDecimalUtil.formatAsReadable(conversionRate);
  }

  public void setConversionRate(BigDecimal conversionRate) {
    this.conversionRate = conversionRate;
  }

  public void setConversionRate(String conversionRateString) {
    this.conversionRate = BigDecimalUtil.parseFromNumberString(conversionRateString);
  }

  public String getConversionRateManyPerClick() {
    return BigDecimalUtil.formatAsReadable(conversionRateManyPerClick);
  }

  public void setConversionRateManyPerClick(BigDecimal conversionRateManyPerClick) {
    this.conversionRateManyPerClick = conversionRateManyPerClick;
  }

  public void setConversionRateManyPerClick(String conversionRateManyPerClickString) {
    this.conversionRateManyPerClick =
        BigDecimalUtil.parseFromNumberString(conversionRateManyPerClickString);
  }

  public String getConversionsManyPerClick() {
    return conversionsManyPerClick;
  }

  public void setConversionsManyPerClick(String conversionsManyPerClick) {
    this.conversionsManyPerClick = conversionsManyPerClick;
  }

  public String getConversionValue() {
    return conversionValue;
  }

  public void setConversionValue(String conversionValue) {
    this.conversionValue = conversionValue;
  }

  public String getCostPerConversion() {
    return BigDecimalUtil.formatAsReadable(costPerConversion);
  }

  public void setCostPerConversion(BigDecimal costPerConversion) {
    this.costPerConversion = costPerConversion;
  }

  public void setCostPerConversion(String costPerConversionString) {
    this.costPerConversion = BigDecimalUtil.parseFromNumberString(costPerConversionString);
  }

  public String getCostPerConversionManyPerClick() {
    return BigDecimalUtil.formatAsReadable(costPerConversionManyPerClick);
  }

  public void setCostPerConversionManyPerClick(BigDecimal costPerConversionManyPerClick) {
    this.costPerConversionManyPerClick = costPerConversionManyPerClick;
  }

  public void setCostPerConversionManyPerClick(String costPerConversionManyPerClickString) {
    this.costPerConversionManyPerClick =
        BigDecimalUtil.parseFromNumberString(costPerConversionManyPerClickString);
  }

  public String getCriteriaParameters() {
    return criteriaParameters;
  }

  public void setCriteriaParameters(String criteriaParameters) {
    this.criteriaParameters = criteriaParameters;
  }

  public String getCustomerDescriptiveName() {
    return customerDescriptiveName;
  }

  public void setCustomerDescriptiveName(String customerDescriptiveName) {
    this.customerDescriptiveName = customerDescriptiveName;
  }

  public String getDayOfWeek() {
    return dayOfWeek;
  }

  public void setDayOfWeek(String dayOfWeek) {
    this.dayOfWeek = dayOfWeek;
  }

  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public String getDomain() {
    return domain;
  }

  public void setDomain(String domain) {
    this.domain = domain;
  }

  public String getIsAutoOptimized() {
    return isAutoOptimized;
  }

  public void setIsAutoOptimized(String isAutoOptimized) {
    this.isAutoOptimized = isAutoOptimized;
  }

  public String getIsBidOnPath() {
    return isBidOnPath;
  }

  public void setIsBidOnPath(String isBidOnPath) {
    this.isBidOnPath = isBidOnPath;
  }

  public String getIsPathExcluded() {
    return isPathExcluded;
  }

  public void setIsPathExcluded(String isPathExcluded) {
    this.isPathExcluded = isPathExcluded;
  }

  public String getMonthOfYear() {
    return monthOfYear;
  }

  public void setMonthOfYear(String monthOfYear) {
    this.monthOfYear = monthOfYear;
  }

  public String getPrimaryCompanyName() {
    return primaryCompanyName;
  }

  public void setPrimaryCompanyName(String primaryCompanyName) {
    this.primaryCompanyName = primaryCompanyName;
  }

  public String getPrimaryUserLogin() {
    return primaryUserLogin;
  }

  public void setPrimaryUserLogin(String primaryUserLogin) {
    this.primaryUserLogin = primaryUserLogin;
  }

  public String getQuarter() {
    return quarter;
  }

  public void setQuarter(String quarter) {
    this.quarter = quarter;
  }

  public String getTotalConvValue() {
    return totalConvValue;
  }

  public void setTotalConvValue(String totalConvValue) {
    this.totalConvValue = totalConvValue;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getValuePerConv() {
    return BigDecimalUtil.formatAsReadable(valuePerConv);
  }

  public void setValuePerConv(BigDecimal valuePerConv) {
    this.valuePerConv = valuePerConv;
  }

  public void setValuePerConv(String valuePerConvString) {
    this.valuePerConv = BigDecimalUtil.parseFromNumberString(valuePerConvString);
  }

  public String getValuePerConversion() {
    return valuePerConversion.toString();
  }

  public void setValuePerConversion(BigDecimal valuePerConversion) {
    this.valuePerConversion = valuePerConversion;
  }

  public void setValuePerConversion(String valuePerConversionString) {
    this.valuePerConversion = BigDecimalUtil.parseFromNumberString(valuePerConversionString);
  }

  public String getValuePerConversionManyPerClick() {
    return BigDecimalUtil.formatAsReadable(valuePerConversionManyPerClick);
  }

  public void setValuePerConversionManyPerClick(BigDecimal valuePerConversionManyPerClick) {
    this.valuePerConversionManyPerClick = valuePerConversionManyPerClick;
  }

  public void setValuePerConversionManyPerClick(String valuePerConversionManyPerClickString) {
    this.valuePerConversionManyPerClick =
        BigDecimalUtil.parseFromNumberString(valuePerConversionManyPerClickString);
  }

  public String getValuePerConvManyPerClick() {
    return BigDecimalUtil.formatAsReadable(valuePerConvManyPerClick);
  }

  public void setValuePerConvManyPerClick(BigDecimal valuePerConvManyPerClick) {
    this.valuePerConvManyPerClick = valuePerConvManyPerClick;
  }

  public void setValuePerConvManyPerClick(String valuePerConvManyPerClickString) {
    this.valuePerConvManyPerClick =
        BigDecimalUtil.parseFromNumberString(valuePerConvManyPerClickString);
  }

  public String getViewThroughConversions() {
    return viewThroughConversions;
  }

  public void setViewThroughConversions(String viewThroughConversions) {
    this.viewThroughConversions = viewThroughConversions;
  }
}
