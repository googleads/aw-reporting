// Copyright 2012 Google Inc. All Rights Reserved.
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

package com.google.api.ads.adwords.jaxws.extensions.report.model.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

/**
 * Util class for Gson Builder.
 *
 * @author jtoledo@google.com (Julian Toledo)
 */
public class GsonUtil {

  public static final GsonBuilder gsonBuilder;

  static {
    gsonBuilder = new GsonBuilder();
    gsonBuilder.setDateFormat(DateUtil.DATE_FORMAT_REPORT);
  }

  /**
   * @return the {@link GsonBuilder} used by this class.
   */
  public static GsonBuilder getGsonBuilder() {
    return gsonBuilder;
  }

  /**
   * Writes the list of objects to the {@link OutputStream} in the readable form, using the
   * configured {@link Gson} instance.
   *
   * @param gson the {@code Gson} instance
   * @param out the {@code OutputStream} to write the JSon
   * @param list the list of objects to be written on the {@code OutputStream}
   * @throws IOException error writing to the {@code OutputStream}
   */
  public static <T> void writeObjectsToStreamAsJson(Gson gson, OutputStream out, List<T> list)
      throws IOException {

    JsonWriter writer = new JsonWriter(new OutputStreamWriter(out, "UTF-8"));
    writer.setIndent("  ");
    writer.beginArray();
    for (T t : list) {
      gson.toJson(t, t.getClass(), writer);
    }
    writer.endArray();
    writer.close();
  }
}
