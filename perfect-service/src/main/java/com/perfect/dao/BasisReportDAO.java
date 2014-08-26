package com.perfect.dao;

import com.perfect.dto.AccountReportDTO;
import com.perfect.entity.*;

import java.util.Date;
import java.util.List;

/**
 * Created by SubDong on 2014/8/6.
 */
public interface BasisReportDAO{
    /**
     * 基础报告
     * @param userTable 数据表名
     * @return
     */
    public List<StructureReportEntity> getUnitReportDate(String userTable);

    /**
     * 	                 _ooOoo_
     *                  o8888888o
     *                  88" . "88
     *                  (| -_- |)
     *                  O\  =  /O
     *               ____/`---'\____
     *             .'  \\|     |//  `.
     *            /  \\|||  :  |||//  \
     *           /  _||||| -:- |||||-  \
     *           |   | \\\  -  /// |   |
     *           | \_|  ''\---/''  |_/ |
     *           \  .-\__  '-'  ___/-. /
     *          __'. .'  /--.--\  `. .' __
     *      ."" '<  `.___\_<|>_/___.'  >'"".
     *     | | :  `- \`.;`\ _ /`;.`/ - ` : | |
     *     \  \ `-.   \_ __\ /__ _/   .-` /  /
     *======`-.____`-.___\_____/___.-`____.-'======
     *                   `=---='
     * ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
     *           佛祖保佑       永无BUG
     * ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
     * 获取账户所有数据
     * @return
     */
    public List<AccountReportDTO> getAccountReport(int Sorted,String fieldName);

    /**
     * 得到用户数据条数
     * @return
     */
    public long getAccountCount();

    /**
     * 时间范围获取账户数据
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return
     */
    public List<AccountReportDTO> getAccountReport(Date startDate,Date endDate);


    /**********API接口*********/
    /**
     * 关键字查询
     * @param id
     * @param table
     * @return
     */
    public List<StructureReportEntity> getKeywordReport(Long[] id,String table);

}
