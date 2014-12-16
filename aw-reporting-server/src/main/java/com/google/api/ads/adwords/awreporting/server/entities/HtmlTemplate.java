//Copyright 2014 Google Inc. All Rights Reserved.
//
//Licensed under the Apache License, Version 2.0 (the "License");
//you may not use this file except in compliance with the License.
//You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
//Unless required by applicable law or agreed to in writing, software
//distributed under the License is distributed on an "AS IS" BASIS,
//WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//See the License for the specific language governing permissions and
//limitations under the License.

package com.google.api.ads.adwords.awreporting.server.entities;

import com.google.api.ads.adwords.awreporting.model.persistence.mongodb.MongoEntity;
import com.google.common.base.Charsets;

import com.googlecode.objectify.annotation.Index;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
<<<<<<< HEAD
import javax.persistence.Lob;
=======
>>>>>>> 2f2a7486c98ac121ad93d5d5bb44c6f875fcbc1a
import javax.persistence.Table;

/**
 * Model class to persist HTML report templates to the datastore.
 * 
 * @author joeltoby
 *
 */
@Entity
@com.googlecode.objectify.annotation.Entity
@Table(name = "AW_HtmlTemplate")
public class HtmlTemplate implements MongoEntity {

  public static final String ID = "id";
  public static final String USER_ID = "userId";
  public static final String IS_PUBLIC = "isPublic";

  @Id
  @com.googlecode.objectify.annotation.Id
  @Column(name = "ID")
  private String id;

  @Index
  @Column(name = "USER_ID")
  private String userId;

  @Column(name = "TEMPLATE_NAME")
  protected String templateName;

  @Column(name = "TEMPLATE_DESCRIPTION")
  protected String templateDescription;

<<<<<<< HEAD
  @Lob
  @Column(name = "TEMPLATE_HTML", length = 100000)
=======
  @Column(name = "TEMPLATE_HTML")
>>>>>>> 2f2a7486c98ac121ad93d5d5bb44c6f875fcbc1a
  protected String templateHtml;

  @Index
  @Column(name = "IS_PUBLIC")
  protected boolean isPublic;

  public HtmlTemplate() {}

  public HtmlTemplate(String userId, String templateName,
      String templateDescription, String htmlTemplate, boolean isPublic) {
    this.userId = userId;
    this.templateName = templateName;
    this.templateDescription = templateDescription;
    this.templateHtml = htmlTemplate;
    this.isPublic = isPublic;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getTemplateName() {
    return templateName;
  }

  public void setTemplateName(String templateName) {
    this.templateName = templateName;
  }

  public String getTemplateDescription() {
    return templateDescription;
  }

  public void setTemplateDescription(String templateDescription) {
    this.templateDescription = templateDescription;
  }

  public String getTemplateHtml() {
    return templateHtml;
  }

  public InputStream getTemplateHtmlAsInputStream() {
    return new ByteArrayInputStream(templateHtml.getBytes(Charsets.UTF_8));
  }

  public void setTemplateHtml(String templateHtml) {
    this.templateHtml = templateHtml;
  }

  public boolean isPublic() {
    return isPublic;
  }

  /**
   * Sets the template as 'public' meaning that it should be accessible to all users
   * @param isPublic
   */
  public void setPublic(boolean isPublic) {
    this.isPublic = isPublic;
  }
}
