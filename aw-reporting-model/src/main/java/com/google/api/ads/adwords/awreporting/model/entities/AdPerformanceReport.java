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
   * Specific report class for AdPerformanceReport.
 *
*/
@Entity
@Table(name = "AW_AdPerformanceReport")
@CsvReport(value = ReportDefinitionReportType.AD_PERFORMANCE_REPORT)
public class AdPerformanceReport extends DateReport {

  @Column(name = "CallOnlyPhoneNumber")
  @CsvField(value = "Call-only ad phone number", reportField = "CallOnlyPhoneNumber")
  private String callOnlyPhoneNumber;

  @Column(name = "ImageAdUrl", length = 2048)
  @CsvField(value = "Image Ad URL", reportField = "ImageAdUrl")
  private String imageAdUrl;

  @Column(name = "MultiAssetResponsiveDisplayAdDescriptions")
  @CsvField(value = "Descriptions (multi asset responsive display)", reportField = "MultiAssetResponsiveDisplayAdDescriptions")
  private String multiAssetResponsiveDisplayAdDescriptions;

  @Column(name = "MultiAssetResponsiveDisplayAdHeadlines")
  @CsvField(value = "Headlines (multi asset responsive display)", reportField = "MultiAssetResponsiveDisplayAdHeadlines")
  private String multiAssetResponsiveDisplayAdHeadlines;

  @Column(name = "MultiAssetResponsiveDisplayAdLandscapeLogoImages")
  @CsvField(value = "Landscape logos (multi asset responsive display)", reportField = "MultiAssetResponsiveDisplayAdLandscapeLogoImages")
  private String multiAssetResponsiveDisplayAdLandscapeLogoImages;

  @Column(name = "MultiAssetResponsiveDisplayAdLogoImages")
  @CsvField(value = "Logos (multi asset responsive display)", reportField = "MultiAssetResponsiveDisplayAdLogoImages")
  private String multiAssetResponsiveDisplayAdLogoImages;

  @Column(name = "MultiAssetResponsiveDisplayAdLongHeadline")
  @CsvField(value = "Long headline (multi asset responsive display)", reportField = "MultiAssetResponsiveDisplayAdLongHeadline")
  private String multiAssetResponsiveDisplayAdLongHeadline;

  @Column(name = "MultiAssetResponsiveDisplayAdMarketingImages")
  @CsvField(value = "Images (multi asset responsive display)", reportField = "MultiAssetResponsiveDisplayAdMarketingImages")
  private String multiAssetResponsiveDisplayAdMarketingImages;

  @Column(name = "MultiAssetResponsiveDisplayAdSquareMarketingImages")
  @CsvField(value = "Square images (multi asset responsive display)", reportField = "MultiAssetResponsiveDisplayAdSquareMarketingImages")
  private String multiAssetResponsiveDisplayAdSquareMarketingImages;

  @Column(name = "PolicySummary")
  @CsvField(value = "Policy", reportField = "PolicySummary")
  private String policySummary;

  @Column(name = "ResponsiveSearchAdDescriptions")
  @CsvField(value = "Responsive Search Ad descriptions", reportField = "ResponsiveSearchAdDescriptions")
  private String responsiveSearchAdDescriptions;

  @Column(name = "ResponsiveSearchAdHeadlines")
  @CsvField(value = "Responsive Search Ad headlines", reportField = "ResponsiveSearchAdHeadlines")
  private String responsiveSearchAdHeadlines;

  @Column(name = "AccentColor")
  @CsvField(value = "Accent color (responsive)", reportField = "AccentColor")
  private String accentColor;

  @Column(name = "AccountCurrencyCode")
  @CsvField(value = "Currency", reportField = "AccountCurrencyCode")
  private String accountCurrencyCode;

  @Column(name = "AccountDescriptiveName")
  @CsvField(value = "Account", reportField = "AccountDescriptiveName")
  private String accountDescriptiveName;

  @Column(name = "AccountTimeZone")
  @CsvField(value = "Time zone", reportField = "AccountTimeZone")
  private String accountTimeZone;

  @Column(name = "ActiveViewCpm")
  @CsvField(value = "Active View avg. CPM", reportField = "ActiveViewCpm")
  @MoneyField
  private BigDecimal activeViewCpm;

  @Column(name = "ActiveViewCtr")
  @CsvField(value = "Active View viewable CTR", reportField = "ActiveViewCtr")
  private BigDecimal activeViewCtr;

  @Column(name = "ActiveViewImpressions")
  @CsvField(value = "Active View viewable impressions", reportField = "ActiveViewImpressions")
  private Long activeViewImpressions;

  @Column(name = "ActiveViewMeasurability")
  @CsvField(value = "Active View measurable impr. / impr.", reportField = "ActiveViewMeasurability")
  private BigDecimal activeViewMeasurability;

  @Column(name = "ActiveViewMeasurableCost")
  @CsvField(value = "Active View measurable cost", reportField = "ActiveViewMeasurableCost")
  @MoneyField
  private BigDecimal activeViewMeasurableCost;

  @Column(name = "ActiveViewMeasurableImpressions")
  @CsvField(value = "Active View measurable impr.", reportField = "ActiveViewMeasurableImpressions")
  private Long activeViewMeasurableImpressions;

  @Column(name = "ActiveViewViewability")
  @CsvField(value = "Active View viewable impr. / measurable impr.", reportField = "ActiveViewViewability")
  private BigDecimal activeViewViewability;

  @Column(name = "AdGroupId")
  @CsvField(value = "Ad group ID", reportField = "AdGroupId")
  private Long adGroupId;

  @Column(name = "AdGroupName")
  @CsvField(value = "Ad group", reportField = "AdGroupName")
  private String adGroupName;

  @Column(name = "AdGroupStatus")
  @CsvField(value = "Ad group state", reportField = "AdGroupStatus")
  private String adGroupStatus;

  @Column(name = "AdNetworkType1")
  @CsvField(value = "Network", reportField = "AdNetworkType1")
  private String adNetworkType1;

  @Column(name = "AdNetworkType2")
  @CsvField(value = "Network (with search partners)", reportField = "AdNetworkType2")
  private String adNetworkType2;

  @Column(name = "AdType")
  @CsvField(value = "Ad type", reportField = "AdType")
  private String adType;

  @Column(name = "AllConversionRate")
  @CsvField(value = "All conv. rate", reportField = "AllConversionRate")
  private BigDecimal allConversionRate;

  @Column(name = "AllConversions")
  @CsvField(value = "All conv.", reportField = "AllConversions")
  private BigDecimal allConversions;

  @Column(name = "AllConversionValue")
  @CsvField(value = "All conv. value", reportField = "AllConversionValue")
  private BigDecimal allConversionValue;

  @Column(name = "AllowFlexibleColor")
  @CsvField(value = "Allow flexible color (responsive)", reportField = "AllowFlexibleColor")
  private String allowFlexibleColor;

  @Column(name = "Automated")
  @CsvField(value = "Auto-applied ad suggestion", reportField = "Automated")
  private String automated;

  @Column(name = "AverageCost")
  @CsvField(value = "Avg. Cost", reportField = "AverageCost")
  @MoneyField
  private BigDecimal averageCost;

  @Column(name = "AverageCpc")
  @CsvField(value = "Avg. CPC", reportField = "AverageCpc")
  @MoneyField
  private BigDecimal averageCpc;

  @Column(name = "AverageCpe")
  @CsvField(value = "Avg. CPE", reportField = "AverageCpe")
  private BigDecimal averageCpe;

  @Column(name = "AverageCpm")
  @CsvField(value = "Avg. CPM", reportField = "AverageCpm")
  @MoneyField
  private BigDecimal averageCpm;

  @Column(name = "AverageCpv")
  @CsvField(value = "Avg. CPV", reportField = "AverageCpv")
  private BigDecimal averageCpv;

  @Column(name = "AveragePageviews")
  @CsvField(value = "Pages / session", reportField = "AveragePageviews")
  private BigDecimal averagePageviews;

  @Column(name = "AveragePosition")
  @CsvField(value = "Avg. position", reportField = "AveragePosition")
  private BigDecimal averagePosition;

  @Column(name = "AverageTimeOnSite")
  @CsvField(value = "Avg. session duration (seconds)", reportField = "AverageTimeOnSite")
  private BigDecimal averageTimeOnSite;

  @Column(name = "BaseAdGroupId")
  @CsvField(value = "Base Ad group ID", reportField = "BaseAdGroupId")
  private Long baseAdGroupId;

  @Column(name = "BaseCampaignId")
  @CsvField(value = "Base Campaign ID", reportField = "BaseCampaignId")
  private Long baseCampaignId;

  @Column(name = "BounceRate")
  @CsvField(value = "Bounce rate", reportField = "BounceRate")
  private BigDecimal bounceRate;

  @Column(name = "BusinessName")
  @CsvField(value = "Business name", reportField = "BusinessName")
  private String businessName;

  @Column(name = "CallToActionText")
  @CsvField(value = "Call to action text (responsive)", reportField = "CallToActionText")
  private String callToActionText;

  @Column(name = "CampaignId")
  @CsvField(value = "Campaign ID", reportField = "CampaignId")
  private Long campaignId;

  @Column(name = "CampaignName")
  @CsvField(value = "Campaign", reportField = "CampaignName")
  private String campaignName;

  @Column(name = "CampaignStatus")
  @CsvField(value = "Campaign state", reportField = "CampaignStatus")
  private String campaignStatus;

  @Column(name = "ClickAssistedConversions")
  @CsvField(value = "Click Assisted Conv.", reportField = "ClickAssistedConversions")
  private Long clickAssistedConversions;

  @Column(name = "ClickAssistedConversionsOverLastClickConversions")
  @CsvField(value = "Click Assisted Conv. / Last Click Conv.", reportField = "ClickAssistedConversionsOverLastClickConversions")
  private BigDecimal clickAssistedConversionsOverLastClickConversions;

  @Column(name = "ClickAssistedConversionValue")
  @CsvField(value = "Click Assisted Conv. Value", reportField = "ClickAssistedConversionValue")
  private BigDecimal clickAssistedConversionValue;

  @Column(name = "Clicks")
  @CsvField(value = "Clicks", reportField = "Clicks")
  private Long clicks;

