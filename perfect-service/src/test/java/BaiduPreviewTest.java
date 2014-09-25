import com.perfect.api.baidu.BaiduSpiderHelper;
import com.perfect.api.baidu.BaiduPreviewHelperFactory;
import com.perfect.autosdk.core.ServiceFactory;
import com.perfect.autosdk.exception.ApiException;
import com.perfect.dto.CreativeDTO;
import org.junit.Test;
import org.unitils.UnitilsJUnit4;
import org.unitils.spring.annotation.SpringApplicationContext;
import org.unitils.spring.annotation.SpringBeanByType;

import java.util.List;

/**
 * Created by vbzer_000 on 14-9-18.
 */
@SpringApplicationContext({"spring.xml"})
public class BaiduPreviewTest extends UnitilsJUnit4 {

    @SpringBeanByType
    private BaiduPreviewHelperFactory factory;


    @Test
    public void test() {

        System.out.println("BaiduPreviewHelper.main");


        try {
            ServiceFactory service = ServiceFactory.getInstance("baidu-上品折扣2103914", "SHANGpin8952", "f35d9f818141591cc4fd43ac8e8056b8", null);

            final BaiduSpiderHelper htmlService = factory.createInstance(service);
            for (final String key : new String[]{"车贷", "婚纱照", "手机", "机票", "二手房"}) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < 100; i++) {
                            List<BaiduSpiderHelper.PreviewData> map = htmlService.getPageData(new String[]{key}, 28000);
                            print(map);
                        }
                    }
                }).start();

            }

            Thread.sleep(100000000);
        } catch (ApiException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void print(List<BaiduSpiderHelper.PreviewData> map) {
        for (BaiduSpiderHelper.PreviewData data : map) {

            for (CreativeDTO dto : data.getLeft()) {
                System.out.println(dto);
            }

            for (CreativeDTO dto : data.getRight()) {
                System.out.println(dto);
            }
        }
    }
}
