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

package com.google.api.ads.adwords.jaxws.extensions.util;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.Resource;

/**
 * This class is used to inject dynamic resource files into the Spring application context
 *
 * @author gustavomoreira@google.com (Gustavo Moreira)
 */
public class DynamicPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {

  private static Resource dynamicResource;

  /**
   * Constructor.
   *
   * Populates the location of the property place holder, with the statically set location.
   */
  public DynamicPropertyPlaceholderConfigurer() {

    if (DynamicPropertyPlaceholderConfigurer.dynamicResource != null) {
      this.setLocation(DynamicPropertyPlaceholderConfigurer.dynamicResource);
    }
  }

  /**
   * Sets the location of the resource that will be recovered dynamically by the container.
   *
   * @param dynamicResource the dynamicResource to set
   */
  public static void setDynamicResource(Resource dynamicResource) {

    if (DynamicPropertyPlaceholderConfigurer.dynamicResource == null) {
      DynamicPropertyPlaceholderConfigurer.dynamicResource = dynamicResource;
    }
  }

}