  @Column(name = "ClickType")
  @CsvField(value = "Click type", reportField = "ClickType")
  private String clickType;

  @Column(name = "CombinedApprovalStatus")
  @CsvField(value = "Approval status", reportField = "CombinedApprovalStatus")
  private String combinedApprovalStatus;

  @Column(name = "ConversionCategoryName")
  @CsvField(value = "Conversion category", reportField = "ConversionCategoryName")
  private String conversionCategoryName;

  @Column(name = "ConversionLagBucket")
  @CsvField(value = "Days to conversion", reportField = "ConversionLagBucket")
  private String conversionLagBucket;

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

  @Column(name = "CostPerCurrentModelAttributedConversion")
  @CsvField(value = "Cost / conv. (current model)", reportField = "CostPerCurrentModelAttributedConversion")
  private BigDecimal costPerCurrentModelAttributedConversion;

  @Column(name = "CreativeDestinationUrl", length = 2048)
  @CsvField(value = "Destination URL", reportField = "CreativeDestinationUrl")
  private String creativeDestinationUrl;

  @Column(name = "CreativeFinalAppUrls", length = 2048)
  @CsvField(value = "App final URL", reportField = "CreativeFinalAppUrls")
  private String creativeFinalAppUrls;

  @Column(name = "CreativeFinalMobileUrls", length = 2048)
  @CsvField(value = "Mobile final URL", reportField = "CreativeFinalMobileUrls")
  private String creativeFinalMobileUrls;

  @Column(name = "CreativeFinalUrls", length = 2048)
  @CsvField(value = "Final URL", reportField = "CreativeFinalUrls")
  private String creativeFinalUrls;

  @Column(name = "CreativeFinalUrlSuffix")
  @CsvField(value = "Final URL suffix", reportField = "CreativeFinalUrlSuffix")
  private String creativeFinalUrlSuffix;

  @Column(name = "CreativeTrackingUrlTemplate", length = 2048)
  @CsvField(value = "Tracking template", reportField = "CreativeTrackingUrlTemplate")
  private String creativeTrackingUrlTemplate;

  @Column(name = "CreativeUrlCustomParameters", length = 2048)
  @CsvField(value = "Custom parameter", reportField = "CreativeUrlCustomParameters")
  private String creativeUrlCustomParameters;

  @Column(name = "CriterionId")
  @CsvField(value = "Keyword ID", reportField = "CriterionId")
  private Long criterionId;

  @Column(name = "CriterionType")
  @CsvField(value = "Criteria Type", reportField = "CriterionType")
  private String criterionType;

  @Column(name = "CrossDeviceConversions")
  @CsvField(value = "Cross-device conv.", reportField = "CrossDeviceConversions")
  private BigDecimal crossDeviceConversions;

  @Column(name = "Ctr")
  @CsvField(value = "CTR", reportField = "Ctr")
  private BigDecimal ctr;

  @Column(name = "CurrentModelAttributedConversions")
  @CsvField(value = "Conversions (current model)", reportField = "CurrentModelAttributedConversions")
  private BigDecimal currentModelAttributedConversions;

  @Column(name = "CurrentModelAttributedConversionValue")
  @CsvField(value = "Conv. value (current model)", reportField = "CurrentModelAttributedConversionValue")
  private BigDecimal currentModelAttributedConversionValue;

  @Column(name = "CustomerDescriptiveName")
  @CsvField(value = "Client name", reportField = "CustomerDescriptiveName")
  private String customerDescriptiveName;

  @Column(name = "Description")
  @CsvField(value = "Description", reportField = "Description")
  private String description;

  @Column(name = "Description1")
  @CsvField(value = "Description line 1", reportField = "Description1")
  private String description1;

  @Column(name = "Description2")
  @CsvField(value = "Description line 2", reportField = "Description2")
  private String description2;

  @Column(name = "Device")
  @CsvField(value = "Device", reportField = "Device")
  private String device;

  @Column(name = "DevicePreference")
  @CsvField(value = "Device preference", reportField = "DevicePreference")
  private Long devicePreference;

  @Column(name = "DisplayUrl", length = 2048)
  @CsvField(value = "Display URL", reportField = "DisplayUrl")
  private String displayUrl;

  @Column(name = "EngagementRate")
  @CsvField(value = "Engagement rate", reportField = "EngagementRate")
  private BigDecimal engagementRate;

  @Column(name = "Engagements")
  @CsvField(value = "Engagements", reportField = "Engagements")
  private Long engagements;

  @Column(name = "EnhancedDisplayCreativeLandscapeLogoImageMediaId")
  @CsvField(value = "Landscape logo ID (responsive)", reportField = "EnhancedDisplayCreativeLandscapeLogoImageMediaId")
  private Long enhancedDisplayCreativeLandscapeLogoImageMediaId;

  @Column(name = "EnhancedDisplayCreativeLogoImageMediaId")
  @CsvField(value = "Logo ID (responsive)", reportField = "EnhancedDisplayCreativeLogoImageMediaId")
  private Long enhancedDisplayCreativeLogoImageMediaId;

  @Column(name = "EnhancedDisplayCreativeMarketingImageMediaId")
  @CsvField(value = "Image ID (responsive)", reportField = "EnhancedDisplayCreativeMarketingImageMediaId")
  private Long enhancedDisplayCreativeMarketingImageMediaId;

  @Column(name = "EnhancedDisplayCreativeMarketingImageSquareMediaId")
  @CsvField(value = "Square image ID (responsive)", reportField = "EnhancedDisplayCreativeMarketingImageSquareMediaId")
  private Long enhancedDisplayCreativeMarketingImageSquareMediaId;

  @Column(name = "ExternalConversionSource")
  @CsvField(value = "Conversion source", reportField = "ExternalConversionSource")
  private String externalConversionSource;

  @Column(name = "FormatSetting")
  @CsvField(value = "Ad format preference (responsive)", reportField = "FormatSetting")
  private String formatSetting;

  @Column(name = "GmailCreativeHeaderImageMediaId")
  @CsvField(value = "Gmail ad header image media id", reportField = "GmailCreativeHeaderImageMediaId")
  private Long gmailCreativeHeaderImageMediaId;

  @Column(name = "GmailCreativeLogoImageMediaId")
  @CsvField(value = "Gmail ad logo image media id", reportField = "GmailCreativeLogoImageMediaId")
  private Long gmailCreativeLogoImageMediaId;

  @Column(name = "GmailCreativeMarketingImageMediaId")
  @CsvField(value = "Gmail ad marketing image media id", reportField = "GmailCreativeMarketingImageMediaId")
  private Long gmailCreativeMarketingImageMediaId;

  @Column(name = "GmailForwards")
  @CsvField(value = "Gmail forwards", reportField = "GmailForwards")
  private Long gmailForwards;

  @Column(name = "GmailSaves")
  @CsvField(value = "Gmail saves", reportField = "GmailSaves")
  private Long gmailSaves;

  @Column(name = "GmailSecondaryClicks")
  @CsvField(value = "Gmail clicks to website", reportField = "GmailSecondaryClicks")
  private Long gmailSecondaryClicks;

  @Column(name = "GmailTeaserBusinessName")
  @CsvField(value = "Gmail ad business name", reportField = "GmailTeaserBusinessName")
  private String gmailTeaserBusinessName;

  @Column(name = "GmailTeaserDescription")
  @CsvField(value = "Gmail ad description", reportField = "GmailTeaserDescription")
  private String gmailTeaserDescription;

  @Column(name = "GmailTeaserHeadline")
  @CsvField(value = "Gmail ad headline", reportField = "GmailTeaserHeadline")
  private String gmailTeaserHeadline;

  @Column(name = "Headline")
  @CsvField(value = "Ad", reportField = "Headline")
  private String headline;

  @Column(name = "HeadlinePart1")
  @CsvField(value = "Headline 1", reportField = "HeadlinePart1")
  private String headlinePart1;

  @Column(name = "HeadlinePart2")
  @CsvField(value = "Headline 2", reportField = "HeadlinePart2")
  private String headlinePart2;

  @Column(name = "Id")
  @CsvField(value = "Ad ID", reportField = "Id")
  private Long id;

  @Column(name = "ImageCreativeImageHeight")
  @CsvField(value = "Image Height", reportField = "ImageCreativeImageHeight")
  private Integer imageCreativeImageHeight;

  @Column(name = "ImageCreativeImageWidth")
  @CsvField(value = "Image Width", reportField = "ImageCreativeImageWidth")
  private Integer imageCreativeImageWidth;

  @Column(name = "ImageCreativeMimeType")
  @CsvField(value = "Image Mime Type", reportField = "ImageCreativeMimeType")
  private Integer imageCreativeMimeType;

  @Column(name = "ImageCreativeName")
  @CsvField(value = "Image ad name", reportField = "ImageCreativeName")
  private String imageCreativeName;

  @Column(name = "ImpressionAssistedConversions")
  @CsvField(value = "Impr. Assisted Conv.", reportField = "ImpressionAssistedConversions")
  private Long impressionAssistedConversions;

  @Column(name = "ImpressionAssistedConversionsOverLastClickConversions")
  @CsvField(value = "Impr. Assisted Conv. / Last Click Conv.", reportField = "ImpressionAssistedConversionsOverLastClickConversions")
  private BigDecimal impressionAssistedConversionsOverLastClickConversions;

  @Column(name = "ImpressionAssistedConversionValue")
  @CsvField(value = "Impr. Assisted Conv. Value", reportField = "ImpressionAssistedConversionValue")
  private BigDecimal impressionAssistedConversionValue;

  @Column(name = "Impressions")
  @CsvField(value = "Impressions", reportField = "Impressions")
  private Long impressions;

  @Column(name = "InteractionRate")
  @CsvField(value = "Interaction Rate", reportField = "InteractionRate")
  private BigDecimal interactionRate;

  @Column(name = "Interactions")
  @CsvField(value = "Interactions", reportField = "Interactions")
  private Long interactions;

  @Column(name = "InteractionTypes")
  @CsvField(value = "Interaction Types", reportField = "InteractionTypes")
  private String interactionTypes;

  @Column(name = "IsNegative")
  @CsvField(value = "Is negative", reportField = "IsNegative")
  private String isNegative;

  @Column(name = "LabelIds")
  @CsvField(value = "Label IDs", reportField = "LabelIds")
  private String labelIds;

