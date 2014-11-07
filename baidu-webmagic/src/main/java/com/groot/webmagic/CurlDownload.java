package com.groot.webmagic;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.downloader.Downloader;

import java.io.*;

/**
 * Created by yousheng on 14/11/5.
 */
public class CurlDownload implements Downloader, Constant {
    @Override
    public Page download(Request request, Task task) {

        File file = null;
        try {

            file = File.createTempFile(request.getExtra(REQUEST_UUID).toString(),".sh");

            file.setExecutable(true);

            FileWriter writer = new FileWriter(file);

            String cmd = request.getExtra(REQUEST_CURL).toString();

            Object keyword = request.getExtra(REQUEST_KEY);
            if(keyword != null) {
                cmd = cmd.replaceAll("test", keyword.toString());
            }

            Object region = request.getExtra(REQUEST_REGION);
            if(region != null) {
                cmd = cmd.replaceAll("area%22%3A\\d+", "area%22%3A" + region.toString() );
            }
            writer.write(cmd);
            writer.flush();

//            System.out.println("file.getAbsolutePath() = " + file.getAbsolutePath());

            InputStream is = Runtime.getRuntime().exec(file.getAbsolutePath()).getInputStream();
            byte[] bytes = new byte[200 * 1024];

            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            int count = -1;
            while((count = is.read(bytes))!=-1){
                bout.write(bytes,0,count);
            }
            String sb = new String(bout.toByteArray());

//            System.out.println("sb.toString() = " + sb.toString());
//            Files.write(tring().getBytes(), new File("/Users/yousheng/text.html"));
            Page page = new Page();
            page.setRawText(sb);
            page.setRequest(request);

            page.setStatusCode(200);
            return page;
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(file != null){
                file.delete();
            }
        }

        return null;
    }

    @Override
    public void setThread(int threadNum) {

    }

}
