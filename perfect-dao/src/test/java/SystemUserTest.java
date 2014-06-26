import com.perfect.mongodb.dao.SystemUserDAO;
import com.perfect.mongodb.entity.BaiduAccountInfo;
import com.perfect.mongodb.entity.SystemUser;
import org.junit.Test;
import org.unitils.UnitilsJUnit4;
import org.unitils.spring.annotation.SpringApplicationContext;
import org.unitils.spring.annotation.SpringBeanByName;

/**
 * Created by vbzer_000 on 2014/6/19.
 */

@SpringApplicationContext({"spring.xml"})
public class SystemUserTest extends UnitilsJUnit4 {

    @SpringBeanByName
    private SystemUserDAO systemUserDAO;

    @Test
    public void init() {
//        systemUserDAO.deleteAll();

        SystemUser systemUser = new SystemUser();
        systemUser.setUserName("perfect");
        systemUser.setPassword("test");

        BaiduAccountInfo baiduAccountInfo = new BaiduAccountInfo();
        baiduAccountInfo.setBaiduUserName("baidu-bjtthunbohui2134115");
        baiduAccountInfo.setBaiduPassword("Bjhunbohui7");
        baiduAccountInfo.setId(6243012l);
        baiduAccountInfo.setToken("7bf36aed42526553d3c0f055eedca7b5");
        systemUser.addBaiduAccountInfo(baiduAccountInfo);


        baiduAccountInfo = new BaiduAccountInfo();
        baiduAccountInfo.setBaiduUserName("baidu-bjperfrct2131113");
        baiduAccountInfo.setBaiduPassword("Perfect2014");
        baiduAccountInfo.setId(7001963l);
        baiduAccountInfo.setToken("377631ee6dbb31ddcbbc0e929ee5efeb");

        systemUser.addBaiduAccountInfo(baiduAccountInfo);


        systemUserDAO.insert(systemUser);
    }
}