  @Column(name = "Labels")
  @CsvField(value = "Labels", reportField = "Labels")
  private String labels;

  @Column(name = "LongHeadline")
  @CsvField(value = "Long headline", reportField = "LongHeadline")
  private String longHeadline;

  @Column(name = "MainColor")
  @CsvField(value = "Main color (responsive)", reportField = "MainColor")
  private String mainColor;

  @Column(name = "MarketingImageCallToActionText")
  @CsvField(value = "Gmail ad marketing image call to action text", reportField = "MarketingImageCallToActionText")
  private String marketingImageCallToActionText;

  @Column(name = "MarketingImageCallToActionTextColor")
  @CsvField(value = "Gmail ad marketing image call to action text color", reportField = "MarketingImageCallToActionTextColor")
  private String marketingImageCallToActionTextColor;

  @Column(name = "MarketingImageDescription")
  @CsvField(value = "Gmail ad marketing image description", reportField = "MarketingImageDescription")
  private String marketingImageDescription;

  @Column(name = "MarketingImageHeadline")
  @CsvField(value = "Gmail ad marketing image headline", reportField = "MarketingImageHeadline")
  private String marketingImageHeadline;

  @Column(name = "MultiAssetResponsiveDisplayAdAccentColor")
  @CsvField(value = "Accent color (multi asset responsive display)", reportField = "MultiAssetResponsiveDisplayAdAccentColor")
  private String multiAssetResponsiveDisplayAdAccentColor;

  @Column(name = "MultiAssetResponsiveDisplayAdAllowFlexibleColor")
  @CsvField(value = "Allow flexible color (multi asset responsive display)", reportField = "MultiAssetResponsiveDisplayAdAllowFlexibleColor")
  private String multiAssetResponsiveDisplayAdAllowFlexibleColor;

  @Column(name = "MultiAssetResponsiveDisplayAdBusinessName")
  @CsvField(value = "Business name (multi asset responsive display)", reportField = "MultiAssetResponsiveDisplayAdBusinessName")
  private String multiAssetResponsiveDisplayAdBusinessName;

  @Column(name = "MultiAssetResponsiveDisplayAdCallToActionText")
  @CsvField(value = "Call to action text (multi asset responsive display)", reportField = "MultiAssetResponsiveDisplayAdCallToActionText")
  private String multiAssetResponsiveDisplayAdCallToActionText;

  @Column(name = "MultiAssetResponsiveDisplayAdDynamicSettingsPricePrefix")
  @CsvField(value = "Price prefix (multi asset responsive display)", reportField = "MultiAssetResponsiveDisplayAdDynamicSettingsPricePrefix")
  private String multiAssetResponsiveDisplayAdDynamicSettingsPricePrefix;

  @Column(name = "MultiAssetResponsiveDisplayAdDynamicSettingsPromoText")
  @CsvField(value = "Promotion text (multi asset responsive display)", reportField = "MultiAssetResponsiveDisplayAdDynamicSettingsPromoText")
  private String multiAssetResponsiveDisplayAdDynamicSettingsPromoText;

  @Column(name = "MultiAssetResponsiveDisplayAdFormatSetting")
  @CsvField(value = "Ad format preference (multi asset responsive display)", reportField = "MultiAssetResponsiveDisplayAdFormatSetting")
  private String multiAssetResponsiveDisplayAdFormatSetting;

  @Column(name = "MultiAssetResponsiveDisplayAdMainColor")
  @CsvField(value = "Main color (multi asset responsive display)", reportField = "MultiAssetResponsiveDisplayAdMainColor")
  private String multiAssetResponsiveDisplayAdMainColor;

  @Column(name = "Path1")
  @CsvField(value = "Path 1", reportField = "Path1")
  private String path1;

  @Column(name = "Path2")
  @CsvField(value = "Path 2", reportField = "Path2")
  private String path2;

  @Column(name = "PercentNewVisitors")
  @CsvField(value = "% new sessions", reportField = "PercentNewVisitors")
  private BigDecimal percentNewVisitors;

  @Column(name = "PricePrefix")
  @CsvField(value = "Price prefix (responsive)", reportField = "PricePrefix")
  private String pricePrefix;

  @Column(name = "PromoText")
  @CsvField(value = "Promotion text (responsive)", reportField = "PromoText")
  private String promoText;

  @Column(name = "ResponsiveSearchAdPath1")
  @CsvField(value = "Responsive Search Ad path 1", reportField = "ResponsiveSearchAdPath1")
  private String responsiveSearchAdPath1;

  @Column(name = "ResponsiveSearchAdPath2")
  @CsvField(value = "Responsive Search Ad path 2", reportField = "ResponsiveSearchAdPath2")
  private String responsiveSearchAdPath2;

  @Column(name = "ShortHeadline")
  @CsvField(value = "Short headline", reportField = "ShortHeadline")
  private String shortHeadline;

  @Column(name = "Slot")
  @CsvField(value = "Top vs. Other", reportField = "Slot")
  private String slot;

  @Column(name = "Status")
  @CsvField(value = "Ad state", reportField = "Status")
  private String status;

  @Column(name = "SystemManagedEntitySource")
  @CsvField(value = "System managed Entity Source", reportField = "SystemManagedEntitySource")
  private String systemManagedEntitySource;

  @Column(name = "ValuePerAllConversion")
  @CsvField(value = "Value / all conv.", reportField = "ValuePerAllConversion")
  private BigDecimal valuePerAllConversion;

  @Column(name = "ValuePerConversion")
  @CsvField(value = "Value / conv.", reportField = "ValuePerConversion")
  private BigDecimal valuePerConversion;

  @Column(name = "ValuePerCurrentModelAttributedConversion")
  @CsvField(value = "Value / conv. (current model)", reportField = "ValuePerCurrentModelAttributedConversion")
  private BigDecimal valuePerCurrentModelAttributedConversion;

  @Column(name = "VideoQuartile100Rate")
  @CsvField(value = "Video played to 100%", reportField = "VideoQuartile100Rate")
  private BigDecimal videoQuartile100Rate;

  @Column(name = "VideoQuartile25Rate")
  @CsvField(value = "Video played to 25%", reportField = "VideoQuartile25Rate")
  private BigDecimal videoQuartile25Rate;

  @Column(name = "VideoQuartile50Rate")
  @CsvField(value = "Video played to 50%", reportField = "VideoQuartile50Rate")
  private BigDecimal videoQuartile50Rate;

  @Column(name = "VideoQuartile75Rate")
  @CsvField(value = "Video played to 75%", reportField = "VideoQuartile75Rate")
  private BigDecimal videoQuartile75Rate;

  @Column(name = "VideoViewRate")
  @CsvField(value = "View rate", reportField = "VideoViewRate")
  private BigDecimal videoViewRate;

  @Column(name = "VideoViews")
  @CsvField(value = "Views", reportField = "VideoViews")
  private Long videoViews;

  @Column(name = "ViewThroughConversions")
  @CsvField(value = "View-through conv.", reportField = "ViewThroughConversions")
  private Long viewThroughConversions;

  /**
   * Hibernate needs an empty constructor
   */
  public AdPerformanceReport() {
  }

  public AdPerformanceReport(Long topAccountId, Long accountId){
    super(topAccountId, accountId);
  }

  public String getCallOnlyPhoneNumber() {
    return callOnlyPhoneNumber;
  }

  public void setCallOnlyPhoneNumber(String callOnlyPhoneNumber) {
    this.callOnlyPhoneNumber = callOnlyPhoneNumber;
  }

  public String getImageAdUrl() {
    return imageAdUrl;
  }

  public void setImageAdUrl(String imageAdUrl) {
    this.imageAdUrl = imageAdUrl;
  }

  public String getMultiAssetResponsiveDisplayAdDescriptions() {
    return multiAssetResponsiveDisplayAdDescriptions;
  }

  public void setMultiAssetResponsiveDisplayAdDescriptions(String multiAssetResponsiveDisplayAdDescriptions) {
    this.multiAssetResponsiveDisplayAdDescriptions = multiAssetResponsiveDisplayAdDescriptions;
  }

  public String getMultiAssetResponsiveDisplayAdHeadlines() {
    return multiAssetResponsiveDisplayAdHeadlines;
  }

  public void setMultiAssetResponsiveDisplayAdHeadlines(String multiAssetResponsiveDisplayAdHeadlines) {
    this.multiAssetResponsiveDisplayAdHeadlines = multiAssetResponsiveDisplayAdHeadlines;
  }

  public String getMultiAssetResponsiveDisplayAdLandscapeLogoImages() {
    return multiAssetResponsiveDisplayAdLandscapeLogoImages;
  }

  public void setMultiAssetResponsiveDisplayAdLandscapeLogoImages(String multiAssetResponsiveDisplayAdLandscapeLogoImages) {
    this.multiAssetResponsiveDisplayAdLandscapeLogoImages = multiAssetResponsiveDisplayAdLandscapeLogoImages;
  }

  public String getMultiAssetResponsiveDisplayAdLogoImages() {
    return multiAssetResponsiveDisplayAdLogoImages;
  }

  public void setMultiAssetResponsiveDisplayAdLogoImages(String multiAssetResponsiveDisplayAdLogoImages) {
    this.multiAssetResponsiveDisplayAdLogoImages = multiAssetResponsiveDisplayAdLogoImages;
  }

  public String getMultiAssetResponsiveDisplayAdLongHeadline() {
    return multiAssetResponsiveDisplayAdLongHeadline;
  }

  public void setMultiAssetResponsiveDisplayAdLongHeadline(String multiAssetResponsiveDisplayAdLongHeadline) {
    this.multiAssetResponsiveDisplayAdLongHeadline = multiAssetResponsiveDisplayAdLongHeadline;
  }

  public String getMultiAssetResponsiveDisplayAdMarketingImages() {
    return multiAssetResponsiveDisplayAdMarketingImages;
  }

  public void setMultiAssetResponsiveDisplayAdMarketingImages(String multiAssetResponsiveDisplayAdMarketingImages) {
    this.multiAssetResponsiveDisplayAdMarketingImages = multiAssetResponsiveDisplayAdMarketingImages;
  }

  public String getMultiAssetResponsiveDisplayAdSquareMarketingImages() {
    return multiAssetResponsiveDisplayAdSquareMarketingImages;
  }

