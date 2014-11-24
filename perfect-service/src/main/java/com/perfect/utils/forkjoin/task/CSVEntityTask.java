package com.perfect.utils.forkjoin.task;

import com.perfect.utils.vo.CSVEntity;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

/**
 * Created by XiaoWei on 2014/8/11.
 */
public class CSVEntityTask extends RecursiveTask<List<CSVEntity>> {
    private static final int threshold = 40000;

    private int first;
    private int last;
    private List<CSVEntity> collectionNameList;

    public CSVEntityTask(int first, int last, List<CSVEntity> collectionNameList) {
        this.first = first;
        this.last = last;
        this.collectionNameList = collectionNameList;
    }

    @Override
    protected List<CSVEntity> compute() {
        List<CSVEntity> csvEntity=new LinkedList<>();
        List<CSVEntity> _newCsvEntity=new LinkedList<>();
        if((last - first) < threshold){
               HashSet h=new HashSet(collectionNameList);
            collectionNameList.clear();
            collectionNameList.addAll(h);
            csvEntity=collectionNameList;
        }else{
            int middle=(last+first)/2;
            CSVEntityTask csvEntityTask =new CSVEntityTask(first,middle,collectionNameList);
            CSVEntityTask csvEntityTask2 =new CSVEntityTask(middle,last,collectionNameList);
            invokeAll(csvEntityTask, csvEntityTask2);
            _newCsvEntity.clear();
            _newCsvEntity=mergeMap(csvEntityTask.join(), csvEntityTask2.join());
        }
        return _newCsvEntity;
    }
    private List<CSVEntity> mergeMap(List<CSVEntity> list1,List<CSVEntity> list2){
        list1.removeAll(list2);
        list1.addAll(list2);
        return list1;
    }
}
