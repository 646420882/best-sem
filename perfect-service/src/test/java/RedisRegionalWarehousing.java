import antlr.InputBuffer;
import com.google.gson.Gson;
import com.perfect.core.AppContext;
import com.perfect.redis.JRedisUtils;
import redis.clients.jedis.Jedis;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by SubDong on 2014/9/17.
 */
public class RedisRegionalWarehousing {

    public static void main(String[] args) throws Exception{
        Jedis jc = JRedisUtils.get();

        String filePath = "F:\\11.txt";
        Map<String,String> map = readTxtFile(filePath);



        String dds = new Gson().toJson(map);
        jc.set("RegionalCodeRedis", dds);
    }


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
}
