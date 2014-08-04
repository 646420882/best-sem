import com.perfect.dao.CampaignDAO;
import com.perfect.entity.CampaignEntity;
import org.junit.Test;
import org.unitils.UnitilsJUnit4;
import org.unitils.spring.annotation.SpringApplicationContext;
import org.unitils.spring.annotation.SpringBeanByName;

import java.util.List;

/**
 * Created by baizz on 2014-7-10.
 */
@SpringApplicationContext({"spring.xml"})
public class CampaignEntityTest extends UnitilsJUnit4 {

    @SpringBeanByName
    private CampaignDAO campaignDAO;

    //@Test
    public void testInsertCampaign() {
        CampaignEntity entity = new CampaignEntity();
        entity.setCampaignId(53642112l);
        entity.setCampaignName("推广计划");
        entity.setBudget(1.2d);
        campaignDAO.insert(entity);
    }

    //@Test
    public void testUpdateCampaign() {
        CampaignEntity entity = new CampaignEntity();
        entity.setCampaignId(57191341l);
        entity.setBudget(1.5d);
        campaignDAO.update(entity);
    }

    @Test
    public void testQueryCampaign() {
        List<CampaignEntity> list = campaignDAO.find(null, 0, 5);
        for (CampaignEntity entity : list) {
            System.out.println(entity.toString());
        }
    }

    //@Test
    public void testDeleteCampaign() {
        campaignDAO.deleteById(53642112l);
    }
}