  public void setMultiAssetResponsiveDisplayAdSquareMarketingImages(String multiAssetResponsiveDisplayAdSquareMarketingImages) {
    this.multiAssetResponsiveDisplayAdSquareMarketingImages = multiAssetResponsiveDisplayAdSquareMarketingImages;
  }

  public String getPolicySummary() {
    return policySummary;
  }

  public void setPolicySummary(String policySummary) {
    this.policySummary = policySummary;
  }

  public String getResponsiveSearchAdDescriptions() {
    return responsiveSearchAdDescriptions;
  }

  public void setResponsiveSearchAdDescriptions(String responsiveSearchAdDescriptions) {
    this.responsiveSearchAdDescriptions = responsiveSearchAdDescriptions;
  }

  public String getResponsiveSearchAdHeadlines() {
    return responsiveSearchAdHeadlines;
  }

  public void setResponsiveSearchAdHeadlines(String responsiveSearchAdHeadlines) {
    this.responsiveSearchAdHeadlines = responsiveSearchAdHeadlines;
  }

  public String getAccentColor() {
    return accentColor;
  }

  public void setAccentColor(String accentColor) {
    this.accentColor = accentColor;
  }

  public String getAccountCurrencyCode() {
    return accountCurrencyCode;
  }

  public void setAccountCurrencyCode(String accountCurrencyCode) {
    this.accountCurrencyCode = accountCurrencyCode;
  }

  public String getAccountDescriptiveName() {
    return accountDescriptiveName;
  }

  public void setAccountDescriptiveName(String accountDescriptiveName) {
    this.accountDescriptiveName = accountDescriptiveName;
  }

  public String getAccountTimeZone() {
    return accountTimeZone;
  }

  public void setAccountTimeZone(String accountTimeZone) {
    this.accountTimeZone = accountTimeZone;
  }

  public BigDecimal getActiveViewCpm() {
    return activeViewCpm;
  }

  public void setActiveViewCpm(BigDecimal activeViewCpm) {
    this.activeViewCpm = activeViewCpm;
  }

  public String getActiveViewCtr() {
    return BigDecimalUtil.formatAsReadable(activeViewCtr);
  }

  public BigDecimal getActiveViewCtrBigDecimal() {
    return activeViewCtr;
  }

  public void setActiveViewCtr(String activeViewCtr) {
    this.activeViewCtr = BigDecimalUtil.parseFromNumberString(activeViewCtr);
  }

  public Long getActiveViewImpressions() {
    return activeViewImpressions;
  }

  public void setActiveViewImpressions(Long activeViewImpressions) {
    this.activeViewImpressions = activeViewImpressions;
  }

  public String getActiveViewMeasurability() {
    return BigDecimalUtil.formatAsReadable(activeViewMeasurability);
  }

  public BigDecimal getActiveViewMeasurabilityBigDecimal() {
    return activeViewMeasurability;
  }

  public void setActiveViewMeasurability(String activeViewMeasurability) {
    this.activeViewMeasurability = BigDecimalUtil.parseFromNumberString(activeViewMeasurability);
  }

  public BigDecimal getActiveViewMeasurableCost() {
    return activeViewMeasurableCost;
  }

  public void setActiveViewMeasurableCost(BigDecimal activeViewMeasurableCost) {
    this.activeViewMeasurableCost = activeViewMeasurableCost;
  }

  public Long getActiveViewMeasurableImpressions() {
    return activeViewMeasurableImpressions;
  }

  public void setActiveViewMeasurableImpressions(Long activeViewMeasurableImpressions) {
    this.activeViewMeasurableImpressions = activeViewMeasurableImpressions;
  }

  public String getActiveViewViewability() {
    return BigDecimalUtil.formatAsReadable(activeViewViewability);
  }

  public BigDecimal getActiveViewViewabilityBigDecimal() {
    return activeViewViewability;
  }

