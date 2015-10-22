import com.perfect.utils.MD5;

/**
 * Created by subdong on 15-10-20.
 */
public class Md5Password {
    public static void main(String[] args) {
        MD5.Builder md5Builder = new MD5.Builder();
        MD5 md5 = md5Builder.password("perfect2015").salt("passwd").build();
        System.out.println(md5);
    }
}
