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
import com.google.api.ads.adwords.awreporting.model.csv.annotation.CsvReport;
import com.google.api.ads.adwords.awreporting.model.csv.annotation.MoneyField;
import com.google.api.ads.adwords.awreporting.model.util.BigDecimalUtil;
import com.google.api.ads.adwords.lib.jaxb.v201806.ReportDefinitionReportType;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
   * Specific report class for BidGoalPerformanceReport.
 *
*/
@Entity
@Table(name = "AW_BidGoalPerformanceReport")
@CsvReport(value = ReportDefinitionReportType.BID_GOAL_PERFORMANCE_REPORT)
public class BidGoalPerformanceReport extends DateReport {

  @Column(name = "AdGroupCount")
  @CsvField(value = "Ad Groups", reportField = "AdGroupCount")
  private Integer adGroupCount;

  @Column(name = "CampaignCount")
  @CsvField(value = "Campaigns", reportField = "CampaignCount")
  private Integer campaignCount;

  @Column(name = "NonRemovedAdGroupCount")
  @CsvField(value = "Non-Removed Ad Groups", reportField = "NonRemovedAdGroupCount")
  private Long nonRemovedAdGroupCount;

  @Column(name = "NonRemovedAdGroupCriteriaCount")
  @CsvField(value = "Non-Removed Keywords", reportField = "NonRemovedAdGroupCriteriaCount")
  private Long nonRemovedAdGroupCriteriaCount;

  @Column(name = "NonRemovedCampaignCount")
  @CsvField(value = "Non-Removed Campaigns", reportField = "NonRemovedCampaignCount")
  private Long nonRemovedCampaignCount;

  @Column(name = "PageOnePromotedBidChangesForRaisesOnly")
  @CsvField(value = "Bid automation (Target search page location)", reportField = "PageOnePromotedBidChangesForRaisesOnly")
  private String pageOnePromotedBidChangesForRaisesOnly;

  @Column(name = "PageOnePromotedBidModifier")
  @CsvField(value = "Bid adjustment (Target search page location)", reportField = "PageOnePromotedBidModifier")
  private BigDecimal pageOnePromotedBidModifier;

  @Column(name = "PageOnePromotedStrategyGoal")
  @CsvField(value = "Location (Target search page location)", reportField = "PageOnePromotedStrategyGoal")
  private String pageOnePromotedStrategyGoal;

  @Column(name = "TargetCpa")
  @CsvField(value = "Target CPA", reportField = "TargetCpa")
  @MoneyField
  private BigDecimal targetCpa;

  @Column(name = "TargetRoas")
  @CsvField(value = "Target ROAS", reportField = "TargetRoas")
  private BigDecimal targetRoas;

  @Column(name = "AccountDescriptiveName")
  @CsvField(value = "Account", reportField = "AccountDescriptiveName")
  private String accountDescriptiveName;

  @Column(name = "AdGroupCriteriaCount")
  @CsvField(value = "Keywords", reportField = "AdGroupCriteriaCount")
  private Integer adGroupCriteriaCount;

  @Column(name = "AllConversionRate")
  @CsvField(value = "All conv. rate", reportField = "AllConversionRate")
  private BigDecimal allConversionRate;

  @Column(name = "AllConversions")
  @CsvField(value = "All conv.", reportField = "AllConversions")
  private BigDecimal allConversions;

  @Column(name = "AllConversionValue")
  @CsvField(value = "All conv. value", reportField = "AllConversionValue")
  private BigDecimal allConversionValue;

  @Column(name = "AverageCpc")
  @CsvField(value = "Avg. CPC", reportField = "AverageCpc")
  @MoneyField
  private BigDecimal averageCpc;

  @Column(name = "AverageCpm")
  @CsvField(value = "Avg. CPM", reportField = "AverageCpm")
  @MoneyField
  private BigDecimal averageCpm;

  @Column(name = "AveragePosition")
  @CsvField(value = "Avg. position", reportField = "AveragePosition")
  private BigDecimal averagePosition;

  @Column(name = "Clicks")
  @CsvField(value = "Clicks", reportField = "Clicks")
  private Long clicks;

