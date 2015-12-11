package com.perfect.app.assistant.controller;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.perfect.core.AppContext;
import com.perfect.dto.baidu.BaiduAccountInfoDTO;
import com.perfect.log.param.LogQueryResult;
import com.perfect.service.AccountManageService;
import com.perfect.service.CampaignService;
import com.perfect.service.OperationLogService;

/**
 * Created by john on 2015/12/4.
 */
@RestController
@RequestMapping("/assistant")
public class AssistantOperationLogController {

 	@Resource
    private CampaignService campaignService;
 	
 	@Resource
 	private AccountManageService accountManageService;
 	
 	@Resource
 	private OperationLogService operationLogService;
	 
    /**获取日志添加对象层级计划、单元、关键字
     * @param type
     * @param id
     * @return
     */
    @RequestMapping("/logLevel")
    @ResponseBody
    public List<Map<String,Object>> logLevel(Integer type,long id) {
    	
    	return this.campaignService.findAllDownloadCampaignByBaiduAccountId(type,id);
    	
    	//return new ArrayList<Map<String,Object>>();
      
    }
    
    
    /**获取当前百度帐户信息
     * @return
     */
    @RequestMapping("/account")
    @ResponseBody
    public Map<String,String> account(){
    	
    	BaiduAccountInfoDTO dto=accountManageService.findByBaiduUserId(AppContext.getAccountId());
    	
    	Map<String,String> accountMap = new HashMap<String,String>();
    	accountMap.put("accountName",dto.getBaiduUserName());
    	accountMap.put("accountId", String.valueOf(AppContext.getAccountId()));
    	return accountMap;
    }
    
    
//    level:RadiocheckedId,
//		opt_id:optContent.join(","),
//		opt_obj:getCheckBoxsValue(),
//		start_time:$("#start_time").val(),
//		end_time:$("#end_time").val()
    /**获取当前百度帐户信息
     * @return
     */
    @RequestMapping("/queryLog")
    @ResponseBody
    public LogQueryResult queryLog(Integer level,String opt_id,
    		String opt_obj,long start_time,long end_time,Integer page_index,Integer page_size){

    	return operationLogService.queryLog(level, opt_id, opt_obj, start_time, end_time, page_index, page_size);
    	
    }
    
    @RequestMapping("/download")
    public void download(Integer level,String opt_id,
    		String opt_obj,long start_time,long end_time,HttpServletResponse response){
    	
    	LogQueryResult result=operationLogService.queryLog(level, opt_id, opt_obj, start_time, end_time, 1, 10000);
    	String content=operationLogService.download(result.getLists());
    	try{
    	// 设置文件后缀
    			String fileName="操作日志-";
    			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhh24mmss");
    			String fn = fileName.concat(sdf.format(new Date()).toString() + ".csv");

    			// 读取字符编码
    			String csvEncoding = "UTF-8";
    			// 设置响应
    			

    			response.setContentType("application/octet-stream; charset=" + csvEncoding);
    			response.setHeader("Content-Disposition", "attachment; filename="
    					+ new String(fn.getBytes(), "iso8859-1"));
    			// 写出响应
    			OutputStream os = response.getOutputStream();
    			os.write(content.getBytes("GBK"));
    			os.flush();
    			os.close();
    	}catch(Exception e){
    		e.printStackTrace();
    	}

    	
    }
    
}
