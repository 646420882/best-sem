import com.perfect.commons.deduplication.Md5Helper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Created by dolphineor on 2015-10-20.
 */
public class KDTest {
    public static void main(String[] args) {
        String k0 = "婚博会0";
        String k0MD5 = Md5Helper.MD5.getMD5(k0);
        String k1 = "婚博会";
        String k2 = "婚博会 优";
        int size = 100_00_000;

        final List<String> list = new ArrayList<>(size);
        IntStream.range(0, size).forEach(i -> {
            list.add(k1 + i);
        });
        System.out.println("=================================");

        long l1 = System.currentTimeMillis();
        boolean d1 = list.stream().anyMatch(k0::equals);
        long l2 = System.currentTimeMillis();
        System.out.println(d1 + ", l2 - l1 = " + (l2 - l1));
        long l3 = System.currentTimeMillis();
        boolean d2 = list.stream().anyMatch(k -> k0MD5.equals(Md5Helper.MD5.getMD5(k)));
        long l4 = System.currentTimeMillis();
        System.out.println(d2 + ", l4 - l3 = " + (l4 - l3));

        String k1MD5 = Md5Helper.MD5.getMD5(k1);
        String k2MD5 = Md5Helper.MD5.getMD5(k2);

        System.out.println("k1MD5: " + k1MD5 + "\n" + "k2MD5: " + k2MD5);
    }
}