  @Column(name = "ConversionCategoryName")
  @CsvField(value = "Conversion category", reportField = "ConversionCategoryName")
  private String conversionCategoryName;

  @Column(name = "ConversionRate")
  @CsvField(value = "Conv. rate", reportField = "ConversionRate")
  private BigDecimal conversionRate;

  @Column(name = "Conversions")
  @CsvField(value = "Conversions", reportField = "Conversions")
  private BigDecimal conversions;

  @Column(name = "ConversionTrackerId")
  @CsvField(value = "Conversion Tracker Id", reportField = "ConversionTrackerId")
  private Long conversionTrackerId;

  @Column(name = "ConversionTypeName")
  @CsvField(value = "Conversion name", reportField = "ConversionTypeName")
  private String conversionTypeName;

  @Column(name = "ConversionValue")
  @CsvField(value = "Total conv. value", reportField = "ConversionValue")
  private BigDecimal conversionValue;

  @Column(name = "Cost")
  @CsvField(value = "Cost", reportField = "Cost")
  @MoneyField
  private BigDecimal cost;

  @Column(name = "CostPerAllConversion")
  @CsvField(value = "Cost / all conv.", reportField = "CostPerAllConversion")
  @MoneyField
  private BigDecimal costPerAllConversion;

  @Column(name = "CostPerConversion")
  @CsvField(value = "Cost / conv.", reportField = "CostPerConversion")
  @MoneyField
  private BigDecimal costPerConversion;

  @Column(name = "CrossDeviceConversions")
  @CsvField(value = "Cross-device conv.", reportField = "CrossDeviceConversions")
  private BigDecimal crossDeviceConversions;

  @Column(name = "Ctr")
  @CsvField(value = "CTR", reportField = "Ctr")
  private BigDecimal ctr;

  @Column(name = "Device")
  @CsvField(value = "Device", reportField = "Device")
  private String device;

  @Column(name = "ExternalConversionSource")
  @CsvField(value = "Conversion source", reportField = "ExternalConversionSource")
  private String externalConversionSource;

  @Column(name = "Id")
  @CsvField(value = "Bid Strategy ID", reportField = "Id")
  private Long id;

  @Column(name = "Impressions")
  @CsvField(value = "Impressions", reportField = "Impressions")
  private Long impressions;

  @Column(name = "Name")
  @CsvField(value = "Bid Strategy Name", reportField = "Name")
  private String name;

  @Column(name = "PageOnePromotedBidCeiling")
  @CsvField(value = "Bid limit (Target search page location)", reportField = "PageOnePromotedBidCeiling")
  @MoneyField
  private BigDecimal pageOnePromotedBidCeiling;

  @Column(name = "PageOnePromotedRaiseBidWhenBudgetConstrained")
  @CsvField(value = "Limited budgets (Target search page location)", reportField = "PageOnePromotedRaiseBidWhenBudgetConstrained")
  private String pageOnePromotedRaiseBidWhenBudgetConstrained;

  @Column(name = "PageOnePromotedRaiseBidWhenLowQualityScore")
  @CsvField(value = "Low quality keywords (Target search page location)", reportField = "PageOnePromotedRaiseBidWhenLowQualityScore")
  private String pageOnePromotedRaiseBidWhenLowQualityScore;

  @Column(name = "Status")
  @CsvField(value = "State", reportField = "Status")
  private String status;

  @Column(name = "TargetCpaMaxCpcBidCeiling")
  @CsvField(value = "Upper Bid limit (Target CPA)", reportField = "TargetCpaMaxCpcBidCeiling")
  @MoneyField
  private BigDecimal targetCpaMaxCpcBidCeiling;

  @Column(name = "TargetCpaMaxCpcBidFloor")
  @CsvField(value = "Lower Bid limit (Target CPA)", reportField = "TargetCpaMaxCpcBidFloor")
  @MoneyField
  private BigDecimal targetCpaMaxCpcBidFloor;

  @Column(name = "TargetOutrankShare")
  @CsvField(value = "Target outranking share", reportField = "TargetOutrankShare")
  private BigDecimal targetOutrankShare;

