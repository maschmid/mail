/*
 * JBoss, Home of Professional Open Source
 * Copyright 2011, Red Hat, Inc., and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jboss.seam.mail.attachments;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.activation.URLDataSource;

import org.jboss.seam.mail.core.AttachmentException;
import org.jboss.seam.mail.core.Header;
import org.jboss.seam.mail.core.enumerations.ContentDisposition;

/**
 * @author Cody Lerum
 */
public class URLAttachment extends BaseAttachment {
    public URLAttachment(String url, String fileName, ContentDisposition contentDisposition) {
        super();

        byte[] bytes;
        URLDataSource uds;

        try {
            uds = new URLDataSource(new URL(url));
            bytes = new byte[uds.getInputStream().available()];
            uds.getInputStream().read(bytes);

            super.setFileName(fileName);
            super.setMimeType(uds.getContentType());
            super.setContentDisposition(contentDisposition);
            super.setBytes(bytes);
        } catch (MalformedURLException e) {
            throw new AttachmentException("Wasn't able to create email attachment from URL: " + url, e);
        } catch (IOException e) {
            throw new AttachmentException("Wasn't able to create email attachment from URL: " + url, e);
        }
    }

    public URLAttachment(String url, String fileName, ContentDisposition contentDisposition, String contentClass) {
        this(url, fileName, contentDisposition);
        super.addHeader(new Header("Content-Class", contentClass));
    }
}
