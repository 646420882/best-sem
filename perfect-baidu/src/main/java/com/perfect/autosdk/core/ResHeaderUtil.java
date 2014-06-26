/**
 * ResHeaderUtil.java
 *
 * Copyright 2010 @company@, Inc.
 *
 * @company@ licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.perfect.autosdk.core;

import com.perfect.autosdk.exception.ApiException;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.List;

/**
 * The response header process utility class
 * 
 * @author @author@ (@author-email@)
 * 
 * @version @version@, $Date: 2010-8-30$
 * 
 */
public abstract class ResHeaderUtil {

    static ThreadLocal<ResHeader> resHeader = new ThreadLocal<ResHeader>();

    /**
     * Get response header if using Json protocol.
     * 
     * @param print whether print response header into console.
     * @return The response header
     */
    public static ResHeader getJsonResHeader(boolean print) {
        ResHeader resH = resHeader.get();
        if (print && resH != null) {
            printResHeader(resH);
        }
        return resH;
    }

    /**
     * Print the response header to Console.
     * 
     * @param resH
     *            The response header
     */
    public static void printResHeader(ResHeader resH) {
        // output soap response header
        System.out.println("Status \t: " + resH.getStatus());
        System.out.println("Desc \t: " + resH.getDesc());
        if (resH.getStatus() != 3) {
            System.out.println("Oprs \t: " + resH.getOprs());
            System.out.println("Oprtime\t: " + resH.getOprtime());
            System.out.println("Quota \t: " + resH.getQuota());
            System.out.println("Rquota \t: " + resH.getRquota());
        }
        List<Failure> failures = resH.getFailures();
        if (failures != null && failures.size() > 0) {
            for (int i = 0; i < failures.size(); i++) {
                System.out.println("Failure [" + i + "] :");
                System.out.println("\tcode \t\t: " + failures.get(i).getCode());
                System.out.println("\tmessage \t: " + failures.get(i).getMessage());
                System.out.println("\tposition \t: " + failures.get(i).getPosition());
                System.out.println("\tcontent \t: " + failures.get(i).getContent());
            }
        }
    }

    /**
     * Get response header from service.
     * 
     * @param proxy
     *            The service instance
     * @param print
     *            Print the response header to Console if <code>true</code>
     * @return The response header
     * @throws ApiException 
     */
    public static ResHeader getResHeader(Object proxy, boolean print) throws ApiException {
        return getJsonResHeader(print);
    }

    ///////////////////////////////////////////////////////////////////////////////
    // Protected or private methods
    ///////////////////////////////////////////////////////////////////////////////

    private static String extractText(Element obj, String localName) {
        NodeList nodelist = obj.getElementsByTagNameNS("*", localName);
        if (nodelist != null && nodelist.getLength() > 0) {
            Element node = (Element) nodelist.item(0);
            if (node != null) {
                String str = node.getTextContent();
                if (str != null) {
                    str = str.trim();
                    if (str.length() > 0)
                        return str;
                }
            }
        }
        return null;
    }

    private static Failure parseFailures(Element obj) {
        Failure fail = new Failure();
        int code = Integer.parseInt(extractText(obj, "code"));
        fail.setCode(code);
        String message = extractText(obj, "message");
        fail.setMessage(message);
        String position = extractText(obj, "position");
        fail.setPosition(position);
        String content = extractText(obj, "content");
        fail.setContent(content);
        return fail;
    }
}
