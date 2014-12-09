package com.perfect.commons.constants;

/**
 * Created by vbzer_000 on 2014/8/27.
 */
public interface MongoEntityConstants {

    //SYS
    public static final String SYS_KEYWORD = "sys_keyword";
    public static final String SYS_CENSUS="sys_census";
    public static final String SYS_CENSUS_EVERYDAY_REPORT="sys_census_everyday_report";
    public static final String SYS_CENSUS_CONFIG="sys_census_config";

    //Jedis static Key
    public static final String TRADE_KEY="trade_key";

    // Tables
    public static final String TBL_CAMPAIGN = "campaign";
    public static final String TBL_ADGROUP = "adgroup";
    public static final String TBL_KEYWORD = "keyword";
    public static final String TBL_CREATIVE = "creative";
    public static final String TBL_BIDDINGRULE = "bidrule";
    public static final String TBL_LOG = "logs";
    public static final String TBL_ACCOUNT_REPORT = "account_report";
    public static final String TBL_MONITORING_FOLDERS = "monitoring_folders";
    public static final String TBL_MONITORING_TARGETS = "monitoring_targets";
    public static final String TBL_CUSTOMGROUP="custom_group";
    public static final String TBL_IMPORTANT_KEYWORD="important_keyword_bidding";

    //backUp
    public static final String BAK_CREATIVE="creative_bak";
    public static final String BAK_ADGROUP="adgroup_bak";
    public static final String BAK_KEYWORD="keyword_bak";
    public static final String BAK_CAMPAIGN="campaign_bak";


    // Fields

    public static final String SYSTEM_ID = "_id";

    public static final String ACCOUNT_ID = "acid";
    public static final String ADGROUP_ID = "agid";
    public static final String KEYWORD_ID = "kwid";
    public static final String CREATIVE_ID = "crid";
    public static final String CAMPAIGN_ID = "cid";
    public static final String REGION_ID = "rgid";
    public static final String OBJ_CAMPAIGN_ID = "ocid";
    public static final String OBJ_ADGROUP_ID = "oagid";
    public static final String OBJ_ID = "oid";
    public static final String BAIDU_ID = "bid";
    public static final String FOLDER_ID = "fdId";
    public static final String MONITOR_ID = "mtId";
    public static final String MONITOR_ACLID = "aclid";

    public static final String NAME = "name";

    //时间查询字段
    public final static String DATE_FIELD = "dat";
    //设备查询字段
    public final static String IPERATE = "ope";
    //新老客户表示
    public final static String USERTYPE = "up";
    //分组字段intoPage（外部链接）
    public final static String INTOPAGE = "tp";
    //分组字段searchEngine(搜索引擎)
    public final static String SEARCHENGINE = "se";
    //Cookie 分组字段
    public final static String COOKIE_UUID ="uid";
}
