// Copyright 2013 Google Inc. All Rights Reserved.
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

package com.google.api.ads.adwords.awreporting.server.util;

import com.google.api.ads.adwords.awreporting.model.entities.Report;
import com.google.api.ads.adwords.awreporting.model.entities.ReportAccount;
import com.google.api.ads.adwords.awreporting.model.entities.ReportAd;
import com.google.api.ads.adwords.awreporting.model.entities.ReportAdExtension;
import com.google.api.ads.adwords.awreporting.model.entities.ReportAdGroup;
import com.google.api.ads.adwords.awreporting.model.entities.ReportCampaign;
import com.google.api.ads.adwords.awreporting.model.entities.ReportCampaignNegativeKeyword;
import com.google.api.ads.adwords.awreporting.model.entities.ReportKeyword;
import com.google.api.ads.adwords.awreporting.model.persistence.EntityPersister;
import com.google.api.ads.adwords.awreporting.model.util.DateUtil;
import com.google.common.collect.Lists;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * StorageHelper
 * 
 * @author jtoledo@google.com (Julian Toledo)
 */
@Component
public class StorageHelper {

  private EntityPersister entityPersister;

  public StorageHelper() {
  }
  
  public StorageHelper(EntityPersister entityPersister) {
    this.entityPersister = entityPersister;
  }

  public EntityPersister getEntityPersister() {
    return entityPersister;
  }
  
  /**
   * @param persister the persister to set
   */
  @Autowired
  public void setPersister(EntityPersister persister) {
    this.entityPersister = persister;
  }

  // Report (Generic for all kinds)
  public <R extends Report> List<R> getReportById(Class<R> classR, String id) {
    return entityPersister.get(classR, Report.ID, id);
  }
  
  public <R extends Report> List<R> getReport(Class<R> classR, String key, Object value,
      String dateKey, Date dateStart, Date dateEnd) {

    if (dateStart != null && dateEnd != null) {
      return entityPersister.get(classR, key, value, dateKey, dateStart, dateEnd);
    } else {
      return entityPersister.get(classR, key, value);
    }
  }

  public <R extends Report> List<R> getReportByAccountId(Class<R> classR, Long accountId, Date dateStart,
      Date dateEnd) {
    return entityPersister.get(classR, Report.ACCOUNT_ID, accountId, Report.DAY, dateStart, dateEnd);
  }

  public <R extends Report> List<R> getReportByTopAccountId(Class<R> classR, Long topAccountId,
      Date dateStart, Date dateEnd) {
    
    return entityPersister.get(
        classR, ReportAccount.TOP_ACCOUNT_ID, topAccountId, Report.DAY, dateStart, dateEnd);
  }

  public <R extends Report> List<R> getReportByDates(Class<R> classR, Date dateStart,
      Date dateEnd) {
    
    return entityPersister.get(classR, null, null, Report.DAY, dateStart, dateEnd);
  }

  // ReportCampaign
  public List<ReportCampaign> getReportCampaignByCampaignId(Long campaignId, Date dateStart,
      Date dateEnd) {
    return entityPersister.get(ReportCampaign.class, ReportCampaign.CAMPAIGN_ID, campaignId,
        Report.DAY, dateStart, dateEnd);
  }

  // ReportAdGroup
  public List<ReportAdGroup> getReportAdGroupByCampaignId(Long campaignId, Date dateStart,
      Date dateEnd) {
    return entityPersister.get(ReportAdGroup.class, ReportAdGroup.CAMPAIGN_ID, campaignId,
        Report.DAY, dateStart, dateEnd);
  }

  public List<ReportAdGroup> getReportAdGroupByAdGroupId(Long adGroupId, Date dateStart,
      Date dateEnd) {
    return entityPersister.get(ReportAdGroup.class, ReportAdGroup.ADGROUP_ID, adGroupId, Report.DAY,
        dateStart, dateEnd);
  }

  // ReportAd
  public List<ReportAd> getReportAdByCampaignId(Long campaignId, Date dateStart, Date dateEnd) {
    return entityPersister.get(ReportAd.class, ReportAd.CAMPAIGN_ID, campaignId, Report.DAY,
        dateStart, dateEnd);
  }

  public List<ReportAd> getReportAdByAdGroupId(Long adGroupId, Date dateStart, Date dateEnd) {
    return entityPersister.get(ReportAd.class, ReportAd.ADGROUP_ID, adGroupId, Report.DAY, dateStart,
        dateEnd);
  }

  public List<ReportAd> getReportAdByAdId(Long adId, Date dateStart, Date dateEnd) {
    return entityPersister.get(ReportAd.class, ReportAd.AD_ID, adId, Report.DAY, dateStart, dateEnd);
  }

  // ReportKeyword
  public List<ReportKeyword> getReportKeywordByCampaignId(Long campaignId, Date dateStart,
      Date dateEnd) {
    return entityPersister.get(ReportKeyword.class, ReportKeyword.CAMPAIGN_ID, campaignId,
        Report.DAY, dateStart, dateEnd);
  }