  public void setActiveViewViewability(String activeViewViewability) {
    this.activeViewViewability = BigDecimalUtil.parseFromNumberString(activeViewViewability);
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

  public String getAdNetworkType1() {
    return adNetworkType1;
  }

  public void setAdNetworkType1(String adNetworkType1) {
    this.adNetworkType1 = adNetworkType1;
  }

  public String getAdNetworkType2() {
    return adNetworkType2;
  }

  public void setAdNetworkType2(String adNetworkType2) {
    this.adNetworkType2 = adNetworkType2;
  }

  public String getAdType() {
    return adType;
  }

  public void setAdType(String adType) {
    this.adType = adType;
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

  public String getAllowFlexibleColor() {
    return allowFlexibleColor;
  }

  public void setAllowFlexibleColor(String allowFlexibleColor) {
    this.allowFlexibleColor = allowFlexibleColor;
  }

  public String getAutomated() {
    return automated;
  }

  public void setAutomated(String automated) {
    this.automated = automated;
  }

  public BigDecimal getAverageCost() {
    return averageCost;
  }

  public void setAverageCost(BigDecimal averageCost) {
    this.averageCost = averageCost;
  }

  public BigDecimal getAverageCpc() {
    return averageCpc;
  }

  public void setAverageCpc(BigDecimal averageCpc) {
    this.averageCpc = averageCpc;
  }

  public String getAverageCpe() {
    return BigDecimalUtil.formatAsReadable(averageCpe);
  }

  public BigDecimal getAverageCpeBigDecimal() {
    return averageCpe;
  }

  public void setAverageCpe(String averageCpe) {
    this.averageCpe = BigDecimalUtil.parseFromNumberString(averageCpe);
  }

  public BigDecimal getAverageCpm() {
    return averageCpm;
  }

  public void setAverageCpm(BigDecimal averageCpm) {
    this.averageCpm = averageCpm;
  }

  public String getAverageCpv() {
    return BigDecimalUtil.formatAsReadable(averageCpv);
  }

  public BigDecimal getAverageCpvBigDecimal() {
    return averageCpv;
  }

  public void setAverageCpv(String averageCpv) {
    this.averageCpv = BigDecimalUtil.parseFromNumberString(averageCpv);
  }

  public String getAveragePageviews() {
    return BigDecimalUtil.formatAsReadable(averagePageviews);
  }

  public BigDecimal getAveragePageviewsBigDecimal() {
    return averagePageviews;
  }

  public void setAveragePageviews(String averagePageviews) {
    this.averagePageviews = BigDecimalUtil.parseFromNumberString(averagePageviews);
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

  public String getAverageTimeOnSite() {
    return BigDecimalUtil.formatAsReadable(averageTimeOnSite);
  }

  public BigDecimal getAverageTimeOnSiteBigDecimal() {
    return averageTimeOnSite;
  }

  public void setAverageTimeOnSite(String averageTimeOnSite) {
    this.averageTimeOnSite = BigDecimalUtil.parseFromNumberString(averageTimeOnSite);
  }

  public Long getBaseAdGroupId() {
    return baseAdGroupId;
  }

  public void setBaseAdGroupId(Long baseAdGroupId) {
    this.baseAdGroupId = baseAdGroupId;
  }

  public Long getBaseCampaignId() {
    return baseCampaignId;
  }

  public void setBaseCampaignId(Long baseCampaignId) {
    this.baseCampaignId = baseCampaignId;
  }

  public String getBounceRate() {
    return BigDecimalUtil.formatAsReadable(bounceRate);
  }

  public BigDecimal getBounceRateBigDecimal() {
    return bounceRate;
  }

  public void setBounceRate(String bounceRate) {
    this.bounceRate = BigDecimalUtil.parseFromNumberString(bounceRate);
  }

  public String getBusinessName() {
    return businessName;
  }

  public void setBusinessName(String businessName) {
    this.businessName = businessName;
  }

  public String getCallToActionText() {
    return callToActionText;
  }

  public void setCallToActionText(String callToActionText) {
    this.callToActionText = callToActionText;
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

  public Long getClickAssistedConversions() {
    return clickAssistedConversions;
  }

  public void setClickAssistedConversions(Long clickAssistedConversions) {
    this.clickAssistedConversions = clickAssistedConversions;
  }

  public String getClickAssistedConversionsOverLastClickConversions() {
    return BigDecimalUtil.formatAsReadable(clickAssistedConversionsOverLastClickConversions);
  }

  public BigDecimal getClickAssistedConversionsOverLastClickConversionsBigDecimal() {
    return clickAssistedConversionsOverLastClickConversions;
  }

  public void setClickAssistedConversionsOverLastClickConversions(String clickAssistedConversionsOverLastClickConversions) {
    this.clickAssistedConversionsOverLastClickConversions = BigDecimalUtil.parseFromNumberString(clickAssistedConversionsOverLastClickConversions);
  }

  public String getClickAssistedConversionValue() {
    return BigDecimalUtil.formatAsReadable(clickAssistedConversionValue);
  }

  public BigDecimal getClickAssistedConversionValueBigDecimal() {
    return clickAssistedConversionValue;
  }

  public void setClickAssistedConversionValue(String clickAssistedConversionValue) {
    this.clickAssistedConversionValue = BigDecimalUtil.parseFromNumberString(clickAssistedConversionValue);
  }

  public Long getClicks() {
    return clicks;
  }

  public void setClicks(Long clicks) {
    this.clicks = clicks;
  }

  public String getClickType() {
    return clickType;
  }

  public void setClickType(String clickType) {
    this.clickType = clickType;
  }

  public String getCombinedApprovalStatus() {
    return combinedApprovalStatus;
  }

  public void setCombinedApprovalStatus(String combinedApprovalStatus) {
    this.combinedApprovalStatus = combinedApprovalStatus;
  }

  public String getConversionCategoryName() {
    return conversionCategoryName;
  }

  public void setConversionCategoryName(String conversionCategoryName) {
    this.conversionCategoryName = conversionCategoryName;
  }

  public String getConversionLagBucket() {
    return conversionLagBucket;
  }

  public void setConversionLagBucket(String conversionLagBucket) {
    this.conversionLagBucket = conversionLagBucket;
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

  public String getCostPerCurrentModelAttributedConversion() {
    return BigDecimalUtil.formatAsReadable(costPerCurrentModelAttributedConversion);
  }

  public BigDecimal getCostPerCurrentModelAttributedConversionBigDecimal() {
    return costPerCurrentModelAttributedConversion;
  }

  public void setCostPerCurrentModelAttributedConversion(String costPerCurrentModelAttributedConversion) {
    this.costPerCurrentModelAttributedConversion = BigDecimalUtil.parseFromNumberString(costPerCurrentModelAttributedConversion);
  }

  public String getCreativeDestinationUrl() {
    return creativeDestinationUrl;
  }

  public void setCreativeDestinationUrl(String creativeDestinationUrl) {
    this.creativeDestinationUrl = creativeDestinationUrl;
  }

  public String getCreativeFinalAppUrls() {
    return creativeFinalAppUrls;
  }

  public void setCreativeFinalAppUrls(String creativeFinalAppUrls) {
    this.creativeFinalAppUrls = creativeFinalAppUrls;
  }

  public String getCreativeFinalMobileUrls() {
    return creativeFinalMobileUrls;
  }

  public void setCreativeFinalMobileUrls(String creativeFinalMobileUrls) {
    this.creativeFinalMobileUrls = creativeFinalMobileUrls;
  }

  public String getCreativeFinalUrls() {
    return creativeFinalUrls;
  }

  public void setCreativeFinalUrls(String creativeFinalUrls) {
    this.creativeFinalUrls = creativeFinalUrls;
  }

  public String getCreativeFinalUrlSuffix() {
    return creativeFinalUrlSuffix;
  }

  public void setCreativeFinalUrlSuffix(String creativeFinalUrlSuffix) {
    this.creativeFinalUrlSuffix = creativeFinalUrlSuffix;
  }

  public String getCreativeTrackingUrlTemplate() {
    return creativeTrackingUrlTemplate;
  }

  public void setCreativeTrackingUrlTemplate(String creativeTrackingUrlTemplate) {
    this.creativeTrackingUrlTemplate = creativeTrackingUrlTemplate;
  }

  public String getCreativeUrlCustomParameters() {
    return creativeUrlCustomParameters;
  }

  public void setCreativeUrlCustomParameters(String creativeUrlCustomParameters) {
    this.creativeUrlCustomParameters = creativeUrlCustomParameters;
  }

  public Long getCriterionId() {
    return criterionId;
  }

  public void setCriterionId(Long criterionId) {
    this.criterionId = criterionId;
  }

  public String getCriterionType() {
    return criterionType;
  }

  public void setCriterionType(String criterionType) {
    this.criterionType = criterionType;
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

  public String getCurrentModelAttributedConversions() {
    return BigDecimalUtil.formatAsReadable(currentModelAttributedConversions);
  }

  public BigDecimal getCurrentModelAttributedConversionsBigDecimal() {
    return currentModelAttributedConversions;
  }

  public void setCurrentModelAttributedConversions(String currentModelAttributedConversions) {
    this.currentModelAttributedConversions = BigDecimalUtil.parseFromNumberString(currentModelAttributedConversions);
  }

  public String getCurrentModelAttributedConversionValue() {
    return BigDecimalUtil.formatAsReadable(currentModelAttributedConversionValue);
  }

  public BigDecimal getCurrentModelAttributedConversionValueBigDecimal() {
    return currentModelAttributedConversionValue;
  }

  public void setCurrentModelAttributedConversionValue(String currentModelAttributedConversionValue) {
    this.currentModelAttributedConversionValue = BigDecimalUtil.parseFromNumberString(currentModelAttributedConversionValue);
  }

  public String getCustomerDescriptiveName() {
    return customerDescriptiveName;
  }

  public void setCustomerDescriptiveName(String customerDescriptiveName) {
    this.customerDescriptiveName = customerDescriptiveName;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getDescription1() {
    return description1;
  }

  public void setDescription1(String description1) {
    this.description1 = description1;
  }

  public String getDescription2() {
    return description2;
  }

  public void setDescription2(String description2) {
    this.description2 = description2;
  }

  public String getDevice() {
    return device;
  }

  public void setDevice(String device) {
    this.device = device;
  }

  public Long getDevicePreference() {
    return devicePreference;
  }

  public void setDevicePreference(Long devicePreference) {
    this.devicePreference = devicePreference;
  }

  public String getDisplayUrl() {
    return displayUrl;
  }

  public void setDisplayUrl(String displayUrl) {
    this.displayUrl = displayUrl;
  }

  public String getEngagementRate() {
    return BigDecimalUtil.formatAsReadable(engagementRate);
  }

  public BigDecimal getEngagementRateBigDecimal() {
    return engagementRate;
  }

  public void setEngagementRate(String engagementRate) {
    this.engagementRate = BigDecimalUtil.parseFromNumberString(engagementRate);
  }

  public Long getEngagements() {
    return engagements;
  }

  public void setEngagements(Long engagements) {
    this.engagements = engagements;
  }

  public Long getEnhancedDisplayCreativeLandscapeLogoImageMediaId() {
    return enhancedDisplayCreativeLandscapeLogoImageMediaId;
  }

  public void setEnhancedDisplayCreativeLandscapeLogoImageMediaId(Long enhancedDisplayCreativeLandscapeLogoImageMediaId) {
    this.enhancedDisplayCreativeLandscapeLogoImageMediaId = enhancedDisplayCreativeLandscapeLogoImageMediaId;
  }

  public Long getEnhancedDisplayCreativeLogoImageMediaId() {
    return enhancedDisplayCreativeLogoImageMediaId;
  }

  public void setEnhancedDisplayCreativeLogoImageMediaId(Long enhancedDisplayCreativeLogoImageMediaId) {
    this.enhancedDisplayCreativeLogoImageMediaId = enhancedDisplayCreativeLogoImageMediaId;
  }

  public Long getEnhancedDisplayCreativeMarketingImageMediaId() {
    return enhancedDisplayCreativeMarketingImageMediaId;
  }

  public void setEnhancedDisplayCreativeMarketingImageMediaId(Long enhancedDisplayCreativeMarketingImageMediaId) {
    this.enhancedDisplayCreativeMarketingImageMediaId = enhancedDisplayCreativeMarketingImageMediaId;
  }

  public Long getEnhancedDisplayCreativeMarketingImageSquareMediaId() {
    return enhancedDisplayCreativeMarketingImageSquareMediaId;
  }

  public void setEnhancedDisplayCreativeMarketingImageSquareMediaId(Long enhancedDisplayCreativeMarketingImageSquareMediaId) {
    this.enhancedDisplayCreativeMarketingImageSquareMediaId = enhancedDisplayCreativeMarketingImageSquareMediaId;
  }

  public String getExternalConversionSource() {
    return externalConversionSource;
  }

  public void setExternalConversionSource(String externalConversionSource) {
    this.externalConversionSource = externalConversionSource;
  }

  public String getFormatSetting() {
    return formatSetting;
  }

  public void setFormatSetting(String formatSetting) {
    this.formatSetting = formatSetting;
  }

  public Long getGmailCreativeHeaderImageMediaId() {
    return gmailCreativeHeaderImageMediaId;
  }

  public void setGmailCreativeHeaderImageMediaId(Long gmailCreativeHeaderImageMediaId) {
    this.gmailCreativeHeaderImageMediaId = gmailCreativeHeaderImageMediaId;
  }

  public Long getGmailCreativeLogoImageMediaId() {
    return gmailCreativeLogoImageMediaId;
  }

  public void setGmailCreativeLogoImageMediaId(Long gmailCreativeLogoImageMediaId) {
    this.gmailCreativeLogoImageMediaId = gmailCreativeLogoImageMediaId;
  }

  public Long getGmailCreativeMarketingImageMediaId() {
    return gmailCreativeMarketingImageMediaId;
  }

  public void setGmailCreativeMarketingImageMediaId(Long gmailCreativeMarketingImageMediaId) {
    this.gmailCreativeMarketingImageMediaId = gmailCreativeMarketingImageMediaId;
  }

  public Long getGmailForwards() {
    return gmailForwards;
  }

  public void setGmailForwards(Long gmailForwards) {
    this.gmailForwards = gmailForwards;
  }

  public Long getGmailSaves() {
    return gmailSaves;
  }

  public void setGmailSaves(Long gmailSaves) {
    this.gmailSaves = gmailSaves;
  }

  public Long getGmailSecondaryClicks() {
    return gmailSecondaryClicks;
  }

  public void setGmailSecondaryClicks(Long gmailSecondaryClicks) {
    this.gmailSecondaryClicks = gmailSecondaryClicks;
  }

  public String getGmailTeaserBusinessName() {
    return gmailTeaserBusinessName;
  }

  public void setGmailTeaserBusinessName(String gmailTeaserBusinessName) {
    this.gmailTeaserBusinessName = gmailTeaserBusinessName;
  }

  public String getGmailTeaserDescription() {
    return gmailTeaserDescription;
  }

  public void setGmailTeaserDescription(String gmailTeaserDescription) {
    this.gmailTeaserDescription = gmailTeaserDescription;
  }

  public String getGmailTeaserHeadline() {
    return gmailTeaserHeadline;
  }

  public void setGmailTeaserHeadline(String gmailTeaserHeadline) {
    this.gmailTeaserHeadline = gmailTeaserHeadline;
  }

  public String getHeadline() {
    return headline;
  }

  public void setHeadline(String headline) {
    this.headline = headline;
  }

  public String getHeadlinePart1() {
    return headlinePart1;
  }

  public void setHeadlinePart1(String headlinePart1) {
    this.headlinePart1 = headlinePart1;
  }

  public String getHeadlinePart2() {
    return headlinePart2;
  }

  public void setHeadlinePart2(String headlinePart2) {
    this.headlinePart2 = headlinePart2;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Integer getImageCreativeImageHeight() {
    return imageCreativeImageHeight;
  }

  public void setImageCreativeImageHeight(Integer imageCreativeImageHeight) {
    this.imageCreativeImageHeight = imageCreativeImageHeight;
  }

  public Integer getImageCreativeImageWidth() {
    return imageCreativeImageWidth;
  }

  public void setImageCreativeImageWidth(Integer imageCreativeImageWidth) {
    this.imageCreativeImageWidth = imageCreativeImageWidth;
  }

  public Integer getImageCreativeMimeType() {
    return imageCreativeMimeType;
  }

  public void setImageCreativeMimeType(Integer imageCreativeMimeType) {
    this.imageCreativeMimeType = imageCreativeMimeType;
  }

  public String getImageCreativeName() {
    return imageCreativeName;
  }

  public void setImageCreativeName(String imageCreativeName) {
    this.imageCreativeName = imageCreativeName;
  }

  public Long getImpressionAssistedConversions() {
    return impressionAssistedConversions;
  }

  public void setImpressionAssistedConversions(Long impressionAssistedConversions) {
    this.impressionAssistedConversions = impressionAssistedConversions;
  }

  public String getImpressionAssistedConversionsOverLastClickConversions() {
    return BigDecimalUtil.formatAsReadable(impressionAssistedConversionsOverLastClickConversions);
  }

  public BigDecimal getImpressionAssistedConversionsOverLastClickConversionsBigDecimal() {
    return impressionAssistedConversionsOverLastClickConversions;
  }

  public void setImpressionAssistedConversionsOverLastClickConversions(String impressionAssistedConversionsOverLastClickConversions) {
    this.impressionAssistedConversionsOverLastClickConversions = BigDecimalUtil.parseFromNumberString(impressionAssistedConversionsOverLastClickConversions);
  }

  public String getImpressionAssistedConversionValue() {
    return BigDecimalUtil.formatAsReadable(impressionAssistedConversionValue);
  }

  public BigDecimal getImpressionAssistedConversionValueBigDecimal() {
    return impressionAssistedConversionValue;
  }

  public void setImpressionAssistedConversionValue(String impressionAssistedConversionValue) {
    this.impressionAssistedConversionValue = BigDecimalUtil.parseFromNumberString(impressionAssistedConversionValue);
  }

  public Long getImpressions() {
    return impressions;
  }

  public void setImpressions(Long impressions) {
    this.impressions = impressions;
  }

  public String getInteractionRate() {
    return BigDecimalUtil.formatAsReadable(interactionRate);
  }

  public BigDecimal getInteractionRateBigDecimal() {
    return interactionRate;
  }

  public void setInteractionRate(String interactionRate) {
    this.interactionRate = BigDecimalUtil.parseFromNumberString(interactionRate);
  }

  public Long getInteractions() {
    return interactions;
  }

  public void setInteractions(Long interactions) {
    this.interactions = interactions;
  }

  public String getInteractionTypes() {
    return interactionTypes;
  }

  public void setInteractionTypes(String interactionTypes) {
    this.interactionTypes = interactionTypes;
  }

  public String getIsNegative() {
    return isNegative;
  }

  public void setIsNegative(String isNegative) {
    this.isNegative = isNegative;
  }

  public String getLabelIds() {
    return labelIds;
  }

  public void setLabelIds(String labelIds) {
    this.labelIds = labelIds;
  }

  public String getLabels() {
    return labels;
  }

  public void setLabels(String labels) {
    this.labels = labels;
  }

  public String getLongHeadline() {
    return longHeadline;
  }

  public void setLongHeadline(String longHeadline) {
    this.longHeadline = longHeadline;
  }

  public String getMainColor() {
    return mainColor;
  }

  public void setMainColor(String mainColor) {
    this.mainColor = mainColor;
  }

  public String getMarketingImageCallToActionText() {
    return marketingImageCallToActionText;
  }

  public void setMarketingImageCallToActionText(String marketingImageCallToActionText) {
    this.marketingImageCallToActionText = marketingImageCallToActionText;
  }

  public String getMarketingImageCallToActionTextColor() {
    return marketingImageCallToActionTextColor;
  }

  public void setMarketingImageCallToActionTextColor(String marketingImageCallToActionTextColor) {
    this.marketingImageCallToActionTextColor = marketingImageCallToActionTextColor;
  }

  public String getMarketingImageDescription() {
    return marketingImageDescription;
  }

  public void setMarketingImageDescription(String marketingImageDescription) {
    this.marketingImageDescription = marketingImageDescription;
  }

  public String getMarketingImageHeadline() {
    return marketingImageHeadline;
  }

  public void setMarketingImageHeadline(String marketingImageHeadline) {
    this.marketingImageHeadline = marketingImageHeadline;
  }

  public String getMultiAssetResponsiveDisplayAdAccentColor() {
    return multiAssetResponsiveDisplayAdAccentColor;
  }

  public void setMultiAssetResponsiveDisplayAdAccentColor(String multiAssetResponsiveDisplayAdAccentColor) {
    this.multiAssetResponsiveDisplayAdAccentColor = multiAssetResponsiveDisplayAdAccentColor;
  }

  public String getMultiAssetResponsiveDisplayAdAllowFlexibleColor() {
    return multiAssetResponsiveDisplayAdAllowFlexibleColor;
  }

  public void setMultiAssetResponsiveDisplayAdAllowFlexibleColor(String multiAssetResponsiveDisplayAdAllowFlexibleColor) {
    this.multiAssetResponsiveDisplayAdAllowFlexibleColor = multiAssetResponsiveDisplayAdAllowFlexibleColor;
  }

  public String getMultiAssetResponsiveDisplayAdBusinessName() {
    return multiAssetResponsiveDisplayAdBusinessName;
  }

  public void setMultiAssetResponsiveDisplayAdBusinessName(String multiAssetResponsiveDisplayAdBusinessName) {
    this.multiAssetResponsiveDisplayAdBusinessName = multiAssetResponsiveDisplayAdBusinessName;
  }

  public String getMultiAssetResponsiveDisplayAdCallToActionText() {
    return multiAssetResponsiveDisplayAdCallToActionText;
  }

  public void setMultiAssetResponsiveDisplayAdCallToActionText(String multiAssetResponsiveDisplayAdCallToActionText) {
    this.multiAssetResponsiveDisplayAdCallToActionText = multiAssetResponsiveDisplayAdCallToActionText;
  }

  public String getMultiAssetResponsiveDisplayAdDynamicSettingsPricePrefix() {
    return multiAssetResponsiveDisplayAdDynamicSettingsPricePrefix;
  }

  public void setMultiAssetResponsiveDisplayAdDynamicSettingsPricePrefix(String multiAssetResponsiveDisplayAdDynamicSettingsPricePrefix) {
    this.multiAssetResponsiveDisplayAdDynamicSettingsPricePrefix = multiAssetResponsiveDisplayAdDynamicSettingsPricePrefix;
  }

  public String getMultiAssetResponsiveDisplayAdDynamicSettingsPromoText() {
    return multiAssetResponsiveDisplayAdDynamicSettingsPromoText;
  }

  public void setMultiAssetResponsiveDisplayAdDynamicSettingsPromoText(String multiAssetResponsiveDisplayAdDynamicSettingsPromoText) {
    this.multiAssetResponsiveDisplayAdDynamicSettingsPromoText = multiAssetResponsiveDisplayAdDynamicSettingsPromoText;
  }

  public String getMultiAssetResponsiveDisplayAdFormatSetting() {
    return multiAssetResponsiveDisplayAdFormatSetting;
  }

  public void setMultiAssetResponsiveDisplayAdFormatSetting(String multiAssetResponsiveDisplayAdFormatSetting) {
    this.multiAssetResponsiveDisplayAdFormatSetting = multiAssetResponsiveDisplayAdFormatSetting;
  }

  public String getMultiAssetResponsiveDisplayAdMainColor() {
    return multiAssetResponsiveDisplayAdMainColor;
  }

  public void setMultiAssetResponsiveDisplayAdMainColor(String multiAssetResponsiveDisplayAdMainColor) {
    this.multiAssetResponsiveDisplayAdMainColor = multiAssetResponsiveDisplayAdMainColor;
  }

  public String getPath1() {
    return path1;
  }

  public void setPath1(String path1) {
    this.path1 = path1;
  }

  public String getPath2() {
    return path2;
  }

  public void setPath2(String path2) {
    this.path2 = path2;
  }

  public String getPercentNewVisitors() {
    return BigDecimalUtil.formatAsReadable(percentNewVisitors);
  }

  public BigDecimal getPercentNewVisitorsBigDecimal() {
    return percentNewVisitors;
  }

  public void setPercentNewVisitors(String percentNewVisitors) {
    this.percentNewVisitors = BigDecimalUtil.parseFromNumberString(percentNewVisitors);
  }

  public String getPricePrefix() {
    return pricePrefix;
  }

  public void setPricePrefix(String pricePrefix) {
    this.pricePrefix = pricePrefix;
  }

  public String getPromoText() {
    return promoText;
  }

  public void setPromoText(String promoText) {
    this.promoText = promoText;
  }

  public String getResponsiveSearchAdPath1() {
    return responsiveSearchAdPath1;
  }

  public void setResponsiveSearchAdPath1(String responsiveSearchAdPath1) {
    this.responsiveSearchAdPath1 = responsiveSearchAdPath1;
  }

  public String getResponsiveSearchAdPath2() {
    return responsiveSearchAdPath2;
  }

  public void setResponsiveSearchAdPath2(String responsiveSearchAdPath2) {
    this.responsiveSearchAdPath2 = responsiveSearchAdPath2;
  }

  public String getShortHeadline() {
    return shortHeadline;
  }

  public void setShortHeadline(String shortHeadline) {
    this.shortHeadline = shortHeadline;
  }

  public String getSlot() {
    return slot;
  }

  public void setSlot(String slot) {
    this.slot = slot;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getSystemManagedEntitySource() {
    return systemManagedEntitySource;
  }

  public void setSystemManagedEntitySource(String systemManagedEntitySource) {
    this.systemManagedEntitySource = systemManagedEntitySource;
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

  public String getValuePerCurrentModelAttributedConversion() {
    return BigDecimalUtil.formatAsReadable(valuePerCurrentModelAttributedConversion);
  }

  public BigDecimal getValuePerCurrentModelAttributedConversionBigDecimal() {
    return valuePerCurrentModelAttributedConversion;
  }

  public void setValuePerCurrentModelAttributedConversion(String valuePerCurrentModelAttributedConversion) {
    this.valuePerCurrentModelAttributedConversion = BigDecimalUtil.parseFromNumberString(valuePerCurrentModelAttributedConversion);
  }

  public String getVideoQuartile100Rate() {
    return BigDecimalUtil.formatAsReadable(videoQuartile100Rate);
  }

  public BigDecimal getVideoQuartile100RateBigDecimal() {
    return videoQuartile100Rate;
  }

  public void setVideoQuartile100Rate(String videoQuartile100Rate) {
    this.videoQuartile100Rate = BigDecimalUtil.parseFromNumberString(videoQuartile100Rate);
  }

  public String getVideoQuartile25Rate() {
    return BigDecimalUtil.formatAsReadable(videoQuartile25Rate);
  }

  public BigDecimal getVideoQuartile25RateBigDecimal() {
    return videoQuartile25Rate;
  }

  public void setVideoQuartile25Rate(String videoQuartile25Rate) {
    this.videoQuartile25Rate = BigDecimalUtil.parseFromNumberString(videoQuartile25Rate);
  }

  public String getVideoQuartile50Rate() {
    return BigDecimalUtil.formatAsReadable(videoQuartile50Rate);
  }

  public BigDecimal getVideoQuartile50RateBigDecimal() {
    return videoQuartile50Rate;
  }

  public void setVideoQuartile50Rate(String videoQuartile50Rate) {
    this.videoQuartile50Rate = BigDecimalUtil.parseFromNumberString(videoQuartile50Rate);
  }

  public String getVideoQuartile75Rate() {
    return BigDecimalUtil.formatAsReadable(videoQuartile75Rate);
  }

  public BigDecimal getVideoQuartile75RateBigDecimal() {
    return videoQuartile75Rate;
  }

  public void setVideoQuartile75Rate(String videoQuartile75Rate) {
    this.videoQuartile75Rate = BigDecimalUtil.parseFromNumberString(videoQuartile75Rate);
  }

  public String getVideoViewRate() {
    return BigDecimalUtil.formatAsReadable(videoViewRate);
  }

  public BigDecimal getVideoViewRateBigDecimal() {
    return videoViewRate;
  }

  public void setVideoViewRate(String videoViewRate) {
    this.videoViewRate = BigDecimalUtil.parseFromNumberString(videoViewRate);
  }

  public Long getVideoViews() {
    return videoViews;
  }

  public void setVideoViews(Long videoViews) {
    this.videoViews = videoViews;
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
    if (campaignId != null) {
      idBuilder.append("-").append(campaignId);
    }
    if (adGroupId != null) {
      idBuilder.append("-").append(adGroupId);
    }
    if (id != null) {
      idBuilder.append("-").append(id);
    }
    idBuilder.append("-").append(getDateLabel());

    // Include all segmentation fields (if set).
    if (!StringUtils.isEmpty(adNetworkType1)) {
      idBuilder.append("-").append(adNetworkType1);
    }
    if (!StringUtils.isEmpty(adNetworkType2)) {
      idBuilder.append("-").append(adNetworkType2);
    }
    if (!StringUtils.isEmpty(clickType)) {
      idBuilder.append("-").append(clickType);
    }
    if (!StringUtils.isEmpty(conversionCategoryName)) {
      idBuilder.append("-").append(conversionCategoryName);
    }
    if (!StringUtils.isEmpty(conversionLagBucket)) {
      idBuilder.append("-").append(conversionLagBucket);
    }
    if (conversionTrackerId != null) {
      idBuilder.append("-").append(conversionTrackerId);
    }
    if (!StringUtils.isEmpty(conversionTypeName)) {
      idBuilder.append("-").append(conversionTypeName);
    }
    if (criterionId != null) {
      idBuilder.append("-").append(criterionId);
    }
    if (!StringUtils.isEmpty(device)) {
      idBuilder.append("-").append(device);
    }
    if (!StringUtils.isEmpty(externalConversionSource)) {
      idBuilder.append("-").append(externalConversionSource);
    }
    if (!StringUtils.isEmpty(slot)) {
      idBuilder.append("-").append(slot);
    }
    this.rowId = idBuilder.toString();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) { return false; }
    if (obj == this) { return true; }
    if (obj.getClass() != getClass()) { return false; }
    AdPerformanceReport other = (AdPerformanceReport) obj;
    return new EqualsBuilder()
      .appendSuper(super.equals(obj))
      .append(callOnlyPhoneNumber, other.callOnlyPhoneNumber)
      .append(imageAdUrl, other.imageAdUrl)
      .append(multiAssetResponsiveDisplayAdDescriptions, other.multiAssetResponsiveDisplayAdDescriptions)
      .append(multiAssetResponsiveDisplayAdHeadlines, other.multiAssetResponsiveDisplayAdHeadlines)
      .append(multiAssetResponsiveDisplayAdLandscapeLogoImages, other.multiAssetResponsiveDisplayAdLandscapeLogoImages)
      .append(multiAssetResponsiveDisplayAdLogoImages, other.multiAssetResponsiveDisplayAdLogoImages)
      .append(multiAssetResponsiveDisplayAdLongHeadline, other.multiAssetResponsiveDisplayAdLongHeadline)
      .append(multiAssetResponsiveDisplayAdMarketingImages, other.multiAssetResponsiveDisplayAdMarketingImages)
      .append(multiAssetResponsiveDisplayAdSquareMarketingImages, other.multiAssetResponsiveDisplayAdSquareMarketingImages)
      .append(policySummary, other.policySummary)
      .append(responsiveSearchAdDescriptions, other.responsiveSearchAdDescriptions)
      .append(responsiveSearchAdHeadlines, other.responsiveSearchAdHeadlines)
      .append(accentColor, other.accentColor)
      .append(accountCurrencyCode, other.accountCurrencyCode)
      .append(accountDescriptiveName, other.accountDescriptiveName)
      .append(accountTimeZone, other.accountTimeZone)
      .append(activeViewCpm, other.activeViewCpm)
      .append(activeViewCtr, other.activeViewCtr)
      .append(activeViewImpressions, other.activeViewImpressions)
      .append(activeViewMeasurability, other.activeViewMeasurability)
      .append(activeViewMeasurableCost, other.activeViewMeasurableCost)
      .append(activeViewMeasurableImpressions, other.activeViewMeasurableImpressions)
      .append(activeViewViewability, other.activeViewViewability)
      .append(adGroupId, other.adGroupId)
      .append(adGroupName, other.adGroupName)
      .append(adGroupStatus, other.adGroupStatus)
      .append(adNetworkType1, other.adNetworkType1)
      .append(adNetworkType2, other.adNetworkType2)
      .append(adType, other.adType)
      .append(allConversionRate, other.allConversionRate)
      .append(allConversions, other.allConversions)
      .append(allConversionValue, other.allConversionValue)
      .append(allowFlexibleColor, other.allowFlexibleColor)
      .append(automated, other.automated)
      .append(averageCost, other.averageCost)
      .append(averageCpc, other.averageCpc)
      .append(averageCpe, other.averageCpe)
      .append(averageCpm, other.averageCpm)
      .append(averageCpv, other.averageCpv)
      .append(averagePageviews, other.averagePageviews)
      .append(averagePosition, other.averagePosition)
      .append(averageTimeOnSite, other.averageTimeOnSite)
      .append(baseAdGroupId, other.baseAdGroupId)
      .append(baseCampaignId, other.baseCampaignId)
      .append(bounceRate, other.bounceRate)
      .append(businessName, other.businessName)
      .append(callToActionText, other.callToActionText)
      .append(campaignId, other.campaignId)
      .append(campaignName, other.campaignName)
      .append(campaignStatus, other.campaignStatus)
      .append(clickAssistedConversions, other.clickAssistedConversions)
      .append(clickAssistedConversionsOverLastClickConversions, other.clickAssistedConversionsOverLastClickConversions)
      .append(clickAssistedConversionValue, other.clickAssistedConversionValue)
      .append(clicks, other.clicks)
      .append(clickType, other.clickType)
      .append(combinedApprovalStatus, other.combinedApprovalStatus)
      .append(conversionCategoryName, other.conversionCategoryName)
      .append(conversionLagBucket, other.conversionLagBucket)
      .append(conversionRate, other.conversionRate)
      .append(conversions, other.conversions)
      .append(conversionTrackerId, other.conversionTrackerId)
      .append(conversionTypeName, other.conversionTypeName)
      .append(conversionValue, other.conversionValue)
      .append(cost, other.cost)
      .append(costPerAllConversion, other.costPerAllConversion)
      .append(costPerConversion, other.costPerConversion)
      .append(costPerCurrentModelAttributedConversion, other.costPerCurrentModelAttributedConversion)
      .append(creativeDestinationUrl, other.creativeDestinationUrl)
      .append(creativeFinalAppUrls, other.creativeFinalAppUrls)
      .append(creativeFinalMobileUrls, other.creativeFinalMobileUrls)
      .append(creativeFinalUrls, other.creativeFinalUrls)
      .append(creativeFinalUrlSuffix, other.creativeFinalUrlSuffix)
      .append(creativeTrackingUrlTemplate, other.creativeTrackingUrlTemplate)
      .append(creativeUrlCustomParameters, other.creativeUrlCustomParameters)
      .append(criterionId, other.criterionId)
      .append(criterionType, other.criterionType)
      .append(crossDeviceConversions, other.crossDeviceConversions)
      .append(ctr, other.ctr)
      .append(currentModelAttributedConversions, other.currentModelAttributedConversions)
      .append(currentModelAttributedConversionValue, other.currentModelAttributedConversionValue)
      .append(customerDescriptiveName, other.customerDescriptiveName)
      .append(description, other.description)
      .append(description1, other.description1)
      .append(description2, other.description2)
      .append(device, other.device)
      .append(devicePreference, other.devicePreference)
      .append(displayUrl, other.displayUrl)
      .append(engagementRate, other.engagementRate)
      .append(engagements, other.engagements)
      .append(enhancedDisplayCreativeLandscapeLogoImageMediaId, other.enhancedDisplayCreativeLandscapeLogoImageMediaId)
      .append(enhancedDisplayCreativeLogoImageMediaId, other.enhancedDisplayCreativeLogoImageMediaId)
      .append(enhancedDisplayCreativeMarketingImageMediaId, other.enhancedDisplayCreativeMarketingImageMediaId)
      .append(enhancedDisplayCreativeMarketingImageSquareMediaId, other.enhancedDisplayCreativeMarketingImageSquareMediaId)
      .append(externalConversionSource, other.externalConversionSource)
      .append(formatSetting, other.formatSetting)
      .append(gmailCreativeHeaderImageMediaId, other.gmailCreativeHeaderImageMediaId)
      .append(gmailCreativeLogoImageMediaId, other.gmailCreativeLogoImageMediaId)
      .append(gmailCreativeMarketingImageMediaId, other.gmailCreativeMarketingImageMediaId)
      .append(gmailForwards, other.gmailForwards)
      .append(gmailSaves, other.gmailSaves)
      .append(gmailSecondaryClicks, other.gmailSecondaryClicks)
      .append(gmailTeaserBusinessName, other.gmailTeaserBusinessName)
      .append(gmailTeaserDescription, other.gmailTeaserDescription)
      .append(gmailTeaserHeadline, other.gmailTeaserHeadline)
      .append(headline, other.headline)
      .append(headlinePart1, other.headlinePart1)
      .append(headlinePart2, other.headlinePart2)
      .append(id, other.id)
      .append(imageCreativeImageHeight, other.imageCreativeImageHeight)
      .append(imageCreativeImageWidth, other.imageCreativeImageWidth)
      .append(imageCreativeMimeType, other.imageCreativeMimeType)
      .append(imageCreativeName, other.imageCreativeName)
      .append(impressionAssistedConversions, other.impressionAssistedConversions)
      .append(impressionAssistedConversionsOverLastClickConversions, other.impressionAssistedConversionsOverLastClickConversions)
      .append(impressionAssistedConversionValue, other.impressionAssistedConversionValue)
      .append(impressions, other.impressions)
      .append(interactionRate, other.interactionRate)
      .append(interactions, other.interactions)
      .append(interactionTypes, other.interactionTypes)
      .append(isNegative, other.isNegative)
      .append(labelIds, other.labelIds)
      .append(labels, other.labels)
      .append(longHeadline, other.longHeadline)
      .append(mainColor, other.mainColor)
      .append(marketingImageCallToActionText, other.marketingImageCallToActionText)
      .append(marketingImageCallToActionTextColor, other.marketingImageCallToActionTextColor)
      .append(marketingImageDescription, other.marketingImageDescription)
      .append(marketingImageHeadline, other.marketingImageHeadline)
      .append(multiAssetResponsiveDisplayAdAccentColor, other.multiAssetResponsiveDisplayAdAccentColor)
      .append(multiAssetResponsiveDisplayAdAllowFlexibleColor, other.multiAssetResponsiveDisplayAdAllowFlexibleColor)
      .append(multiAssetResponsiveDisplayAdBusinessName, other.multiAssetResponsiveDisplayAdBusinessName)
      .append(multiAssetResponsiveDisplayAdCallToActionText, other.multiAssetResponsiveDisplayAdCallToActionText)
      .append(multiAssetResponsiveDisplayAdDynamicSettingsPricePrefix, other.multiAssetResponsiveDisplayAdDynamicSettingsPricePrefix)
      .append(multiAssetResponsiveDisplayAdDynamicSettingsPromoText, other.multiAssetResponsiveDisplayAdDynamicSettingsPromoText)
      .append(multiAssetResponsiveDisplayAdFormatSetting, other.multiAssetResponsiveDisplayAdFormatSetting)
      .append(multiAssetResponsiveDisplayAdMainColor, other.multiAssetResponsiveDisplayAdMainColor)
      .append(path1, other.path1)
      .append(path2, other.path2)
      .append(percentNewVisitors, other.percentNewVisitors)
      .append(pricePrefix, other.pricePrefix)
      .append(promoText, other.promoText)
      .append(responsiveSearchAdPath1, other.responsiveSearchAdPath1)
      .append(responsiveSearchAdPath2, other.responsiveSearchAdPath2)
      .append(shortHeadline, other.shortHeadline)
      .append(slot, other.slot)
      .append(status, other.status)
      .append(systemManagedEntitySource, other.systemManagedEntitySource)
      .append(valuePerAllConversion, other.valuePerAllConversion)
      .append(valuePerConversion, other.valuePerConversion)
      .append(valuePerCurrentModelAttributedConversion, other.valuePerCurrentModelAttributedConversion)
      .append(videoQuartile100Rate, other.videoQuartile100Rate)
      .append(videoQuartile25Rate, other.videoQuartile25Rate)
      .append(videoQuartile50Rate, other.videoQuartile50Rate)
      .append(videoQuartile75Rate, other.videoQuartile75Rate)
      .append(videoViewRate, other.videoViewRate)
      .append(videoViews, other.videoViews)
      .append(viewThroughConversions, other.viewThroughConversions)
      .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
      .appendSuper(super.hashCode())
      .append(callOnlyPhoneNumber)
      .append(imageAdUrl)
      .append(multiAssetResponsiveDisplayAdDescriptions)
      .append(multiAssetResponsiveDisplayAdHeadlines)
      .append(multiAssetResponsiveDisplayAdLandscapeLogoImages)
      .append(multiAssetResponsiveDisplayAdLogoImages)
      .append(multiAssetResponsiveDisplayAdLongHeadline)
      .append(multiAssetResponsiveDisplayAdMarketingImages)
      .append(multiAssetResponsiveDisplayAdSquareMarketingImages)
      .append(policySummary)
      .append(responsiveSearchAdDescriptions)
      .append(responsiveSearchAdHeadlines)
      .append(accentColor)
      .append(accountCurrencyCode)
      .append(accountDescriptiveName)
      .append(accountTimeZone)
      .append(activeViewCpm)
      .append(activeViewCtr)
      .append(activeViewImpressions)
      .append(activeViewMeasurability)
      .append(activeViewMeasurableCost)
      .append(activeViewMeasurableImpressions)
      .append(activeViewViewability)
      .append(adGroupId)
      .append(adGroupName)
      .append(adGroupStatus)
      .append(adNetworkType1)
      .append(adNetworkType2)
      .append(adType)
      .append(allConversionRate)
      .append(allConversions)
      .append(allConversionValue)
      .append(allowFlexibleColor)
      .append(automated)
      .append(averageCost)
      .append(averageCpc)
      .append(averageCpe)
      .append(averageCpm)
      .append(averageCpv)
      .append(averagePageviews)
      .append(averagePosition)
      .append(averageTimeOnSite)
      .append(baseAdGroupId)
      .append(baseCampaignId)
      .append(bounceRate)
      .append(businessName)
      .append(callToActionText)
      .append(campaignId)
      .append(campaignName)
      .append(campaignStatus)
      .append(clickAssistedConversions)
      .append(clickAssistedConversionsOverLastClickConversions)
      .append(clickAssistedConversionValue)
      .append(clicks)
      .append(clickType)
      .append(combinedApprovalStatus)
      .append(conversionCategoryName)
      .append(conversionLagBucket)
      .append(conversionRate)
      .append(conversions)
      .append(conversionTrackerId)
      .append(conversionTypeName)
      .append(conversionValue)
      .append(cost)
      .append(costPerAllConversion)
      .append(costPerConversion)
      .append(costPerCurrentModelAttributedConversion)
      .append(creativeDestinationUrl)
      .append(creativeFinalAppUrls)
      .append(creativeFinalMobileUrls)
      .append(creativeFinalUrls)
      .append(creativeFinalUrlSuffix)
      .append(creativeTrackingUrlTemplate)
      .append(creativeUrlCustomParameters)
      .append(criterionId)
      .append(criterionType)
      .append(crossDeviceConversions)
      .append(ctr)
      .append(currentModelAttributedConversions)
      .append(currentModelAttributedConversionValue)
      .append(customerDescriptiveName)
      .append(description)
      .append(description1)
      .append(description2)
      .append(device)
      .append(devicePreference)
      .append(displayUrl)
      .append(engagementRate)
      .append(engagements)
      .append(enhancedDisplayCreativeLandscapeLogoImageMediaId)
      .append(enhancedDisplayCreativeLogoImageMediaId)
      .append(enhancedDisplayCreativeMarketingImageMediaId)
      .append(enhancedDisplayCreativeMarketingImageSquareMediaId)
      .append(externalConversionSource)
      .append(formatSetting)
      .append(gmailCreativeHeaderImageMediaId)
      .append(gmailCreativeLogoImageMediaId)
      .append(gmailCreativeMarketingImageMediaId)
      .append(gmailForwards)
      .append(gmailSaves)
      .append(gmailSecondaryClicks)
      .append(gmailTeaserBusinessName)
      .append(gmailTeaserDescription)
      .append(gmailTeaserHeadline)
      .append(headline)
      .append(headlinePart1)
      .append(headlinePart2)
      .append(id)
      .append(imageCreativeImageHeight)
      .append(imageCreativeImageWidth)
      .append(imageCreativeMimeType)
      .append(imageCreativeName)
      .append(impressionAssistedConversions)
      .append(impressionAssistedConversionsOverLastClickConversions)
      .append(impressionAssistedConversionValue)
      .append(impressions)
      .append(interactionRate)
      .append(interactions)
      .append(interactionTypes)
      .append(isNegative)
      .append(labelIds)
      .append(labels)
      .append(longHeadline)
      .append(mainColor)
      .append(marketingImageCallToActionText)
      .append(marketingImageCallToActionTextColor)
      .append(marketingImageDescription)
      .append(marketingImageHeadline)
      .append(multiAssetResponsiveDisplayAdAccentColor)
      .append(multiAssetResponsiveDisplayAdAllowFlexibleColor)
      .append(multiAssetResponsiveDisplayAdBusinessName)
      .append(multiAssetResponsiveDisplayAdCallToActionText)
      .append(multiAssetResponsiveDisplayAdDynamicSettingsPricePrefix)
      .append(multiAssetResponsiveDisplayAdDynamicSettingsPromoText)
      .append(multiAssetResponsiveDisplayAdFormatSetting)
      .append(multiAssetResponsiveDisplayAdMainColor)
      .append(path1)
      .append(path2)
      .append(percentNewVisitors)
      .append(pricePrefix)
      .append(promoText)
      .append(responsiveSearchAdPath1)
      .append(responsiveSearchAdPath2)
      .append(shortHeadline)
      .append(slot)
      .append(status)
      .append(systemManagedEntitySource)
      .append(valuePerAllConversion)
      .append(valuePerConversion)
      .append(valuePerCurrentModelAttributedConversion)
      .append(videoQuartile100Rate)
      .append(videoQuartile25Rate)
      .append(videoQuartile50Rate)
      .append(videoQuartile75Rate)
      .append(videoViewRate)
      .append(videoViews)
      .append(viewThroughConversions)
      .toHashCode();
  }

}
