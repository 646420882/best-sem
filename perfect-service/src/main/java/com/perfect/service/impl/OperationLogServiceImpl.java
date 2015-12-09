package com.perfect.service.impl;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.stereotype.Component;

import com.perfect.core.AppContext;
import com.perfect.log.filters.field.enums.AdGroupEnum;
import com.perfect.log.filters.field.enums.CampaignEnum;
import com.perfect.log.filters.field.enums.CreativeEnum;
import com.perfect.log.filters.field.enums.Expression;
import com.perfect.log.filters.field.enums.FieldEnum;
import com.perfect.log.filters.field.enums.KeyWordEnum;
import com.perfect.log.model.QueryOperationRecordModel;
import com.perfect.log.param.LogConfig;
import com.perfect.log.param.LogQueryResult;
import com.perfect.log.util.LogOptUtil;
import com.perfect.service.OperationLogService;

@Component
public class OperationLogServiceImpl implements OperationLogService {

	private String logColumn[]={"时间","操作内容","操作类型","操作层级","层级名称","所属账户","推广计划","推广单元","操作前"	,"操作后"};
	
	@Override
	public LogQueryResult queryLog(Integer level, String opt_id,
			String opt_obj, long start_time, long end_time, Integer page_index,
			Integer page_size) {
		 LogConfig log = new LogConfig();
    	 log.addPageParam(page_index, page_size);
    	 log.addField(FieldEnum.USERID.toString(), Expression.EQ.toString(),AppContext.getAccountId());
    	 log.addField(FieldEnum.OPTLEVEL.toString(), Expression.EQ.toString(),level);
    	 log.addField(FieldEnum.OPTIME.toString(), Expression.GT.toString(),start_time);
		 log.addField(FieldEnum.OPTIME.toString(), Expression.LT.toString(),end_time);
		 log.addField(FieldEnum.OPTCONTENT.toString(), Expression.IN.toString(),opt_obj);
		 
    	 switch (level) {
    	 	case CampaignEnum.KeyID:
			 log.addField(FieldEnum.PLANID.toString(), Expression.IN.toString(),opt_id);
			break;
    	 	case AdGroupEnum.KeyID:
			 log.addField(FieldEnum.UNITID.toString(), Expression.IN.toString(),opt_id);
			break;
    	 	case CreativeEnum.KeyID :
   			 log.addField(FieldEnum.OPTCOMPREHENSIVEID.toString(), Expression.IN.toString(),opt_id);
   			break;
    	 	case KeyWordEnum.KeyID :
			 log.addField(FieldEnum.OPTCOMPREHENSIVEID.toString(), Expression.IN.toString(),opt_id);
			break;

		default:
			break;
		}
    	return LogOptUtil.findLogs(log);
	}

	@Override
	public String download(List<QueryOperationRecordModel> lists) {
		StringBuffer buf= new StringBuffer();
		//创建表头
		for(int i=0;i<logColumn.length;i++){
			buf.append(logColumn[i]).append(",");
		}
		buf.append("\r\n");
		
		if(lists!=null){
			//{"时间","操作内容","操作类型","操作层级","层级名称","所属账户","推广计划","推广单元","操作前"	,"操作后"};	
			//创建数据
			for(QueryOperationRecordModel model:lists){
				long time=model.getOptTime();
				
				buf.append(dateFormat(time)).append(",");
				buf.append(model.getContentType()).append(",");
				buf.append(model.getOptTypeName()).append(",");
				buf.append(model.getdName()).append(",");
				buf.append(model.getOptContent()).append(",");
				buf.append(model.getBdfcName()).append(",");
				buf.append(model.getPlanName()).append(",");
				buf.append(model.getUnitName()).append(",");
				buf.append(model.getOldValue()).append(",");
				buf.append(model.getNewValue()).append(",");
				buf.append("\r\n");
			}
			
			
		}
		return buf.toString();
	}

	
	/**毫秒转日期字符串
	 * @param time
	 * @return
	 */
	String dateFormat(long time){
		Date dat=new Date(time);  
        GregorianCalendar gc = new GregorianCalendar();   
        gc.setTime(dat);  
        java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss");  
        String sb=format.format(gc.getTime());
        return sb;
	}
	
}
