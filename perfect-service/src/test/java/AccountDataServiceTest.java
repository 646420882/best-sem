import com.perfect.service.AccountDataService;
import org.junit.Test;
import org.unitils.spring.annotation.SpringApplicationContext;
import org.unitils.spring.annotation.SpringBeanByName;

/**
 * Created by yousheng on 2014/8/13.
 *
 * @author yousheng
 */

@SpringApplicationContext({"spring.xml"})
public class AccountDataServiceTest {

    @SpringBeanByName
    private AccountDataService accountDataService;

    @Test
    public void test() {
        accountDataService.initAccountData("perfect");
    }
}