  @Column(name = "TargetOutrankShareBidChangesForRaisesOnly")
  @CsvField(value = "Bid automation (Target Outranking Share)", reportField = "TargetOutrankShareBidChangesForRaisesOnly")
  private String targetOutrankShareBidChangesForRaisesOnly;

  @Column(name = "TargetOutrankShareCompetitorDomain")
  @CsvField(value = "Competitor domain (Target Outranking Share)", reportField = "TargetOutrankShareCompetitorDomain")
  private String targetOutrankShareCompetitorDomain;

  @Column(name = "TargetOutrankShareMaxCpcBidCeiling")
  @CsvField(value = "Upper Max Cpc Bid limit (Target Outranking Share)", reportField = "TargetOutrankShareMaxCpcBidCeiling")
  @MoneyField
  private BigDecimal targetOutrankShareMaxCpcBidCeiling;

  @Column(name = "TargetOutrankShareRaiseBidWhenLowQualityScore")
  @CsvField(value = "Low quality keywords (Target Outranking Share)", reportField = "TargetOutrankShareRaiseBidWhenLowQualityScore")
  private String targetOutrankShareRaiseBidWhenLowQualityScore;

  @Column(name = "TargetRoasBidCeiling")
  @CsvField(value = "Upper Bid limit (Target ROAS)", reportField = "TargetRoasBidCeiling")
  @MoneyField
  private BigDecimal targetRoasBidCeiling;

  @Column(name = "TargetRoasBidFloor")
  @CsvField(value = "Lower Bid limit (Target ROAS)", reportField = "TargetRoasBidFloor")
  @MoneyField
  private BigDecimal targetRoasBidFloor;

  @Column(name = "TargetSpendBidCeiling")
  @CsvField(value = "Bid limit (Maximize clicks)", reportField = "TargetSpendBidCeiling")
  @MoneyField
  private BigDecimal targetSpendBidCeiling;

  @Column(name = "TargetSpendSpendTarget")
  @CsvField(value = "Target spend (Maximize clicks)", reportField = "TargetSpendSpendTarget")
  @MoneyField
  private BigDecimal targetSpendSpendTarget;

  @Column(name = "Type")
  @CsvField(value = "Bid Strategy Type", reportField = "Type")
  private String type;

  @Column(name = "ValuePerAllConversion")
  @CsvField(value = "Value / all conv.", reportField = "ValuePerAllConversion")
  private BigDecimal valuePerAllConversion;

  @Column(name = "ValuePerConversion")
  @CsvField(value = "Value / conv.", reportField = "ValuePerConversion")
  private BigDecimal valuePerConversion;

  @Column(name = "ViewThroughConversions")
  @CsvField(value = "View-through conv.", reportField = "ViewThroughConversions")
  private Long viewThroughConversions;

  /**
   * Hibernate needs an empty constructor
   */
  public BidGoalPerformanceReport() {
  }

  public BidGoalPerformanceReport(Long topAccountId, Long accountId){
    super(topAccountId, accountId);
  }

  public Integer getAdGroupCount() {
    return adGroupCount;
  }

  public void setAdGroupCount(Integer adGroupCount) {
    this.adGroupCount = adGroupCount;
  }

  public Integer getCampaignCount() {
    return campaignCount;
  }

  public void setCampaignCount(Integer campaignCount) {
    this.campaignCount = campaignCount;
  }

  public Long getNonRemovedAdGroupCount() {
    return nonRemovedAdGroupCount;
  }

  public void setNonRemovedAdGroupCount(Long nonRemovedAdGroupCount) {
    this.nonRemovedAdGroupCount = nonRemovedAdGroupCount;
  }

  public Long getNonRemovedAdGroupCriteriaCount() {
    return nonRemovedAdGroupCriteriaCount;
  }

  public void setNonRemovedAdGroupCriteriaCount(Long nonRemovedAdGroupCriteriaCount) {
    this.nonRemovedAdGroupCriteriaCount = nonRemovedAdGroupCriteriaCount;
  }

  public Long getNonRemovedCampaignCount() {
    return nonRemovedCampaignCount;
  }

