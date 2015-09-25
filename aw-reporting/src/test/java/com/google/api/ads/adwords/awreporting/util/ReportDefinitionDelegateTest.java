// Copyright 2015 Google Inc. All Rights Reserved.
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

package com.google.api.ads.adwords.awreporting.util;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import com.google.api.ads.adwords.awreporting.util.ReportDefinitionDelegate;
import com.google.api.ads.adwords.jaxws.v201506.cm.ApiException_Exception;
import com.google.api.ads.adwords.jaxws.v201506.cm.ReportDefinitionField;
import com.google.api.ads.adwords.jaxws.v201506.cm.ReportDefinitionReportType;
import com.google.api.ads.adwords.jaxws.v201506.cm.ReportDefinitionServiceInterface;
import com.google.common.collect.Lists;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;

/**
 * Test case for the {@code ReportDefinitionDelegate} class.
 */
public class ReportDefinitionDelegateTest {

  private ReportDefinitionField reportDefinitionField;
  @Mock
  private ReportDefinitionServiceInterface reportDefinitionServiceInterfaceMock;
  private ReportDefinitionDelegate reportDefinitionDelegate;

  @Before
  public void setUp() throws ApiException_Exception {
    List<ReportDefinitionField> reportFieldsList = Lists.newArrayList();
    
    // add a explicitly segmented field
    reportDefinitionField = new ReportDefinitionField();
    reportDefinitionField.setFieldName("AdNetworkType1");
    reportDefinitionField.setFieldBehavior("SEGMENT");
    reportFieldsList.add(reportDefinitionField);
    
    // add a non-segmented field
    reportDefinitionField = new ReportDefinitionField();
    reportDefinitionField.setFieldName("ActiveViewCpm");
    reportDefinitionField.setFieldBehavior("METRIC");
    reportFieldsList.add(reportDefinitionField);
    
    // add a implicitly segmented field
    reportDefinitionField = new ReportDefinitionField();
    reportDefinitionField.setFieldName("AdGroupId");
    reportDefinitionField.setFieldBehavior("ATTRIBUTE");
    reportFieldsList.add(reportDefinitionField);

    MockitoAnnotations.initMocks(this);

    when(reportDefinitionServiceInterfaceMock.getReportFields(
        Mockito.<ReportDefinitionReportType>anyObject())).thenReturn(reportFieldsList);
        
    reportDefinitionDelegate = new ReportDefinitionDelegate(reportDefinitionServiceInterfaceMock);
  }

  /**
   * Tests the getSegmentedReportFields(AdWordsSession adWordsSession).
   *
   * @throws ApiException_Exception, exception class for holding a list of service errors
   */
  @Test
  public void testGetSegmentedReportFields() throws Exception {
    List<String> list = reportDefinitionDelegate.getSegmentedReportFields("KEYWORDS_PERFORMANCE_REPORT");

    assertTrue(list.contains("AdNetworkType1"));
    assertTrue(!list.contains("ActiveViewCpm"));
    assertTrue(!list.contains("AdGroupId"));
  }

}
