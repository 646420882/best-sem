import com.perfect.dao.AdgroupDAO;
import com.perfect.entity.AdgroupEntity;
import org.junit.Test;
import org.unitils.UnitilsJUnit4;
import org.unitils.spring.annotation.SpringApplicationContext;
import org.unitils.spring.annotation.SpringBeanByName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by baizz on 2014-7-9.
 */
@SpringApplicationContext({"spring.xml"})
public class AdgroupEntityTest extends UnitilsJUnit4 {

    @SpringBeanByName
    private AdgroupDAO adgroupDAO;

    //@Test
    public void testInsertAdgroup() {
        AdgroupEntity entity = new AdgroupEntity();
        entity.setAdgroupId(5364257l);
        entity.setAdgroupName("婚博会");
        entity.setCampaignId(53642111l);
        adgroupDAO.insert(entity);
    }

    //@Test
    public void testBatchInsertAdgroup() {
        AdgroupEntity entity = new AdgroupEntity();
        entity.setAdgroupId(53642125l);
        entity.setAdgroupName("中国婚博会");
        entity.setCampaignId(53642112l);
        AdgroupEntity entity1 = new AdgroupEntity();
        entity1.setAdgroupId(536421136l);
        entity1.setAdgroupName("北京婚博会");
        entity1.setCampaignId(53642112l);
        List<AdgroupEntity> list = new ArrayList<>();
        list.add(entity);
        list.add(entity1);
        adgroupDAO.insertAll(list);
    }

    @Test
    public void testQueryAdgroup() {
        List<AdgroupEntity> list = adgroupDAO.find(null, 0, 5);
        for (AdgroupEntity entity : list) {
            System.out.println(entity.toString());
        }
    }

    //@Test
    public void testFindOneAdgroup() {
        AdgroupEntity entity = adgroupDAO.findOne(5364212l);
        System.out.println(entity.toString());
    }

    //@Test
    public void testUpdateAdgroup() {
        AdgroupEntity entity = new AdgroupEntity();
        entity.setAdgroupId(5364212l);
        entity.setMaxPrice(1.0d);
        adgroupDAO.update(entity);
    }

    //@Test
    public void testDeleteAdgroup() {
        adgroupDAO.deleteById(536421136l);
    }
}
