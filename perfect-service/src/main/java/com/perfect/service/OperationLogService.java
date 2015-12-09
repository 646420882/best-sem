package com.perfect.service;

import java.util.List;

import com.perfect.log.model.QueryOperationRecordModel;
import com.perfect.log.param.LogQueryResult;

/**日志操作
 * @author DELL
 *
 */
public interface OperationLogService {

	
	/**查询日志
	 * @param level
	 * @param opt_id
	 * @param opt_obj
	 * @param start_time
	 * @param end_time
	 * @param page_index
	 * @param page_size
	 * @return
	 */
	public LogQueryResult queryLog(Integer level,String opt_id,
    		String opt_obj,long start_time,long end_time,Integer page_index,Integer page_size);
	
	
	
	/**导出为csv
	 * @param lists
	 * @return
	 */
	public String download(List<QueryOperationRecordModel> lists);
	
}
