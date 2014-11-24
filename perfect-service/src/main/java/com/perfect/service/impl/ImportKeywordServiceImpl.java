package com.perfect.service.impl;

import com.perfect.service.ImportKeywordService;

/**
 * Created by XiaoWei on 2014/7/29.
 */
public class ImportKeywordServiceImpl implements ImportKeywordService {
//    @Resource
//    AccountAnalyzeDAO accountAnalyzeDAO;
//    @Resource
//    KeywordDAO keywordDAO;
//    private final String DATE_START = "startDate";
//    private final String DATE_END = "endDate";
//    private final String USER_NAME = "userTable";
//    private final String ORDER_BY = "sort";
//    private final String LIMIT = "limit";
//
//    /**
//     * 获取重点关键字过滤出来的关键字数据
//     *
//     * @return
//     */
//
////    @Override
//    public List<KeywordRealTimeDataVOEntity> getMap(HttpServletRequest request) {
//        List<Long> keywords = null;
//        List<KeywordRealTimeDataVOEntity> entities = new ArrayList<>();
//        Map<Long, KeywordRealTimeDataVOEntity> map = new HashMap<>();
//        List<KeywordRealTimeDataVOEntity> importKeywordList = new ArrayList<>();
//        List<KeywordRealTimeDataVOEntity> list = null;
//        List<String> dates = getCurrDate(request);
//        String currLoginUserName = AppContext.getUser();
//        if (!currLoginUserName.equals(null) || !currLoginUserName.equals("")) {
//            currLoginUserName = getUpCaseWord(currLoginUserName);
//            for (int i = 0; i < dates.size(); i++) {
//                entities = accountAnalyzeDAO.performance(currLoginUserName + "-KeywordRealTimeData-log-" + dates.get(i));
//                if (keywords.size() > 0) {
//                    for (int k = 0; k < keywords.size(); k++) {
//                        for (int j = 0; j < entities.size(); j++) {
//                            if (keywords.get(k).equals(entities.get(j).getKeywordId())) {
//                                importKeywordList.add(entities.get(j));
//                            }
//                        }
//                    }
//                }
//            }
//            if (importKeywordList.size() != 0) {
//                ForkJoinPool joinPool = new ForkJoinPool();
//                try {
//                    Future<Map<Long, KeywordRealTimeDataVOEntity>> joinTask = joinPool.submit(new ImportKeywordFork(importKeywordList, 0, importKeywordList.size()));
//                    map = joinTask.get();
//                    DecimalFormat df = new DecimalFormat("#.00");
//                    for (Map.Entry<Long, KeywordRealTimeDataVOEntity> entry : map.entrySet()) {
//                        if (entry.getValue().getImpression() == 0) {
//                            entry.getValue().setCtr(0.00);
//                        } else {
//                            entry.getValue().setCtr(Double.parseDouble(df.format(entry.getValue().getClick().doubleValue() / entry.getValue().getImpression().doubleValue())));
//                        }
//                        if (entry.getValue().getClick() == 0) {
//                            entry.getValue().setCpc(0.00);
//                        } else {
//                            entry.getValue().setCpc(Double.parseDouble(df.format(entry.getValue().getCost() / entry.getValue().getClick().doubleValue())));
//                        }
//
//                        entry.getValue().setOrderBy(request.getParameter(ORDER_BY));
//                    }
//
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } catch (ExecutionException e) {
//                    e.printStackTrace();
//                }
//                joinPool.shutdown();
//            }
//
//            list = new ArrayList<>(map.values());
//            List<KeywordRealTimeDataVOEntity> finalList = new ArrayList<>();
//            Integer limit = Integer.parseInt(request.getParameter(LIMIT));
//            if (list.size() > limit) {
//                for (int i = 0; i < limit; i++) {
//                    finalList.add(list.get(i));
//                }
//                Collections.sort(finalList);
//                return finalList;
//            } else {
//                Collections.sort(list);
//                return list;
//            }
//        }
//        return list;
//    }
//
//
//    /**
//     * 转换首字母大写
//     *
//     * @param str
//     * @return
//     */
//    private String getUpCaseWord(String str) {
//        if (str.length() > 1) {
//            String first = str.substring(0, 1).toUpperCase();
//            String rest = str.substring(1, str.length());
//            return new StringBuffer(first).append(rest).toString();
//        }
//        return str;
//    }
//
//    /**
//     * 获取时间段数组
//     *
//     * @param request
//     * @return
//     */
//    private List<String> getCurrDate(HttpServletRequest request) {
//        String startDate = request.getParameter(DATE_START);
//        String endDate = request.getParameter(DATE_END);
//        List<String> dates = DateUtils.getPeriod(startDate, endDate);
//        return dates;
//    }

}
