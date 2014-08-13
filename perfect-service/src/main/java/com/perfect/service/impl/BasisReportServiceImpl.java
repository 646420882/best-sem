package com.perfect.service.impl;

import com.perfect.dao.BasisReportDAO;
import com.perfect.entity.StructureReportEntity;
import com.perfect.service.BasisReportService;
import com.perfect.utils.reportUtil.BasisReportDefaultUtil;
import com.perfect.utils.reportUtil.BasistReportPCPlusMobUtil;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

/**
 * Created by SubDong on 2014/8/6.
 */
@Repository("basisReportService")
public class BasisReportServiceImpl implements BasisReportService{
    @Resource
    private BasisReportDAO basisReportDAO;
    /**
     * 获取单元报告
     * @param terminal 推广设备 0、全部  1、PC端 2、移动端
     * @param date 时间
     * @param categoryTime 分类时间  0、默认 1、分日 2、分周 3、分月
     * @return
     */
    public Map<String,List<StructureReportEntity>> getUnitReportDate(String[] date,int terminal,int categoryTime){
        List<StructureReportEntity> objectsList = new ArrayList<>();

        switch (categoryTime){
            case 0:
                Map<String, StructureReportEntity> map = new HashMap<>();
                List<StructureReportEntity> returnList = new ArrayList<>();
                Map<String, List<StructureReportEntity>> listMap = new HashMap<>();
                for (int i=0; i< date.length;i++){
                    List<StructureReportEntity> object = basisReportDAO.getUnitReportDate(date[i]+"-adgroup");
                    objectsList.addAll(object);
                }
                int s = objectsList.size();
                ForkJoinPool joinPool = new ForkJoinPool();
                    try {
                        Future<Map<String, StructureReportEntity>> joinTask = joinPool.submit(new BasisReportDefaultUtil(objectsList,0,objectsList.size()));
                        map = joinTask.get();
                        DecimalFormat df = new DecimalFormat("#.00");
                                for (Map.Entry<String, StructureReportEntity> voEntity : map.entrySet()){
                                    if(voEntity.getValue().getMobileImpression() == 0){
                                voEntity.getValue().setMobileCtr(0.00);
                            }else{
                                voEntity.getValue().setMobileCtr(Double.parseDouble(df.format(voEntity.getValue().getMobileClick().doubleValue() / voEntity.getValue().getMobileImpression().doubleValue())));
                            }
                            if(voEntity.getValue().getMobileClick() == 0){
                                voEntity.getValue().setMobileCpc(0.00);
                            }else{
                                voEntity.getValue().setMobileCpc(Double.parseDouble(df.format(voEntity.getValue().getMobileCost() / voEntity.getValue().getMobileClick().doubleValue())));
                            }

                            if(voEntity.getValue().getPcImpression() == 0){
                                voEntity.getValue().setPcCtr(0.00);
                            }else{
                                voEntity.getValue().setPcCtr(Double.parseDouble(df.format(voEntity.getValue().getPcClick().doubleValue() / voEntity.getValue().getPcImpression().doubleValue())));
                            }
                            if(voEntity.getValue().getPcClick() == 0){
                                voEntity.getValue().setPcCpc(0.00);
                            }else{
                                voEntity.getValue().setPcCpc(Double.parseDouble(df.format(voEntity.getValue().getPcCost() / voEntity.getValue().getPcClick().doubleValue())));
                            }
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                    joinPool.shutdown();
                List<StructureReportEntity> list = new ArrayList<>(map.values());

                if(terminal == 0){
                    Future<List<StructureReportEntity>> joinTask = joinPool.submit(new BasistReportPCPlusMobUtil(objectsList,0,objectsList.size()));
                    try {
                        returnList = joinTask.get();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                    listMap.put(date[0]+"-至-"+date[date.length],list);
                    return listMap;
                }else{
                    listMap.put(date[0]+"-至-"+date[date.length],list);
                    return listMap;
                }
            case 1:
                for (int i=0; i<=date.length;i++){
                    List<StructureReportEntity> object = basisReportDAO.getUnitReportDate(date[i]+"Unit");
                    for (StructureReportEntity o : object){

                    }
                    objectsList.addAll(object);
                }
                if (terminal == 0){

                }
                return null;
            case 2:
                for (int i=0; i<=date.length;i++){
                    List<StructureReportEntity> object = basisReportDAO.getUnitReportDate(date[i]+"Unit");
                    objectsList.addAll(object);
                }
                return null;
            case 3:
                for (int i=0; i<=date.length;i++){
                    List<StructureReportEntity> object = basisReportDAO.getUnitReportDate(date[i]+"Unit");
                    objectsList.addAll(object);
                }
                return null;
        }


        return null;
    }
}
