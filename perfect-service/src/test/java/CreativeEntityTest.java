import com.perfect.dao.CreativeDAO;
import com.perfect.entity.CreativeEntity;
import org.junit.Test;
import org.unitils.UnitilsJUnit4;
import org.unitils.spring.annotation.SpringApplicationContext;
import org.unitils.spring.annotation.SpringBeanByName;

import java.util.List;

/**
 * Created by baizz on 2014-7-10.
 */
@SpringApplicationContext({"spring.xml"})
public class CreativeEntityTest extends UnitilsJUnit4 {

    @SpringBeanByName
    private CreativeDAO creativeDAO;

    //@Test
    public void testInsertCreative() {
        CreativeEntity creativeEntity = new CreativeEntity();
        creativeEntity.setCreativeId(636821134l);
        creativeEntity.setAdgroupId(536421136l);
        creativeEntity.setPause(true);
        creativeEntity.setDescription1("Test Creative");
        creativeDAO.insert(creativeEntity);
    }

    //@Test
    public void testUpdateCreative() {
        CreativeEntity creativeEntity = new CreativeEntity();
        creativeEntity.setCreativeId(636821133l);
        creativeEntity.setDescription2("Creative description2");
        creativeDAO.update(creativeEntity);
    }

    @Test
    public void testQueryCreative() {
        List<CreativeEntity> list = creativeDAO.find(null, 0, 5);
        for (CreativeEntity entity : list) {
            System.out.println(entity.toString());
        }
    }

    //@Test
    public void testDeleteCreative() {
        creativeDAO.deleteById(636821134l);
    }
}
