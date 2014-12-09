package com.perfect.db.mongodb.impl;

import com.perfect.dao.keyword.KeywordQualityDAO;
import com.perfect.db.mongodb.base.AbstractUserBaseDAOImpl;
import com.perfect.db.mongodb.base.BaseMongoTemplate;
import com.perfect.dto.keyword.KeywordReportDTO;
import com.perfect.entity.report.KeywordReportEntity;
import com.perfect.utils.DateUtils;
import com.perfect.utils.ObjectUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by baizz on 2014-07-24.
 * 2014-12-2 refactor
 */
@Repository("keywordQualityDAO")
public class KeywordQualityDAOImpl extends AbstractUserBaseDAOImpl<KeywordReportDTO, Long> implements KeywordQualityDAO {

    @Override
    @SuppressWarnings("unchecked")
    public Class<KeywordReportEntity> getEntityClass() {
        return KeywordReportEntity.class;
    }

    @Override
    public Class<KeywordReportDTO> getDTOClass() {
        return KeywordReportDTO.class;
    }

    @Override
    public List<KeywordReportDTO> findYesterdayKeywordReport() {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        Date startDate = new Date(), endDate = new Date();
//
//        boolean isLoadYesterdayData = false;
//        if (_startDate != null) {
//            Assert.notNull(_endDate, "_endDate must not be null!");
//        }
//        if (_endDate != null) {
//            Assert.notNull(_startDate, "_startDate must not be null!");
//        }
//        if (_startDate == null && _endDate == null) {
//            isLoadYesterdayData = true;
//            //昨天的时间
//            Calendar cal = Calendar.getInstance();
//            cal.set(Calendar.HOUR_OF_DAY, 0);
//            cal.set(Calendar.MINUTE, 0);
//            cal.set(Calendar.SECOND, 0);
//            cal.set(Calendar.MILLISECOND, 0);
//            cal.add(Calendar.DATE, -1);
//            startDate = cal.getTime();
//            endDate = cal.getTime();
//        } else {
//            try {
//                startDate = sdf.parse(_startDate);
//                endDate = sdf.parse(_endDate);
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//        }

        String collectionName = DateUtils.getYesterdayStr() + "-keyword";
//        List<String> names = new ArrayList<>();     //待查询的collectionName

//        if (isLoadYesterdayData) {
//            String name = _startDate + "-keyword";
//            names.add(name);
//        } else {
//            if (_startDate.equals(_endDate)) {
//                //查询的是某一天的数据
//                String name = _startDate + "-keyword";
//                names.add(name);
//            } else {
//                //查询的是某一个时间段的数据
//                Calendar cal1 = Calendar.getInstance();
//                cal1.setTime(startDate);
//                while (cal1.getTime().getTime() <= endDate.getTime()) {
//                    cal1.setTime(startDate);
//                    names.add(sdf.format(cal1.getTime()) + "-keyword");
//                    cal1.add(Calendar.DATE, 1);
//                    startDate = cal1.getTime();
//                }
//
//            }
//
//        }

        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserReportMongo();
        List<KeywordReportEntity> list = mongoTemplate.findAll(getEntityClass(), collectionName);

//        //查询
//        ForkJoinPool forkJoinPool = new ForkJoinPool();
//        try {
//            String userName = AppContext.getUser();
//            QueryTask task = new QueryTask(userName,names, 0, names.size());
//            Future<List<KeywordReportEntity>> voResult = forkJoinPool.submit(task);
//            list = voResult.get();
//        } catch (InterruptedException | ExecutionException e) {
//            e.printStackTrace();
//        } finally {
//            forkJoinPool.shutdown();
//        }
        return ObjectUtils.convert(list, getDTOClass());
    }

    @Override
    public List<KeywordReportDTO> find(Map<String, Object> params, int skip, int limit) {
        return null;
    }

//    @Override
//    public List<Long> findYesterdayAllKeywordId() {
//        String collectionName = DateUtils.getYesterdayStr() + "-keyword";
//        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserReportMongo();
//
//        Aggregation aggregation = newAggregation(
//                project(KEYWORD_ID, "pccli").andExclude(SYSTEM_ID),
//                match(Criteria.where("pccli").ne(null))
//        );
//
//        AggregationResults<KeywordIdVO> results = mongoTemplate.aggregate(aggregation, collectionName, KeywordIdVO.class);
//        List<Long> keywordIdList = new ArrayList<>();
//        for (KeywordIdVO vo : results) {
//            keywordIdList.add(vo.getKeywordId());
//        }
//
//        return keywordIdList;
//    }


//    @Deprecated
//    class KeywordIdVO {
//
//        @Field(KEYWORD_ID)
//        private Long keywordId;
//
//        public Long getKeywordId() {
//            return keywordId;
//        }
//
//        public void setKeywordId(Long keywordId) {
//            this.keywordId = keywordId;
//        }
//    }
//
//    @Deprecated
//    class QueryTask extends RecursiveTask<List<KeywordReportEntity>> {
//
//        private static final int threshold = 4;
//        private final String userName;
//
//        private int first;
//        private int last;
//        private List<String> collectionNameList;
//
//        QueryTask(String userName, List<String> collectionNameList, int first, int last) {
//            this.first = first;
//            this.last = last;
//            this.collectionNameList = collectionNameList;
//            this.userName = userName;
//        }
//
//        @Override
//        protected List<KeywordReportEntity> compute() {
//            AppContext.setUser(userName);
//
//            List<KeywordReportEntity> voList = new ArrayList<>();
//            boolean status = (last - first) < threshold;
//            if (status) {
//                MongoTemplate mongoTemplate = BaseMongoTemplate.getUserReportMongo();
//                for (int i = first; i < last; i++) {
//                    voList.addAll(mongoTemplate.findAll(KeywordReportEntity.class, collectionNameList.get(i)));
//                }
//            } else {
//                int middle = (first + last) / 2;
//                QueryTask task1 = new QueryTask(userName, collectionNameList, first, middle);
//                QueryTask task2 = new QueryTask(userName, collectionNameList, middle, last);
//                task1.fork();
//                task2.fork();
//                voList.addAll(task1.join());
//                voList.addAll(task2.join());
//            }
//            return voList;
//        }
//    }
}
