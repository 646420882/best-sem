package com.perfect.utils;

import com.perfect.entity.CSVEntity;
import com.perfect.utils.forkjoin.task.CSVEntityTask;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

/**
 * Created by XiaoWei on 2014/8/8.
 */
public class CsvLinkedSupport {

    public static void saveToDB(List<CSVEntity> csvEntityList){
        ForkJoinPool forkJoinPool=new ForkJoinPool();
        List<CSVEntity>  csvEntities=new LinkedList<>();
        CSVEntityTask csvEntityTask =new CSVEntityTask(0,csvEntityList.size(),csvEntityList);
        Future<List<CSVEntity>> result=forkJoinPool.submit(csvEntityTask);
        try {
            csvEntities=result.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }finally {
            forkJoinPool.shutdown();
        }
        for (CSVEntity cs:csvEntities){
            System.out.println(cs.getKeyword()+">>");
        }

    }
    public static   boolean saveToDB(Map<Integer,CSVEntity> csvEntityMap){
        return  false;
    }

    public static void main(String[] agrs){
        List<String> A=new LinkedList<>();
        List<String> B=new LinkedList<>();
        A.add("aaa");
        A.add("bbb");
        B.add("aaa");
        B.add("bbb");
        B.add("ccc");
        A.removeAll(B);
        A.addAll(B);
        for (String s:A){
            System.out.println(s);
        }
    }
}
