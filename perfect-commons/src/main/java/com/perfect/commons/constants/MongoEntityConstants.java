package com.perfect.commons.constants;

/**
 * Created by vbzer_000 on 2014/8/27.
 */
public interface MongoEntityConstants {

    //SYS
    String SYS_KEYWORD = "sys_keyword";
    String SYS_CENSUS = "sys_census";
    String SYS_CENSUS_EVERYDAY_REPORT = "sys_census_everyday_report";
    String SYS_CENSUS_CONFIG = "sys_census_config";

    //Jedis static Key
    String TRADE_KEY = "trade_key";

    // Tables
    String TBL_CAMPAIGN = "campaign";
    String TBL_ADGROUP = "adgroup";
    String TBL_KEYWORD = "keyword";
    String TBL_CREATIVE = "creative";
    String TBL_SUBLINK = "sublink";
    String TBL_MOBILESUBLINK = "mobilesublink";
    String TBL_BIDDINGRULE = "bidrule";
    String TBL_LOG = "logs";
    String TBL_ACCOUNT_REPORT = "account_report";
    String TBL_MONITORING_FOLDERS = "monitoring_folders";
    String TBL_MONITORING_TARGETS = "monitoring_targets";
    String TBL_CUSTOMGROUP = "custom_group";
    String TBL_IMPORTANT_KEYWORD = "important_keyword_bidding";
    String TBL_NMS_ACCOUNT_REPORT = "nms_account_report";

    //backUp
    String BAK_CREATIVE = "creative_bak";
    String BAK_ADGROUP = "adgroup_bak";
    String BAK_KEYWORD = "keyword_bak";
    String BAK_CAMPAIGN = "campaign_bak";


    // Fields
    String SYSTEM_ID = "_id";
    String ACCOUNT_ID = "acid";
    String ADGROUP_ID = "agid";
    String KEYWORD_ID = "kwid";
    String CREATIVE_ID = "crid";
    String SUBLINK_ID = "sid";
    String CAMPAIGN_ID = "cid";
    String REGION_ID = "rgid";
    String OBJ_CAMPAIGN_ID = "ocid";
    String OBJ_ADGROUP_ID = "oagid";
    String OBJ_ID = "oid";
    String BAIDU_ID = "bid";
    String FOLDER_ID = "fdId";
    String MONITOR_ID = "mtId";
    String MONITOR_ACLID = "aclid";
    String NAME = "name";


    String DATE_FIELD = "dat";  //时间查询字段
    String IPERATE = "ope"; //设备查询字段
    String USERTYPE = "up"; //新老客户表示
    String INTOPAGE = "tp"; //分组字段intoPage（外部链接）
    String SEARCHENGINE = "se"; //分组字段searchEngine(搜索引擎)
    String COOKIE_UUID = "uid"; //Cookie 分组字段
}