  public void setNonRemovedCampaignCount(Long nonRemovedCampaignCount) {
    this.nonRemovedCampaignCount = nonRemovedCampaignCount;
  }

  public String getPageOnePromotedBidChangesForRaisesOnly() {
    return pageOnePromotedBidChangesForRaisesOnly;
  }

  public void setPageOnePromotedBidChangesForRaisesOnly(String pageOnePromotedBidChangesForRaisesOnly) {
    this.pageOnePromotedBidChangesForRaisesOnly = pageOnePromotedBidChangesForRaisesOnly;
  }

  public String getPageOnePromotedBidModifier() {
    return BigDecimalUtil.formatAsReadable(pageOnePromotedBidModifier);
  }

  public BigDecimal getPageOnePromotedBidModifierBigDecimal() {
    return pageOnePromotedBidModifier;
  }

  public void setPageOnePromotedBidModifier(String pageOnePromotedBidModifier) {
    this.pageOnePromotedBidModifier = BigDecimalUtil.parseFromNumberString(pageOnePromotedBidModifier);
  }

  public String getPageOnePromotedStrategyGoal() {
    return pageOnePromotedStrategyGoal;
  }

  public void setPageOnePromotedStrategyGoal(String pageOnePromotedStrategyGoal) {
    this.pageOnePromotedStrategyGoal = pageOnePromotedStrategyGoal;
  }

  public BigDecimal getTargetCpa() {
    return targetCpa;
  }

  public void setTargetCpa(BigDecimal targetCpa) {
    this.targetCpa = targetCpa;
  }

  public String getTargetRoas() {
    return BigDecimalUtil.formatAsReadable(targetRoas);
  }

  public BigDecimal getTargetRoasBigDecimal() {
    return targetRoas;
  }

  public void setTargetRoas(String targetRoas) {
    this.targetRoas = BigDecimalUtil.parseFromNumberString(targetRoas);
  }

  public String getAccountDescriptiveName() {
    return accountDescriptiveName;
  }

  public void setAccountDescriptiveName(String accountDescriptiveName) {
    this.accountDescriptiveName = accountDescriptiveName;
  }

  public Integer getAdGroupCriteriaCount() {
    return adGroupCriteriaCount;
  }

  public void setAdGroupCriteriaCount(Integer adGroupCriteriaCount) {
    this.adGroupCriteriaCount = adGroupCriteriaCount;
  }

  public String getAllConversionRate() {
    return BigDecimalUtil.formatAsReadable(allConversionRate);
  }

  public BigDecimal getAllConversionRateBigDecimal() {
    return allConversionRate;
  }

  public void setAllConversionRate(String allConversionRate) {
    this.allConversionRate = BigDecimalUtil.parseFromNumberString(allConversionRate);
  }

  public String getAllConversions() {
    return BigDecimalUtil.formatAsReadable(allConversions);
  }

  public BigDecimal getAllConversionsBigDecimal() {
    return allConversions;
  }

  public void setAllConversions(String allConversions) {
    this.allConversions = BigDecimalUtil.parseFromNumberString(allConversions);
  }

  public String getAllConversionValue() {
    return BigDecimalUtil.formatAsReadable(allConversionValue);
  }

  public BigDecimal getAllConversionValueBigDecimal() {
    return allConversionValue;
  }

  public void setAllConversionValue(String allConversionValue) {
    this.allConversionValue = BigDecimalUtil.parseFromNumberString(allConversionValue);
  }

  public BigDecimal getAverageCpc() {
    return averageCpc;
  }

  public void setAverageCpc(BigDecimal averageCpc) {
    this.averageCpc = averageCpc;
  }

  public BigDecimal getAverageCpm() {
    return averageCpm;
  }

  public void setAverageCpm(BigDecimal averageCpm) {
    this.averageCpm = averageCpm;
  }

  public String getAveragePosition() {
    return BigDecimalUtil.formatAsReadable(averagePosition);
  }

  public BigDecimal getAveragePositionBigDecimal() {
    return averagePosition;
  }

  public void setAveragePosition(String averagePosition) {
    this.averagePosition = BigDecimalUtil.parseFromNumberString(averagePosition);
  }

  public Long getClicks() {
    return clicks;
  }

