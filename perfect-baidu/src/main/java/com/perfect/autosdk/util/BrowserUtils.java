/**
 * Baidu.com Inc.
 * Copyright (c) 2000-2014 All Rights Reserved.
 * work 2014-2-14
 */
package com.perfect.autosdk.util;

import java.awt.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author wangbin
 * @version 1.0
 * @title BrowserUtils
 * @description browser
 * @date 2014-2-14
 */
public class BrowserUtils {

    /**
     * @param url
     * @descirption run native browser to open a url,
     * NOTICE this method is not available in J2SE5 or lower version
     */
    public static void runBrowser(String url) {
        try {
            Desktop desktop = Desktop.getDesktop();
            if (Desktop.isDesktopSupported() && desktop.isSupported(Desktop.Action.BROWSE)) {
                URI uri = new URI(url);
                desktop.browse(uri);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (URISyntaxException ex) {
            ex.printStackTrace();
        }
        return;
    }

    /**
     * @param url
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InterruptedException
     * @throws InvocationTargetException
     * @throws IOException
     * @throws NoSuchMethodException
     * @descripiton run native browser to open a url, this method is safe in all java versions
     */
    @SuppressWarnings("unchecked")
    public static void runBrowser_ForJ2SE5Below(String url) {
        String osName = System.getProperty("os.name", "");
        try {
            if (osName.startsWith("Mac OS")) {
                Class fileMgr;
                fileMgr = Class.forName("com.apple.eio.FileManager");
                Method openURL = fileMgr.getDeclaredMethod("openURL", new Class[]{String.class});
                openURL.invoke(null, new Object[]{url});
            } else if (osName.startsWith("Windows")) {
                Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + url);
            } else { // assume Unix or Linux
                String[] browsers = {"firefox", "opera", "konqueror", "epiphany", "mozilla", "netscape"};
                String browser = null;
                for (int count = 0; count < browsers.length && browser == null; count++)
                    if (Runtime.getRuntime().exec(new String[]{"which", browsers[count]}).waitFor() == 0)
                        browser = browsers[count];
                if (browser == null)
                    throw new NoSuchMethodException("Could not find web browser");
                else
                    Runtime.getRuntime().exec(new String[]{browser, url});
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        BrowserUtils.runBrowser("www.baidu.com");
    }

}
