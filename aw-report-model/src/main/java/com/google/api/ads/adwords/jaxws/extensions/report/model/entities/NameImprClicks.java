package com.google.api.ads.adwords.jaxws.extensions.report.model.entities;

public class NameImprClicks {
  public String clickType;
  public Long impressions = 0L;
  public Long clicks = 0L;
  
  public NameImprClicks() {}
  
  public NameImprClicks(String clickType) {
    this.clickType = clickType;
  }

}

