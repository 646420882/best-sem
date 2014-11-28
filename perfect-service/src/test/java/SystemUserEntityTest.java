import com.perfect.dao.sys.SystemUserDAO;
import com.perfect.entity.sys.BaiduAccountInfoEntity;
import com.perfect.entity.MD5;
import com.perfect.entity.sys.SystemUserEntity;
import org.junit.Test;
import org.unitils.UnitilsJUnit4;
import org.unitils.spring.annotation.SpringApplicationContext;
import org.unitils.spring.annotation.SpringBeanByName;

/**
 * Created by baizz on 2014-6-19.
 */

@SpringApplicationContext({"spring.xml"})
public class SystemUserEntityTest extends UnitilsJUnit4 {

    private static final String userName = "perfect";

    private static final String passwd = "123";

    @SpringBeanByName
    private SystemUserDAO systemUserDAO;

    @Test
    public void addSysUser() {

//        systemUserDAO.deleteAll();

        SystemUserEntity systemUserEntity = new SystemUserEntity();
        systemUserEntity.setUserName(userName);
        MD5.Builder md5Builder = new MD5.Builder();
        MD5 md5 = md5Builder.password(passwd).salt(userName).build();
        systemUserEntity.setPassword(md5.getMD5());
        systemUserEntity.setAccess(2);

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
