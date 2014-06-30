import com.perfect.dao.SystemUserDAO;
import com.perfect.entity.BaiduAccountInfoEntity;
import com.perfect.entity.SystemUserEntity;
import org.junit.Test;
import org.unitils.UnitilsJUnit4;
import org.unitils.spring.annotation.SpringApplicationContext;
import org.unitils.spring.annotation.SpringBeanByName;

/**
 * Created by vbzer_000 on 2014/6/19.
 */

@SpringApplicationContext({"spring.xml"})
public class SystemUserEntityTest extends UnitilsJUnit4 {

    @SpringBeanByName
    private SystemUserDAO systemUserDAO;

    @Test
    public void init() {
//        systemUserDAO.deleteAll();

        SystemUserEntity systemUserEntity = new SystemUserEntity();
        systemUserEntity.setUserName("perfect");
        systemUserEntity.setPassword("test");

        BaiduAccountInfoEntity baiduAccountInfoEntity = new BaiduAccountInfoEntity();
        baiduAccountInfoEntity.setBaiduUserName("baidu-bjtthunbohui2134115");
        baiduAccountInfoEntity.setBaiduPassword("Bjhunbohui7");
        baiduAccountInfoEntity.setId(6243012l);
        baiduAccountInfoEntity.setToken("7bf36aed42526553d3c0f055eedca7b5");
        systemUserEntity.addBaiduAccountInfo(baiduAccountInfoEntity);


        baiduAccountInfoEntity = new BaiduAccountInfoEntity();
        baiduAccountInfoEntity.setBaiduUserName("baidu-bjperfrct2131113");
        baiduAccountInfoEntity.setBaiduPassword("Perfect2014");
        baiduAccountInfoEntity.setId(7001963l);
        baiduAccountInfoEntity.setToken("377631ee6dbb31ddcbbc0e929ee5efeb");

        systemUserEntity.addBaiduAccountInfo(baiduAccountInfoEntity);


        systemUserDAO.insert(systemUserEntity);
    }
}
