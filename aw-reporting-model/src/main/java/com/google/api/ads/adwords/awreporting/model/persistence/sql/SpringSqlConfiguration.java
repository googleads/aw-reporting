// Copyright 2018 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.api.ads.adwords.awreporting.model.persistence.sql;

import com.google.api.ads.adwords.awreporting.model.persistence.sql.SqlReportEntitiesPersister.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;

/**
 * Spring configuration class for the SQL persistence bean, specifically {@link
 * SqlReportEntitiesPersister}.
 *
 * <p>Configures the transaction settings to {@link EnableRetry}, this is required for MS SQL Server
 * which will fail transactions for spurious deadlocks on pages (rather than deadlocks on rows, see
 * github issue 294).
 */
@Configuration
@EnableRetry
public class SpringSqlConfiguration {

  @Bean
  public Config sqlReportEntityConfig() {
    return new SqlReportEntitiesPersister.Config();
  }
}
