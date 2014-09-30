import com.google.gson.Gson;
import com.perfect.dao.RegionalCodeDAO;
import com.perfect.dto.RegionalCodeDTO;
import com.perfect.redis.JRedisUtils;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by SubDong on 2014/9/17.
 */
public class RedisRegionalWarehousing {

    @Resource
    private RegionalCodeDAO regionalCodeDAO;


    public static void main(String[] args) throws Exception{
        Jedis jc = JRedisUtils.get();

        String filePath = "F:\\11.txt";
        Map<String,String> map = readTxtFile(filePath);



        String dds = new Gson().toJson(map);
        jc.set("RegionalCodeRedis", dds);
    }


    /**
     * 入redis
     * @param filePath
     * @return
     */
    public static Map<String,String> readTxtFile(String filePath){
        Map<String,String> map = new HashMap<>();
        try {
            String encoding="GBK";
            File file=new File(filePath);
            if(file.isFile() && file.exists()){ //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file),encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while((lineTxt = bufferedReader.readLine()) != null){
                    String a = lineTxt.trim();
                    String[] dd = a.split(" ");
                    map.put(dd[1],dd[0]);
                }
                read.close();
            }else{
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 入库
     * @param filePath
     */
    public void readTxtFileDB(String filePath){
        Map<Integer,String> map = new HashMap<>();
        try {
            String encoding="GBK";
            File file=new File(filePath);
            if(file.isFile() && file.exists()){ //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file),encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while((lineTxt = bufferedReader.readLine()) != null){
                    String a = lineTxt.trim();
                    String[] dd = a.split(" ");
                    map.put(Integer.valueOf(dd[1]),dd[0]);
                }
                List<RegionalCodeDTO> redisList = new ArrayList<>();
                for (Map.Entry<Integer, String> voEntity : map.entrySet()){
                    RegionalCodeDTO regional = new RegionalCodeDTO();
                    int daima = voEntity.getKey();
                    int guojia = daima/100000;
                    regional.setStateId(String.valueOf(guojia));

                    int sheng = (daima % 100000)/1000;

                    int quyu = daima % 1000;

                    if(guojia == 0){
                        regional.setStateId("00");
                        regional.setStateName("中国");
                    }else{
                        regional.setStateId(String.valueOf(guojia));
                        regional.setStateName(map.get(guojia *100000));
                    }

                    if(sheng == 0){
                        regional.setProvinceId("00");
                        regional.setProvinceName("");
                    }else{
                        regional.setProvinceId(String.valueOf(sheng));
                        regional.setProvinceName(map.get(sheng * 1000));
                    }


                    if(quyu == 0){
                        regional.setRegionId("000");
                        regional.setRegionName("");
                    }else{
                        regional.setRegionId(String.valueOf(quyu));
                        regional.setRegionName(map.get(sheng*1000+quyu));
                    }
                    redisList.add(regional);
                }
                regionalCodeDAO.insertRegionalCode(redisList);
                read.close();
            }else{
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
    }
}