  public List<ReportKeyword> getReportKeywordByAdGroupId(Long adGroupId, Date dateStart,
      Date dateEnd) {
    return entityPersister.get(ReportKeyword.class, ReportKeyword.ADGROUP_ID, adGroupId, Report.DAY,
        dateStart, dateEnd);
  }

  public List<ReportKeyword> getReportKeywordByKeywordId(Long keywordId, Date dateStart,
      Date dateEnd) {
    return entityPersister.get(ReportKeyword.class, ReportKeyword.KEYWORD_ID, keywordId, Report.DAY,
        dateStart, dateEnd);
  }

  // ReportAdExtension
  public List<ReportAdExtension> getReportAdExtensionByCampaignId(Long campaignId, Date dateStart,
      Date dateEnd) {
    return entityPersister.get(ReportAdExtension.class, ReportAdExtension.CAMPAIGN_ID, campaignId,
        Report.DAY, dateStart, dateEnd);
  }

  public List<ReportAdExtension> getReportAdExtensionByAdExtensionId(Long adExtensionId, Date dateStart,
      Date dateEnd) {
    return entityPersister.get(ReportAdExtension.class, ReportAdExtension.ADEXTENSION_ID, adExtensionId, Report.DAY,
        dateStart, dateEnd);
  }

  // ReportCampaignNegativeKeyword
  public List<ReportCampaignNegativeKeyword> getReportCampaignNegativeKeywordByCampaignId(Long keywordId, Date dateStart,
      Date dateEnd) {
    return entityPersister.get(ReportCampaignNegativeKeyword.class, ReportCampaignNegativeKeyword.CAMPAIGN_ID, keywordId, Report.DAY,
        dateStart, dateEnd);
  }

  public List<ReportCampaignNegativeKeyword> getReportCampaignNegativeKeywordByKeywordId(Long keywordId, Date dateStart,
      Date dateEnd) {
    return entityPersister.get(ReportCampaignNegativeKeyword.class, ReportCampaignNegativeKeyword.KEYWORD_ID, keywordId, Report.DAY,
        dateStart, dateEnd);
  }

  public List<ReportCampaignNegativeKeyword> getReportCampaignNegativeKeywordByEndDateInRange(
      Date dateStart, Date dateEnd) {

    return entityPersister.get(ReportCampaignNegativeKeyword.class, null, null, Report.DATE_END,
        DateUtil.formatYearMonthDayNoDash(dateStart), DateUtil.formatYearMonthDayNoDash(dateEnd));
  }
  
  public List<ReportCampaignNegativeKeyword> getReportCampaignNegativeKeywordByAccountAndEndDateInRange(
      Long accountId, Date dateStart, Date dateEnd) {

    return entityPersister.get(ReportCampaignNegativeKeyword.class, Report.ACCOUNT_ID, accountId, Report.DATE_END,
        DateUtil.formatYearMonthDayNoDash(dateStart), DateUtil.formatYearMonthDayNoDash(dateEnd));
  }

  public void createReportIndexes() {
    // Create Indexes
    List<String> indexes = Lists.newArrayList();
    indexes.add(Report.ACCOUNT_ID);
    indexes.add(Report.DAY);
    entityPersister.createIndex(ReportAccount.class, indexes);
    entityPersister.createIndex(ReportCampaign.class, indexes);
    entityPersister.createIndex(ReportAdGroup.class, indexes);
    entityPersister.createIndex(ReportAd.class, indexes);
    entityPersister.createIndex(ReportKeyword.class, indexes);
    entityPersister.createIndex(ReportAdExtension.class, indexes);

    entityPersister.createIndex(ReportAccount.class, Report.ACCOUNT_ID);
    entityPersister.createIndex(ReportCampaign.class, Report.ACCOUNT_ID);
    entityPersister.createIndex(ReportAdGroup.class, Report.ACCOUNT_ID);
    entityPersister.createIndex(ReportAd.class, Report.ACCOUNT_ID);
    entityPersister.createIndex(ReportKeyword.class, Report.ACCOUNT_ID);
    entityPersister.createIndex(ReportAdExtension.class, Report.ACCOUNT_ID);
    entityPersister.createIndex(ReportCampaignNegativeKeyword.class, Report.ACCOUNT_ID);

    entityPersister.createIndex(ReportCampaign.class, Report.CAMPAIGN_ID);
    entityPersister.createIndex(ReportAdGroup.class, Report.CAMPAIGN_ID);
    entityPersister.createIndex(ReportAd.class, Report.CAMPAIGN_ID);
    entityPersister.createIndex(ReportKeyword.class, Report.CAMPAIGN_ID);
    entityPersister.createIndex(ReportAdExtension.class, Report.CAMPAIGN_ID);

    entityPersister.createIndex(ReportAdGroup.class, Report.ADGROUP_ID);
    entityPersister.createIndex(ReportAd.class, Report.ADGROUP_ID);
    entityPersister.createIndex(ReportKeyword.class, Report.ADGROUP_ID);

    entityPersister.createIndex(ReportAd.class, Report.AD_ID);

    entityPersister.createIndex(ReportKeyword.class, Report.KEYWORD_ID);

    entityPersister.createIndex(ReportAdExtension.class, Report.ADEXTENSION_ID);
  }
}
