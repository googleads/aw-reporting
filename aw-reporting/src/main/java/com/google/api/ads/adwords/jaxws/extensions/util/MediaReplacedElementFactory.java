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

import com.lowagie.text.Image;
import com.lowagie.text.pdf.codec.Base64;

import org.apache.commons.io.IOUtils;
import org.w3c.dom.Element;
import org.xhtmlrenderer.extend.FSImage;
import org.xhtmlrenderer.extend.ReplacedElement;
import org.xhtmlrenderer.extend.ReplacedElementFactory;
import org.xhtmlrenderer.extend.UserAgentCallback;
import org.xhtmlrenderer.layout.LayoutContext;
import org.xhtmlrenderer.pdf.ITextFSImage;
import org.xhtmlrenderer.pdf.ITextImageElement;
import org.xhtmlrenderer.render.BlockBox;
import org.xhtmlrenderer.simple.extend.FormSubmissionListener;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;

/**
 * Replaced element in order to replace elements like
 * <tt>&lt;div class="media" data-src="image.png"/></tt> with the real media content.
 *
 * @author markbowyer borrowed totally from "Alex" on StackOverflow.
 */
public class MediaReplacedElementFactory implements ReplacedElementFactory {

  private final ReplacedElementFactory superFactory;

  public MediaReplacedElementFactory(ReplacedElementFactory superFactory) {
    this.superFactory = superFactory;
  }

  /**
   * @see org.xhtmlrenderer.extend.ReplacedElementFactory
   *      #createReplacedElement(org.xhtmlrenderer.layout.LayoutContext,
   *      org.xhtmlrenderer.render.BlockBox, org.xhtmlrenderer.extend.UserAgentCallback, int, int)
   */
  @Override
  public ReplacedElement createReplacedElement(LayoutContext layoutContext, BlockBox blockBox,
      UserAgentCallback userAgentCallback, int cssWidth, int cssHeight) {
    Element element = blockBox.getElement();
    if (element == null) {
      return null;
    }
    String nodeName = element.getNodeName();
    String className = element.getAttribute("class");

    // Replace any <div class="media" data-src="image.png" /> with the
    // binary data of `image.png` into the PDF.
    if ("div".equals(nodeName) && className.startsWith("media")) {
      if (!element.hasAttribute("data-src")) {
        throw new RuntimeException(
            "An element with class `media` is missing a `data-src` "
                + "attribute indicating the media file.");
      }

      InputStream input = null;
      String dataSrc = element.getAttribute("data-src");
      try {

        if (dataSrc.startsWith("http")) {
          input = new URL(dataSrc).openStream();
        } else if (dataSrc.startsWith("data:image") && dataSrc.contains("base64")) {

          byte[] image = Base64.decode(dataSrc.split(",")[1]);
          input = new ByteArrayInputStream(image);

        } else {
          input = new FileInputStream(dataSrc);
        }

        final byte[] bytes = IOUtils.toByteArray(input);
        final Image image = Image.getInstance(bytes);
        final FSImage fsImage = new ITextFSImage(image);
        if (fsImage != null) {
          if ((cssWidth != -1) || (cssHeight != -1)) {
            fsImage.scale(cssWidth, cssHeight);
          }
          return new ITextImageElement(fsImage);
        }
      } catch (Exception e) {
        throw new RuntimeException(
            "There was a problem trying to read a template embedded graphic.", e);
      } finally {
        IOUtils.closeQuietly(input);
      }
    }
    return this.superFactory.createReplacedElement(
        layoutContext, blockBox, userAgentCallback, cssWidth, cssHeight);
  }

  /**
   * @see org.xhtmlrenderer.extend.ReplacedElementFactory#reset()
   */
  @Override
  public void reset() {
    this.superFactory.reset();
  }

  /**
   * @see org.xhtmlrenderer.extend.ReplacedElementFactory#remove(org.w3c.dom.Element)
   */
  @Override
  public void remove(Element e) {
    this.superFactory.remove(e);
  }

  /**
   * @see org.xhtmlrenderer.extend.ReplacedElementFactory
   *      #setFormSubmissionListener(org.xhtmlrenderer.simple.extend.FormSubmissionListener)
   */
  @Override
  public void setFormSubmissionListener(FormSubmissionListener listener) {
    this.superFactory.setFormSubmissionListener(listener);
  }

}
