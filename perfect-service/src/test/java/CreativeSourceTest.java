import com.perfect.entity.CreativeSourceEntity;
import com.perfect.service.CreativeSourceService;
import org.apache.commons.codec.binary.Base64;
import org.junit.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.unitils.UnitilsJUnit4;
import org.unitils.spring.annotation.SpringApplicationContext;
import org.unitils.spring.annotation.SpringBeanByType;
import sun.misc.BASE64Encoder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vbzer_000 on 2014/9/16.
 */
@SpringApplicationContext({"spring.xml"})
public class CreativeSourceTest extends UnitilsJUnit4 {

    @SpringBeanByType
    private CreativeSourceService creativeSourceService;


    @Test
    public void save() {

        List<CreativeSourceEntity> list = new ArrayList<>();
        CreativeSourceEntity entity = new CreativeSourceEntity();
        entity.setTitle("你好吗");
        entity.setBody("我非常的好");

        String code = entity.getTitle() + entity.getBody();
        boolean exist = creativeSourceService.exits(Base64.encodeBase64String(code.getBytes()));
        if (!exist) {
            entity.setId(Base64.encodeBase64String(code.getBytes()));
            list.add(entity);
        }

        entity = new CreativeSourceEntity();
        entity.setTitle("你好吗1");
        entity.setBody("我非常的好2");
        code = entity.getTitle() + entity.getBody();
        exist = creativeSourceService.exits(Base64.encodeBase64String(code.getBytes()));
        if (!exist) {
            entity.setId(Base64.encodeBase64String(code.getBytes()));
            list.add(entity);
        }

        entity = new CreativeSourceEntity();
        entity.setTitle("中国结婚新人首选一站式结婚采购平台 - 中国婚博会官方");
        entity.setBody("中国婚博会官网-中国婚嫁门户网站,在北京,上海,广州,武汉,天津等都成立了分公司,最真实的新人订单点评,最前沿礼服,摄影,婚戒,婚宴,婚庆,婚品等流行款,上万新人...");
        code = entity.getTitle() + entity.getBody();
        exist = creativeSourceService.exits(Base64.encodeBase64String(code.getBytes()));
        if (!exist) {
            entity.setId(Base64.encodeBase64String(code.getBytes()));
            list.add(entity);
        }


        if(list.isEmpty())
            return;
        creativeSourceService.save(list);
    }


    @Test
    public void search() {
        Page<CreativeSourceEntity> page = creativeSourceService.search("结婚", "广州", "", new PageRequest(0, 1));

        System.out.println("page.getTotalElements() = " + page.getTotalElements());
    }
}
