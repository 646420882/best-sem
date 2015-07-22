package com.perfect.commons.constants;

/**
 * Created by baizz on 2015-1-12.
 */
public interface RedisConstants {

    String CATEGORY_KEY = "_keywords_category";


    // nms report key
    String REPORT_ID_COMMIT_STATUS = "nms-report-id-commit-status"; // 0: 未完成, 1: 完成

    String REPORT_FILE_URL_SUCCEED = "nms-report-file-url-success";

    String REPORT_FILE_URL_FAILED = "nms-report-file-url-fail";

    String REPORT_FILE_URL_GENERATE_COMPLETE = "nms-report-file-url-complete";  // 0: 未完成, 1: 完成
}