  public void setClicks(Long clicks) {
    this.clicks = clicks;
  }

  public String getConversionCategoryName() {
    return conversionCategoryName;
  }

  public void setConversionCategoryName(String conversionCategoryName) {
    this.conversionCategoryName = conversionCategoryName;
  }

  public String getConversionRate() {
    return BigDecimalUtil.formatAsReadable(conversionRate);
  }

  public BigDecimal getConversionRateBigDecimal() {
    return conversionRate;
  }

  public void setConversionRate(String conversionRate) {
    this.conversionRate = BigDecimalUtil.parseFromNumberString(conversionRate);
  }

  public String getConversions() {
    return BigDecimalUtil.formatAsReadable(conversions);
  }

  public BigDecimal getConversionsBigDecimal() {
    return conversions;
  }

  public void setConversions(String conversions) {
    this.conversions = BigDecimalUtil.parseFromNumberString(conversions);
  }

  public Long getConversionTrackerId() {
    return conversionTrackerId;
  }

  public void setConversionTrackerId(Long conversionTrackerId) {
    this.conversionTrackerId = conversionTrackerId;
  }

  public String getConversionTypeName() {
    return conversionTypeName;
  }

  public void setConversionTypeName(String conversionTypeName) {
    this.conversionTypeName = conversionTypeName;
  }

  public String getConversionValue() {
    return BigDecimalUtil.formatAsReadable(conversionValue);
  }

  public BigDecimal getConversionValueBigDecimal() {
    return conversionValue;
  }

  public void setConversionValue(String conversionValue) {
    this.conversionValue = BigDecimalUtil.parseFromNumberString(conversionValue);
  }

  public BigDecimal getCost() {
    return cost;
  }

  public void setCost(BigDecimal cost) {
    this.cost = cost;
  }

  public BigDecimal getCostPerAllConversion() {
    return costPerAllConversion;
  }

  public void setCostPerAllConversion(BigDecimal costPerAllConversion) {
    this.costPerAllConversion = costPerAllConversion;
  }

  public BigDecimal getCostPerConversion() {
    return costPerConversion;
  }

  public void setCostPerConversion(BigDecimal costPerConversion) {
    this.costPerConversion = costPerConversion;
  }

  public String getCrossDeviceConversions() {
    return BigDecimalUtil.formatAsReadable(crossDeviceConversions);
  }

  public BigDecimal getCrossDeviceConversionsBigDecimal() {
    return crossDeviceConversions;
  }

  public void setCrossDeviceConversions(String crossDeviceConversions) {
    this.crossDeviceConversions = BigDecimalUtil.parseFromNumberString(crossDeviceConversions);
  }

  public String getCtr() {
    return BigDecimalUtil.formatAsReadable(ctr);
  }

  public BigDecimal getCtrBigDecimal() {
    return ctr;
  }

  public void setCtr(String ctr) {
    this.ctr = (ctr == null ? null : BigDecimalUtil.parseFromNumberString(ctr.replace("%","")));
  }

  public String getDevice() {
    return device;
  }

  public void setDevice(String device) {
    this.device = device;
  }

  public String getExternalConversionSource() {
    return externalConversionSource;
  }

