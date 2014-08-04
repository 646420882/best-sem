import com.perfect.dao.KeywordDAO;
import com.perfect.entity.KeywordEntity;
import org.junit.Test;
import org.unitils.UnitilsJUnit4;
import org.unitils.spring.annotation.SpringApplicationContext;
import org.unitils.spring.annotation.SpringBeanByName;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by baizz on 2014-7-7.
 */
//@SpringApplicationContext({"spring.xml"})
public class KeywordEntityTest extends UnitilsJUnit4 {

    //@SpringBeanByName
    private KeywordDAO keywordDAO;

    //@Test
    @SuppressWarnings("unchecked")
    public void testReflect() {

        KeywordEntity keywordEntity = new KeywordEntity();
        keywordEntity.setKeywordId(59659050l);
        keywordEntity.setMatchType(1);
        keywordEntity.setPause(true);
        keywordEntity.setMobileDestinationUrl("http://localhost/keyword/59659050");
        keywordEntity.setPrice(2.d);
        try {
            Class _class = keywordEntity.getClass();
            Field[] fields = _class.getDeclaredFields();
            for (Field field : fields) {
                String fieldName = field.getName();
                StringBuilder fieldGetterName = new StringBuilder("get");
                fieldGetterName.append(fieldName.substring(0, 1).toUpperCase()).append(fieldName.substring(1));
                Method method = _class.getDeclaredMethod(fieldGetterName.toString());
                Object _obj = method.invoke(keywordEntity);
                System.out.println("===" + _obj + "===");
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    //@Test
    public void testInsertKeyword() {
        List<KeywordEntity> list = new ArrayList<>();
        KeywordEntity keywordEntity = new KeywordEntity();
        keywordEntity.setKeywordId(566590590l);
        keywordEntity.setKeyword("北京婚博会时间");
        keywordEntity.setAdgroupId(536421136l);
        keywordEntity.setMatchType(1);
        keywordEntity.setPause(true);
        keywordEntity.setMobileDestinationUrl("http://localhost/keyword/566590590");
        keywordEntity.setPrice(2.5d);
        keywordDAO.insert(keywordEntity);
    }

    //@Test
    public void testModifyKeyword() {
        KeywordEntity keywordEntity = new KeywordEntity();
        keywordEntity.setKeywordId(59659050l);
        keywordEntity.setPrice(3.d);
        keywordDAO.update(keywordEntity);
    }

    //@Test
    public void testUpdateMulti() {
        //将所有包含"北京"的关键词的价格修改为5.00
        keywordDAO.updateMulti("price", "北京", 5.0d);
    }

    //@Test
    public void testDeleteKeyword() {
        KeywordEntity keywordEntity = new KeywordEntity();
        keywordEntity.setKeywordId(56659059l);
        keywordEntity.setKeyword("中国婚博会");
        keywordEntity.setAdgroupId(71656581l);
        keywordEntity.setMatchType(1);
        keywordEntity.setPause(true);
        keywordEntity.setMobileDestinationUrl("http://localhost/keyword/56659059");
        keywordEntity.setPrice(3.5d);
        keywordDAO.delete(keywordEntity);
    }

    //@Test
    public void testQueryKeywordByPage() {
        List<KeywordEntity> list = keywordDAO.find(null, 0, 3);
        for (KeywordEntity entity : list) {
            System.out.println(entity.toString());
        }
    }

    //@Test
    public void testQueryKeyword() {
        KeywordEntity keywordEntity = keywordDAO.findOne(56659059l);
        System.out.println(keywordEntity.toString());
    }

    //@Test
    public void testGetKeywordIdByAdgroupId() {
        List<Long> keywordIds = keywordDAO.getKeywordIdByAdgroupId(71656581l);
        for (Long id : keywordIds) {
            System.out.println("id: " + id);
        }
    }

}
