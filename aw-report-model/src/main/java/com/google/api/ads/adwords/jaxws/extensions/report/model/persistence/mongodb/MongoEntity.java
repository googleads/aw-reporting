// Copyright 2014 Google Inc. All Rights Reserved.
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

package com.google.api.ads.adwords.jaxws.extensions.report.model.persistence.mongodb;

/**
 * An Interface to set the proper MongoDB "_id" for entities that need MongoDB Storage.
 *
 * @author jtoledo@google.com (Julian Toledo)
 */
public interface MongoEntity {

  /**
   * Retrieves the Id to be used in Mongo.
   *
   * @return the String ID to be used in Mongo.
   */
  String getId();

}