  public void setExternalConversionSource(String externalConversionSource) {
    this.externalConversionSource = externalConversionSource;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getImpressions() {
    return impressions;
  }

  public void setImpressions(Long impressions) {
    this.impressions = impressions;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public BigDecimal getPageOnePromotedBidCeiling() {
    return pageOnePromotedBidCeiling;
  }

  public void setPageOnePromotedBidCeiling(BigDecimal pageOnePromotedBidCeiling) {
    this.pageOnePromotedBidCeiling = pageOnePromotedBidCeiling;
  }

  public String getPageOnePromotedRaiseBidWhenBudgetConstrained() {
    return pageOnePromotedRaiseBidWhenBudgetConstrained;
  }

  public void setPageOnePromotedRaiseBidWhenBudgetConstrained(String pageOnePromotedRaiseBidWhenBudgetConstrained) {
    this.pageOnePromotedRaiseBidWhenBudgetConstrained = pageOnePromotedRaiseBidWhenBudgetConstrained;
  }

  public String getPageOnePromotedRaiseBidWhenLowQualityScore() {
    return pageOnePromotedRaiseBidWhenLowQualityScore;
  }

  public void setPageOnePromotedRaiseBidWhenLowQualityScore(String pageOnePromotedRaiseBidWhenLowQualityScore) {
    this.pageOnePromotedRaiseBidWhenLowQualityScore = pageOnePromotedRaiseBidWhenLowQualityScore;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public BigDecimal getTargetCpaMaxCpcBidCeiling() {
    return targetCpaMaxCpcBidCeiling;
  }

  public void setTargetCpaMaxCpcBidCeiling(BigDecimal targetCpaMaxCpcBidCeiling) {
    this.targetCpaMaxCpcBidCeiling = targetCpaMaxCpcBidCeiling;
  }

  public BigDecimal getTargetCpaMaxCpcBidFloor() {
    return targetCpaMaxCpcBidFloor;
  }

  public void setTargetCpaMaxCpcBidFloor(BigDecimal targetCpaMaxCpcBidFloor) {
    this.targetCpaMaxCpcBidFloor = targetCpaMaxCpcBidFloor;
  }

  public String getTargetOutrankShare() {
    return BigDecimalUtil.formatAsReadable(targetOutrankShare);
  }

  public BigDecimal getTargetOutrankShareBigDecimal() {
    return targetOutrankShare;
  }

  public void setTargetOutrankShare(String targetOutrankShare) {
    this.targetOutrankShare = BigDecimalUtil.parseFromNumberString(targetOutrankShare);
  }

  public String getTargetOutrankShareBidChangesForRaisesOnly() {
    return targetOutrankShareBidChangesForRaisesOnly;
  }

  public void setTargetOutrankShareBidChangesForRaisesOnly(String targetOutrankShareBidChangesForRaisesOnly) {
    this.targetOutrankShareBidChangesForRaisesOnly = targetOutrankShareBidChangesForRaisesOnly;
  }

  public String getTargetOutrankShareCompetitorDomain() {
    return targetOutrankShareCompetitorDomain;
  }

  public void setTargetOutrankShareCompetitorDomain(String targetOutrankShareCompetitorDomain) {
    this.targetOutrankShareCompetitorDomain = targetOutrankShareCompetitorDomain;
  }

  public BigDecimal getTargetOutrankShareMaxCpcBidCeiling() {
    return targetOutrankShareMaxCpcBidCeiling;
  }

  public void setTargetOutrankShareMaxCpcBidCeiling(BigDecimal targetOutrankShareMaxCpcBidCeiling) {
    this.targetOutrankShareMaxCpcBidCeiling = targetOutrankShareMaxCpcBidCeiling;
  }

  public String getTargetOutrankShareRaiseBidWhenLowQualityScore() {
    return targetOutrankShareRaiseBidWhenLowQualityScore;
  }

  public void setTargetOutrankShareRaiseBidWhenLowQualityScore(String targetOutrankShareRaiseBidWhenLowQualityScore) {
    this.targetOutrankShareRaiseBidWhenLowQualityScore = targetOutrankShareRaiseBidWhenLowQualityScore;
  }

  public BigDecimal getTargetRoasBidCeiling() {
    return targetRoasBidCeiling;
  }

  public void setTargetRoasBidCeiling(BigDecimal targetRoasBidCeiling) {
    this.targetRoasBidCeiling = targetRoasBidCeiling;
  }

  public BigDecimal getTargetRoasBidFloor() {
    return targetRoasBidFloor;
  }

  public void setTargetRoasBidFloor(BigDecimal targetRoasBidFloor) {
    this.targetRoasBidFloor = targetRoasBidFloor;
  }

  public BigDecimal getTargetSpendBidCeiling() {
    return targetSpendBidCeiling;
  }

  public void setTargetSpendBidCeiling(BigDecimal targetSpendBidCeiling) {
    this.targetSpendBidCeiling = targetSpendBidCeiling;
  }

  public BigDecimal getTargetSpendSpendTarget() {
    return targetSpendSpendTarget;
  }

  public void setTargetSpendSpendTarget(BigDecimal targetSpendSpendTarget) {
    this.targetSpendSpendTarget = targetSpendSpendTarget;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getValuePerAllConversion() {
    return BigDecimalUtil.formatAsReadable(valuePerAllConversion);
  }

  public BigDecimal getValuePerAllConversionBigDecimal() {
    return valuePerAllConversion;
  }

  public void setValuePerAllConversion(String valuePerAllConversion) {
    this.valuePerAllConversion = BigDecimalUtil.parseFromNumberString(valuePerAllConversion);
  }

  public String getValuePerConversion() {
    return BigDecimalUtil.formatAsReadable(valuePerConversion);
  }

  public BigDecimal getValuePerConversionBigDecimal() {
    return valuePerConversion;
  }

  public void setValuePerConversion(String valuePerConversion) {
    this.valuePerConversion = BigDecimalUtil.parseFromNumberString(valuePerConversion);
  }

  public Long getViewThroughConversions() {
    return viewThroughConversions;
  }

  public void setViewThroughConversions(Long viewThroughConversions) {
    this.viewThroughConversions = viewThroughConversions;
  }

  @Override
  public void setRowId() {
    // General fields for generating unique id.
    StringBuilder idBuilder = new StringBuilder(getCustomerId().toString());
    if (id != null) {
      idBuilder.append("-").append(id);
    }
    idBuilder.append("-").append(getDateLabel());

    // Include all segmentation fields (if set).
    if (!StringUtils.isEmpty(conversionCategoryName)) {
      idBuilder.append("-").append(conversionCategoryName);
    }
    if (conversionTrackerId != null) {
      idBuilder.append("-").append(conversionTrackerId);
    }
    if (!StringUtils.isEmpty(conversionTypeName)) {
      idBuilder.append("-").append(conversionTypeName);
    }
    if (!StringUtils.isEmpty(device)) {
      idBuilder.append("-").append(device);
    }
    if (!StringUtils.isEmpty(externalConversionSource)) {
      idBuilder.append("-").append(externalConversionSource);
    }
    this.rowId = idBuilder.toString();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) { return false; }
    if (obj == this) { return true; }
    if (obj.getClass() != getClass()) { return false; }
    BidGoalPerformanceReport other = (BidGoalPerformanceReport) obj;
    return new EqualsBuilder()
      .appendSuper(super.equals(obj))
      .append(adGroupCount, other.adGroupCount)
      .append(campaignCount, other.campaignCount)
      .append(nonRemovedAdGroupCount, other.nonRemovedAdGroupCount)
      .append(nonRemovedAdGroupCriteriaCount, other.nonRemovedAdGroupCriteriaCount)
      .append(nonRemovedCampaignCount, other.nonRemovedCampaignCount)
      .append(pageOnePromotedBidChangesForRaisesOnly, other.pageOnePromotedBidChangesForRaisesOnly)
      .append(pageOnePromotedBidModifier, other.pageOnePromotedBidModifier)
      .append(pageOnePromotedStrategyGoal, other.pageOnePromotedStrategyGoal)
      .append(targetCpa, other.targetCpa)
      .append(targetRoas, other.targetRoas)
      .append(accountDescriptiveName, other.accountDescriptiveName)
      .append(adGroupCriteriaCount, other.adGroupCriteriaCount)
      .append(allConversionRate, other.allConversionRate)
      .append(allConversions, other.allConversions)
      .append(allConversionValue, other.allConversionValue)
      .append(averageCpc, other.averageCpc)
      .append(averageCpm, other.averageCpm)
      .append(averagePosition, other.averagePosition)
      .append(clicks, other.clicks)
      .append(conversionCategoryName, other.conversionCategoryName)
      .append(conversionRate, other.conversionRate)
      .append(conversions, other.conversions)
      .append(conversionTrackerId, other.conversionTrackerId)
      .append(conversionTypeName, other.conversionTypeName)
      .append(conversionValue, other.conversionValue)
      .append(cost, other.cost)
      .append(costPerAllConversion, other.costPerAllConversion)
      .append(costPerConversion, other.costPerConversion)
      .append(crossDeviceConversions, other.crossDeviceConversions)
      .append(ctr, other.ctr)
      .append(device, other.device)
      .append(externalConversionSource, other.externalConversionSource)
      .append(id, other.id)
      .append(impressions, other.impressions)
      .append(name, other.name)
      .append(pageOnePromotedBidCeiling, other.pageOnePromotedBidCeiling)
      .append(pageOnePromotedRaiseBidWhenBudgetConstrained, other.pageOnePromotedRaiseBidWhenBudgetConstrained)
      .append(pageOnePromotedRaiseBidWhenLowQualityScore, other.pageOnePromotedRaiseBidWhenLowQualityScore)
      .append(status, other.status)
      .append(targetCpaMaxCpcBidCeiling, other.targetCpaMaxCpcBidCeiling)
      .append(targetCpaMaxCpcBidFloor, other.targetCpaMaxCpcBidFloor)
      .append(targetOutrankShare, other.targetOutrankShare)
      .append(targetOutrankShareBidChangesForRaisesOnly, other.targetOutrankShareBidChangesForRaisesOnly)
      .append(targetOutrankShareCompetitorDomain, other.targetOutrankShareCompetitorDomain)
      .append(targetOutrankShareMaxCpcBidCeiling, other.targetOutrankShareMaxCpcBidCeiling)
      .append(targetOutrankShareRaiseBidWhenLowQualityScore, other.targetOutrankShareRaiseBidWhenLowQualityScore)
      .append(targetRoasBidCeiling, other.targetRoasBidCeiling)
      .append(targetRoasBidFloor, other.targetRoasBidFloor)
      .append(targetSpendBidCeiling, other.targetSpendBidCeiling)
      .append(targetSpendSpendTarget, other.targetSpendSpendTarget)
      .append(type, other.type)
      .append(valuePerAllConversion, other.valuePerAllConversion)
      .append(valuePerConversion, other.valuePerConversion)
      .append(viewThroughConversions, other.viewThroughConversions)
      .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
      .appendSuper(super.hashCode())
      .append(adGroupCount)
      .append(campaignCount)
      .append(nonRemovedAdGroupCount)
      .append(nonRemovedAdGroupCriteriaCount)
      .append(nonRemovedCampaignCount)
      .append(pageOnePromotedBidChangesForRaisesOnly)
      .append(pageOnePromotedBidModifier)
      .append(pageOnePromotedStrategyGoal)
      .append(targetCpa)
      .append(targetRoas)
      .append(accountDescriptiveName)
      .append(adGroupCriteriaCount)
      .append(allConversionRate)
      .append(allConversions)
      .append(allConversionValue)
      .append(averageCpc)
      .append(averageCpm)
      .append(averagePosition)
      .append(clicks)
      .append(conversionCategoryName)
      .append(conversionRate)
      .append(conversions)
      .append(conversionTrackerId)
      .append(conversionTypeName)
      .append(conversionValue)
      .append(cost)
      .append(costPerAllConversion)
      .append(costPerConversion)
      .append(crossDeviceConversions)
      .append(ctr)
      .append(device)
      .append(externalConversionSource)
      .append(id)
      .append(impressions)
      .append(name)
      .append(pageOnePromotedBidCeiling)
      .append(pageOnePromotedRaiseBidWhenBudgetConstrained)
      .append(pageOnePromotedRaiseBidWhenLowQualityScore)
      .append(status)
      .append(targetCpaMaxCpcBidCeiling)
      .append(targetCpaMaxCpcBidFloor)
      .append(targetOutrankShare)
      .append(targetOutrankShareBidChangesForRaisesOnly)
      .append(targetOutrankShareCompetitorDomain)
      .append(targetOutrankShareMaxCpcBidCeiling)
      .append(targetOutrankShareRaiseBidWhenLowQualityScore)
      .append(targetRoasBidCeiling)
      .append(targetRoasBidFloor)
      .append(targetSpendBidCeiling)
      .append(targetSpendSpendTarget)
      .append(type)
      .append(valuePerAllConversion)
      .append(valuePerConversion)
      .append(viewThroughConversions)
      .toHashCode();
  }

}
